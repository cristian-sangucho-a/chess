package logica.pieza;

import logica.Posicion;
import logica.color.ColorAjedrez;
import logica.exception.ExcepcionEnMovimientoHorizontal;
import logica.exception.ExcepcionEnMovimiento;
import logica.exception.ExcepcionEnMovimientoEnVertical;
import logica.exception.ExcepcionUbicacionFueraDeRango;
import logica.interfaz.MovibleHorizontal;
import logica.interfaz.MovibleVertical;

import java.util.ArrayList;

/**
 * Clase correspondiente a la pieza Torre
 * Subclase de Pieza e implementa MovibleVertical y MovibleHorizontal
 *
 * @autor ACCBM
 */
public class Torre extends Pieza implements MovibleVertical, MovibleHorizontal {
    public Torre(ColorAjedrez color) {
        super(color);
    }

    @Override
    public void esMovible(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionEnMovimiento {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        if (!coinciden(filaDeSalida, filaDeLlegada)) {
            verificarMovimientoEnVertical(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        }
        if (!coinciden(columnaDeSalida, columnaDeLlegada)) {
            verificarMovimientoEnHorizontal(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesIntermedias(Posicion posicionDeSalida, Posicion posicionDeLlegada) {
        Integer filaDeSalida = posicionDeSalida.getFila();
        Integer columnaDeSalida = posicionDeSalida.getColumna();
        Integer filaDeLlegada = posicionDeLlegada.getFila();
        Integer columnaDeLlegada = posicionDeLlegada.getColumna();

        ArrayList<Posicion> posicionesPosicionesIntermedias = new ArrayList<>();
        if (coinciden(filaDeSalida, filaDeLlegada)) {
            posicionesPosicionesIntermedias.addAll(obtenerPosicionesHorizontales(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada));
        }
        if (coinciden(columnaDeSalida, columnaDeLlegada)) {
            posicionesPosicionesIntermedias.addAll(obtenerPosicionesVerticales(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada));
        }
        return posicionesPosicionesIntermedias;
    }

    @Override
    public void verificarMovimientoEnVertical(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoEnVertical {
        if (!coinciden(columnaSalida, columnaLlegada)) {
            throw new ExcepcionEnMovimientoEnVertical("Movimiento vertical imposible para la torre");
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesVerticales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesIntermediasVerticales = new ArrayList<>();
        if (seQuiereMoverHaciaArriba(filaSalida, filaLlegada)) {
            for (int i = filaSalida + 1; i < filaLlegada; i++) {
                try {
                    posicionesIntermediasVerticales.add(new Posicion(i, columnaSalida));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        if (seQuiereMoverHaciaAbajo(filaSalida, filaLlegada)) {
            for (int i = filaSalida - 1; i > filaLlegada; i--) {
                try {
                    posicionesIntermediasVerticales.add(new Posicion(i, columnaSalida));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        return posicionesIntermediasVerticales;
    }

    @Override
    public void verificarMovimientoEnHorizontal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoHorizontal {
        if (!coinciden(filaSalida, filaLlegada)) {
            throw new ExcepcionEnMovimientoHorizontal("Movimiento horizontal imposible para la torre");
        }
    }

    @Override
    public ArrayList<Posicion> obtenerPosicionesHorizontales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) {
        ArrayList<Posicion> posicionesIntermediasHorizontales = new ArrayList<>();
        if (seQuiereMoverHaciaLaDerecha(columnaSalida, columnaLlegada)) {
            for (int i = columnaSalida + 1; i < columnaLlegada; i++) {
                try {
                    posicionesIntermediasHorizontales.add(new Posicion(filaSalida, i));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        if (seQuiereMoverHaciaLaIzquierda(columnaSalida, columnaLlegada)) {
            for (int i = columnaSalida - 1; i > columnaLlegada; i--) {
                try {
                    posicionesIntermediasHorizontales.add(new Posicion(filaSalida, i));
                } catch (ExcepcionUbicacionFueraDeRango e) {
                }
            }
        }
        return posicionesIntermediasHorizontales;
    }
}
