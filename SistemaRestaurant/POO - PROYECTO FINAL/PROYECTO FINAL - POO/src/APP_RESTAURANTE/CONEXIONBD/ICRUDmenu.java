package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Plato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ICRUDmenu {

    //Constructor
    public ICRUDmenu() {
    }

    public ArrayList<Plato> listarMenu(String nombreMenu, int idMenu) {
        ArrayList<Plato> listPlatosPorMenu = new ArrayList<>();
        String consultaListarPlatosDeMenu = "SELECT Plato.numeroPlato, Plato.nombrePlato, Plato.precioPlato, Plato.idMenu " +
        "FROM Plato " +
        "INNER JOIN Menu ON Plato.idMenu = Menu.idMenu " +
        "WHERE Menu.nombreMenu = ? AND Plato.idMenu = ?";

        try (PreparedStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareStatement(consultaListarPlatosDeMenu)) {
            comando.setString(1, nombreMenu);
            comando.setInt(2, idMenu);

            try (ResultSet resultSet = comando.executeQuery()) {
                while (resultSet.next()) {
                    Plato plato = new Plato();
                    plato.setIdMenu(resultSet.getInt("idMenu"));
                    plato.setNumeroPlato(resultSet.getInt("numeroPlato"));
                    plato.setNombrePlato(resultSet.getString("nombrePlato"));
                    plato.setPrecioPlato(resultSet.getDouble("precioPlato"));
                    listPlatosPorMenu.add(plato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPlatosPorMenu;
    }
}
