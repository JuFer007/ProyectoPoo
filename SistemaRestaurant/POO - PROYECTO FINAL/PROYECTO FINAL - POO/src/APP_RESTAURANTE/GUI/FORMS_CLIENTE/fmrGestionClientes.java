package APP_RESTAURANTE.GUI.FORMS_CLIENTE;
import APP_RESTAURANTE.CLASES.Cliente;
import APP_RESTAURANTE.CONEXIONBD.ICRUDCliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class fmrGestionClientes extends JFrame {
    private JPanel jpPrincialClientes;
    private JButton btnAgregarCliente;
    private JButton btnModificarCliente;
    private JButton btnEliminarCliente;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ICRUDCliente cliente;
    private JFrame formularioPrincipal;

    public fmrGestionClientes(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        cliente = new ICRUDCliente();
        prepararFormulario();
        prepararTabla();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
        //Boton agregar cliente
        btnAgregarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreCliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente");
                if (nombreCliente != null && !nombreCliente.trim().isEmpty()) {
                    if (nombreCliente.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
                        Cliente nuevocliente = new Cliente(nombreCliente);
                        cliente.crear(nuevocliente);
                        JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
                        cargarDatos();
                    } else {
                        JOptionPane.showMessageDialog(null, "El nombre del cliente solo puede contener letras");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre del cliente no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Boton modificar cliente
        btnModificarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablaClientes.getSelectedRow();
                if (row != -1) {
                    int idCliente = (int) tablaClientes.getValueAt(row, 0);
                    String nombreCliente = (String) tablaClientes.getValueAt(row, 1);
                    if (nombreCliente != null && !nombreCliente.trim().isEmpty()) {
                        if (nombreCliente.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
                            Cliente clienteModificar = new Cliente(nombreCliente);
                            clienteModificar.setIdCliente(idCliente);
                            clienteModificar.setNombreCliente(nombreCliente);
                            cliente.actualizarCliente(clienteModificar);
                            JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");
                            cargarDatos();
                        } else {
                            JOptionPane.showMessageDialog(null, "El nombre del cliente solo puede contener letras", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Boton eliminar cliente
        btnEliminarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablaClientes.getSelectedRow();
                if (row != -1) {
                    int idCliente = (int) tablaClientes.getValueAt(row, 0);
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este cliente?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        cliente.eliminarCliente(idCliente);
                        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
                        cargarDatos();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                }
            }
        });
        cargarDatos();
    }

    private void prepararFormulario() {
        setSize(600, 600);
        setTitle("Gestion de clientes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(jpPrincialClientes);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepararTabla() {
        modeloTabla = new DefaultTableModel();
        tablaClientes.setModel(modeloTabla);
        modeloTabla.addColumn("ID Cliente");
        modeloTabla.addColumn("Nombres y Apellidos");

        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setRowSelectionAllowed(true);
        tablaClientes.setColumnSelectionAllowed(false);
        tablaClientes.setCellSelectionEnabled(false);

        tablaClientes.setSelectionBackground(Color.CYAN);
        tablaClientes.setSelectionForeground(Color.BLACK);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);

        List<Cliente> clientes = cliente.listarClientes();

        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{cliente.getIdCliente(), cliente.getNombreCliente()});
        }
    }
}
