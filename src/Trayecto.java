import java.io.*;
import java.sql.*;

public class Trayecto {

    static int idtrayecto;
    static String origen;
    static String destino;



    public void insertTrayecto() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection conexBD = DriverManager.getConnection("jdbc:mariadb://localhost:3306/aeropuerto", "root", "dam2223");
        String consulta = "SELECT COUNT(*) FROM trayecto";
        Statement statement = conexBD.createStatement();
        ResultSet resultado = statement.executeQuery(consulta);

        if (resultado.next() && resultado.getInt(1) != 0) {
            System.out.println("Tabla ya cargada");
        }else {
            FileInputStream fis = null;
            DataInputStream entrada = null;
            PreparedStatement pstmt = null;
            try {
                fis = new FileInputStream("src/Trayectos.dat");
                entrada = new DataInputStream(fis);
                while (true) {
                    idtrayecto = entrada.readInt();
                    origen = entrada.readUTF();
                    destino = entrada.readUTF();
                    System.out.printf("\n\t%d Origen: %s → Destino: %s",
                            idtrayecto, origen, destino);

                    String sql = "INSERT INTO trayecto (idtrayecto, origen, destino) VALUES (?, ?, ?)";
                    pstmt = conexBD.prepareStatement(sql);
                    pstmt.setInt(1, idtrayecto);
                    pstmt.setString(2, origen);
                    pstmt.setString(3, destino);
                    int filActualizadas = pstmt.executeUpdate();
                    System.out.printf(" %d registro\n", filActualizadas);

                }
            } catch (FileNotFoundException fnfe) {
                System.out.println(fnfe.getMessage());
            } catch (EOFException eofe) {
                System.out.println("\n\nFin de fichero");
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (entrada != null) {
                        entrada.close();
                    }
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }
        }


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Trayecto m= new Trayecto();
        m.insertTrayecto();
    }

}