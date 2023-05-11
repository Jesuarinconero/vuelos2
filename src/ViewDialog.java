import javax.swing.*;
import java.awt.*;

public class ViewDialog extends JDialog {
    private JLabel dniLabel;
    private JLabel nameLabel;
    private JLabel categoryLabel;

    public ViewDialog(JFrame parent, Miembro miembro) {
        super(parent, "View Item", true);

        dniLabel = new JLabel("DNI: " + miembro.getDni());
        nameLabel = new JLabel("Nombre: " + miembro.getNombre());
        categoryLabel = new JLabel("Categoría: " + miembro.getCategoria());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(dniLabel);
        panel.add(nameLabel);
        panel.add(categoryLabel);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}