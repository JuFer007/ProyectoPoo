package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Pedido;
import java.sql.*;
import java.util.ArrayList;

public class ICRUDPedido {

    //Constructor
    public void ICRUDPedido(){
    };

    //Metodo para insertar pedido
    public void insertarPedido(Pedido pedido) {
        String consultaInsertNuevoPedido = "INSERT INTO Pedido (idCliente, codigoPedido, fechaPedido, horaPedido, totalPedido) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareStatement(consultaInsertNuevoPedido);
            comando.setInt(1, pedido.getIdCliente());
            comando.setString(2, pedido.getCodigoPedido());
            comando.setDate(3, pedido.getFechaPedido());
            comando.setTime(4, pedido.getHoraPedido());
            comando.setDouble(5, pedido.getTotalPedido());
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Generar nuevo codigo pedido
    public String generarCodigoPedido() {
        int numeroAleatorio = (int) (Math.random() * 1000);
        return String.format("PED%03d", numeroAleatorio);
    }

    //Obtener el id del nuevo pedido generado
    public int obtenerUltimoIDPedido() {
        String consulta = "SELECT TOP 1 idPedido FROM Pedido ORDER BY idPedido DESC";
        int ultimoCodigo = 0;

        try (Statement stmt = ConexionBD_SQL.getInstancia().getConexion().createStatement();
             ResultSet resultadoConsulta = stmt.executeQuery(consulta)) {

            if (resultadoConsulta.next()) {
                ultimoCodigo = resultadoConsulta.getInt("idPedido");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimoCodigo;
    }

    //Listar todos los pedidos
    public ArrayList<Pedido> listarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT idPedido, codigoPedido, fechaPedido, horaPedido, FORMAT(totalPedido, 'N2') as totalPedido, idCliente FROM Pedido ORDER BY idPedido";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(query);
            ResultSet resultadoConsulta = comando.executeQuery();
            while (resultadoConsulta.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultadoConsulta.getInt("idPedido"));
                pedido.setCodigoPedido(resultadoConsulta.getString("codigoPedido"));
                pedido.setFechaPedido(resultadoConsulta.getDate("fechaPedido"));
                pedido.setHoraPedido(resultadoConsulta.getTime("horaPedido"));
                pedido.setTotalPedido(resultadoConsulta.getDouble("totalPedido"));
                pedido.setIdCliente(resultadoConsulta.getInt("idCliente"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    //Eliminar pedido
    public void eliminarPedido(int idPedido) {
        String comandoInsertEliminarPedido = "{CALL usp_EliminarPedido(?)}";
        try (
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(comandoInsertEliminarPedido)) {
            comando.setInt(1, idPedido);
            int filasAfectadas = comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Obtener fecha del pedido
    public String ObtenerFechaPedido(int idPedido) {
        String fechaPedio = "";
        String consultaObtenerNombreCliente = "{ CALL usp_ObtenerFechaPedido(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaObtenerNombreCliente);
            comando.setInt(1, idPedido);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                fechaPedio = resultado.getString("fechaPedido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fechaPedio;
    }

    //Obtener hora del pedido
    public String ObtenerHoraPedido(int idPedido) {
        String horaPedido = null   ;
        String consultaObtenerNombreCliente = "{ CALL usp_ObtenerHoraPedido(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaObtenerNombreCliente);
            comando.setInt(1, idPedido);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                horaPedido = resultado.getTime("horaPedido").toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(horaPedido);
    }

    //Obtener total del pedido
    public double ObtenerTotalPedido(int idPedido) {
        double totalPedido = 0;
        String consultaObtenerNombreCliente = "{ CALL usp_ObtenerTotalPedido(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaObtenerNombreCliente);
            comando.setInt(1, idPedido);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                totalPedido = resultado.getDouble("totalPedido");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalPedido;
    }
}