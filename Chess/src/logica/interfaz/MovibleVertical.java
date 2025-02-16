package logica.interfaz;

import logica.Posicion;
import logica.exception.ExcepcionEnMovimiento;
import logica.exception.ExcepcionUbicacionFueraDeRango;

import java.util.ArrayList;

public interface MovibleVertical {

    /**
     * Verifica que la fila de salida y la de llegada sean las mismas
     */
    void verificarMovimientoEnVertical(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimiento;

    /**
     * Obtiene las posiciones posibles de la ficha a lo vertical desde la posicion en la que se encuentra
     * y las almacena en un array list y si la posisiones estan fuera del tablero se genera una excepcion de ExcepcionUbicacionFueraDeRango
     * o si la establoqueada por otra ficha.
     * @return posicionesPosiblesVerticales
     */
    ArrayList<Posicion> obtenerPosicionesVerticales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) ;
}
