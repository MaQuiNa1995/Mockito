package maquina1995.mockito.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import maquina1995.mockito.dominio.Heroe;
import maquina1995.mockito.dominio.Monstruo;

/**
 * Clase que contiene la lógica asociada a la mazmorra donde entra el
 * {@link Heroe}
 * 
 * @author MaQuiNa1995
 *
 */
@Service
public class MazmorraServiceImpl implements MazmorraService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MazmorraServiceImpl.class);

	/**
	 * Objeto usado para generar y recuperar monstruos
	 */
	@Autowired
	private MonstruoService monstruoService;

	@Override
	public Heroe entrarMazmorra(Heroe heroe) {

		Monstruo monstruo = monstruoService.cogerMonstruoRandom();

		this.combatir(heroe, monstruo);
		this.subirExperiencia(heroe, monstruo.getExperiencia());

		return heroe;
	}

	/**
	 * Método usado para subir la experiencia al héroe
	 * 
	 * @param heroe               {@link Heroe} objeto que representa al heroe
	 * @param experienciaMonstruo entero que representa la experiencia que gana el
	 *                            héroe en caso de estar vivo
	 * 
	 */
	private void subirExperiencia(Heroe heroe, int experienciaMonstruo) {

		if (heroe.getVida() >= 1) {
			int experienciaSumada = heroe.getExperiencia() + experienciaMonstruo;
			heroe.setExperiencia(experienciaSumada);
		}
	}

	/**
	 * Método que representa la lucha entre un {@link Heroe} y un {@link Monstruo}
	 * 
	 * @param heroe    {@link Heroe} objeto que representa al heroe
	 * @param monstruo {@link Monstruo} objeto que representa al héroe
	 * 
	 * @return booleano que representa el exito o no del héroe en el combate
	 */
	private boolean combatir(Heroe heroe, Monstruo monstruo) {

		LOGGER.info("Empieza el combate entre {} y {}", heroe.getNombre(), monstruo.getNombre());

		// Marcamos un booleano para indicar si el héroe es vencedor
		boolean heroeVencedor = false;

		// Creamos un booleano para indicar si se ha muerto el héroe o el monstruo
		boolean isAlgunoMuerto = false;

		// bucle que simula la pelea entre el heroe y el mounstruo
		do {

			// El héroe ataca primero
			int vidaRestanteMounstruo = monstruo.getVida() - heroe.getDanno();
			monstruo.setVida(vidaRestanteMounstruo);

			LOGGER.info("El héroe ataca, vida restante del monstruo: {}", vidaRestanteMounstruo);

			// si el monstruo ha muerto no puede atacar
			if (vidaRestanteMounstruo > 0) {
				// hacemos el calculo de la vida que le queda al
				int vidaRestanteHeroe = heroe.getVida() - monstruo.getDanno();
				heroe.setVida(vidaRestanteHeroe);

				LOGGER.info("El  {} ataca, vida restante del héroe: {} ", monstruo.getNombre(), vidaRestanteHeroe);
			} else {
				// como el mounstruo ha muerto marcamos que el héroe es vencedor
				heroeVencedor = Boolean.TRUE;
			}

			isAlgunoMuerto = monstruo.getVida() < 1 || heroe.getVida() < 1;

		} while (!isAlgunoMuerto);

		LOGGER.info("---------------------------");

		return heroeVencedor;
	}

}
