package logica.pieza;

import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.*;
import logica.interfaz.*;

import java.util.ArrayList;

/**
 * Clase correspondiente a la pieza Reina
 * Subclase de Pieza e implementa MovibleEnVertical, MovibleEnHorizontal y MovibleEnDiagonal
 *
 * @author: ACCBM
 */
public class Reina extends Pieza implements MovibleVertical, MovibleDiagonal, MovibleHorizontal {

    public Reina(ColorAjedrez color) {
        super(color);
    }

    @Override
    public void esMovible(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionEnMovimiento {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();
        if (!coinciden(filaDeSalida, filaDeLlegada) && !coinciden(columnaDeSalida, columnaDeLlegada)) {
            verificarMovimientoEnDiagonal(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        } else if (!coinciden(filaDeSalida, filaDeLlegada)) {
            verificarMovimientoEnVertical(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        } else if (!coinciden(columnaDeSalida, columnaDeLlegada)) {
            verificarMovimientoEnHorizontal(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        }
    }

    @Override
    public void verificarMovimientoEnDiagonal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoDiagonal {
        Integer incrementoEnFila = Math.abs(filaLlegada - filaSalida);
        Integer incrementoEnColumna = Math.abs(columnaLlegada - columnaSalida);
        if (!coinciden(incrementoEnFila, incrementoEnColumna)) {
            throw new ExcepcionEnMovimientoDiagonal("Movimiento diagonal imposible para la reina");
        }
    }

    /**
     * @see #coinciden(Integer, Integer)
     */
    @Override
    public void verificarMovimientoEnVertical(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoEnVertical {
        if (!coinciden(columnaSalida, columnaLlegada)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento vertical imposible para la reina");
        }
    }

    @Override
    public void verificarMovimientoEnHorizontal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoHorizontal {
        if (!coinciden(filaSalida, filaLlegada)) {
            throw new ExcepcionEnMovimientoHorizontal("Movimiento horizontal imposible para la reina");
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion posicionDeSalida, Posicion posicionDeLlegada) {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        ArrayList<Posicion> posicionesIntermediasDeLaReina = new ArrayList<>();
        if (coinciden(filaDeSalida, filaDeLlegada)) {
            posicionesIntermediasDeLaReina.addAll(obtenerPosicionesHorizontales(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada));
        } else if (coinciden(columnaDeSalida, columnaDeLlegada)) {
            posicionesIntermediasDeLaReina.addAll(obtenerPosicionesVerticales(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada));
        } else {
            posicionesIntermediasDeLaReina.addAll(obtenerPosicionesDiagonales(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada));
        }
        return posicionesIntermediasDeLaReina;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesDiagonales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesPosiblesDiagonales = new ArrayList<>();
        Integer incrementoEnFila = filaLlegada - filaSalida;
        Integer incrementoEnColumna = columnaLlegada - columnaSalida;

        int[][] aumentoFilasColumnas = obtenerAumentosFilasColumnas(incrementoEnFila, incrementoEnColumna);

        for (int i = 0; i < Math.abs(filaLlegada - filaSalida) - 1; i++) {
            try {
                posicionesPosiblesDiagonales.add(new Posicion(filaSalida + aumentoFilasColumnas[0][i], columnaSalida + aumentoFilasColumnas[1][i]));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesPosiblesDiagonales;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesVerticales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesPosiblesVerticales = new ArrayList<>();
        //Este if verifica si la pieza se mueve hacia arriba
        if (seQuiereMoverHaciaArriba(filaSalida, filaLlegada)) {
            for (int i = filaSalida + 1; i < filaLlegada; i++) {
                try {
                    posicionesPosiblesVerticales.add(new Posicion(i, columnaSalida));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        //Este if verifica si la pieza se mueve hacia abajo
        if (seQuiereMoverHaciaAbajo(filaSalida, filaLlegada)) {
            for (int i = filaSalida - 1; i > filaLlegada; i--) {
                try {
                    posicionesPosiblesVerticales.add(new Posicion(i, columnaSalida));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        return posicionesPosiblesVerticales;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesHorizontales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesPosiblesHorizontales = new ArrayList<>();
        int columna1;
        int columna2;
        //Este if verifica si la pieza se mueve a la derecha
        if (seQuiereMoverHaciaLaDerecha(columnaSalida, columnaLlegada)) {
            for (int i = columnaSalida + 1; i < columnaLlegada; i++) {
                try {
                    posicionesPosiblesHorizontales.add(new Posicion(filaSalida, i));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        //Este if verifica si la pieza se mueve a la izquierda
        if (seQuiereMoverHaciaLaIzquierda(columnaSalida, columnaLlegada)) {
            for (int i = columnaSalida - 1; i > columnaLlegada; i--) {
                try {
                    posicionesPosiblesHorizontales.add(new Posicion(filaSalida, i));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        return posicionesPosiblesHorizontales;
    }
}
