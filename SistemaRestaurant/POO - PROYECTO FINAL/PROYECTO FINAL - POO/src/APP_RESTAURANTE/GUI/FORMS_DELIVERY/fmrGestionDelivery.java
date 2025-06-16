package APP_RESTAURANTE.GUI.FORMS_DELIVERY;
import APP_RESTAURANTE.CLASES.Delivery;
import APP_RESTAURANTE.CLASES.Empleado;
import APP_RESTAURANTE.CONEXIONBD.ICRUDDelivery;
import APP_RESTAURANTE.CONEXIONBD.ICRUDEmpleado;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class fmrGestionDelivery extends JFrame{
    private JPanel JpGestionPedidos;
    private JLabel txtTitulo;
    private JLabel txtNuevaDireccion;
    private JTextField cajaNuevaDireccion;
    private JLabel textIDpedido;
    private JTextField cajaIDdelivery;
    private JTable tablaDeliverys = new JTable();
    private JButton btnModificarDelivery;
    private JComboBox comboBoxListarDeliverys;
    private JButton btnAsignarRepartidorAdelivery;
    private ICRUDEmpleado icrudEmpleado = new ICRUDEmpleado();
    private int idDelivery = -1;
    private ICRUDDelivery icrudDelivery = new ICRUDDelivery();
    private JFrame formularioPrincipal;

    public fmrGestionDelivery(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        prepararFormulario();
        prepararTabla();
        cargarDatos();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
        //Obtener id delivery al hacer click en la tabla
        tablaDeliverys.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaDeliverys.getSelectedRow();
                if (filaSeleccionada != -1) {
                    idDelivery = (int) tablaDeliverys.getValueAt(filaSeleccionada, 0);
                }
            }
        });

        // Botón para modificar delivery
        btnModificarDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaDireccionDelivery = txtNuevaDireccion.getText();
                String idDeliveryText = cajaIDdelivery.getText();
                if (nuevaDireccionDelivery.trim().isEmpty() || idDeliveryText.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese valores válidos para la dirección y el ID de delivery", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int idDelivery = Integer.parseInt(idDeliveryText);
                        if (idDelivery <= 0) {
                            JOptionPane.showMessageDialog(null, "Ingrese un ID de delivery válido (mayor que 0)", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Delivery deliveryExistente = icrudDelivery.buscarDelivery(idDelivery);
                        if (deliveryExistente == null) {
                            JOptionPane.showMessageDialog(null, "El ID de delivery no existe", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        icrudDelivery.modificarDatosDelivery(idDelivery, nuevaDireccionDelivery);
                        cargarDatos();
                        JOptionPane.showMessageDialog(null, "Delivery modificado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El ID de delivery debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //Asignar neuvo repartidor a un delivery pendiente
        btnAsignarRepartidorAdelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idDelivery == -1) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID de delivery válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int filaSeleccionada = tablaDeliverys.getSelectedRow();
                String estadoDelivery = tablaDeliverys.getValueAt(filaSeleccionada, 2).toString();

                if (!"Pendiente".equalsIgnoreCase(estadoDelivery)){
                    JOptionPane.showMessageDialog(null, "Solo se puede seleccionar deliverys con estado: Pendiente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                List<Empleado> repartidores = icrudEmpleado.listarRepartidores();
                if (repartidores.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay repartidores disponibles", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mostrarDialogoParaAsignarRepartidorAdelivery(idDelivery);
            }
        });

        //Elegir opcion del listado de comboBox
        comboBoxListarDeliverys.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarTabla();
            }
        });
    }

    private void prepararFormulario() {
        JpGestionPedidos = new JPanel(new BorderLayout());
        setTitle("Gestión Delivery's");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        EmptyBorder margin = new EmptyBorder(25, 25, 25, 25);
        JpGestionPedidos.setBorder(margin);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        txtTitulo = new JLabel("Gestión de Deliverys", SwingConstants.CENTER);
        txtTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel panelComboBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        comboBoxListarDeliverys = new JComboBox<>();
        comboBoxListarDeliverys.addItem("Todas los deliverys");
        comboBoxListarDeliverys.addItem("Deliverys Pendiente");
        comboBoxListarDeliverys.addItem("Deliverys en Camino");
        comboBoxListarDeliverys.addItem("Deliverys Entregados");
        panelComboBox.add(comboBoxListarDeliverys);

        panelSuperior.add(txtTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelComboBox, BorderLayout.EAST);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtNuevaDireccion = new JLabel("Nueva Dirección:");
        cajaNuevaDireccion = new JTextField(15);
        textIDpedido = new JLabel("ID Delivery:");
        cajaIDdelivery = new JTextField(10);
        btnModificarDelivery = new JButton("Modificar Delivery");
        btnAsignarRepartidorAdelivery = new JButton("Asignar Repartidor");
        panelInferior.add(txtNuevaDireccion);
        panelInferior.add(cajaNuevaDireccion);
        panelInferior.add(textIDpedido);
        panelInferior.add(cajaIDdelivery);
        panelInferior.add(btnModificarDelivery);
        panelInferior.add(btnAsignarRepartidorAdelivery);

        JpGestionPedidos.add(panelSuperior, BorderLayout.NORTH);
        JpGestionPedidos.add(panelInferior, BorderLayout.SOUTH);

        add(JpGestionPedidos);
        setVisible(true);
    }

    private void prepararTabla() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };        modeloTabla.addColumn("ID Delivery");
        modeloTabla.addColumn("Direccion Entrega");
        modeloTabla.addColumn("Estado Delivery");
        modeloTabla.addColumn("ID Pedido");
        modeloTabla.addColumn("ID Repartidor");
        tablaDeliverys = new JTable(modeloTabla);

        TableColumnModel columnModel = tablaDeliverys.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tablaDeliverys);
        JpGestionPedidos.add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaDeliverys.getModel();
        modeloTabla.setNumRows(0);
        List<Delivery> deliveries = icrudDelivery.listarDeliverys();
        for (Delivery delivery : deliveries){
            Object[] datos = {
              delivery.getIdDelivery(),
              delivery.getDireccionEntrega(),
              delivery.getEstadoDelivery(),
              delivery.getIdPedido(),
              delivery.getIdRepartidor()
            };
            modeloTabla.addRow(datos);
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaDeliverys.getModel();
        modeloTabla.setNumRows(0);
        List<Delivery> deliveries = obtenerListadoDeliverys();
        for (Delivery delivery : deliveries){
            Object[] datos = {
                    delivery.getIdDelivery(),
                    delivery.getDireccionEntrega(),
                    delivery.getEstadoDelivery(),
                    delivery.getIdPedido(),
                    delivery.getIdRepartidor()
            };
            modeloTabla.addRow(datos);
        }
    }

    //Listado de deliverys
    private List<Delivery> obtenerListadoDeliverys() {
        String tipoListado = (String) comboBoxListarDeliverys.getSelectedItem();
        switch (tipoListado) {
            case "Todos los deliverys":
                return icrudDelivery.listarDeliverys();
            case "Deliverys Pendiente":
                return icrudDelivery.listaDeliverysPendiente();
            case "Deliverys en Camino":
                return icrudDelivery.listaDeliverysEnCamino();
            case "Deliverys Entregados":
                return icrudDelivery.listaDeliverysEntregados();
            default:
                return new ArrayList<>();
        }
    }

    //Metodo para asignar un repartidor al delivery
    private void mostrarDialogoParaAsignarRepartidorAdelivery(int idDelivery) {
        List<Empleado> repartidores = icrudEmpleado.listarRepartidores();
        JComboBox<String> comboBoxRepartidores = new JComboBox<>();
        for (Empleado repartidor : repartidores) {
            comboBoxRepartidores.addItem(repartidor.getIdEmpleado() + " - " + repartidor.getNombreEmpleado());
        }
        int opcion = JOptionPane.showConfirmDialog(null, comboBoxRepartidores, "Seleccionar Repartidor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION) {
            String seleccionado = (String) comboBoxRepartidores.getSelectedItem();
            int idRepartidor = Integer.parseInt(seleccionado.split(" - ")[0]);
            try {
                icrudDelivery.asignarDeliveryA_Repartidor(idRepartidor, idDelivery);
                JOptionPane.showMessageDialog(null, "Repartidor asignado al delivery de manera exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
