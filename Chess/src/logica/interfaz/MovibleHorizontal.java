package logica.interfaz;

import logica.Posicion;
import logica.exception.ExcepcionEnMovimiento;
import logica.exception.ExcepcionUbicacionFueraDeRango;

import java.util.ArrayList;

public interface MovibleHorizontal {

    /**
     * Verifica que la fila de salida y la de llegada sean las mismas
     */
    void verificarMovimientoEnHorizontal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimiento;

    /**
     * Obtiene las posiciones posibles de la ficha a lo horizontal desde la posicion en la que se encuentra
     * y las almacena en un array list y si la posisiones estan fuera del tablero se genera una excepcion de ExcepcionUbicacionFueraDeRango
     * o si la establoqueada por otra ficha.
     * @return posicionesIntermediasHorizontales
     */
    ArrayList<Posicion> obtenerPosicionesHorizontales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada);
}