package logica;

import logica.exception.ExcepcionUbicacionFueraDeRango;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representa las posiciones en un tablero de ajedrez
 * @autor ACCBM
 *
 */
public class Posicion implements Serializable {
    private int fila;
    private int columna;

    public Posicion(int fila, int columna) throws ExcepcionUbicacionFueraDeRango {
        if (!esPosible(fila) || !esPosible(columna)) {
            throw new ExcepcionUbicacionFueraDeRango("Posicion fuera del tablero");
        }
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Recoge valores segun lsa etiquetas de un tablero de ajedrez
     *
     * @param fila
     * @param columna
     */
    public Posicion(int fila, String columna) throws ExcepcionUbicacionFueraDeRango {
        int filaNueva = fila - 1;
        if (!esPosible(filaNueva)) {
            throw new ExcepcionUbicacionFueraDeRango("Posicion fuera del tablero");
        }
        int columnaNueva = transformarAEntero(columna.toUpperCase());
        this.fila = filaNueva;
        this.columna = columnaNueva;
    }

    /**
     * Devuelve un equivalente entero segun el string para la columna
     *
     * @param columna
     * @return indice de la columna
     */
    private int transformarAEntero(String columna) throws ExcepcionUbicacionFueraDeRango {
        ArrayList<String> columnas = new ArrayList<>();
        columnas.add("A");
        columnas.add("B");
        columnas.add("C");
        columnas.add("D");
        columnas.add("E");
        columnas.add("F");
        columnas.add("G");
        columnas.add("H");
        if (!columnas.contains(columna)) {
            throw new ExcepcionUbicacionFueraDeRango("La posicion no existe en el tablero");
        }
        return columnas.indexOf(columna);
    }
    /**
     * Comprueba que el valor se encuentre en un rango valido
     *
     * @param posicion
     * @return true si int está dentro del tablero
     */
    private boolean esPosible(int posicion) {
        return posicion >= 0 && posicion <= 7;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve la posicion desde la perspectiva contraria del tablero
     * @return posicion con fila invertida
     */
    public Posicion obtenerEquivalente() throws ExcepcionUbicacionFueraDeRango {
        if (fila == 7) return new Posicion(0, columna);
        if (fila == 6) return new Posicion(1, columna);
        if (fila == 5) return new Posicion(2, columna);
        if (fila == 4) return new Posicion(3, columna);
        if (fila == 3) return new Posicion(4, columna);
        if (fila == 2) return new Posicion(5, columna);
        if (fila == 1) return new Posicion(6, columna);
        if (fila == 0) return new Posicion(7, columna);
        return this;
    }

    @Override
    public String toString() {
        return "[" + transformarFila(fila) + " , " + transformarColumna(columna) + "]";
    }

    private String transformarColumna(int columna) {
        ArrayList<String> columnas = new ArrayList<>();
        columnas.add("A");
        columnas.add("B");
        columnas.add("C");
        columnas.add("D");
        columnas.add("E");
        columnas.add("F");
        columnas.add("G");
        columnas.add("H");
        return columnas.get(columna);
    }

    private Integer transformarFila(int fila) {
        return fila + 1;
    }
}
