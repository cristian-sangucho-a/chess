import logica.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Font.PLAIN;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryStream;


public class MainMenu extends JFrame {
    private JPanel panel;
    private JButton botonInicio;
    private JButton botonCargar;


    public MainMenu() throws HeadlessException {
        super("MainMenu");

        inicializarBotones();
        inicializarPantalla();
        accionClickBotones();

    }

    private void accionClickBotones() {
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuIngresoDeNombre ingresoDeNombres = new SubMenuIngresoDeNombre();
                setVisible(false);
            }
        });
        botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Partida ajedrez = new Partida("Jugador 1", "Jugador 2");
                try {

                    InputStream is = new FileInputStream(obtenerArchivo());
                    ObjectInputStream ois = new ObjectInputStream(is);

                    ajedrez = (Partida) ois.readObject();

                    JOptionPane.showMessageDialog(null, "Cargado con éxito");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                TableroGUI tablero = new TableroGUI(ajedrez);
                setVisible(false);
            }
        });
    }

    private File obtenerArchivo() {
        //JFileChooser archivoAEscoger = new JFileChooser("C:/Users/intel/OneDrive - Escuela Politécnica Nacional/OD - P R O G R A M A C I ON  -  II/PROYECTO");
        JFileChooser archivoAEscoger = new JFileChooser("../GUI_Chess");
        archivoAEscoger.showOpenDialog(archivoAEscoger);
        return archivoAEscoger.getSelectedFile();
    }

    private void inicializarBotones() {
        botonInicio.setBounds(100, 180, 155, 40);
        botonCargar.setBounds(300, 180, 155, 40);

        botonInicio.setFont(new Font("MONOSPACED", PLAIN, 16));
        botonCargar.setFont(new Font("MONOSPACED", PLAIN, 16));

        botonInicio.setOpaque(true);
        botonCargar.setOpaque(true);

        botonInicio.setBorder(new LineBorder(Color.DARK_GRAY));
        botonCargar.setBorder(new LineBorder(Color.DARK_GRAY));

        botonInicio.setVisible(true);
        botonCargar.setVisible(true);

        add(botonInicio);
        add(botonCargar);

    }

    private void inicializarPantalla() {
        setTitle("Chess Simulator");
        JLabel background;
        setSize(570, 385);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("../GUI_Chess/src/recurso/menu.jpg");
        //ImageIcon img = new ImageIcon("GUI_Chess/src/recurso/menu.jpg");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 566, 390);
        add(background);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
