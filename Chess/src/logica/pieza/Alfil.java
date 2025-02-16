package logica.pieza;

import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.ExcepcionAjedrez;
import logica.exception.ExcepcionEnMovimiento;
import logica.exception.ExcepcionEnMovimientoDiagonal;
import logica.exception.ExcepcionUbicacionFueraDeRango;
import logica.interfaz.*;

import java.util.ArrayList;

/**
 * Clase correspondiente a la pieza Alfil
 * Subclase de Pieza e implementa MovibleDiagonal
 *
 * @autor: ACCBM
 */
public class Alfil extends Pieza implements MovibleDiagonal {
    public Alfil(ColorAjedrez color) {
        super(color);
    }

    @Override
    public void esMovible(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionAjedrez {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        verificarMovimientoEnDiagonal(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion posicionDeSalida, Posicion posicionDeLlegada) {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        ArrayList<Posicion> posicionesIntermedias = new ArrayList<>();
        posicionesIntermedias.addAll(obtenerPosicionesDiagonales(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada));
        return posicionesIntermedias;
    }

    @Override
    public void verificarMovimientoEnDiagonal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoDiagonal {
        int incrementoEnFila = Math.abs(filaLlegada - filaSalida);
        int incrementoEnColumna = Math.abs(columnaLlegada - columnaSalida);

        if (!esIgualLaCantidadDeFilasYColumnasQueSeMueve(incrementoEnFila, incrementoEnColumna)) {
            throw new ExcepcionEnMovimientoDiagonal("Movimiento diagonal imposible para el alfil");
        }
    }

    private boolean esIgualLaCantidadDeFilasYColumnasQueSeMueve(int cantidadDeCasillasFilaQueSeMueve, int cantidadDeCasillasColumnaQueSeMueve) {
        return cantidadDeCasillasFilaQueSeMueve == cantidadDeCasillasColumnaQueSeMueve;
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesDiagonales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesDiagonales = new ArrayList<>();
        Integer incrementoEnFila = filaLlegada - filaSalida;
        Integer incrementoEnColumna = columnaLlegada - columnaSalida;
        int[][] aumentoFilasColumnas = obtenerAumentosFilasColumnas(incrementoEnFila, incrementoEnColumna);

        for (int i = 0; i < Math.abs(filaLlegada - filaSalida) - 1; i++) {
            try {
                posicionesDiagonales.add(new Posicion(filaSalida + aumentoFilasColumnas[0][i], columnaSalida + aumentoFilasColumnas[1][i]));
            } catch (ExcepcionUbicacionFueraDeRango e) {
            }
        }
        return posicionesDiagonales;
    }
}
