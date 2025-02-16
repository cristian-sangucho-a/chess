package logica.pieza;

import logica.Celda;
import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.ExcepcionAjedrez;
import logica.exception.ExcepcionUbicacionFueraDeRango;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pieza implements Serializable {
    protected ColorAjedrez color;
    protected Celda celda;

    public Pieza(ColorAjedrez color) {
        this.color = color;
    }

    public ColorAjedrez getColor() {
        return color;
    }

    public void setCelda(Celda celda) {
        this.celda = celda;
    }

    public void borrarCelda() {
        celda = null;
    }

    public abstract void esMovible(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionAjedrez;

    /**
     * Con los parametros recogidos de posiciones, calcula las posiciones intermedias de las casillas para un movimiento
     */
    public abstract ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionUbicacionFueraDeRango;

    //__________________________________________________________________________________________________________________
    protected boolean coinciden(Integer valorUno, Integer ValorDos) {
        return valorUno.equals(ValorDos);
    }

    protected boolean seQuiereMoverHaciaAbajo(Integer filaSalida, Integer filaLlegada) {
        return filaLlegada < filaSalida;
    }

    protected boolean seQuiereMoverHaciaArriba(Integer filaSalida, Integer filaLlegada) {
        return filaLlegada > filaSalida;
    }

    protected boolean seQuiereMoverHaciaLaIzquierda(Integer columnaSalida, Integer columnaLlegada) {
        return columnaLlegada < columnaSalida;
    }

    protected boolean seQuiereMoverHaciaLaDerecha(Integer columnaSalida, Integer columnaLlegada) {
        return columnaLlegada > columnaSalida;
    }

    protected int[][] obtenerAumentosFilasColumnas(Integer incrementoEnFila, Integer incrementoEnColumna) {
        int[][] aumentoFilasColumnas = new int[2][Math.abs(incrementoEnFila) - 1];
        int casilla = 1;
        //El siguiente if comprueba un movimiento diagonal superar derecha
        if (incrementoEnFila > 0 && incrementoEnColumna > 0) {
            for (int aux = 0; aux < Math.abs(incrementoEnFila) - 1; aux++) {
                aumentoFilasColumnas[0][aux] = casilla;
                aumentoFilasColumnas[1][aux] = casilla;
                casilla++;
            }
        }
        //El siguiente if comprueba un movimiento diagonal inferior izquierda
        if (incrementoEnFila < 0 && incrementoEnColumna < 0) {
            for (int aux = 0; aux < Math.abs(incrementoEnFila) - 1; aux++) {
                aumentoFilasColumnas[0][aux] = -casilla;
                aumentoFilasColumnas[1][aux] = -casilla;
                casilla++;
            }
        }
        //El siguiente if comprueba un movimiento diagonal superar izquierda
        if (incrementoEnFila > 0 && incrementoEnColumna < 0) {
            for (int aux = 0; aux < Math.abs(incrementoEnFila) - 1; aux++) {
                aumentoFilasColumnas[0][aux] = casilla;
                aumentoFilasColumnas[1][aux] = -casilla;
                casilla++;
            }
        }
        //El siguiente if comprueba un movimiento diagonal inferior derecha
        if (incrementoEnFila < 0 && incrementoEnColumna > 0) {
            for (int aux = 0; aux < Math.abs(incrementoEnFila) - 1; aux++) {
                aumentoFilasColumnas[0][aux] = -casilla;
                aumentoFilasColumnas[1][aux] = casilla;
                casilla++;
            }
        }
        return aumentoFilasColumnas;
    }

}