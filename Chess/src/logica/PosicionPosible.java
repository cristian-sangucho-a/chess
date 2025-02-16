package logica;

import logica.color.ColorAjedrez;
import logica.exception.*;
import logica.interfaz.*;
import logica.pieza.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Permite obtener las posiciones posibles de una pieza dentro de un rango
 *
 * @author ACCBM
 */

public class PosicionPosible implements Serializable {
    private static final int VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO = 8;
    private static final int VALOR_INFERIOR_A_CERO = -1;

    /**
     * Devuelve las posiciones en las que la pieza se puede mover
     *
     * @param piezaACalcularPosiciones
     * @param posicionDeLlegada
     * @param celdas
     * @return ArrayList de posiciones pposibles
     */
    public static ArrayList<Posicion> obtenerLasPosicionesPosibles(Pieza piezaACalcularPosiciones, Posicion posicionDeLlegada, Celda[][] celdas) throws ExcepcionUbicacionFueraDeRango {
        ColorAjedrez colorDeLaPieza = piezaACalcularPosiciones.getColor();
        int filaDeSalida = posicionDeLlegada.getFila();
        int columnaDeSalida = posicionDeLlegada.getColumna();
        ArrayList<Posicion> posicionesPosibles = new ArrayList<>();

        //Los siguientes 5 if's calculan las posiciones posibles en cada tipo de movimiento
        //El peon es un caso especial porque hace diferentes casos dependiendo del color de pieza
        if (piezaACalcularPosiciones instanceof Peon) {
            // El peon negro y el blanco igualmente son casos distintos
            //Este if es para el peon negro
            if (colorDeLaPieza != ColorAjedrez.CLARO) {
                posicionDeLlegada = posicionDeLlegada.obtenerEquivalente();
                filaDeSalida = posicionDeLlegada.getFila();
                columnaDeSalida = posicionDeLlegada.getColumna();

                posicionesPosibles.addAll(descartarPosicionesNoPosiblesPeon(equivalentes(((Peon) piezaACalcularPosiciones).obtenerPosicionesVerticales(filaDeSalida, columnaDeSalida, VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO, columnaDeSalida)), celdas));
                posicionesPosibles.addAll(descartarPosicionesNoPosiblesDiagonales(equivalentes(extraerPosicionPosiblesDiagonalSuperiorDerecha(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida)), celdas, colorDeLaPieza));
                posicionesPosibles.addAll(descartarPosicionesNoPosiblesDiagonales(equivalentes(extraerPosicionPosiblesDiagonalSuperiorIzquierda(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida)), celdas, colorDeLaPieza));
                return posicionesPosibles;
            }

            //Lineas para el peon blanco
            posicionesPosibles.addAll(descartarPosicionesNoPosiblesPeon(((Peon) piezaACalcularPosiciones).obtenerPosicionesVerticales(filaDeSalida, columnaDeSalida, VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO, columnaDeSalida), celdas));
            posicionesPosibles.addAll(descartarPosicionesNoPosiblesDiagonales(extraerPosicionPosiblesDiagonalSuperiorDerecha(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
            posicionesPosibles.addAll(descartarPosicionesNoPosiblesDiagonales(extraerPosicionPosiblesDiagonalSuperiorIzquierda(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
            return posicionesPosibles;
        }

        if (piezaACalcularPosiciones instanceof MovibleHorizontal) {
            posicionesPosibles.addAll(descartarPosicionesNoPosibles(((MovibleHorizontal) piezaACalcularPosiciones).obtenerPosicionesHorizontales(filaDeSalida, columnaDeSalida, filaDeSalida, VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO), celdas, colorDeLaPieza));
            posicionesPosibles.addAll(descartarPosicionesNoPosibles(((MovibleHorizontal) piezaACalcularPosiciones).obtenerPosicionesHorizontales(filaDeSalida, columnaDeSalida, filaDeSalida, VALOR_INFERIOR_A_CERO), celdas, colorDeLaPieza));
        }

        if (piezaACalcularPosiciones instanceof MovibleDiagonal) {
            obtenerPosicionesPosiblesDiagonales(piezaACalcularPosiciones, celdas, colorDeLaPieza, filaDeSalida, columnaDeSalida, posicionesPosibles);
        }

        if (piezaACalcularPosiciones instanceof MovibleVertical) {
            posicionesPosibles.addAll(descartarPosicionesNoPosibles(((MovibleVertical) piezaACalcularPosiciones).obtenerPosicionesVerticales(filaDeSalida, columnaDeSalida, VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO, columnaDeSalida), celdas, colorDeLaPieza));
            posicionesPosibles.addAll(descartarPosicionesNoPosibles(((MovibleVertical) piezaACalcularPosiciones).obtenerPosicionesVerticales(filaDeSalida, columnaDeSalida, VALOR_INFERIOR_A_CERO, columnaDeSalida), celdas, colorDeLaPieza));
        }

        if (piezaACalcularPosiciones instanceof MovibleEnL) {
            posicionesPosibles.addAll(descartarPosicionesNoPosiblesSinPiezasDelMismoColor(((MovibleEnL) piezaACalcularPosiciones).obtenerPosicionesEnL(filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
        }

        return posicionesPosibles;
    }

    private static void obtenerPosicionesPosiblesDiagonales(Pieza piezaACalcularPosiciones, Celda[][] celdas, ColorAjedrez colorDeLaPieza, int filaDeSalida, int columnaDeSalida, ArrayList<Posicion> posicionesPosibles) throws ExcepcionUbicacionFueraDeRango {
        posicionesPosibles.addAll(descartarPosicionesNoPosibles(extraerPosicionPosiblesDiagonalSuperiorDerecha(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
        posicionesPosibles.addAll(descartarPosicionesNoPosibles(extraerPosicionPosiblesDiagonalInferiorDerecha(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
        posicionesPosibles.addAll(descartarPosicionesNoPosibles(extraerPosicionPosiblesDiagonalSuperiorIzquierda(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
        posicionesPosibles.addAll(descartarPosicionesNoPosibles(extraerPosicionPosiblesDiagonalInferiorIzquierda(piezaACalcularPosiciones, filaDeSalida, columnaDeSalida), celdas, colorDeLaPieza));
    }


    private static ArrayList<Posicion> descartarPosicionesNoPosiblesPeon(ArrayList<Posicion> listaDePosiciones, Celda[][] celdas) {
        ArrayList<Posicion> posicionesPosibles = new ArrayList<>();

        for (Posicion posicion : listaDePosiciones) {
            Pieza piezaEnLaPosicion = celdas[posicion.getFila()][posicion.getColumna()].getPieza();
            if (piezaEnLaPosicion == null) {
                posicionesPosibles.add(posicion);
            }
        }
        return posicionesPosibles;
    }

    private static ArrayList<Posicion> descartarPosicionesNoPosiblesDiagonales(ArrayList<Posicion> listaPosiciones, Celda[][] celdas, ColorAjedrez colorPiezaEnLaSalida) {
        ArrayList<Posicion> posicionesPosibles = new ArrayList<>();
        for (Posicion posicion : listaPosiciones) {
            Pieza piezaEnLaPosición = celdas[posicion.getFila()][posicion.getColumna()].getPieza();
            if (piezaEnLaPosición != null) {
                if (colorPiezaEnLaSalida != piezaEnLaPosición.getColor()) {
                    posicionesPosibles.add(posicion);
                    return posicionesPosibles;
                }
            }
        }
        return posicionesPosibles;
    }

    /**
     * Encuentra las posiciones equivalentes, se lo utiliza para el peon negro
     *
     * @param listaPosicionesPosibles
     * @return posicionesPosibles con filas dadas la vuelta
     */
    private static ArrayList<Posicion> equivalentes(ArrayList<Posicion> listaPosicionesPosibles) {
        ArrayList<Posicion> posicionesPosibles = new ArrayList<>();
        for (Posicion posicion : listaPosicionesPosibles) {
            try {
                posicionesPosibles.add(posicion.obtenerEquivalente());
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesPosibles;
    }

    private static ArrayList<Posicion> descartarPosicionesNoPosiblesSinPiezasDelMismoColor(ArrayList<Posicion> listaDePosiciones, Celda[][] celdas, ColorAjedrez colorDeLaPiezaEnLaSalida) {
        ArrayList<Posicion> posicionesPosibles = listaDePosiciones;
        for (Iterator<Posicion> iterator = posicionesPosibles.iterator(); iterator.hasNext(); ) {
            Posicion posicion = iterator.next();
            Pieza piezaEnLaPosicion = celdas[posicion.getFila()][posicion.getColumna()].getPieza();
            if (piezaEnLaPosicion != null) {
                if (colorDeLaPiezaEnLaSalida == piezaEnLaPosicion.getColor()) {
                    iterator.remove(); // iterator : transforma de arreglo
                }
            }
        }
        return posicionesPosibles;
    }

    private static ArrayList<Posicion> descartarPosicionesNoPosibles(ArrayList<Posicion> listaDePosiciones, Celda[][] celdas, ColorAjedrez colorDeLaPiezaEnLaSalida) {
        ArrayList<Posicion> posicionesPosibles = new ArrayList<>();
        for (Posicion posicion : listaDePosiciones) {
            Pieza piezaEnLaPosicion = celdas[posicion.getFila()][posicion.getColumna()].getPieza();
            if (piezaEnLaPosicion != null) {
                if (colorDeLaPiezaEnLaSalida != piezaEnLaPosicion.getColor()) {
                    posicionesPosibles.add(posicion);
                    return posicionesPosibles;
                } else {
                    return posicionesPosibles;
                }
            }
            posicionesPosibles.add(posicion);
        }
        return posicionesPosibles;
    }

    private static ArrayList<Posicion> extraerPosicionPosiblesDiagonalInferiorIzquierda(Pieza piezaACalcularSusPosiblesPosiciones, int filaDeSalida, int columnaDeSalida) throws ExcepcionUbicacionFueraDeRango {
        int filaDeLLegada = filaDeSalida;
        int columnaDeLlegada = columnaDeSalida;
        while (filaDeLLegada > VALOR_INFERIOR_A_CERO && columnaDeLlegada > VALOR_INFERIOR_A_CERO) {
            filaDeLLegada--;
            columnaDeLlegada--;
        }
        return ((MovibleDiagonal) piezaACalcularSusPosiblesPosiciones).obtenerPosicionesDiagonales(filaDeSalida, columnaDeSalida, filaDeLLegada, columnaDeLlegada);
    }

    private static ArrayList<Posicion> extraerPosicionPosiblesDiagonalInferiorDerecha(Pieza piezaACalcularSusPosiblesPosiciones, int filaDeSalida, int columnaDeSalida) throws ExcepcionUbicacionFueraDeRango {
        int filaDeLLegada = filaDeSalida;
        int columnaDeLlegada = columnaDeSalida;
        while (filaDeLLegada > VALOR_INFERIOR_A_CERO && columnaDeLlegada < VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO) {
            filaDeLLegada--;
            columnaDeLlegada++;
        }
        return ((MovibleDiagonal) piezaACalcularSusPosiblesPosiciones).obtenerPosicionesDiagonales(filaDeSalida, columnaDeSalida, filaDeLLegada, columnaDeLlegada);
    }

    private static ArrayList<Posicion> extraerPosicionPosiblesDiagonalSuperiorIzquierda(Pieza piezaACalcularSusPosiblesPosiciones, int filaDeSalida, int columnaDeSalida) throws ExcepcionUbicacionFueraDeRango {
        int filaDeLLegada = filaDeSalida;
        int columnaDeLlegada = columnaDeSalida;
        while (filaDeLLegada < VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO && columnaDeLlegada > VALOR_INFERIOR_A_CERO) {
            filaDeLLegada++;
            columnaDeLlegada--;
        }
        return ((MovibleDiagonal) piezaACalcularSusPosiblesPosiciones).obtenerPosicionesDiagonales(filaDeSalida, columnaDeSalida, filaDeLLegada, columnaDeLlegada);
    }

    private static ArrayList<Posicion> extraerPosicionPosiblesDiagonalSuperiorDerecha(Pieza piezaACalcularSusPosiblesPosiciones, int filaDeSalida, int columnaDeSalida) throws ExcepcionUbicacionFueraDeRango {
        int filaDeLLegada = filaDeSalida;
        int columnaDeLlegada = columnaDeSalida;
        while (filaDeLLegada < VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO && columnaDeLlegada < VALOR_SUPERIOR_AL_MAXIMO_DE_FILAS_DEL_TABLERO) {
            filaDeLLegada++;
            columnaDeLlegada++;
        }
        return ((MovibleDiagonal) piezaACalcularSusPosiblesPosiciones).obtenerPosicionesDiagonales(filaDeSalida, columnaDeSalida, filaDeLLegada, columnaDeLlegada);
    }
}
