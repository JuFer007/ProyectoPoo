package APP_RESTAURANTE.CONEXIONBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD_SQL {
    private Connection conexion = null;
    private static ConexionBD_SQL instancia = null;

    public ConexionBD_SQL() {
        String cadenaConexionBD = "jdbc:sqlserver://localhost:1433;databaseName=RESTAURANTE;user=sa;password=Fjunior07!;encrypt=false;trustServerCertificate=true";
        try {
            conexion = DriverManager.getConnection(cadenaConexionBD);
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public static ConexionBD_SQL getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD_SQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}