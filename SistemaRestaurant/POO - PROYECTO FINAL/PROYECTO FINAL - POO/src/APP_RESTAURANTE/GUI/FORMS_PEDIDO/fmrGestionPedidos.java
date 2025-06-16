package APP_RESTAURANTE.GUI.FORMS_PEDIDO;
import APP_RESTAURANTE.CLASES.Pedido;
import APP_RESTAURANTE.CONEXIONBD.ICRUDPedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class fmrGestionPedidos extends JFrame {
    private JPanel JpPrincipalGestionPedidos;
    private JTable tableListPedidos;
    private JButton btnAgregarPedido;
    private JButton btnDetallePedidoButton;
    private JButton btnEliminarPedido;
    private DefaultTableModel tableModel;
    private int idPedidoSeleccionado = -1;
    private JFrame formularioPrincipal;

    public fmrGestionPedidos(JFrame formularioPrincipal) {
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
        PrepararFormulario();
        configurarTabla();
        cargarDatos();
        //Boton agregar pedido
        btnAgregarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrAgregarPedido agregarPedido = new fmrAgregarPedido(fmrGestionPedidos.this);
                agregarPedido.setVisible(true);
            }
        });
        //Ver detalle pedido
        btnDetallePedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idPedidoSeleccionado != -1){
                    fmrDetallePedido detallePedido = new fmrDetallePedido(idPedidoSeleccionado, fmrGestionPedidos.this);
                    detallePedido.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(fmrGestionPedidos.this, "Por favor, seleccione un ID de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        //Eliminar pedido
        btnEliminarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idPedidoSeleccionado != -1){
                    int confirmacion = JOptionPane.showConfirmDialog(fmrGestionPedidos.this, "¿Está seguro de eliminar este pedido?"
                    , "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    ICRUDPedido icrduPedido = new ICRUDPedido();
                    icrduPedido.eliminarPedido(idPedidoSeleccionado);

                    JOptionPane.showMessageDialog(fmrGestionPedidos.this, "El pedido se eliminó correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(fmrGestionPedidos.this, "Por favor, selecciona un pedido para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
    }

    private void PrepararFormulario() {
        JpPrincipalGestionPedidos = new JPanel();
        GroupLayout layout = new GroupLayout(JpPrincipalGestionPedidos);
        JpPrincipalGestionPedidos.setLayout(layout);

        setContentPane(JpPrincipalGestionPedidos);
        setTitle("Gestión de Pedidos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        btnAgregarPedido = new JButton("Agregar Pedido");
        btnDetallePedidoButton = new JButton("Ver Detalle Pedido");
        btnEliminarPedido = new JButton("Eliminar Pedido");

        JLabel lblTitulo = new JLabel("Gestión de Pedidos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        tableModel = new DefaultTableModel(new String[]{"ID Pedido", "Código", "Fecha", "Hora", "Total", "ID Cliente"}, 0);
        tableListPedidos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableListPedidos);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(lblTitulo, GroupLayout.Alignment.CENTER)
        .addComponent(scrollPane)
        .addGroup(layout.createSequentialGroup()
        .addGap(100)
        .addComponent(btnAgregarPedido)
        .addGap(20)
        .addComponent(btnDetallePedidoButton)
        .addGap(20)
        .addComponent(btnEliminarPedido)
        .addGap(100)
        )
        );

        layout.setVerticalGroup(
        layout.createSequentialGroup()
        .addComponent(lblTitulo)
        .addGap(20)
        .addComponent(scrollPane)
        .addGap(20)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(btnAgregarPedido)
        .addComponent(btnDetallePedidoButton)
        .addComponent(btnEliminarPedido)
        )
        .addGap(20)
        );
    }

    private void configurarTabla() {
        tableListPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tableListPedidos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idPedido = (int) tableModel.getValueAt(filaSeleccionada, 0);
                    System.out.println("ID del pedido seleccionado: " + idPedido);
                    idPedidoSeleccionado = idPedido;
                }
            }
        });
    }

    private void cargarDatos() {
        ICRUDPedido crudPedido = new ICRUDPedido();
        ArrayList<Pedido> listarPedidos = crudPedido.listarPedidos();
        String formatPrecio = String.format("%.2f", listarPedidos.get(0).getTotalPedido());

        for (Pedido pedido : listarPedidos) {
            Object[] fila = {
                    pedido.getIdPedido(),
                    pedido.getCodigoPedido(),
                    pedido.getFechaPedido(),
                    pedido.getHoraPedido(),
                    pedido.getTotalPedido(),
                    pedido.getIdCliente()
            };
            tableModel.addRow(fila);
        }
    }
}
