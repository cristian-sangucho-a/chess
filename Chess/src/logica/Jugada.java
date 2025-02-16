package logica;

import logica.color.ColorAjedrez;
import logica.exception.*;
import logica.pieza.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase dedicada a realizar todas las acciones y controles referentes a mover una pieza.
 *
 * @autor ACCBM
 */
public class Jugada implements Serializable {
    private EstadoDelRey estado;
    private Celda[][] celdas;
    private Jugador jugador;
    private int FILA_TOPE_PARA_CAMBIO_DE_PEON_A_OTRA_PIEZA = 7;

    /**
     * Constructor para cada jugada.
     *
     * @param jugadorEnTurno
     * @param tablero
     */
    public Jugada(Jugador jugadorEnTurno, Tablero tablero) {
        this.jugador = jugadorEnTurno;
        this.celdas = tablero.getCeldas();
        estado = EstadoDelRey.VIVO;
    }


    /**
     * Verifica varias condiciones, dependiendo de la pieza y posiciones, y de cumplirse mueve la pieza
     *
     * @param posicionDeSalida
     * @param posicionDeLlegada
     */
    public void moverPieza(Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionAjedrez {
        int filaDeSalida = posicionDeSalida.getFila();
        int columnaDeSalida = posicionDeSalida.getColumna();
        int filaDeLlegada = posicionDeLlegada.getFila();
        int columnaDeLlegada = posicionDeLlegada.getColumna();

        cumpleCondicionesMinimasParaElMovimiento(celdas, filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);

        //Este if realiza el movimiento, y divide en dos casos:
        //1. Si en la posicion de llegada existe una pieza del rival, entonces hay que comer esa pieza.
        //2. Si en la posicion de llegada no existe pieza.
        if (existeUnaPieza(celdas[filaDeLlegada][columnaDeLlegada].getPieza())) {
            cumpleFormaDeComerPropiosDeLaPieza(celdas[filaDeSalida][columnaDeSalida].getPieza(), posicionDeSalida, posicionDeLlegada);
        } else {
            cumpleMovimientosPropiosDeLaPieza(celdas[filaDeSalida][columnaDeSalida].getPieza(), posicionDeSalida, posicionDeLlegada);
        }

        verificarLaInexistenciaDePiezasEntreAmbasPosiciones(celdas[filaDeSalida][columnaDeSalida].getPieza(), posicionDeSalida, posicionDeLlegada);

        verificarVictoria(filaDeLlegada, columnaDeLlegada);

        //Esto es comer la pieza del contrincante
        celdas[filaDeLlegada][columnaDeLlegada].borrarPieza();
        cambiarPiezaDeCelda(filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);

        //Realiza la coronacion del peon
        verificarEnJaque(posicionDeLlegada, celdas);
        transformarPeon(filaDeLlegada, columnaDeLlegada);
    }

    private void verificarVictoria(int filaDeLlegada, int columnaDeLlegada) {
        if (esLaPiezaEnLaPosicionDeLlegadaUnRey(filaDeLlegada, columnaDeLlegada)) {
            estado = EstadoDelRey.JAQUE_MATE;
        }
    }

    /**
     * Verifica si jugador escoge piezas de su color y si el movimiento de
     * llegada no topa con una pieza de su propio color
     *
     * @param celdas
     * @param filaDeSalida
     * @param columnaDeSalida
     * @param filaDeLlegada
     * @param columnaDeLlegada
     */
    private void cumpleCondicionesMinimasParaElMovimiento(Celda[][] celdas, int filaDeSalida, int columnaDeSalida, int filaDeLlegada, int columnaDeLlegada) throws ExcepcionMinimaDeMovimiento {
        verificarIgualdadEntreElColorDeLaPiezaYDelJugador(celdas, filaDeSalida, columnaDeSalida);
        verificarLaDiferenciaDeColorEntrePiezas(celdas, filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada);
    }

    private void verificarIgualdadEntreElColorDeLaPiezaYDelJugador(Celda[][] celdas, int filaDeSalida, int columnaDeSalida) throws ExcepcionDeColorParaElTurno {
        if (jugador.obtenerColor() != celdas[filaDeSalida][columnaDeSalida].getPieza().getColor()) {
            throw new ExcepcionDeColorParaElTurno("No es tu turno de juego");
        }
    }

    //No permite que comas tus propias piezas
    private void verificarLaDiferenciaDeColorEntrePiezas(Celda[][] celdas, int filaDeSalida, int columnaDeSalida, int filaDeLlegada, int columnaDeLlegada) throws ExcepcionAutoAtaque {
        if (existeUnaPieza(celdas[filaDeLlegada][columnaDeLlegada].getPieza())) {
            if (esLaPiezaDeLlegadaIgualEnColorQuePiezaDeSalida(celdas, filaDeSalida, columnaDeSalida, filaDeLlegada, columnaDeLlegada)) {
                throw new ExcepcionAutoAtaque("Ya existe una pieza de tu equipo en la celda de llegada");
            }
        }
    }

    private boolean existeUnaPieza(Pieza pieza) {
        return pieza != null;
    }

    private boolean esLaPiezaDeLlegadaIgualEnColorQuePiezaDeSalida(Celda[][] celdas, int filaDeSalida, int columnaDeSalida, int filaDeLlegada, int columnaDeLlegada) {
        return celdas[filaDeLlegada][columnaDeLlegada].getPieza().getColor() == celdas[filaDeSalida][columnaDeSalida].getPieza().getColor();
    }

    /**
     * @param pieza
     * @param posicionDeSalida
     * @param posicionDeLlegada
     */
    private void cumpleFormaDeComerPropiosDeLaPieza(Pieza pieza, Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionAjedrez {
        //Si la pieza es un peon entonces se ejecuta el metodo puedeComer()
        //pues el peon es la unica pieza que come de distinta forma de la que se mueve
        if (pieza instanceof Peon) {
            ((Peon) pieza).puedeComer(posicionDeSalida, posicionDeLlegada);
        } else {
            pieza.esMovible(posicionDeSalida, posicionDeLlegada);
        }
    }


    private void cumpleMovimientosPropiosDeLaPieza(Pieza pieza, Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionAjedrez {
        pieza.esMovible(posicionDeSalida, posicionDeLlegada);
    }

    /*###############################################################################################################*/
    private void verificarLaInexistenciaDePiezasEntreAmbasPosiciones(Pieza pieza, Posicion posicionDeSalida, Posicion posicionDeLlegada) throws ExcepcionAjedrez {
        ArrayList<Posicion> posicionesIntermedias = pieza.obtenerPosicionesIntermedias(posicionDeSalida, posicionDeLlegada);
        for (Posicion posicion : posicionesIntermedias) {
            int fila = posicion.getFila();
            int columna = posicion.getColumna();
            Pieza piezaEnCeldaIntermedia = celdas[fila][columna].getPieza();
            if (existeUnaPieza(piezaEnCeldaIntermedia)) {
                throw new ExcepcionAtravesarPiezas("No es posible llegar a una celda atravesando piezas");
            }
        }
    }

    private boolean esLaPiezaEnLaPosicionDeLlegadaUnRey(int filaDeLlegada, int columnaDeLlegada) {
        return celdas[filaDeLlegada][columnaDeLlegada].getPieza() instanceof Rey;
    }

    private void cambiarPiezaDeCelda(int filaDeSalida, int columnaDeSalida, int filaDeLlegada, int columnaDeLLegada) {
        //asigna la pieza a la casilla que se requiere  //llegar ya ahora si
        celdas[filaDeLlegada][columnaDeLLegada].setPieza(celdas[filaDeSalida][columnaDeSalida].getPieza());
        //borra la pieza anterior de la casilla de salida
        celdas[filaDeSalida][columnaDeSalida].borrarPieza();
    }

    /**
     * Realiza la coronacion del peon
     *
     * @param filaDeLlegada
     * @param columnaDeLlegada
     */
    private void transformarPeon(int filaDeLlegada, int columnaDeLlegada) throws ExcepcionTransformarPeon {
        if (celdas[filaDeLlegada][columnaDeLlegada].getPieza() instanceof Peon) {
            if (llegaElPeonALaUltimaFila(filaDeLlegada) || filaDeLlegada == 0) {
                throw new ExcepcionTransformarPeon("Añadir método que transforme el peón ");
            }
        }
    }

    private boolean llegaElPeonALaUltimaFila(int filaDeLlegada) {
        return filaDeLlegada == FILA_TOPE_PARA_CAMBIO_DE_PEON_A_OTRA_PIEZA;
    }

    /**
     * Determina la pieza con la cual el peon va a cambiar
     *
     * @param color
     * @param opcion
     * @return pieza nueva
     */
    private Pieza solicitarPieza(ColorAjedrez color, int opcion) {
        switch (opcion) {
            case 1:
                return new Reina(color);
            case 2:
                return new Caballo(color);
            case 3:
                return new Torre(color);
            case 4:
                return new Alfil(color);
        }
        return null;
    }

    /**
     * Lanza un mensaje siempre cuando una pieza llegue a una celda en la que pueda amenazar al rey contrincante
     *
     * @param posicionDeLlegada
     * @param celdas
     */
    public void verificarEnJaque(Posicion posicionDeLlegada, Celda[][] celdas) throws ExcepcionUbicacionFueraDeRango {
        int filaDeSalida = posicionDeLlegada.getFila();
        int columnaDeSalida = posicionDeLlegada.getColumna();

        Pieza piezaACalcularSusPosiblesPosiciones = celdas[filaDeSalida][columnaDeSalida].getPieza();

        //Este arraylist guarda las posiciones a las cuales la pieza se puede mover
        ArrayList<Posicion> posicionesPosiblesParaPieza = PosicionPosible.obtenerLasPosicionesPosibles(piezaACalcularSusPosiblesPosiciones, posicionDeLlegada, celdas);

        //El for comprueba si el rey se encuentra en la linea de accion de la pieza si es asi seniala el Jaque
        for (Posicion posicion : posicionesPosiblesParaPieza) {
            int fila = posicion.getFila();
            int columna = posicion.getColumna();
            Pieza piezaEnCelda = celdas[fila][columna].getPieza();

            if (piezaEnCelda instanceof Rey) {
                estado = EstadoDelRey.JAQUE;
            }
        }
    }

    public void enviarJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Celda[][] obtenerCeldasDelTablero() {
        return celdas;
    }

    public EstadoDelRey getEstadoDelRey() {
        return estado;
    }

    /**
     * Realiza la coronacion del peon
     *
     * @param posicion
     * @param opcionDePiezaAIntroducir
     */
    public void cambiarPeon(Posicion posicion, Integer opcionDePiezaAIntroducir) {
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        ColorAjedrez colorTemporal = celdas[fila][columna].getPieza().getColor();
        celdas[fila][columna].borrarPieza();
        celdas[fila][columna].setPieza(solicitarPieza(colorTemporal, opcionDePiezaAIntroducir));
    }

    public void enviarEstadoDelRey(EstadoDelRey estado) {
        this.estado = estado;
    }
}