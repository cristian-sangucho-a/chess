package logica.pieza;

import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.ExcepcionEnMovimientoEnL;
import logica.exception.ExcepcionUbicacionFueraDeRango;
import logica.interfaz.MovibleEnL;

import java.lang.Integer;

import java.util.ArrayList;

/**
 * Clase correspondiente a la pieza Caballo
 *  Subclase de Pieza e implementa MovibleEnL
 *  @autor: ACCBM
 */
public class Caballo extends Pieza implements MovibleEnL {
    private static final Integer AUMENTO_LARGO = 2;
    private static final Integer AUMENTO_CORTO = 1;

    public Caballo(ColorAjedrez color) {
        super(color);
    }

    @Override
    public void esMovible(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionEnMovimientoEnL {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();
        verificarMovimientoEnL(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
    }

    public void verificarMovimientoEnL(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoEnL {
        Integer[] posicionesFila = {filaSalida + AUMENTO_LARGO, filaSalida + AUMENTO_LARGO, filaSalida + AUMENTO_CORTO, filaSalida + AUMENTO_CORTO,
                filaSalida - AUMENTO_CORTO, filaSalida - AUMENTO_CORTO, filaSalida - AUMENTO_LARGO, filaSalida - AUMENTO_LARGO};
        Integer[] posicionesColumna = {columnaSalida - AUMENTO_CORTO, columnaSalida + AUMENTO_CORTO, columnaSalida - AUMENTO_LARGO, columnaSalida + AUMENTO_LARGO,
                columnaSalida - AUMENTO_LARGO, columnaSalida + AUMENTO_LARGO, columnaSalida - AUMENTO_CORTO, columnaSalida + AUMENTO_CORTO};
        int auxiliar = 0;
        for (int m = 0; m < posicionesFila.length; m++) {
            if (filaLlegada == posicionesFila[m] && columnaLlegada == posicionesColumna[m]) {
                auxiliar++;
            }
        }
        if (auxiliar == 0) {
            throw new ExcepcionEnMovimientoEnL("Movimiento imposible para el caballo");
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion FilaDeSalida, Posicion columnaSalida) {
        ArrayList<Posicion> posicionesTemporales = new ArrayList<>();
        return posicionesTemporales;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesEnL(Integer filaSalida, Integer columnaSalida)  {
        ArrayList<Posicion> posicionesPosiblesParaUnMovimientoEnL = new ArrayList<>();
        Integer[] posicionesFila = {filaSalida + AUMENTO_LARGO, filaSalida + AUMENTO_LARGO, filaSalida + AUMENTO_CORTO, filaSalida + AUMENTO_CORTO,
                filaSalida - AUMENTO_CORTO, filaSalida - AUMENTO_CORTO, filaSalida - AUMENTO_LARGO, filaSalida - AUMENTO_LARGO};
        Integer[] posicionesColumna = {columnaSalida - AUMENTO_CORTO, columnaSalida + AUMENTO_CORTO, columnaSalida - AUMENTO_LARGO, columnaSalida + AUMENTO_LARGO,
                columnaSalida - AUMENTO_LARGO, columnaSalida + AUMENTO_LARGO, columnaSalida - AUMENTO_CORTO, columnaSalida + AUMENTO_CORTO};

        for (int i = 0; i < posicionesFila.length; i++) {
            try {
                posicionesPosiblesParaUnMovimientoEnL.add(new Posicion(posicionesFila[i], posicionesColumna[i]));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesPosiblesParaUnMovimientoEnL;
    }
}


