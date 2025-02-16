package logica.pieza;

import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.*;
import logica.interfaz.MovibleDiagonal;
import logica.interfaz.MovibleHorizontal;
import logica.interfaz.MovibleVertical;

import java.util.ArrayList;

/**
 * Esta clase corresponde a la pieza Rey
 * Es una subclase de pieza e implementa las interfaces MovibleVertical, MovibleDiagonal y MovibleHorizontal
 *
 * @author: ACCBM
 **/
public class Rey extends Pieza implements MovibleVertical, MovibleDiagonal, MovibleHorizontal {
    private static final Integer AUMENTO = 1; //es lo que se puede mover el rey

    public Rey(ColorAjedrez color) {
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
            throw new ExcepcionEnMovimientoDiagonal("Movimiento imposible para el rey");
        }
        if (superaSuMovimientoPermitidoEnDiagonal(incrementoEnFila, incrementoEnColumna)) {
            throw new ExcepcionEnMovimientoDiagonal("Movimiento imposible para el Rey porque supera su movimiento permitido");
        }
    }

    @Override
    public void verificarMovimientoEnVertical(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoEnVertical {
        Integer incrementoEnFila = Math.abs(filaLlegada - filaSalida);
        if (quiereMoverseHaciaLosLados(columnaSalida, columnaLlegada)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento vertical imposible para el Rey");
        }
        if (quiereMoverseMasDeLoPermitido(incrementoEnFila)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento imposible para el Rey porque supera su movimiento permitido");
        }
    }

    @Override
    public void verificarMovimientoEnHorizontal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoHorizontal {
        Integer incrementoEnColumna = Math.abs(columnaLlegada - columnaSalida);
        if (quiereMoverseHaciaElFrenteOHaciaAtras(filaSalida, filaLlegada)) {
            throw new ExcepcionEnMovimientoHorizontal("Movimiento horizontal imposible para el Rey");
        }
        if (quiereMoverseMasDeLoPermitido(incrementoEnColumna)) {
            throw new ExcepcionEnMovimientoHorizontal("Movimiento imposible para el Rey porque supera su movimiento permitido");
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionUbicacionFueraDeRango {
        ArrayList<Posicion> posicionesIntermedias = new ArrayList<>();
        return posicionesIntermedias;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesDiagonales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesDiagonales = new ArrayList<>();
        Integer incrementoEnFila = filaLlegada - filaSalida;
        Integer incrementoEnColumna = columnaLlegada - columnaSalida;

        int[] aumentoFilasColumnas = obtenerAumentosFilasColumnasRey(incrementoEnFila, incrementoEnColumna);
        try {
            posicionesDiagonales.add(new Posicion(filaSalida + aumentoFilasColumnas[0], columnaSalida + aumentoFilasColumnas[1]));
        } catch (ExcepcionUbicacionFueraDeRango e) {
        }
        return posicionesDiagonales;
    }

    /**
     * Obtiene los valores que van a aumentar la fila y la columna en los movimientos posibles en diagonal
     * @return arraylist de aumentos
     */
    private int[] obtenerAumentosFilasColumnasRey(Integer incrementoEnFila, Integer incrementoEnColumna) {
        int[] aumentoFilasColumnas = new int[2];
        //El siguiente if comprueba un movimiento diagonal superar derecha
        if (incrementoEnFila > 0 && incrementoEnColumna > 0) {
            aumentoFilasColumnas[0] = AUMENTO;
            aumentoFilasColumnas[1] = AUMENTO;
        }
        //El siguiente if comprueba un movimiento diagonal inferior izquierda
        if (incrementoEnFila < 0 && incrementoEnColumna < 0) {
            aumentoFilasColumnas[0] = -AUMENTO;
            aumentoFilasColumnas[1] = -AUMENTO;
        }
        //El siguiente if comprueba un movimiento diagonal superar izquierda
        if (incrementoEnFila > 0 && incrementoEnColumna < 0) {
            aumentoFilasColumnas[0] = AUMENTO;
            aumentoFilasColumnas[1] = -AUMENTO;

        }
        //El siguiente if comprueba un movimiento diagonal inferior derecha
        if (incrementoEnFila < 0 && incrementoEnColumna > 0) {
            aumentoFilasColumnas[0] = -AUMENTO;
            aumentoFilasColumnas[1] = AUMENTO;
        }
        return aumentoFilasColumnas;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesVerticales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesVerticales = new ArrayList<>();
        if (seQuiereMoverHaciaArriba(filaSalida, filaLlegada)) {
            try {
                posicionesVerticales.add(new Posicion(filaSalida + 1, columnaSalida));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        if (seQuiereMoverHaciaAbajo(filaSalida, filaLlegada)) {
            try {
                posicionesVerticales.add(new Posicion(filaSalida - 1, columnaSalida));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesVerticales;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesHorizontales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesHorizontales = new ArrayList<>();
        if (seQuiereMoverHaciaLaDerecha(columnaSalida, columnaLlegada)) {
            try {
                posicionesHorizontales.add(new Posicion(filaSalida, columnaSalida + 1));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        if (seQuiereMoverHaciaLaIzquierda(columnaSalida, columnaLlegada)) {
            try {
                posicionesHorizontales.add(new Posicion(filaSalida, columnaSalida - 1));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesHorizontales;
    }

    //Métodos abstraídos
    private boolean superaSuMovimientoPermitidoEnDiagonal(Integer incrementoEnFila, Integer incrementoEnColumna) {
        return incrementoEnFila > AUMENTO || incrementoEnColumna > AUMENTO;
    }

    private boolean quiereMoverseHaciaLosLados(Integer columnaSalida, Integer columnaLlegada) {
        return columnaSalida != columnaLlegada;
    }

    private boolean quiereMoverseHaciaElFrenteOHaciaAtras(Integer filaSalida, Integer filaLlegada) {
        return filaSalida != filaLlegada;
    }

    private boolean quiereMoverseMasDeLoPermitido(Integer incrementoEnCasilla) {
        return incrementoEnCasilla > AUMENTO;
    }
}
