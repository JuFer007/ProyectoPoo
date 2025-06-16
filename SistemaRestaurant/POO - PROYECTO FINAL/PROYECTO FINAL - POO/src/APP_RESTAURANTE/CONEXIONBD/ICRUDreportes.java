package APP_RESTAURANTE.CONEXIONBD;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ICRUDreportes {
    //Constructor
    public ICRUDreportes() {
    }

    //Listar los pedidos segun el mes
    public ArrayList<String> listarPedidosDelMes(int mes) {
        String consultaInsertListarPedidosSegunMes = "{ CALL uspListaPedidoSegunMes(?) }";
        ArrayList<String> pedidos = new ArrayList<>();
        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertListarPedidosSegunMes)) {
            comando.setInt(1, mes);
            boolean resultadoConsulta = comando.execute();

            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    while (rs.next()) {
                        String pedido = rs.getString("codigoPedido") + ", " + rs.getDate("fechaPedido") + ", "
                                + rs.getString("horaPedido") + ", " + rs.getInt("idCliente") + ", "
                                + rs.getDouble("totalPedido");
                        pedidos.add(pedido);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar el procedimiento uspListaPedidoSegunMes", e);
        }
        return pedidos;
    }

    //Listar plato mas vendido segun el mes
    public String listarPlatoMasVendidoPorMes(int mes) {
        String consultaPlatoMasVendido = "{ CALL uspListarPlatoMasVendidoPorMes(?) }";
        String platoMasVendido = "No disponible";
        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaPlatoMasVendido)) {
            comando.setInt(1, mes);
            boolean resultadoConsulta = comando.execute();

            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    if (rs.next()) {
                        platoMasVendido = rs.getString("nombrePlato");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar el procedimiento uspListarPlatoMasVendidoPorMes", e);
        }
        return platoMasVendido;
    }

    //Listar el cliente mas frecuente segun mes
    public String listarClienteMasFrecuente(int mes) {
        String consultaClienteMasFrecuente = "{ CALL uspClienteMasFrecuente(?) }";
        String clienteMasFrecuente = "No disponible";
        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaClienteMasFrecuente)) {
            comando.setInt(1, mes);
            boolean resultadoConsulta = comando.execute();

            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    if (rs.next()) {
                        clienteMasFrecuente = rs.getString("nombreCliente");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar el procedimiento uspClienteMasFrecuente", e);
        }
        return clienteMasFrecuente;
    }

    //Obtener el valor total de ventas por el mes
    public double obtenerTotalVendido(int mes) {
        String consultaTotalVendido = "{ CALL uspTotalVendidoEnElMes(?) }";
        double totalVendido = 0.0;
        try (CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaTotalVendido)) {
            comando.setInt(1, mes);
            boolean resultadoConsulta = comando.execute();
            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    if (rs.next()) {
                        totalVendido = rs.getDouble("TotalDelMes");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalVendido;
    }
}