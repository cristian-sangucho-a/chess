package logica;

import logica.color.ColorAjedrez;

import java.io.Serializable;

/**
 * @autor ACCBM
 */
public class Jugador implements Serializable {

    private String nombre;
    private ColorAjedrez colorDePiezas;

    /**
     * Establece nombre y color para el jugador
     * @param nombre
     * @param colorDePiezas
     */
    public Jugador(String nombre, ColorAjedrez colorDePiezas) {
        this.nombre = nombre;
        this.colorDePiezas = colorDePiezas;
    }

    public ColorAjedrez obtenerColor() {
        return colorDePiezas;
    }

    @Override
    public String toString() {
        return nombre + ", con color de pieza: " + colorDePiezas;
    }
}
