package APP_RESTAURANTE.CONEXIONBD;
import APP_RESTAURANTE.CLASES.Empleado;
import APP_RESTAURANTE.CLASES.Mesa;
import APP_RESTAURANTE.CLASES.Mesero;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ICRUDEmpleado {

    //Constructor
    public ICRUDEmpleado() {
    }

    //Metodo para crear o agregar nuevos empleados
    public void crear(Empleado nuevoEmpleado) {
        String cosultaInsertCrearEmpleado = "{ CALL usp_CrearEmpleado(?, ?, ?, ?, ?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(cosultaInsertCrearEmpleado);
            comando.setInt(1, nuevoEmpleado.getIdEmpleado());
            comando.setString(2, nuevoEmpleado.getNombreEmpleado());
            comando.setDouble(3, nuevoEmpleado.getSalarioEmpleado());
            comando.setString(4, nuevoEmpleado.getDniEmpleado());
            comando.setString(5, nuevoEmpleado.getCargoEmpleado());
            comando.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para actualizar datos del empleado
    public void actualizar(Empleado empleado) {
        String mensaje = "";
        String consultaInsertActualizarEmpleado = "{ CALL usp_ActualizarEmpleado(?, ?, ?, ?, ?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertActualizarEmpleado);
            comando.setString(1, empleado.getDniEmpleado());
            comando.setString(2, empleado.getNombreEmpleado());
            comando.setString(3, empleado.getCargoEmpleado());
            comando.setDouble(4, empleado.getSalarioEmpleado());
            comando.setInt(5, empleado.getIdEmpleado());

            boolean resultadoConsulta = comando.execute();

            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    if (rs.next()) {
                        if (rs.getMetaData().getColumnLabel(1).equals("mensaje")) {
                            mensaje = rs.getString("mensaje");
                            System.out.println(mensaje);
                        }
                    }
                }
            }
        } catch (Exception e) {
            mensaje = "Error al actualizar los datos del empleado acatualizado.";
            System.out.println(mensaje);
        }
    }

    //Metodo para eliminar empleado
    public void eliminar(String dniEmpleado) {
        String resultado = "";
        String consultaInsertEliminarEmpleado = "{ CALL usp_eliminarEmpleado(?) }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertEliminarEmpleado);
            comando.setString(1, dniEmpleado);

            boolean resultadoConsulta = comando.execute();

            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    if (rs.next()) {
                        if (rs.getMetaData().getColumnLabel(1).equals("mensaje")) {
                            resultado = rs.getString("mensaje");
                            System.out.println(resultado);
                        }
                    }
                }
            }
        } catch (SQLException e){
        }
    }

    //Metodo para listar Empleados
    public List<Empleado> listarEmpleado() {
        List<Empleado> empleados = new ArrayList<>();
        String consultaInsertListarEmpleados = "{ CALL usp_listarEmpleado() }";
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consultaInsertListarEmpleados);
            boolean resultadoConsulta = comando.execute();
            if (resultadoConsulta) {
                try (ResultSet rs = comando.getResultSet()) {
                    while (rs.next()) {
                        int empleadoId = rs.getInt("empleadoId");
                        String dniEmpleado = rs.getString("dniEmpleado");
                        String nombreEmpleado = rs.getString("nombreEmpleado");
                        String cargoEmpleado = rs.getString("cargoEmpleado");
                        double salarioEmpleado = rs.getDouble("salarioEmpleado");

                        Empleado empleado = new Empleado(nombreEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado, empleadoId);
                        empleados.add(empleado);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleados;
    }

    //Listar Meseros
    public List<Empleado> listarMeseros() {
        String consulta = "SELECT nombreEmpleado, empleadoId from Empleado where cargoEmpleado = 'Mesero' ";
        List<Empleado> listaMeseros = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Empleado mesero = new Empleado();
                mesero.setNombreEmpleado(rs.getString("nombreEmpleado"));
                mesero.setIdEmpleado(rs.getInt("empleadoId"));
            listaMeseros.add(mesero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMeseros;
    }

    //Listar Repartidores
    public List<Empleado> listarRepartidores() {
        String consulta = "SELECT nombreEmpleado, empleadoId from Empleado where cargoEmpleado = 'Repartidor' ";
        List<Empleado> listaMeseros = new ArrayList<>();
        try {
            CallableStatement comando = ConexionBD_SQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Empleado mesero = new Empleado();
                mesero.setNombreEmpleado(rs.getString("nombreEmpleado"));
                mesero.setIdEmpleado(rs.getInt("empleadoId"));
                listaMeseros.add(mesero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMeseros;
    }
}