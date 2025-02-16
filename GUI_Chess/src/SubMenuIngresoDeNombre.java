import logica.Partida;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Font.PLAIN;

public class SubMenuIngresoDeNombre extends JFrame {


    private JTextField nombreJugadorClaras;
    private JTextField nombreJugadorOscuras;
    private JButton botonContinuar;
    private int auxParaClaras;
    private int auxParaOscuras;

    public SubMenuIngresoDeNombre() {
        super("MainMenu");
        inicializarInputsDeNombres();
        inicializarBoton();
        inicializarPantalla();
        borrarInstrucionesDeLosInputs();
        inicializarAccionesParaElBoton();

    }

    private void inicializarAccionesParaElBoton() {
        botonContinuar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TableroGUI tablero = new TableroGUI(nombreJugadorClaras.getText(), nombreJugadorOscuras.getText());
                setVisible(false);
            }
        });
    }

    private void borrarInstrucionesDeLosInputs() {
        nombreJugadorClaras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (auxParaClaras > 0) {
                    return;
                }
                nombreJugadorClaras.setText("");
                auxParaClaras++;

            }
        });
        nombreJugadorOscuras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (auxParaOscuras > 0) {
                    return;
                }
                nombreJugadorOscuras.setText("");
                auxParaOscuras++;
            }
        });
    }

    private void inicializarInputsDeNombres() {
        nombreJugadorOscuras.setBounds(200, 265, 200, 30);
        nombreJugadorClaras.setBounds(200, 100, 200, 30);

        nombreJugadorOscuras.setText("Jugador2");
        nombreJugadorClaras.setText("Jugador1");

        nombreJugadorOscuras.setEditable(true);
        nombreJugadorClaras.setEditable(true);

        nombreJugadorOscuras.setHorizontalAlignment(JTextField.LEFT);
        nombreJugadorClaras.setHorizontalAlignment(JTextField.LEFT);

        add(nombreJugadorOscuras, null);
        add(nombreJugadorClaras, null);


    }

    private void inicializarPantalla() {
        setTitle("Ingrese los nombres de los jugadores");
        JLabel background;
        setSize(610, 440);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("../GUI_Chess/src/recurso/seleccionarColor.jpg");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 600, 400);
        add(background);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarBoton() {
        botonContinuar.setBounds(400, 320, 155, 40);
        botonContinuar.setFont(new Font("MONOSPACED", PLAIN, 16));
        botonContinuar.setOpaque(true);
        botonContinuar.setBorder(new LineBorder(Color.DARK_GRAY));
        botonContinuar.setVisible(true);
        add(botonContinuar);


    }

}
