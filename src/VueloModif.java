import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class VueloModif extends JFrame implements ActionListener {
    public JButton btnaceptar, btnlimpiar;
    public JTextField[] camposTexto;
    private static final String url = "jdbc:mariadb://localhost:3306/aeropuerto";
    private static final String user = "root";
    private static final String password = "12345";
    public JFrame frame;
    public JPanel terceFila;
    public DefaultListModel<Vuelo> model;


    private int idVuelo;
    private String idAvion;
    private String origen;
    private String destino;
    private Time horaSalida;
    private Time horaLlegada;
    private List<Vuelo> datos;

    public VueloModif(int idVuelo, String idAvion, String origen, String destino, Time horaSalida, Time horaLlegada) {
        this.idVuelo = idVuelo;
        this.idAvion = idAvion;
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;


    }

    public VueloModif() {

    }





    public void VueloModif() {
        setSize(500, 600);
        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Primera fila
        JPanel primeraFila = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        primeraFila.setBorder(new EmptyBorder(1, 1, 10, 1));
        JLabel letraVuelo = new JLabel("VUELO");
        primeraFila.add(letraVuelo);

        // Segunda fila
        JPanel segunFila = new JPanel(new GridLayout(1, 1, 20, 10));

        JPanel dato = new JPanel(new GridLayout(1, 2, 30, 20));
        JPanel labeldatos = new JPanel(new GridLayout(8, 1, 10, 10));
        labeldatos.add(new JLabel("ID VUELO: "));
        labeldatos.add(new JLabel("ID AVION "));
        labeldatos.add(new JLabel("ORIGEN "));
        labeldatos.add(new JLabel("DESTINO: "));

        labeldatos.add(new JLabel("HORA SALIDA "));
        labeldatos.add(new JLabel("HORA LLEGADA"));
        dato.add(labeldatos);


        JPanel textodatos = new JPanel(new GridLayout(8, 1, 20, 20));
        camposTexto = new JTextField[]{
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),

        };
        for (JTextField campo : camposTexto) {
            textodatos.add(campo);
        }
        dato.add(textodatos);
        segunFila.add(dato);

        // Tercera fila
        terceFila = new JPanel(new FlowLayout(1, 20, 0));
        terceFila.setBorder(new EmptyBorder(20, 0, 20, 0));

        btnaceptar = new JButton("ACEPTAR");
        btnlimpiar = new JButton("LIMPIAR CAMPOS");
        terceFila.add(btnaceptar);
        terceFila.add(btnlimpiar);

        // Registrar el controlador de eventos
        btnaceptar.addActionListener(this);
        btnlimpiar.addActionListener(this);

        // Añadir paneles
        principal.add(primeraFila);
        principal.add(segunFila);
        principal.add(terceFila);
        add(principal);
        setVisible(true);
        // Conectar con la base de datos y hacer la consulta



    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnaceptar) {
                // Recuperar los datos de los campos de texto
                int idVuelo = Integer.parseInt(camposTexto[0].getText());
                int idAvion = Integer.parseInt(camposTexto[1].getText());
                String origen = camposTexto[2].getText();
                String destino = camposTexto[3].getText();
                Time horaSalida = Time.valueOf(camposTexto[4].getText());
                Time horaLlegada = Time.valueOf(camposTexto[5].getText());

                // Conectar con la base de datos y actualizar el registro correspondiente
                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    // Actualizar la tabla de vuelos
                    String queryVuelos = "UPDATE VUELOS SET HoraSalida = ?, HoraLlegada = ?, Aviones_idAviones = ? WHERE idVuelos = ?";
                    PreparedStatement statementVuelos = conn.prepareStatement(queryVuelos);
                    statementVuelos.setTime(1, horaSalida);
                    statementVuelos.setTime(2, horaLlegada);
                    statementVuelos.setInt(3, idAvion);
                    statementVuelos.setInt(4, idVuelo);
                    int rowsUpdatedVuelos = statementVuelos.executeUpdate();

                    // Actualizar la tabla de trayectos
                    String queryTrayectos = "UPDATE TRAYECTO SET Origen = ?, Destino = ? WHERE ";
                    queryTrayectos += "Destino = ? AND idTrayecto IN ";
                    queryTrayectos += "(SELECT Trayecto_idTrayecto FROM VUELOS WHERE idVuelos = ?)";
                    PreparedStatement statementTrayectos = conn.prepareStatement(queryTrayectos);
                    statementTrayectos.setString(1, origen);
                    statementTrayectos.setString(2, destino);
                    statementTrayectos.setString(3, destino);
                    statementTrayectos.setInt(4, idVuelo);
                    int rowsUpdatedTrayectos = statementTrayectos.executeUpdate();

                    if (rowsUpdatedVuelos > 0 && rowsUpdatedTrayectos > 0) {
                        JOptionPane.showMessageDialog(null, "El vuelo ha sido modificado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el vuelo.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar el vuelo: " + ex.getMessage());
                }
            }

        }
    }

