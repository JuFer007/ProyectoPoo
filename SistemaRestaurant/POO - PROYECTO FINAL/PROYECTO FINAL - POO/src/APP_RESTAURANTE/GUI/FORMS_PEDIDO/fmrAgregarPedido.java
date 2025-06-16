package APP_RESTAURANTE.GUI.FORMS_PEDIDO;
import APP_RESTAURANTE.CLASES.*;
import APP_RESTAURANTE.CONEXIONBD.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class fmrAgregarPedido extends JFrame{
    private JPanel JpPanelPrincipalAgregarPedido;
    private JLabel txtTitulo;
    private JComboBox comboBoxClientes;
    private JButton btnNuevoCliente;
    private JLabel textSelleccionPlato;
    private JList listPlatos;
    private JLabel textCantidad;
    private JTextField txtCantidadPlatos;
    private JButton btnAgregarMasPlatos;
    private JLabel txtEsDelivery;
    private JRadioButton btnRdSiDelivery;
    private JRadioButton btnRdNoDelivery;
    private JButton btnFinalizarPedido;
    private JList listDetalle;
    private JLabel txtDetallePedido;
    private ICRUDPedido icrudPedido;
    private ICRUDdetallePedido icruDdetallePedido;
    private ICRUDDelivery icrudDelivery;
    private Cliente clienteSeleccionado;
    private Repartidor repartidorSeleccionado;
    private Plato platoSeleccionado;
    private JFrame formularioPrincipal;

    public fmrAgregarPedido(JFrame formularioPrincipal) {
        icrudPedido = new ICRUDPedido();
        icruDdetallePedido = new ICRUDdetallePedido();
        icrudDelivery = new ICRUDDelivery();
        String codigoPedido = icrudPedido.generarCodigoPedido();
        this.formularioPrincipal = formularioPrincipal;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });

        setTitle("Agregar Pedido");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        prepararFormulario();
        cargarPlatos();
        listarClientes();

        setVisible(true);

        //Boton agregar nuevo cliente
        btnNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoCliente = JOptionPane.showInputDialog(fmrAgregarPedido.this, "Ingrese el nuevo Cliente",
                "Nuevo Cliente", JOptionPane.QUESTION_MESSAGE);
                if (nuevoCliente != null && !nuevoCliente.isEmpty()) {
                    try {
                        Cliente cliente = new Cliente(nuevoCliente);
                        ICRUDCliente crudCliente = new ICRUDCliente();
                        crudCliente.crear(cliente);

                        JOptionPane.showMessageDialog(fmrAgregarPedido.this, "Cliente " + nuevoCliente +
                        "agregado de manera exitosa. " , "Confirmación", JOptionPane.INFORMATION_MESSAGE);

                        listarClientes();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(fmrAgregarPedido.this, "Error al agregar nuevo cliente: " +
                        exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(fmrAgregarPedido.this, "Debe ingresar un nombre valido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Boton agregar otro plato
        btnAgregarMasPlatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarPlatoSeleccionado()) return;

                String cantidadPlatoText = txtCantidadPlatos.getText();
                if (!esCantidadValida(cantidadPlatoText)) return;

                int cantidadPlato = Integer.parseInt(cantidadPlatoText);
                Plato platoSeleccionado = (Plato) listPlatos.getSelectedValue();

                DefaultListModel<Plato> modeloDetalle;
                if (listDetalle.getModel() instanceof DefaultListModel) {
                    modeloDetalle = (DefaultListModel<Plato>) listDetalle.getModel();
                } else {
                    modeloDetalle = new DefaultListModel<>();
                    listDetalle.setModel(modeloDetalle);
                }

                for (int i = 0; i < cantidadPlato; i++) {
                    modeloDetalle.addElement(platoSeleccionado);
                }

                try {
                    int idPedido = icrudPedido.obtenerUltimoIDPedido();
                    int numeroPlato = platoSeleccionado.getNumeroPlato();
                    double precio = platoSeleccionado.getPrecioPlato();
                    double subtotal = precio * cantidadPlato;

                    DetallePedido detallePedido = new DetallePedido(idPedido, numeroPlato, cantidadPlato, precio, platoSeleccionado.getNombrePlato(), subtotal);
                    icruDdetallePedido.insertarDetallePedido(detallePedido);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(fmrAgregarPedido.this, "Error al agregar detalle al pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                txtCantidadPlatos.setText("");
            }
        });
        //Boton finalizar pedido
        btnFinalizarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarClienteSeleccionado()) return;
                if (!validarDetallePedido()) return;

                Cliente clienteSeleccionado = (Cliente) comboBoxClientes.getSelectedItem();

                double totalPedido = 0;
                DefaultListModel<Plato> modeloDetalle = (DefaultListModel<Plato>) listDetalle.getModel();

                for (int i = 0; i < modeloDetalle.size(); i++) {
                    Plato plato = modeloDetalle.getElementAt(i);
                    totalPedido += plato.getPrecioPlato();
                }

                Pedido nuevoPedido = new Pedido(icrudPedido.generarCodigoPedido(),
                        new Date(System.currentTimeMillis()),
                        new Time(System.currentTimeMillis()), totalPedido, clienteSeleccionado.getIdCliente());

                try {
                    icrudPedido.insertarPedido(nuevoPedido);

                    if (btnRdSiDelivery.isSelected()) {
                        String direccion = JOptionPane.showInputDialog(fmrAgregarPedido.this,
                                "Ingrese la dirección del pedido:", "Delivery", JOptionPane.QUESTION_MESSAGE);

                        if (direccion == null || direccion.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(fmrAgregarPedido.this, "La dirección no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        icrudDelivery.insertarNuevoDelivery(direccion, "En camino", icrudPedido.obtenerUltimoIDPedido(), icrudDelivery.generarIdRepartidor());
                    }

                    JOptionPane.showMessageDialog(fmrAgregarPedido.this, "Pedido registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(fmrAgregarPedido.this, "Error al registrar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void prepararFormulario() {
        JpPanelPrincipalAgregarPedido = new JPanel();
        JpPanelPrincipalAgregarPedido.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // Márgenes
        JpPanelPrincipalAgregarPedido.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        txtTitulo = new JLabel("Agregar Pedido", JLabel.CENTER);
        txtTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JpPanelPrincipalAgregarPedido.add(txtTitulo, gbc);

        JLabel lblSeleccionCliente = new JLabel("Seleccionar Cliente:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JpPanelPrincipalAgregarPedido.add(lblSeleccionCliente, gbc);

        comboBoxClientes = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        JpPanelPrincipalAgregarPedido.add(comboBoxClientes, gbc);

        btnNuevoCliente = new JButton("Nuevo Cliente");
        gbc.gridx = 2;
        gbc.gridy = 1;
        JpPanelPrincipalAgregarPedido.add(btnNuevoCliente, gbc);

        JLabel lblSeleccionPlato = new JLabel("Seleccione Plato:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        JpPanelPrincipalAgregarPedido.add(lblSeleccionPlato, gbc);

        listPlatos = new JList<>();
        JScrollPane scrollPlatos = new JScrollPane(listPlatos);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        JpPanelPrincipalAgregarPedido.add(scrollPlatos, gbc);

        JLabel lblDetallePedido = new JLabel("Detalle Pedido:");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JpPanelPrincipalAgregarPedido.add(lblDetallePedido, gbc);

        listDetalle = new JList<>();
        JScrollPane scrollDetalle = new JScrollPane(listDetalle);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        JpPanelPrincipalAgregarPedido.add(scrollDetalle, gbc);

        JLabel lblCantidad = new JLabel("Cantidad:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JpPanelPrincipalAgregarPedido.add(lblCantidad, gbc);

        txtCantidadPlatos = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        JpPanelPrincipalAgregarPedido.add(txtCantidadPlatos, gbc);

        JLabel lblEsDelivery = new JLabel("¿Es Delivery?");
        gbc.gridx = 0;
        gbc.gridy = 5;
        JpPanelPrincipalAgregarPedido.add(lblEsDelivery, gbc);

        btnRdSiDelivery = new JRadioButton("Sí");
        btnRdNoDelivery = new JRadioButton("No");
        ButtonGroup grupoDelivery = new ButtonGroup();
        grupoDelivery.add(btnRdSiDelivery);
        grupoDelivery.add(btnRdNoDelivery);

        JPanel panelDelivery = new JPanel();
        panelDelivery.add(btnRdSiDelivery);
        panelDelivery.add(btnRdNoDelivery);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JpPanelPrincipalAgregarPedido.add(panelDelivery, gbc);

        btnAgregarMasPlatos = new JButton("Agregar Otro Plato");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JpPanelPrincipalAgregarPedido.add(btnAgregarMasPlatos, gbc);

        btnFinalizarPedido = new JButton("Finalizar Pedido");
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JpPanelPrincipalAgregarPedido.add(btnFinalizarPedido, gbc);

        add(JpPanelPrincipalAgregarPedido);
    }

    public void cargarPlatos() {
        try {
            ICRUDPlato crudPlato = new ICRUDPlato();
            List<Plato> platos = crudPlato.listarPlato();
            DefaultListModel<Plato> modelo = new DefaultListModel<>();
            for (Plato plato : platos) {
                modelo.addElement(plato);
            }
            listPlatos.setModel(modelo);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar platos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Listar todos los clientes
    public void listarClientes() {
        try {
            ICRUDCliente crudCliente = new ICRUDCliente();
            List<Cliente> clientes = crudCliente.listarClientes();
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron clientes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                comboBoxClientes.setModel(new DefaultComboBoxModel<>(clientes.toArray(new Cliente[0])));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Validacion de campos
    private boolean esCantidadValida(String cantidadText) {
        if (cantidadText == null || cantidadText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!cantidadText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    //Validar el cliente seleccionado
    private boolean validarClienteSeleccionado() {
        if (comboBoxClientes.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    //Validar el plato seleccionado
    private boolean validarPlatoSeleccionado() {
        if (listPlatos.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un plato.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    //Validar que el detalle de pedido no este vacio
    private boolean validarDetallePedido() {
        DefaultListModel<Plato> modeloDetalle = (DefaultListModel<Plato>) listDetalle.getModel();
        if (modeloDetalle == null || modeloDetalle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un plato al pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
