import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//VERSION FUNCIONA

public class JListExample implements ActionListener {
    JFrame frame;
    private JList<Miembro> list;
    private DefaultListModel<Miembro> model;
    private JTextField textField;
    private JButton addButton, modifyButton, viewButton, deleteButton;

    private static final String url = "jdbc:mariadb://localhost:3306/aeropuerto";
    private static final String user = "root";
    private static final String password = "dam2223";

    static {
        try {
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

    public JListExample() {
        initialize();
        loadData();
    }

    private void initialize() {
        frame = new JFrame("Tripulación");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);

        addButton = new JButton("Añadir");
        addButton.addActionListener(this);

        modifyButton = new JButton("Modificar");
        modifyButton.addActionListener(this);

        viewButton = new JButton("Ver");
        viewButton.addActionListener(this);

        deleteButton = new JButton("Borrar");
        deleteButton.addActionListener(this);

        textField = new JTextField();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.NORTH);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<Miembro> data = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT dni, nombre, categoria FROM miembros")) {
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                Miembro persona = new Miembro( dni,nombre,categoria); //el orden importa !!!!
                data.add(persona);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Miembro item : data) {
            model.addElement(item);
        }
    }


    private void modifyData(Miembro oldItem ,Miembro newItem) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("UPDATE miembros SET nombre = ?, dni = ?, categoria = ? WHERE dni = ?")) {
            stmt.setString(1, newItem.getNombre());
            stmt.setString(2, newItem.getDni());
            stmt.setString(3, newItem.getCategoria());
            stmt.setString(4, oldItem.getDni());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int index = model.indexOf(oldItem);
        model.setElementAt(newItem, index);
    }

    private void viewData(Miembro item) {
        textField.setText(item.getNombre());

        // Agregar el código necesario para mostrar los otros campos
    }



    private void deleteData(Miembro item) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM miembros WHERE dni = ?")) {
            stmt.setString(1, item.getDni());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.removeElement(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            AddDataPanel panel = new AddDataPanel(frame, model,list);
            JDialog dialog = new JDialog(frame, "Add Item", true);
            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);

        } else if (e.getSource() == viewButton) {
            Miembro item = list.getSelectedValue();
            if (item != null) {
                ViewDialog dialog = new ViewDialog(frame, item);
                dialog.setVisible(true);
            }
        }



        /*else if (e.getSource() == modifyButton) {
            Miembro oldItem = list.getSelectedValue();
            String nombre = textField.getText();
            if (oldItem != null && nombre != null && !nombre.isEmpty()) {
                oldItem.setNombre(nombre);
                modifyData(oldItem,nombre);
            }
            textField.setText("");
        } else if (e.getSource() == viewButton) {
            Miembro item = list.getSelectedValue();
            if (item != null) {
                viewData(item);
            }
        } */else if (e.getSource() == deleteButton) {
            Miembro item = list.getSelectedValue();
            if (item != null) {
                deleteData(item);
            }
            textField.setText("");
        }
    }
}