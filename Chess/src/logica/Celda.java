package logica;

import logica.pieza.Pieza;

import java.io.Serializable;

/**
 * Destinada a contener una pieza
 *
 * @author ACCBM
 */
public class Celda implements Serializable {
    private Pieza pieza;

    public Celda() {
    }

    /**
     * Coloca una nueva pieza en la celda
     *
     * @param pieza
     */
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        pieza.setCelda(this);
    }

    public Pieza getPieza() {
        return pieza;
    }

    /**
     * Borra la pieza actualmente existente
     */
    public void borrarPieza() {
        if (pieza != null) {
            pieza.borrarCelda();
            this.pieza = null;
        }
    }

    @Override
    public String toString() {
        return "pieza " + pieza;
    }

    public boolean existePieza() {
        if (pieza == null) {
            return false;
        }
        return true;
    }
}
