package APP_RESTAURANTE.GUI.FORMS_PEDIDO;
import APP_RESTAURANTE.CONEXIONBD.ICRUDdetallePedido;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class fmrDetallePedido extends JFrame {
    private JPanel JpPrincipalDetallePedido;
    private JLabel txtDetallePedido;
    private JLabel txtNombreCliente;
    private JTextField textNombreCliente;
    private JTextField textFecha;
    private JLabel txtFechaPedido;
    private JLabel txtHoraPedido;
    private JTextField textHora;
    private JTable tablaDetallePedido;
    private JLabel txtTotalPedido;
    private JTextField textTotal;
    private JLabel txtCodigoPedido;
    private JTextField textoCodigoPedido;
    private DefaultTableModel tablamodelo;
    private int idPedido;
    private JFrame formularioPrincipal;

    public fmrDetallePedido(int idPedido, JFrame formularioPrincipal) {
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
        this.idPedido = idPedido;
        prepararFormulario();
        cargarDetallesPedido();
    }

    private void prepararFormulario() {
        JpPrincipalDetallePedido = new JPanel();
        JpPrincipalDetallePedido.setLayout(null);

        txtDetallePedido = new JLabel("Detalle Pedido");
        txtDetallePedido.setBounds(120, 20, 160, 30);
        txtDetallePedido.setHorizontalAlignment(SwingConstants.CENTER);
        txtDetallePedido.setFont(new Font("Arial", Font.BOLD, 18));
        JpPrincipalDetallePedido.add(txtDetallePedido);

        txtNombreCliente = new JLabel("Nombre Cliente:");
        txtNombreCliente.setBounds(20, 60, 150, 30);
        JpPrincipalDetallePedido.add(txtNombreCliente);

        textNombreCliente = new JTextField();
        textNombreCliente.setBounds(180, 60, 200, 20);
        JpPrincipalDetallePedido.add(textNombreCliente);

        txtFechaPedido = new JLabel("Fecha Pedido:");
        txtFechaPedido.setBounds(20, 100, 150, 30);
        JpPrincipalDetallePedido.add(txtFechaPedido);

        textFecha = new JTextField();
        textFecha.setBounds(180, 100, 200, 20);
        JpPrincipalDetallePedido.add(textFecha);

        txtHoraPedido = new JLabel("Hora Pedido:");
        txtHoraPedido.setBounds(20, 140, 150, 30);
        JpPrincipalDetallePedido.add(txtHoraPedido);

        textHora = new JTextField();
        textHora.setBounds(180, 140, 200, 20);
        JpPrincipalDetallePedido.add(textHora);

        txtCodigoPedido = new JLabel("Código Pedido:");
        txtCodigoPedido.setBounds(20, 180, 150, 30);
        JpPrincipalDetallePedido.add(txtCodigoPedido);

        textoCodigoPedido = new JTextField();
        textoCodigoPedido.setBounds(180, 180, 200, 20);
        JpPrincipalDetallePedido.add(textoCodigoPedido);

        txtTotalPedido = new JLabel("Total Pedido:");
        txtTotalPedido.setBounds(20, 220, 150, 30);
        JpPrincipalDetallePedido.add(txtTotalPedido);

        textTotal = new JTextField();
        textTotal.setBounds(180, 220, 200, 20);
        JpPrincipalDetallePedido.add(textTotal);

        tablamodelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablamodelo.addColumn("N° Plato");
        tablamodelo.addColumn("Nombre Plato");
        tablamodelo.addColumn("Cantidad");
        tablamodelo.addColumn("Precio Unitario");

        tablaDetallePedido = new JTable(tablamodelo);

        tablaDetallePedido.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                c.setBackground(Color.LIGHT_GRAY);
                return c;
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaDetallePedido.getColumnCount(); i++) {
            tablaDetallePedido.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            centerRenderer.setBackground(Color.white);
            centerRenderer.setForeground(Color.black);
        }

        tablaDetallePedido.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaDetallePedido.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaDetallePedido.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaDetallePedido.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(tablaDetallePedido);
        scrollPane.setBounds(20, 270, 380, 200);
        JpPrincipalDetallePedido.add(scrollPane);

        setContentPane(JpPrincipalDetallePedido);
        setSize(430, 530);
        setTitle("Detalle Pedido");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void cargarDetallesPedido() {
        ICRUDdetallePedido icrudDetallePedido = new ICRUDdetallePedido();
        ArrayList<String> detalles = icrudDetallePedido.mostrarDetallePedido(idPedido);

        if (!detalles.isEmpty()) {
            String[] primerDetalleArray = detalles.get(0).split(", ");
            textNombreCliente.setText(primerDetalleArray[0].split(": ")[1]);
            textoCodigoPedido.setText(primerDetalleArray[1].split(": ")[1]);
            textFecha.setText(primerDetalleArray[2].split(": ")[1]);
            textHora.setText(primerDetalleArray[3].split(": ")[1]);
            textTotal.setText(primerDetalleArray[4].split(": ")[1]);

            for (String detallePlato : detalles) {
                String[] detallePlatoArray = detallePlato.split(", ");
                if (detallePlatoArray.length >= 9) {
                    Object[] fila = new Object[4];
                    fila[0] = detallePlatoArray[5].split(": ")[1];
                    fila[1] = detallePlatoArray[6].split(": ")[1];
                    fila[2] = detallePlatoArray[7].split(": ")[1];
                    fila[3] = detallePlatoArray[8].split(": ")[1];
                    tablamodelo.addRow(fila);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron detalles para el pedido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
