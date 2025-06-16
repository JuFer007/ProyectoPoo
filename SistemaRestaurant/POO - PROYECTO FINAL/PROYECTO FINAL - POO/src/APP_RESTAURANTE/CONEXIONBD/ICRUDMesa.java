package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Mesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ICRUDMesa {
    //Constructor
    public ICRUDMesa() {
    }

    //Mostrar todas las mesas y sus datos
    public List<Mesa> listarTodasLasMesas() {
        String consulta = "SELECT idMesa, numeroMesa, capacidadMesa, estadoMesa, idMesero, estado_reserva, idClienteReserva FROM Mesa";
        List<Mesa> listaMesas = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setCapacidadMesa(rs.getInt("capacidadMesa"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                mesa.setIdMesero(rs.getInt("idMesero"));
                mesa.setEstadoReserva(rs.getString("estado_reserva"));
                mesa.setIdClienteReserva(rs.getInt("idClienteReserva"));
                listaMesas.add(mesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMesas;
    }

    //Mostrar mesas ocupadas
    public List<Mesa> listarMesasOcupadas() {
        String consulta = "SELECT idMesa, numeroMesa, capacidadMesa, estadoMesa, idMesero, estado_reserva, idClienteReserva FROM Mesa WHERE estadoMesa = 'Ocupada'";
        List<Mesa> listaMesas = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setCapacidadMesa(rs.getInt("capacidadMesa"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                mesa.setIdMesero(rs.getInt("idMesero"));
                mesa.setEstadoReserva(rs.getString("estado_reserva"));
                mesa.setIdClienteReserva(rs.getInt("idClienteReserva"));
                listaMesas.add(mesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMesas;
    }

    //Mostrar mesas libres
    public List<Mesa> listarMesasLibres() {
        String consulta = "SELECT idMesa, numeroMesa, capacidadMesa, estadoMesa, idMesero, estado_reserva, idClienteReserva FROM Mesa WHERE estadoMesa = 'Libre'";
        List<Mesa> listaMesas = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setCapacidadMesa(rs.getInt("capacidadMesa"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                mesa.setIdMesero(rs.getInt("idMesero"));
                mesa.setEstadoReserva(rs.getString("estado_reserva"));
                mesa.setIdClienteReserva(rs.getInt("idClienteReserva"));
                listaMesas.add(mesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMesas;
    }

    //Modificar capacidad de mesa
    public boolean modificarCapacidadMesa(int idMesa, int nuevaCapacidad) {
        String consulta = "{CALL ModificarCapacidadMesa(?, ?)}";
        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta)) {
            comando.setInt(1, idMesa);
            comando.setInt(2, nuevaCapacidad);
            comando.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Asignar reserva a un cliente
    public void agregarReservacion(int idMesa, int idCliente) {
        String consultaAsignarReserva = "UPDATE Mesa SET idClienteReserva = ? WHERE idMesa = ?"
        + "UPDATE Mesa SET estado_reserva = 'Reservado' WHERE idMesa = ?";
        try {
            PreparedStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareStatement(consultaAsignarReserva);
            comando.setInt(1, idCliente);
            comando.setInt(2, idMesa);
            comando.setInt(3, idMesa);
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Asignar asignar mesero a mesa libre
    public void asignarMeseroAmesa(int idMesero, int idMesa){
        String consulta = "{ CALL usp_AsignarMeseroAmesa(?, ?) }";
        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta)) {
            comando.setInt(1, idMesero);
            comando.setInt(2, idMesa);
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
