import java.awt.*;
import java.sql.SQLException;

public class VueloControler {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VueloVista vueloVista = null;
            try {
                vueloVista = new VueloVista();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
