package logica;

import logica.color.ColorAjedrez;
import logica.exception.ExcepcionAjedrez;
import logica.exception.ExcepcionUbicacionFueraDeRango;
import logica.exception.ExcepcionVictoria;

import java.io.Serializable;

/**
 * Dedicada a emular una partida de ajedrez
 *
 * @autor ACCBM
 */
public class Partida implements Serializable {
    private boolean enCurso;
    private Jugador jugadorEnTurno;
    private Jugador jugadorObscuras;
    private Jugador jugadorClaras;
    private Tablero tablero;
    private Jugada jugada;

    /**
     * Instancia e inicializa todos los elementos para la partida
     *
     * @param nombreJugador1 con el color claro
     * @param nombreJugador2 con el color obscuro
     */
    public Partida(String nombreJugador1, String nombreJugador2) {
        this.jugadorClaras = new Jugador(nombreJugador1, ColorAjedrez.CLARO);
        this.jugadorObscuras = new Jugador(nombreJugador2, ColorAjedrez.OBSCURO);
        this.tablero = new Tablero();
        jugadorEnTurno = jugadorClaras;
        jugada = new Jugada(jugadorEnTurno, tablero);
        enCurso = true;
    }

    public void mostrarTablero() {
        int DIMENSION_MAXIMA_DEL_TABLERO = 8;
        Celda[][] celdas = tablero.getCeldas();
        for (int fila = 0; fila < DIMENSION_MAXIMA_DEL_TABLERO; fila++) {
            for (int columna = 0; columna < DIMENSION_MAXIMA_DEL_TABLERO; columna++) {
                try {
                    Posicion posicion = new Posicion(fila, columna);
                    System.out.println(posicion + " = " + celdas[fila][columna]);
                } catch (ExcepcionUbicacionFueraDeRango e) {
                    System.out.println("Error");
                }
            }
        }
    }

    /**
     * Envia a jugada la posicionSalida y la posicionDeLlegada y procesos para completar un movimiento
     */
    public void moverPieza(Posicion posiciónSalida, Posicion posiciónDeLlegada) throws ExcepcionAjedrez {
        jugada.moverPieza(posiciónSalida, posiciónDeLlegada);
        actualizarTableroDespuésDeLaJugada();
        evaluarGanador();
        alternarJugadores();
    }

    private void actualizarTableroDespuésDeLaJugada() {
        this.tablero.enviarCeldas(jugada.obtenerCeldasDelTablero());
    }

    private void evaluarGanador() throws ExcepcionVictoria {
        if (jugada.getEstadoDelRey() == EstadoDelRey.JAQUE_MATE) {
            enCurso = false;
            throw new ExcepcionVictoria(jugadorEnTurno + " se ha hecho con la victoria");
        }
    }

    private void alternarJugadores() {
        if (jugadorEnTurno == jugadorClaras) {
            jugadorEnTurno = jugadorObscuras;
        } else if (jugadorEnTurno == jugadorObscuras) {
            jugadorEnTurno = jugadorClaras;
        }
        //Deja listo para que el proximo movimiento pueda hacer el siguiente jugador
        jugada.enviarJugador(jugadorEnTurno);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean obtenerEstadoDeLaPartida() {
        return enCurso;
    }

    public ColorAjedrez obtenerColorJugadorEnTurno() {
        return jugadorEnTurno.obtenerColor();
    }

    /**
     * Al llegar a la fila tope del lado contrario, se cambiará la pieza peon por cualquier otra.
     *
     * @param posicion
     * @param opcionDePiezaAIntroducir
     */
    public void cambiarPeon(Posicion posicion, Integer opcionDePiezaAIntroducir) throws ExcepcionVictoria {
        jugada.cambiarPeon(posicion, opcionDePiezaAIntroducir);
        actualizarTableroDespuésDeLaJugada();
        evaluarGanador();
        alternarJugadores();
    }

    public boolean verificarReyEnJaque() {
        return jugada.getEstadoDelRey() == EstadoDelRey.JAQUE;
    }

    public void enviarEstadoDelRey(EstadoDelRey estado) {
        jugada.enviarEstadoDelRey(estado);
    }
}
