package logica.interfaz;
import logica.Posicion; // oe cris puedes revisar la torre ya estan los java docs para que me avises si estan bien
import logica.exception.ExcepcionEnMovimiento;
import logica.exception.ExcepcionEnMovimientoEnL;

import java.util.ArrayList;

public interface MovibleEnL {
    /**
     * A partir de la fila y columna de salida, para cada valor calcula las ocho posibles posiciones en fila y columna segun dos incrementos (SUPERIOR e INFERIOR),
     * estos valores se almacena por separado en dos arrays y juntos conforman un par ordenado de posiciones. Mediante un
     * for se recorre el array y si alguno de estos valores calculados es igual a su equivalente en fila y columna de
     * llegada se aumenta uno hacia un contador 'aux'.
     * Finalmente, se verifica que haya por lo menos una coincidencia de estos valores, caso contrario lanza la excepcion.
     */
    void verificarMovimientoEnL(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimiento;

    /**
     * A partir de una fila y columna de Salida se calcula las ocho posibles posiciones en fila y columna segun dos incrementos (SUPERIOR e INFERIOR),
     * estos valores se almacena por separado en dos arrays y juntos conforman un par ordenado de posiciones, mediante un for
     * se recorre en simultaneo ambos arrays y con esos valores se generan nuevas posiciones que se agregan hacia el Arraylist a retornar
     */
    ArrayList<Posicion> obtenerPosicionesEnL(Integer filaSalida, Integer columnaSalida);
}
