package maquina1995.mockito.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import maquina1995.mockito.dominio.Heroe;
import maquina1995.mockito.dominio.Monstruo;

/**
 * Clase que contiene la l�gica asociada a la mazmorra donde entra el
 * {@link Heroe}
 * 
 * @author MaQuiNa1995
 *
 */
@Service
public class MazmorraServiceImpl implements MazmorraService {

    private static final Logger LOGGER = Logger.getLogger(MazmorraServiceImpl.class.getName());

    /**
     * Objeto usado para generar y recuperar monstruos
     */
    @Autowired
    private MonstruoService monstruoService;

    @Override
    public Heroe entrarMazmorra(Heroe heroe) {

	Monstruo monstruo = monstruoService.cogerMounstruoRandom();

	combatir(heroe, monstruo);

	subirExperiencia(heroe, monstruo.getExperiencia());

	return heroe;
    }

    /**
     * M�todo usado para subir la experiencia del h�roe si está vivo
     * 
     * @param heroe               {@link Heroe} objeto que representa al heroe
     * @param experienciaMonstruo entero que representa la experiencia que gana el
     *                            h�roe en caso de que est� vivo
     * 
     */
    private void subirExperiencia(Heroe heroe, int experienciaMonstruo) {

	if (heroe.getVida() >= 1) {
	    int experienciaSumada = heroe.getExperiencia() + experienciaMonstruo;
	    heroe.setExperiencia(experienciaSumada);
	}
    }

    /**
     * M�todo que representa el combate entre el {@link Heroe} y un {@link Monstruo}
     * 
     * @param heroe    {@link Heroe} objeto que representa al heroe
     * @param monstruo {@link Monstruo} objeto que representa al h�roe
     * 
     * @return booleano que representa el exito o no del h�roe en el combate
     */
    private boolean combatir(Heroe heroe, Monstruo monstruo) {

	LOGGER.info("Empieza el combate entre " + heroe.getNombre() + " y " + monstruo.getNombre());

	// Marcamos un booleano para indicar si el h�roe es vencedor
	boolean heroeVencedor = Boolean.FALSE;

	// Creamos un booleano para indicar si se ha muerto el h�roe o el monstruo
	boolean isAlgunoMuerto = Boolean.FALSE;

	// bucle que simula la pelea entre el heroe y el mounstruo
	do {

	    // El h�roe ataca primero
	    int vidaRestanteMounstruo = monstruo.getVida() - heroe.getDanno();
	    monstruo.setVida(vidaRestanteMounstruo);

	    LOGGER.info("El h�roe ataca, vida restante del monstruo: " + vidaRestanteMounstruo);

	    // si el monstruo ha muerto no puede atacar
	    if (vidaRestanteMounstruo > 0) {
		// hacemos el calculo de la vida que le queda al
		int vidaRestanteHeroe = heroe.getVida() - monstruo.getDanno();
		heroe.setVida(vidaRestanteHeroe);

		LOGGER.info("El " + monstruo.getNombre() + " ataca, vida restante del h�roe: " + vidaRestanteHeroe);
	    } else {
		// como el mounstruo ha muerto marcamos que el h�roe es vencedor
		heroeVencedor = Boolean.TRUE;
	    }

	    isAlgunoMuerto = monstruo.getVida() < 1 || heroe.getVida() < 1;

	} while (isAlgunoMuerto == Boolean.FALSE);

	LOGGER.info("---------------------------");

	return heroeVencedor;
    }

}
