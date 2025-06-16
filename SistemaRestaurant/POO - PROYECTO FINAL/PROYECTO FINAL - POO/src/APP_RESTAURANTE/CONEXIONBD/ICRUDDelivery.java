package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Cliente;
import APP_RESTAURANTE.CLASES.Delivery;
import APP_RESTAURANTE.CLASES.Pedido;
import APP_RESTAURANTE.CLASES.Repartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ICRUDDelivery {
    //Constructor
    public ICRUDDelivery() {
    }

    //Modificar datos de delivery
    public void modificarDatosDelivery(int idDelivery, String nuevaDireccion) {
        String consultaInsertModificarDelivery = "{CALL usp_ModificarDelivey(?, ?)}";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertModificarDelivery);
            comando.setInt(1, idDelivery);
            comando.setString(2, nuevaDireccion);
            comando.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Listar todos los deliverys
    public ArrayList<Delivery> listarDeliverys() {
        String consultaListarDeliverys = "{CALL usp_ListarDelivery()}";
        ArrayList<Delivery> listaDelivery = new ArrayList<>();
        CallableStatement comando = null;
        ResultSet rs = null;
        try {
            comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaListarDeliverys);
            rs = comando.executeQuery();
            while (rs.next()) {
                int idDelivery = rs.getInt("idDelivery");
                String direccionEntrega = rs.getString("direccionEntrega");
                String estadoDelivery = rs.getString("estadoDelivery");
                int idPedido = rs.getInt("idPedido");
                int idRepartidor = rs.getInt("idRepartidor");
                Delivery delivery = new Delivery(idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor);
                listaDelivery.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (comando != null) comando.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaDelivery;
    }

    //Listar los deliverys pendientes
    public List<Delivery> listaDeliverysPendiente() {
        String consultaListarDeliveryPendiente = "SELECT idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor FROM DELIVERY WHERE estadoDelivery = 'Pendiente'";
        List<Delivery> listaDeliverysPendientes = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaListarDeliveryPendiente);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                int idDelivery = rs.getInt("idDelivery");
                String direccionEntrega = rs.getString("direccionEntrega");
                String estadoDelivery = rs.getString("estadoDelivery");
                int idPedido = rs.getInt("idPedido");
                int idRepartidor = rs.getInt("idRepartidor");
                Delivery delivery = new Delivery(idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor);
                listaDeliverysPendientes.add(delivery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDeliverysPendientes;
    }

    //Listar los deliverys entregados
    public List<Delivery> listaDeliverysEntregados() {
        String consultaListarDeliveryPendiente = "SELECT idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor FROM DELIVERY WHERE ESTADODELIVERY = 'ENTREGADO'";
        List<Delivery> listaDeliverysPendientes = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaListarDeliveryPendiente);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                int idDelivery = rs.getInt("idDelivery");
                String direccionEntrega = rs.getString("direccionEntrega");
                String estadoDelivery = rs.getString("estadoDelivery");
                int idPedido = rs.getInt("idPedido");
                int idRepartidor = rs.getInt("idRepartidor");
                Delivery delivery = new Delivery(idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor);
                listaDeliverysPendientes.add(delivery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDeliverysPendientes;
    }

    //Listar los deliverys en camino
    public List<Delivery> listaDeliverysEnCamino() {
        String consultaListarDeliveryPendiente = "SELECT idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor FROM DELIVERY WHERE ESTADODELIVERY = 'EN CAMINO'";
        List<Delivery> listaDeliverysPendientes = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaListarDeliveryPendiente);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                int idDelivery = rs.getInt("idDelivery");
                String direccionEntrega = rs.getString("direccionEntrega");
                String estadoDelivery = rs.getString("estadoDelivery");
                int idPedido = rs.getInt("idPedido");
                int idRepartidor = rs.getInt("idRepartidor");
                Delivery delivery = new Delivery(idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor);
                listaDeliverysPendientes.add(delivery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDeliverysPendientes;
    }

    //Asignar repartidor a Delivery
    public void asignarDeliveryA_Repartidor(int idRepartidor, int idDelivery){
        String consulta = "{ CALL usp_AsignarRepartidorAdelivery(?, ?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            comando.setInt(1, idRepartidor);
            comando.setInt(2, idDelivery);
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Insertar nuevo delivery
    public void insertarNuevoDelivery(String direccionEntrega, String estadoDelivery, int idPedido, int idRepartidor) {
        String consulta = "{ CALL usp_InsertarNuevoDelivery(?, ?, ?, ?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            comando.setString(1, direccionEntrega);
            comando.setString(2, estadoDelivery);
            comando.setInt(3, idPedido);
            comando.setInt(4, idRepartidor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Generar id de repartidor aleatoriamente
    public int generarIdRepartidor() {
        int[] idsRepartidores = {12345678, 12940013, 17723812, 24873461, 29706002, 35554218, 45385772, 80088869};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(idsRepartidores.length);
        return idsRepartidores[indiceAleatorio];
    }

    //Buscar Delivery en la base de datos segun ID
    public Delivery buscarDelivery(int idDelivery) {
        Delivery delivery = null;
        String consulta = "SELECT * FROM Delivery HWERE idDelivery = ?";
        try {
            PreparedStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            comando.setInt(1, idDelivery);
            ResultSet resultSet = comando.executeQuery();
            if (resultSet.next()){
                delivery = new Delivery();
                delivery.setIdDelivery(resultSet.getInt("idDelivery"));
                delivery.setDireccionEntrega(resultSet.getString("direccionEntrega"));
                delivery.setEstadoDelivery(resultSet.getString("estadoDelivery"));
                delivery.setIdPedido(resultSet.getInt("idPedido"));
                delivery.setIdRepartidor(resultSet.getInt("idRepartidor"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return delivery;
    }
}
