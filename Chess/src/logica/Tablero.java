package logica;

import logica.color.ColorAjedrez;
import logica.pieza.*;

import java.io.Serializable;

/**
 * Representacion de un tablero, mediante un array bidimensional de la clase Celda
 * @author ACCBM
 */
public class Tablero implements Serializable {
    private final int CANTIDAD_DE_CELDAS_POR_EJE = 8;
    private Celda[][] celdas;

    public Tablero() {
        this.celdas = new Celda[CANTIDAD_DE_CELDAS_POR_EJE][CANTIDAD_DE_CELDAS_POR_EJE];
        inicializarCeldas();
        ordenarPiezas();
    }

    /**
     * Instancia todas las celdas del tablero
     */
    private void inicializarCeldas() {
        for (int fila = 0; fila < CANTIDAD_DE_CELDAS_POR_EJE; fila++) {
            for (int columna = 0; columna < CANTIDAD_DE_CELDAS_POR_EJE; columna++) {
                celdas[fila][columna] = new Celda();
            }
        }
    }

    /**
     * Coloca las piezas segun las posiciones iniciales del ajedrez
     */
    private void ordenarPiezas() {
        ColorAjedrez colorRetador = ColorAjedrez.CLARO;
        ColorAjedrez colorOponente = ColorAjedrez.OBSCURO;
        //Peones
        for (int columna = 0; columna < CANTIDAD_DE_CELDAS_POR_EJE; columna++) {
            celdas[1][columna].setPieza(new Peon(colorRetador));
            celdas[CANTIDAD_DE_CELDAS_POR_EJE - 2][columna].setPieza(new Peon(colorOponente));
        }
        //Torres
        celdas[0][0].setPieza(new Torre(colorRetador));
        celdas[0][7].setPieza(new Torre(colorRetador));
        celdas[7][0].setPieza(new Torre(colorOponente));
        celdas[7][7].setPieza(new Torre(colorOponente));

        //caballos
        celdas[0][1].setPieza(new Caballo(colorRetador));
        celdas[0][6].setPieza(new Caballo(colorRetador));
        celdas[7][1].setPieza(new Caballo(colorOponente));
        celdas[7][6].setPieza(new Caballo(colorOponente));

        //Alfiles
        celdas[0][2].setPieza(new Alfil(colorRetador));
        celdas[0][5].setPieza(new Alfil(colorRetador));
        celdas[7][2].setPieza(new Alfil(colorOponente));
        celdas[7][5].setPieza(new Alfil(colorOponente));

        //Reinas
        celdas[0][3].setPieza(new Reina(colorRetador));
        celdas[7][3].setPieza(new Reina(colorOponente));

        //Reyes
        celdas[0][4].setPieza(new Rey(colorRetador));
        celdas[7][4].setPieza(new Rey(colorOponente));
    }

    public Celda[][] getCeldas() {
        return celdas;
    }

    public void enviarCeldas(Celda[][] celdas) {
        this.celdas = celdas;
    }
}