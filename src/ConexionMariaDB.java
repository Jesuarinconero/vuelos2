import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexionMariaDB {

    Connection conexion = null;
    Statement comando = null;
    ResultSet registro;

    public Connection MariaDB() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String servidor = "jdbc:mariadb://localhost:3306/aeropuerto";
            String usuario = "root";
            String pass = "12345";
            conexion = DriverManager.getConnection(servidor, usuario, pass);

            // Crea el Statement y ejecuta una consulta para probar la conexión
            comando = conexion.createStatement();
            registro = comando.executeQuery("SELECT 1");

            // Cierra el ResultSet y el Statement
            registro.close();
            comando.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se encontró el driver de la base de datos.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + ex.getMessage());
        }

        return conexion;
    }

}
