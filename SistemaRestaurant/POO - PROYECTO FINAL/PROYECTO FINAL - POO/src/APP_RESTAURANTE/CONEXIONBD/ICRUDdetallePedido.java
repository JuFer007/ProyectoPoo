package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.DetallePedido;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ICRUDdetallePedido {
    //Constructor
    public ICRUDdetallePedido() {
    }

    //Verificar numero del plato existente
    private boolean verificarNumeroPlatoExistente(int numeroPlato) {
        String consultaVerificarNumeroPlato = "SELECT COUNT(*) FROM Plato WHERE numeroPlato = ?";
        try (PreparedStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareStatement(consultaVerificarNumeroPlato)) {
            comando.setInt(1, numeroPlato);
            ResultSet rs = comando.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Insertar detalle de pedido
    public void insertarDetallePedido (DetallePedido detallePedido){
        if (verificarNumeroPlatoExistente(detallePedido.getNumeroPlato())) {
            String consultaInsertNuevoDetallePedido = "INSERT INTO DetallePedido (idPedido, numeroPlato, cantidad, precioUnitario, nombrePlato) VALUES (?, ?, ?, ?, ?)";
            try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertNuevoDetallePedido)) {
                comando.setInt(1, detallePedido.getIdPedido());
                comando.setInt(2, detallePedido.getNumeroPlato());
                comando.setInt(3, detallePedido.getCantidad());
                comando.setDouble(4, detallePedido.getPrecioUnitario());
                comando.setString(5, detallePedido.getNombrePlato());
                comando.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> mostrarDetallePedido(int idPedido) {
        ArrayList<String> detallePedido = new ArrayList<>();
        String consultaInsertMostrarPedido = "{ CALL usp_MostrarDetallePedido(?) }";

        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertMostrarPedido);
            comando.setInt(1, idPedido);
            boolean hayResultados = comando.execute();

            while (hayResultados) {
                ResultSet resultadoConsulta = comando.getResultSet();
                while (resultadoConsulta.next()) {
                    String nombreCliente = resultadoConsulta.getString("nombreCliente");
                    String codigoPedido = resultadoConsulta.getString("codigoPedido");
                    String fechaPedido = resultadoConsulta.getString("fechaPedido");
                    String horaPedido = resultadoConsulta.getString("horaPedido");
                    double totalPedido = resultadoConsulta.getDouble("totalPedido");
                    int numeroPlato = resultadoConsulta.getInt("numeroPlato");
                    String nombrePlato = resultadoConsulta.getString("nombrePlato");
                    int cantidad = resultadoConsulta.getInt("cantidad");
                    double precioUnitario = resultadoConsulta.getDouble("precioUnitario");

                    String detalle = "Cliente: " + nombreCliente +
                            ", Código Pedido: " + codigoPedido +
                            ", Fecha: " + fechaPedido +
                            ", Hora: " + horaPedido +
                            ", Total Pedido: " + String.format("%.2f", totalPedido) +
                            ", Número Plato: " + numeroPlato +
                            ", Nombre Plato: " + nombrePlato +
                            ", Cantidad: " + cantidad +
                            ", Precio Unitario: " + String.format("%.2f", precioUnitario);
                    detallePedido.add(detalle);
                }
                resultadoConsulta.close();
                hayResultados = comando.getMoreResults();
            }
            comando.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return detallePedido;
    }

    //Mostrar detalle de pedido segun idPedido
    public ArrayList<DetallePedido> mostrarDetallePedidoParaComprobante(int idPedido) {
        ArrayList<DetallePedido> DetallePedidoSegunId = new ArrayList<>();
        String consultaListarDetallePedido = "{ CALL usp_ListarDetalleSegunPedido (?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaListarDetallePedido);
            comando.setInt(1, idPedido);
            ResultSet resultados = comando.executeQuery();
            while (resultados.next()) {
                int IDPedido = resultados.getInt("idPedido");
                int numeroPlato = resultados.getInt("numeroPlato");
                String nombrePlato = resultados.getString("nombrePlato");
                int cantidad = resultados.getInt("cantidad");
                double precioUnitario = resultados.getDouble("precioUnitario");
                double subtotal = resultados.getDouble("subtotal");

                DetallePedido detallepedido = new DetallePedido(IDPedido, numeroPlato, cantidad, precioUnitario, nombrePlato, subtotal);
                DetallePedidoSegunId.add(detallepedido);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return DetallePedidoSegunId;
    }

}