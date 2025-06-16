package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Cliente;
import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ICRUDCliente {

    //Constructor
    public ICRUDCliente(){

    }

    //Agregar o insertar cliente
    public void crear(Cliente nuevoCliente) {
        String consultaInsertCrearCliente = "{ CALL usp_CrearCliente(?)}";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertCrearCliente);
            comando.setString(1, nuevoCliente.getNombreCliente());
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Actualizar o modificar cliente
    public void actualizarCliente(Cliente cliente) {
        String consulta = "{ CALL usp_ActualizarCliente(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            comando.setString(1, cliente.getNombreCliente());
            comando.setInt(2, cliente.getIdCliente());

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String mensaje = resultado.getString("mensaje");
                JOptionPane.showMessageDialog(null, mensaje);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error :" + e.getMessage());
        }
    }

    //Eliminar cliente
    public void eliminarCliente(int idCliente) {
        String consultaInsertEliminarCliente = "{ CALL usp_EliminarCliente(?)}";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertEliminarCliente);
            comando.setInt(1, idCliente);
            comando.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Listar todos los clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String consultaInsertListarCliente = "{ CALL usp_ListarClientes() }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertListarCliente);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                int idCliente = resultado.getInt("idCliente");
                String nombreCliente = resultado.getString("nombreCliente");
                Cliente cliente = new Cliente();
                cliente.setIdCliente(idCliente);
                cliente.setNombreCliente(nombreCliente);
                clientes.add(cliente);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    //Obtener nombre cliente para comprobante de pago
    public String nombreCliente(int idPedido) {
        String nombreCliente = "";
        String consultaObtenerNombreCliente = "{ CALL usp_ObtenerNombreCliente(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaObtenerNombreCliente);
            comando.setInt(1, idPedido);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                nombreCliente = resultado.getString("nombreCliente");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nombreCliente;
    }
}