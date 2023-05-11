import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueloVista implements ActionListener {
    public JFrame frame;
    private DefaultTableModel model;
    public JTable tablaPasajeros;
    public JButton btnadd, btnmodificar, btnborrar;

    private static final String url = "jdbc:mariadb://localhost:3306/aeropuerto";
    private static final String user = "root";
    private static final String password = "12345";


    public VueloVista() throws SQLException, ClassNotFoundException {
        iniciar();
        cargarDatos();

    }


    private void deleteData(Object item) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM vuelos WHERE idVUELOS = ?")) {
            stmt.setString(1, String.valueOf((item)));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() throws ClassNotFoundException, SQLException {

        List<Vuelo> data = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT v.idVUELOS, v.HoraSalida, v.HoraLlegada, v.Fecha, v.Aviones_idAviones, t.Origen, t.Destino, a.idAviones, v.Trayecto_idTrayecto\n" +
                             "FROM vuelos v \n" +
                             "JOIN trayecto t ON v.Trayecto_idTrayecto = t.idTrayecto\n" +
                             "JOIN aviones a ON v.Aviones_idAviones = a.idAviones");) {

            while (rs.next()) {
                int idVuelo = rs.getInt("idVUELOS");
                String horaSalida = rs.getString("HoraSalida");
                String horaLlegada = rs.getString("HoraLlegada");
                String fecha = rs.getString("Fecha");
                int idAvion = rs.getInt("idAviones");
                String idTrayecto = rs.getString("Trayecto_idTrayecto");
                String destino = rs.getString("Destino");
                String origen = rs.getString("Origen");
                String llegada = rs.getString("HoraLlegada");
                Vuelo vuelo = new Vuelo(idVuelo, origen, destino, horaSalida, horaLlegada, fecha);
                vuelo.setIdAvion(idAvion);
                vuelo.setIdTrayecto(idTrayecto);
                data.add(vuelo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = data.size() - 1; i >= 0; i--) {
            Vuelo item = data.get(i);
            model.addRow(new Object[]{item.getIdVuelo(), item.getIdAvion(), item.getOrigen(), item.getDestino(), item.getHoraSalida(), item.getHoraLlegada()});
        }
    }

    public void iniciar() {
        frame = new JFrame("Tripulación");
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel panelprincipal = new JPanel(new BorderLayout());


        model = new DefaultTableModel(new String[]{"ID Vuelos", "ID Avion", "Origen", "Destino", "Hora de Salida", "Hora de Llegada"}, 0);
        tablaPasajeros = new JTable(model);


        //No permite al usuario editar nada  tablaPasajeros.setEnabled(false);


        TableColumn coldIdVuelo = tablaPasajeros.getColumnModel().getColumn(0);
        coldIdVuelo.setPreferredWidth(100);

        TableColumn colIdAvion = tablaPasajeros.getColumnModel().getColumn(1);
        colIdAvion.setPreferredWidth(100);
        TableColumn colOrigen = tablaPasajeros.getColumnModel().getColumn(2);
        colOrigen.setPreferredWidth(100);
        TableColumn colDestino = tablaPasajeros.getColumnModel().getColumn(3);
        colDestino.setPreferredWidth(100);
        TableColumn colhoraDeSalida = tablaPasajeros.getColumnModel().getColumn(4);
        colhoraDeSalida.setPreferredWidth(100);
        TableColumn colhoraDellegada = tablaPasajeros.getColumnModel().getColumn(5);
        colhoraDellegada.setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane();
        JPanel paneltabla = new JPanel();
        scrollPane.setViewportView(tablaPasajeros);
        paneltabla.add(scrollPane);
        panelprincipal.add(paneltabla, BorderLayout.NORTH);
        tablaPasajeros = new JTable(model);


        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        btnadd = new JButton("Añadir Vuelo");
        btnmodificar = new JButton("Modificar Vuelo");
        btnborrar = new JButton("Eliminar Vuelo");
        botones.add(btnadd);
        botones.add(btnmodificar);
        botones.add(btnborrar);
        btnadd.addActionListener(this);
        btnmodificar.addActionListener(this);
        btnborrar.addActionListener(this);

        panelprincipal.add(botones, BorderLayout.CENTER);
        frame.add(panelprincipal);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnadd) {
            VueloAñadir vueloAñadir = new VueloAñadir();
            vueloAñadir.VueloAñadir();
        } else if (e.getSource() == btnmodificar) {
            VueloModif vueloModif = new VueloModif();
            vueloModif.VueloModif();
        } else if (e.getSource() == btnborrar) {
            int selec = tablaPasajeros.getSelectedRow();
            Object idP = tablaPasajeros.getValueAt(selec, 0);


            model.removeRow(selec);
            deleteData(idP);
            tablaPasajeros.repaint();
        }
    }
}

