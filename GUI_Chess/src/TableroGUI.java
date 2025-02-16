import logica.*;
import logica.color.ColorAjedrez;
import logica.exception.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static java.awt.Font.PLAIN;

public class TableroGUI extends JFrame {

    public static final int ANCHO_POR_ALTO = 80;//94

    public static final int FILA_X_COLUMNA = 8;//8
    private JPanel panel1;
    private JButton botonGuardarPartida;
    private JButton botonTerminarPartida;
    private JButton botonMostrarAyuda;
    private JLabel ayuda;
    private JButton labelColor;
    private JLabel labelFilasTablero;
    private JLabel labelColumnasTablero;

    private CeldaGUI[][] celdasGUI;

    private SubMenuIngresoDeNombre subMenuIngresoDeNombre;
    private ArrayList<CeldaGUI> celdasPosiblesAnterioresTemporales;
    private Partida ajedrez;

    public TableroGUI(String nombreDelJugador1, String nombreDelJugador2) {
        super("tablero");
        CeldaGUI[][] celdasGUI = new CeldaGUI[FILA_X_COLUMNA][FILA_X_COLUMNA];
        ajedrez = new Partida(nombreDelJugador1, nombreDelJugador2);
        inicializaComponentesSecundarios();
        inicializarCuadriculaDeBotones(celdasGUI);
        colocarPiezas(ajedrez, celdasGUI);
        inicializarPantalla();
        inicializarAccionVisualizarPosicionesPosibles(celdasGUI, ajedrez);
        inicializarAccionMoverPieza(celdasGUI, ajedrez);
        inicializarAccionDeBotones();
    }


    public TableroGUI(Partida ajedrez) {
        CeldaGUI[][] celdasGUI = new CeldaGUI[FILA_X_COLUMNA][FILA_X_COLUMNA];
        inicializaComponentesSecundarios();
        inicializarCuadriculaDeBotones(celdasGUI);
        this.ajedrez = ajedrez;
        colocarPiezas(ajedrez, celdasGUI);
        inicializarPantalla();
        inicializarAccionVisualizarPosicionesPosibles(celdasGUI, ajedrez);
        inicializarAccionMoverPieza(celdasGUI, ajedrez);
        inicializarAccionDeBotones();
        cambiaColorDelLayOut(ajedrez);
    }


    private void inicializaComponentesSecundarios() {
        inicializarAyudaLabel();
        inicializarLabelColor();
        inicializarLabelFilasTablero();
        inicializarLabelColumnasTableros();
        inicializarBotonGuardarPartida();
        inicializarBotonTerminarPartida();
        inicializarBotonMostrarAyuda();
    }

    private void inicializarLabelColumnasTableros() {
        ImageIcon img2 = new ImageIcon("../GUI_Chess/src/recurso/columnasTablero.jpeg");
        labelColumnasTablero = new JLabel("", img2, JLabel.CENTER);
        labelColumnasTablero.setBounds(ANCHO_POR_ALTO, 0, 640, 20);
        labelColumnasTablero.setVisible(true);
        add(labelColumnasTablero);
    }

    private void inicializarLabelFilasTablero() {
        ImageIcon img1 = new ImageIcon("../GUI_Chess/src/recurso/filasTablero.jpeg");
        labelFilasTablero = new JLabel("", img1, JLabel.CENTER);
        labelFilasTablero.setBounds(ANCHO_POR_ALTO - 23, 25, 20, 640);
        labelFilasTablero.setVisible(true);
        add(labelFilasTablero);
    }

    private void inicializarLabelColor() {
        labelColor.setBounds(ANCHO_POR_ALTO * 9 + 20, 40, 180, 180);
        labelColor.setBackground(Color.WHITE);
        labelColor.setBorder(new LineBorder(Color.BLACK));
        labelColor.setVisible(true);
        add(labelColor);
    }

    private void inicializarAyudaLabel() {
        ImageIcon img = new ImageIcon("../GUI_Chess/src/recurso/ayuda.jpg");
        ayuda = new JLabel("", img, JLabel.CENTER);
        ayuda.setBounds(ANCHO_POR_ALTO + 6, ANCHO_POR_ALTO + 6, 800, 533);
        ayuda.setVisible(false);
        add(ayuda);

    }

    private void inicializarAccionDeBotones() {
        inicializarAccionGuardarPartida();
        inicializarAccionBotonTerminarPartida();
        inicializarAccionMostrarAyuda();

    }

    private void inicializarAccionMostrarAyuda() {
        botonMostrarAyuda.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ayuda.setVisible(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                ayuda.setVisible(false);
            }
        });
    }

    private void inicializarAccionBotonTerminarPartida() {
        botonTerminarPartida.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }

    private void inicializarAccionGuardarPartida() {
        botonGuardarPartida.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    OutputStream os = new FileOutputStream(JOptionPane.showInputDialog("Su partida se guardará como:  "));
                    ObjectOutputStream oos = new ObjectOutputStream(os);

                    oos.writeObject(ajedrez);
                    oos.close();

                    JOptionPane.showMessageDialog(null, "Guardado con éxito");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void inicializarAccionMoverPieza(CeldaGUI[][] celdasGUI, Partida ajedrez) {
        Celda[][] celdas = ajedrez.getTablero().getCeldas();

        for (int i = celdasGUI.length - 1; i >= 0; i--) {
            for (int j = 0; j < celdasGUI.length; j++) {
                int filaSalida = i;
                int columnaSalida = j;

                celdasGUI[i][j].addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        super.mouseDragged(e);
                        if (!celdasPosiblesAnterioresTemporales.isEmpty()) {
                            desmarcarCeldasPosiblesAnteriorTemporales(celdasPosiblesAnterioresTemporales);
                        }

                        try {
                            if (celdasGUI[filaSalida][columnaSalida].getPieza() == null) {
                                throw new ExcepcionInexistenciaDeUnaPieza(" No existe pieza a mover");
                            }
                        } catch (ExcepcionInexistenciaDeUnaPieza ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }
                });
                celdasGUI[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                        Point p = e.getPoint(); //crea un putno en refernecia al boton arrastrado siendo el origen la esquina superior izquierda
                        //System.out.println(p);
                        int aumentoColumna = calcularDiferenciaDePosicion(p.getX());
                        int aumentoFila = calcularDiferenciaDePosicion(p.getY()) * -1;
                        if (aumentoColumna == 0 && aumentoFila == 0) {
                            return;
                        }
                        int filaLlegada = filaSalida + aumentoFila;
                        int columnaLlegada = columnaSalida + aumentoColumna;
                        try {
                            if (ajedrez.obtenerEstadoDeLaPartida()) {
                                ajedrez.moverPieza(new Posicion(filaSalida, columnaSalida), new Posicion(filaLlegada, columnaLlegada));
                                actualizarGUI(ajedrez, celdasGUI, filaSalida, columnaSalida);
                                if (ajedrez.verificarReyEnJaque()) {
                                    JOptionPane.showMessageDialog(null, "Rey " + ajedrez.obtenerColorJugadorEnTurno() + " esta en Jake");
                                }
                                ajedrez.enviarEstadoDelRey(EstadoDelRey.VIVO);
                            } else {
                                JOptionPane.showMessageDialog(null, "Partida Finalizada");
                            }
                        } catch (ExcepcionVictoria eV) {
                            JOptionPane.showMessageDialog(null, eV.getMessage());
                            actualizarGUI(ajedrez, celdasGUI, filaSalida, columnaSalida);

                        } catch (ExcepcionTransformarPeon eP) {
                            try {
                                transformarPeon(filaLlegada, columnaLlegada, ajedrez, celdasGUI, filaSalida, columnaSalida);
                            } catch (ExcepcionVictoria eV2) {
                                JOptionPane.showMessageDialog(null, eV2.getMessage());
                                actualizarGUI(ajedrez, celdasGUI, filaSalida, columnaSalida);
                            }

                        } catch (ExcepcionAjedrez eA) {
                            JOptionPane.showMessageDialog(null, eA.getMessage());
                        }
                    }
                });
            }
        }
    }

    private void transformarPeon(int filaLlegada, int columnaLlegada, Partida ajedrez, CeldaGUI[][] celdasGUI, int filaSalida, int columnaSalida) throws ExcepcionVictoria {
        try {
            String menu = "Ingrese la pieza a reemplazar: ";
            menu += "\n 1. Reina";
            menu += "\n 2. Caballo";
            menu += "\n 3. Torre";
            menu += "\n 4. Alfil";
            Integer aux = 0;
            Integer piezaAIntroducir = 0;
            while (aux == 0) {
                try {
                    piezaAIntroducir = Integer.parseInt(JOptionPane.showInputDialog(menu));
                } catch (Exception e2) {
                    transformarPeon(filaLlegada, columnaLlegada, ajedrez, celdasGUI, filaSalida, columnaSalida);
                    return;
                }

                if (piezaAIntroducir > 0 && piezaAIntroducir <= 4) {
                    ajedrez.cambiarPeon(new Posicion(filaLlegada, columnaLlegada), piezaAIntroducir);
                    actualizarGUI(ajedrez, celdasGUI, filaSalida, columnaSalida);
                    aux = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Escoja una pieza dentro del rango definido en el menú");
                }
            }

        } catch (ExcepcionUbicacionFueraDeRango e3) {
        }
    }

    private void cambiaColorDelLayOut(Partida ajedrez) {
        if (ajedrez.obtenerColorJugadorEnTurno() != ColorAjedrez.CLARO) {
            labelColor.setBackground(Color.BLACK);
        } else {
            labelColor.setBackground(Color.WHITE);
        }
    }

    private void actualizarTableroGUI(Partida ajedrez, CeldaGUI[][] celdasGUI) {
        colocarPiezas(ajedrez, celdasGUI);
    }

    private void moverPiezaGUI(CeldaGUI celdaSalida, CeldaGUI celdaLlegada) {
        celdaLlegada.setPieza(celdaSalida.getPieza());
        celdaSalida.borrarPieza();
    }

    private void actualizarGUI(Partida ajedrez, CeldaGUI[][] celdasGUI, int filaSalida, int columnaSalida) {
        actualizarTableroGUI(ajedrez, celdasGUI);
        celdasGUI[filaSalida][columnaSalida].borrarPieza();
        cambiaColorDelLayOut(ajedrez);
    }

    private int calcularDiferenciaDePosicion(double x) {
        int diferencia = 0;
        if (x > 0) {
            while (x > ANCHO_POR_ALTO) {
                diferencia++;
                x = x - ANCHO_POR_ALTO;
            }
            return diferencia;
        }
        if (x < 0) {
            diferencia--;
            while (x < -ANCHO_POR_ALTO) {
                diferencia--;
                x = x + ANCHO_POR_ALTO;
            }
            return diferencia;
        }
        return 0;
    }


    private void inicializarAccionVisualizarPosicionesPosibles(CeldaGUI[][] celdasGUI, Partida ajedrez) {
        Celda[][] celdas = ajedrez.getTablero().getCeldas();
        celdasPosiblesAnterioresTemporales = new ArrayList<>();

        for (int i = 0; i < celdasGUI.length; i++) {
            for (int j = 0; j < celdasGUI[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                celdasGUI[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        desmarcarCeldasPosiblesAnteriorTemporales(celdasPosiblesAnterioresTemporales);
                        ArrayList<Posicion> posicionesPosiblesParaPieza;
                        if (!existePiezaEnCelda(celdas[finalI][finalJ])) {
                            return;
                        }
                        try {
                            posicionesPosiblesParaPieza = PosicionPosible.obtenerLasPosicionesPosibles(celdas[finalI][finalJ].getPieza(), new Posicion(finalI, finalJ), celdas);
                        } catch (ExcepcionUbicacionFueraDeRango ex) {
                            throw new RuntimeException(ex);
                        }
                        for (Posicion posicion : posicionesPosiblesParaPieza) {
                            int filaPosible = posicion.getFila();
                            int columnaPosible = posicion.getColumna();
                            celdasPosiblesAnterioresTemporales.add(celdasGUI[filaPosible][columnaPosible]);
                            celdasGUI[filaPosible][columnaPosible].marcar();
                        }
                    }
                });
            }
        }
    }

    private boolean existePiezaEnCelda(Celda celda) {
        return celda.existePieza();
    }

    public void desmarcarCeldasPosiblesAnteriorTemporales(ArrayList<CeldaGUI> celdasPosiblesAnterioresTemporales) {
        for (CeldaGUI celda : celdasPosiblesAnterioresTemporales) {
            celda.desmarcar();
        }
        celdasPosiblesAnterioresTemporales.clear();
    }

    private void colocarPiezas(Partida ajedrez, CeldaGUI[][] celdasGUI) {
        Celda[][] celdas = ajedrez.getTablero().getCeldas();
        for (int i = 0; i < celdasGUI.length; i++) {
            for (int j = 0; j < celdasGUI[i].length; j++) {
                celdasGUI[i][j].setPieza(celdas[i][j].getPieza());
            }
        }
    }

    private void inicializarBotonMostrarAyuda() {
        //botonMostrarAyuda.setBounds(0, ANCHO_POR_ALTO * FILA_X_COLUMNA + 24, 300, 30);
        botonMostrarAyuda.setBounds((ANCHO_POR_ALTO * 9) + 10, 0, 200, 30);
        botonMostrarAyuda.setFont(new Font("MONOSPACED", PLAIN, 16));
        botonMostrarAyuda.setOpaque(true);
        botonMostrarAyuda.setBorder(new LineBorder(Color.DARK_GRAY));
        botonMostrarAyuda.setVisible(true);
        botonMostrarAyuda.setBackground(new Color(255, 153, 153));
        add(botonMostrarAyuda);
    }

    private void inicializarBotonGuardarPartida() {
        botonGuardarPartida.setBounds(ANCHO_POR_ALTO * 10, 600, 155, 40);
        botonGuardarPartida.setFont(new Font("MONOSPACED", PLAIN, 16));
        botonGuardarPartida.setOpaque(true);
        botonGuardarPartida.setBorder(new LineBorder(Color.DARK_GRAY));
        botonGuardarPartida.setVisible(true);
        botonGuardarPartida.setBackground(new Color(200, 255, 255));
        add(botonGuardarPartida);
    }

    private void inicializarBotonTerminarPartida() {
        botonTerminarPartida.setBounds((ANCHO_POR_ALTO * 10) - 20, 550, 175, 40);
        botonTerminarPartida.setFont(new Font("MONOSPACED", PLAIN, 16));
        botonTerminarPartida.setOpaque(true);
        botonTerminarPartida.setBorder(new LineBorder(Color.DARK_GRAY));
        botonTerminarPartida.setVisible(true);
        botonTerminarPartida.setBackground(new Color(200, 255, 255));
        add(botonTerminarPartida);
    }

    //Con este método se puede rotar el tablero e iniciar la partida con otro color
    private void inicializarCuadriculaDeBotones(CeldaGUI[][] celda) {
        int x = ANCHO_POR_ALTO;
        int y = 25;
        int aumento = ANCHO_POR_ALTO;
        for (int i = celda.length - 1; i >= 0; i--) {
            for (int j = 0; j < celda.length; j++) {
                celda[i][j] = new CeldaGUI();
                if ((i % 2) == 0) {
                    if ((j % 2) == 0) {
                        inicializarBoton(celda[i][j], x, y, new Color(132, 135, 135));
                    } else {
                        inicializarBoton(celda[i][j], x, y, new Color(255, 252, 240));
                    }
                } else {
                    if ((j % 2) == 0) {
                        inicializarBoton(celda[i][j], x, y, new Color(255, 252, 240));
                    } else {
                        inicializarBoton(celda[i][j], x, y, new Color(132, 135, 135));
                    }
                }
                x = x + aumento;
            }
            y = y + aumento;
            x = aumento;
        }
    }


    private void inicializarPantalla() {
        setTitle("Juego en progreso");
        setBounds(900, 900, (ANCHO_POR_ALTO * 13) - 40, ANCHO_POR_ALTO * 9);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void inicializarBoton(JButton boton, int x, int y, Color color) {
        boton.setBounds(x, y, ANCHO_POR_ALTO, ANCHO_POR_ALTO);
        boton.setBackground(color);
        boton.setOpaque(true);
        boton.setBorder(new LineBorder(Color.DARK_GRAY));
        boton.setVisible(true);
        add(boton);
    }


}
