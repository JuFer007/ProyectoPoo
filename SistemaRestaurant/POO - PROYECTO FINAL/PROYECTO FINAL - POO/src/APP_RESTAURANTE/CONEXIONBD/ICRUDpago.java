package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Pago;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ICRUDpago {
    //Constructor
    public ICRUDpago() {

    }

    //Listar todos los pagos
    public List<Pago> listarPagos() {
        String consulta = "{ CALL usp_ListarPagos() }";
        List<Pago> listaPagos = new ArrayList<>();

        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta)) {
            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    Pago pago = new Pago(0, 0.0, "", null, "", -1);
                    pago.setIdPago(resultado.getInt("idPago"));
                    pago.setMontoPago(resultado.getDouble("montoPago"));
                    pago.setMetodoPago(resultado.getString("metodoPago"));
                    pago.setFechaPago(resultado.getDate("fechaPago"));
                    pago.setEstadoPago(resultado.getString("estadoPago"));
                    pago.setIdPedido(resultado.getInt("idPedido"));
                    listaPagos.add(pago);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar pagos", e);
        }

        return listaPagos;
    }

    //Modificar estado de pago
    public void editarEstadoPago(String nuevoEstado, int idPago) {
        String consulta = "{ CALL usp_editarEstadoPago(?, ?) }";

        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta)) {
            comando.setString(1, nuevoEstado);
            comando.setInt(2, idPago);
            comando.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al editar el estado del pago", e);
        }
    }

    //Obtener el tipo de pago
    public String obtenerMetodoDePago(int idPedido) {
        String totalPedido = "";
        String consultaObtenerNombreCliente = "{ CALL usp_ObtenerMetodoPago(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaObtenerNombreCliente);
            comando.setInt(1, idPedido);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                totalPedido = resultado.getString("metodoPago");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalPedido;
    }
}