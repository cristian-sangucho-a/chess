package logica.interfaz;
import logica.Posicion;
import logica.exception.ExcepcionEnMovimientoDiagonal;
import java.util.ArrayList;

public interface MovibleDiagonal {

    /**
     * Considerando los incrementos de fila y columna, se verifica la coincidencia de estos dos parametros
     * para comprobar el movimiento en diagonal.
     */
    void verificarMovimientoEnDiagonal(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) throws ExcepcionEnMovimientoDiagonal;

    /**
     * A partir de las filas y columnas calcula nuevas posiciones entre una de salida y otra de llegada conformadas en
     * forma diagonal.
     * @return posicionesPosiblesDiagonales
     */
    ArrayList<Posicion> obtenerPosicionesDiagonales(Integer filaSalida, Integer columnaSalida, Integer filaLlegada, Integer columnaLlegada) ;
}

