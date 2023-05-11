import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


public class VueloAñadir extends JFrame implements ActionListener {
    public JButton btnaceptar, btnlimpiar;
    public JTextField[] camposTexto;
    private static final String url = "jdbc:mariadb://localhost:3306/aeropuerto";
    private static final String user = "root";
    private static final String password = "12345";
    public JFrame frame;
    public JPanel terceFila;
    public DefaultListModel<Vuelo> model;
    VueloVista vueloVista;


    public JList Vuelo;


    public void VueloAñadir() {
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

        JPanel datos = new JPanel(new GridLayout(1, 2, 30, 20));
        JPanel labeldatos = new JPanel(new GridLayout(8, 1, 10, 10));
        labeldatos.add(new JLabel("ID VUELO: "));
        labeldatos.add(new JLabel("ID AVION "));
        labeldatos.add(new JLabel("ORIGEN "));
        labeldatos.add(new JLabel("DESTINO: "));

        labeldatos.add(new JLabel("HORA SALIDA "));
        labeldatos.add(new JLabel("HORA LLEGADA"));
        datos.add(labeldatos);


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
        datos.add(textodatos);
        segunFila.add(datos);

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


    }



    /*
     *
     *
     *
     * ARREGLAR DESTINO Y MIRAR LA CONSULTA PARA QUE NO COGA BILBAO POR DEFECTO
     *
     *
     **/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnlimpiar) {
            for (JTextField campo : camposTexto) {
                campo.setText("");
                //Establece al inicio el textfield camposTexto[0].requestFocus();

            }
        } else if (e.getSource() == btnaceptar) {

            int idvuelo = Integer.parseInt(camposTexto[0].getText());
            String idAvion = String.valueOf(Integer.parseInt(camposTexto[1].getText()));
            String origen = camposTexto[2].getText();
            String destino = camposTexto[3].getText();
            Time horaSalida;
            Time horaLlegada;

            try {
                horaSalida = Time.valueOf(camposTexto[4].getText());
                horaLlegada = Time.valueOf(camposTexto[5].getText());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid time format. Please enter times in the format 'hh:mm:ss'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int trayecto = 7;

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO trayecto (idTrayecto, Origen, Destino) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                stmt.setInt(1, trayecto);
                stmt.setString(2, origen);
                stmt.setString(3, destino);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                trayecto++;

                stmt = conn.prepareStatement("INSERT INTO vuelos (idVUELOS, HoraSalida, HoraLlegada, Fecha, Aviones_idAviones, Trayecto_idTrayecto) VALUES (?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, idvuelo);
                stmt.setTime(2, horaSalida);
                stmt.setTime(3, horaLlegada);
                stmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
                stmt.setString(5, idAvion);
                stmt.setInt(6, trayecto);
                stmt.executeUpdate();


            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
    }
}












