package logica.pieza;

import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.*;
import logica.interfaz.*;

import java.util.ArrayList;

/**
 * Esta clase corresponde a la pieza peon
 * Es una subclase de pieza e implementa las interfaces MovibleVertical y MovibleDiagonal
 *
 * @author: ACCBM
 **/

public class Peon extends Pieza implements MovibleVertical, MovibleDiagonal {
    private int cantidadDeMovimientosRealizados;
    private static final int CASILLA_PERMITIDA = 1;

    public Peon(ColorAjedrez color) {
        super(color);
    }

    @Override
    public void esMovible(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionEnMovimientoEnVertical {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        verificarMovimientoEnVertical(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        cantidadDeMovimientosRealizados++;
    }

    /**
     * Al comer de manera distinta a su movimiento, se debe evaluar de que forma come el peón aparte
     */
    public void puedeComer(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionEnMovimientoDiagonal {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        verificarMovimientoEnDiagonal(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        cantidadDeMovimientosRealizados++;
    }

    /**
     * Debido a su movimiento el peón no devuelve posiciones intermedias
     */
    @Override
    public ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion posicionDeSalida, Posicion posicionDeLlegada) {
        ArrayList<Posicion> posicionesIntermedias = new ArrayList<>();
        return posicionesIntermedias;
    }

    @Override
    public void verificarMovimientoEnVertical(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoEnVertical {
        if (quiereMoverseHaciaLosLados(columnaSalida, columnaLlegada)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento no permitido para el peon");
        }
        if (quiereRetroceder(filaSalida, filaLlegada)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento no permitido, el peon no puede retroceder");
        }
        if (quiereAvanzarMasDeLoPermitido(filaLlegada, filaSalida)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento fuera del alcance del peon");
        }
    }

    private boolean quiereRetroceder(Integer filaSalida, Integer filaLlegada) {
        if (color != ColorAjedrez.CLARO) {
            return filaSalida < filaLlegada;
        }
        return filaSalida > filaLlegada;
    }

    /**
     * Este metodo sirve para verificar si el peon puede moverse 2 o 1 casillas en sus movimiento
     */
    private boolean quiereAvanzarMasDeLoPermitido(Integer filaLlegada, Integer filaSalida) {
        int casillaPermitida = obtenerCasillaPermitida();
        if (color != ColorAjedrez.CLARO) {
            return filaLlegada < (filaSalida - casillaPermitida);
        }
        return filaLlegada > (filaSalida + casillaPermitida);
    }

    private boolean quiereMoverseHaciaLosLados(Integer columnaSalida, Integer columnaLlegada) {
        return columnaSalida != columnaLlegada;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesVerticales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesIntermediasVerticales = new ArrayList<>();
        int casillaPermitida = obtenerCasillaPermitida();
        for (int aumentoEnCasilla = 1; aumentoEnCasilla <= casillaPermitida; aumentoEnCasilla++) {
            try {
                posicionesIntermediasVerticales.add(new Posicion(filaSalida + aumentoEnCasilla, columnaSalida));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesIntermediasVerticales;
    }

    /**
     * Devuelve el numero de casillas que se puede mover el peon, este valor cambia despues de su primer movimiento
     */
    private int obtenerCasillaPermitida() {
        int casillaPermitida = CASILLA_PERMITIDA;
        if (cantidadDeMovimientosRealizados == 0) {
            casillaPermitida = 2;
        }
        return casillaPermitida;
    }

    @Override
    public void verificarMovimientoEnDiagonal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoDiagonal {
        int columnaDeLlegadaEsperada = obtenerColumnaPermitida(columnaLlegada, columnaSalida);
        int filaLlegadaEsperada = obtenerFilaPermitida(filaSalida);

        if (quiereRetroceder(filaSalida, filaLlegada)) {
            throw new ExcepcionEnMovimientoDiagonal("El peon solamente come en diagonal, mientras avanza");
        }
        if (!coinciden(columnaLlegada, columnaDeLlegadaEsperada) || !coinciden(filaLlegada, filaLlegadaEsperada)) {
            throw new ExcepcionEnMovimientoDiagonal("El peon solamente come en diagonal 1x1");
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesDiagonales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesIntermediasDiagonales = new ArrayList<>();
        Integer incrementoEnFila = filaLlegada - filaSalida;
        Integer incrementoEnColumna = columnaLlegada - columnaSalida;
        int casillaPermitida = obtenerAvance(incrementoEnFila, incrementoEnColumna);
        try {
            posicionesIntermediasDiagonales.add(new Posicion(filaSalida + CASILLA_PERMITIDA, columnaSalida + casillaPermitida));
        } catch (ExcepcionUbicacionFueraDeRango e) {
        }
        return posicionesIntermediasDiagonales;
    }

    private int obtenerAvance(int incrementoEnFila, int incrementoEnColumna) {
        int casillaPermitida = CASILLA_PERMITIDA;
        if (incrementoEnFila > 0 && incrementoEnColumna < 0) {
            casillaPermitida = -1;
        }
        return casillaPermitida;
    }

    /**
     * Entrega la columna a la que el peon se puede mover cuando quiere comer una pieza
     *
     * @return columna a la que se puede mover en diagonal
     */
    private int obtenerColumnaPermitida(Integer columnaLlegada, Integer columnaSalida) {
        if (seQuiereMoverHaciaLaIzquierda(columnaSalida, columnaLlegada)) {
            return columnaSalida - CASILLA_PERMITIDA;
        }
        return columnaSalida + CASILLA_PERMITIDA;
    }

    /**
     * Entrega la fila a la que el peon se puede mover cuando quiere comer una pieza
     *
     * @return fila a la que se puede mover en diagonal
     */
    private int obtenerFilaPermitida(Integer filaSalida) {
        if (color != ColorAjedrez.CLARO) {
            return (filaSalida - CASILLA_PERMITIDA);
        }
        return (filaSalida + CASILLA_PERMITIDA);
    }
}