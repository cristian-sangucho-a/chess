import logica.color.ColorAjedrez;
import logica.pieza.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class CeldaGUI extends JButton {
    private Pieza pieza;
    public CeldaGUI() {
        }
    public void setPieza(Pieza pieza) {
        if (!existePieza(pieza)) {
            return;
        }
        this.pieza = pieza;
        setIcon(determinarIconoSegunLaPieza(pieza));
    }



    private boolean existePieza(Pieza pieza) {
        return pieza != null;
    }
    private Icon determinarIconoSegunLaPieza(Pieza pieza) {
        if (pieza.getColor() == ColorAjedrez.CLARO) {
            if (pieza instanceof Peon) {
                return new ImageIcon("../GUI_Chess/src/recurso/piezasPics/Blancas/PeonB.png");
            }
            if (pieza instanceof Torre) {
                return new ImageIcon("../GUI_Chess/src/recurso/PiezasPics/Blancas/TorreB.png");
            }
            if (pieza instanceof Alfil) {
                return new ImageIcon("../GUI_Chess/src/recurso/PiezasPics/Blancas/AlfilB.png");
            }
            if (pieza instanceof Caballo) {
                return new ImageIcon("../GUI_Chess/src/recurso/PiezasPics/Blancas/CaballoB.png");
            }
            if (pieza instanceof Rey) {
                return new ImageIcon("../GUI_Chess/src/recurso/PiezasPics/Blancas/ReyB.png");
            }
            if (pieza instanceof Reina) {
                return new ImageIcon("../GUI_Chess/src/recurso/PiezasPics/Blancas/ReinaB.png");
            }
        }
        if (pieza.getColor() == ColorAjedrez.OBSCURO) {
            if (pieza instanceof Peon) {
                return new ImageIcon("../GUI_Chess/src/recurso/PiezasPics/Negras/PeonN.png");
            }
            if (pieza instanceof Torre) {
                return new ImageIcon("../GUI_Chess/src/recurso/piezasPics/Negras/TorreN.png");
            }
            if (pieza instanceof Alfil) {
                return new ImageIcon("../GUI_Chess/src/recurso/piezasPics/Negras/AlfilN.png");
            }
            if (pieza instanceof Caballo) {
                return new ImageIcon("../GUI_Chess/src/recurso/piezasPics/Negras/CaballoN.png");
            }
            if (pieza instanceof Rey) {
                return new ImageIcon("../GUI_Chess/src/recurso/piezasPics/Negras/ReyN.png");
            }
            if (pieza instanceof Reina) {
                return new ImageIcon("../GUI_Chess/src/recurso/piezasPics/Negras/ReinaN.png");
            }
        }
        return null;
    }


    public void marcar() {
        setBorderPainted(true);
        setBorder(new LineBorder(new Color(89, 239, 216), 10));
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void desmarcar() {
        setBorderPainted(true);
        setBorder(new LineBorder(Color.DARK_GRAY));
    }

    @Override
    public String toString() {
        return "pieza=" + pieza ;
    }

    public void borrarPieza() {
        pieza = null;
        setIcon(null);
    }
}
