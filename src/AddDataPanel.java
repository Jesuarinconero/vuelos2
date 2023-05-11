import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class AddDataPanel extends JPanel implements ActionListener {
    private static final String URL = "jdbc:mariadb://localhost:3306/aeropuerto";
    private static final String USER = "root";
    private static final String PASSWORD = "dam2223";



    private final JFrame frame;
    private final DefaultListModel<Miembro> model;
    //private final JTextField nombreField, apellido1Field, apellido2Field, edadField, dniField, telefonoField, ecorreoField, direccionField, categoriaField, infoAdicionalField;

    private final  JList jList;
    private JTextField[] camposTexto;
    private ButtonGroup grupoBotones;
    private JTextArea panelinformacion;

    private  JButton btnaceptar ;
    private JButton btnlimpiar;

    private JPanel textodatos;



    public AddDataPanel(JFrame frame, DefaultListModel<Miembro> model,JList<Miembro> jList) {
        this.frame = Objects.requireNonNull(frame);
        this.model = Objects.requireNonNull(model);
        this.jList = Objects.requireNonNull(jList);

        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Primera fila
        JPanel primeraFila = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        primeraFila.setBorder(new EmptyBorder(1, 1, 10, 1));

        JPanel foto = new JPanel();
        foto.add(new JLabel("FOTO"));
        primeraFila.add(foto);

        JPanel botones = new JPanel(new GridLayout(3, 1, 10, 10));
        botones.add(new JButton("Añadir"));
        botones.add(new JButton("Borrar"));
        botones.add(new JButton("Cambiar"));
        primeraFila.add(botones);

        JPanel categoria = new JPanel(new GridLayout(2, 1, 10, 10));
        categoria.add(new JLabel("Categoría:"));

        JPanel radioBotones = new JPanel();
        grupoBotones = new ButtonGroup();
        JRadioButton botonPiloto = new JRadioButton("Piloto");
        JRadioButton botonCopiloto = new JRadioButton("Copiloto");
        JRadioButton botonAzafata = new JRadioButton("Azafata");

        grupoBotones.add(botonPiloto);
        grupoBotones.add(botonCopiloto);
        grupoBotones.add(botonAzafata);
        radioBotones.add(botonPiloto);
        radioBotones.add(botonCopiloto);
        radioBotones.add(botonAzafata);
        categoria.add(radioBotones);
        primeraFila.add(categoria);

        // Segunda fila
        JPanel segunFila = new JPanel(new GridLayout(1, 1, 20, 10));

        JPanel datos = new JPanel(new GridLayout(1, 2, 30, 20));
        JPanel labeldatos = new JPanel(new GridLayout(8, 1, 10, 10));
        labeldatos.add(new JLabel("Nombre: "));
        labeldatos.add(new JLabel("Apellido 1: "));
        labeldatos.add(new JLabel("Apellido 2: "));
        labeldatos.add(new JLabel("Edad: "));
        labeldatos.add(new JLabel("DNI: "));
        labeldatos.add(new JLabel("Teléfono: "));
        labeldatos.add(new JLabel("Correo electrónico: "));
        labeldatos.add(new JLabel("Dirección: "));
        datos.add(labeldatos);

        textodatos = new JPanel(new GridLayout(8, 1, 20, 20));
        camposTexto = new JTextField[] {
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JTextField(10)
        };
        for (JTextField campo : camposTexto) {
            textodatos.add(campo);
        }
        datos.add(textodatos);
        segunFila.add(datos);


        JPanel informacion = new JPanel();
        informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));


        /// TextArea
        JLabel label = new JLabel("Información adicional:");
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        informacion.add(label);

        // Cambiar la declaración de panelinformacion a JTextArea
        panelinformacion = new JTextArea();
        panelinformacion.setLineWrap(true); // Ajuste de línea automático
        panelinformacion.setWrapStyleWord(true); // Ajuste de línea en palabras completas

        // Envuelve el JTextArea en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelinformacion);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        informacion.add(scrollPane);
        informacion.add(new JLabel("   "));
        informacion.add(new JLabel("   "));
        informacion.add(new JLabel("   "));
        informacion.add(new JLabel("   "));


        segunFila.add(informacion);

        // Tercera fila
        JPanel terceFila = new JPanel(new FlowLayout(1, 20, 0));
        terceFila.setBorder(new EmptyBorder(20, 0, 20, 0));
        btnaceptar = new JButton("Aceptar");
        btnlimpiar = new JButton("Limpiar");
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

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnlimpiar){
            for (Component c : textodatos.getComponents()) {
                if (c instanceof JTextField) {
                    ((JTextField) c).setText("");
                }
            }
            // Limpiar botones de radio
            grupoBotones.clearSelection();

            // Limpiar JTextArea
            panelinformacion.setText("");

        }if ("Aceptar".equals(e.getActionCommand())) {
            String nombre = camposTexto[0].getText();
            String apellido1 = camposTexto[1].getText();
            String apellido2 = camposTexto[2].getText();
            int edad;
            try {
                edad = Integer.parseInt(camposTexto[3].getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String dni = camposTexto[4].getText();
            String telefono = camposTexto[5].getText();
            String ecorreo = camposTexto[6].getText();
            String direccion = camposTexto[7].getText();
            String categoria = "";
            for (Enumeration<AbstractButton> buttons = grupoBotones.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    categoria = button.getText();
                    break;
                }
            }
            String infoAdicional = panelinformacion.getText();

            if (nombre.isEmpty() || apellido1.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a name, first last name, and DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO aeropuerto.miembros (nombre, apellido1, apellido2,Edad, dni, telefono, ecorreo, direccion, categoria,InfoAdicional) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)")) {
                stmt.setString(1, nombre);
                stmt.setString(2, apellido1);
                stmt.setString(3, apellido2);
                stmt.setInt(4, edad);
                stmt.setString(5, dni);
                stmt.setString(6, telefono);
                stmt.setString(7, ecorreo);
                stmt.setString(8, direccion);
                stmt.setString(9, categoria);
                stmt.setString(10, infoAdicional);
                stmt.executeUpdate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //mejorar esto
            Miembro persona = new Miembro( dni,nombre,categoria);
            model.addElement(persona);//solo el nombre al JList
            jList.validate();
            jList.repaint();

            JOptionPane.showMessageDialog(frame, "Tripulante añadido");

        } else if ("Cancel".equals(e.getActionCommand())) {

            frame.dispose();
        }
    }
}