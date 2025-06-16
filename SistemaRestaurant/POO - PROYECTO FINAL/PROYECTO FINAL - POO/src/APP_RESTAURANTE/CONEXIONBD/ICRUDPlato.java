package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Plato;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ICRUDPlato {
    public ICRUDPlato() {
    }

    //Agregar
    public void crear(Plato nuevoPlato) {
        String consultaInsertCrearPlato = "{ CALL usp_CrearPlato (?, ?, ?)}";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertCrearPlato);
            comando.setString(1, nuevoPlato.getNombrePlato());
            comando.setDouble(2, nuevoPlato.getPrecioPlato());
            comando.setInt(3, nuevoPlato.getIdMenu());
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Eliminar
    public void eliminar(String nombrePlato) {
        String resultado = "";
        String consultaInsertEliminarPlato = "{ CALL usp_EliminarPlato(?)}";
        try{
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertEliminarPlato);
            comando.setString(1, nombrePlato);

            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    resultado = rs.getString("mensaje");
                    System.out.println(resultado);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Modificar
    public void actualizar(Plato plato) {
        String mensaje = "";
        String consultaInsertActualizarPlato = "{ CALL usp_ActualizarPlato(?, ?, ?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertActualizarPlato);
            comando.setString(1, plato.getNombrePlato());
            comando.setDouble(2, plato.getPrecioPlato());
            comando.setInt(3, plato.getIdMenu());

            boolean resultadoConsulta = comando.execute();

            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    if (rs.next()) {
                        if (rs.getMetaData().getColumnLabel(1).equals("mensaje")) {
                            mensaje = rs.getString("mensaje");
                        }
                    }
                }
            }
            comando.execute();
        } catch (Exception e) {
            mensaje = "Error al actualizar los datos del empleado acatualizado.";
            System.out.println(mensaje);
        }
    }

    //Listar
    public List<Plato> listarPlato(){
        List<Plato> platos = new ArrayList<>();
        String consultaInsertListarPlato = "{ CALL usp_ListarPlatos()}";
        try{
            //Conexion y consulta a bd
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertListarPlato);
            boolean resultadoConsulta = comando.execute();
            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()){
                    while (rs.next()){
                        String nombrePlato = rs.getString("nombrePlato");
                        double precioPlato = rs.getDouble("precioPlato");
                        int idMenu = rs.getInt("idMenu");

                        Plato plato = new Plato(nombrePlato, precioPlato, idMenu);
                        platos.add(plato);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return platos;
    }
}
