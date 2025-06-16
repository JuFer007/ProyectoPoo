package APP_RESTAURANTE.GUI.FORMS_PAGO;
import APP_RESTAURANTE.CLASES.ComprobantePDF;
import APP_RESTAURANTE.CLASES.Pago;
import APP_RESTAURANTE.CONEXIONBD.ICRUDpago;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class fmrGestionPago extends JFrame {
    private JPanel JpPrincipalGestionPago;
    private JLabel txtTitulo;
    private JLabel txtIDpago;
    private JTextField cajaIdPago;
    private JButton btnModificarPago;
    private JTable tablaPagos;
    private JLabel txtNuevoEstado;
    private JTextField cajaNuevoEstado;
    private JButton btnGenerarComprobante;
    private DefaultTableModel modelo;
    private ICRUDpago icrudpago = new ICRUDpago();
    private JFrame formularioPrincipal;

    public fmrGestionPago(JFrame formularioPrincipal) {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.formularioPrincipal = formularioPrincipal;
        tablaPagos = new JTable(modelo);
        prepararFormulario();
        permitirSoloNumeros(cajaIdPago);
        configurarTabla();
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
        //Modificar estado Pago
        btnModificarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idPago = Integer.parseInt(cajaIdPago.getText());
                    String nuevoEstado = cajaNuevoEstado.getText();
                    if (!estadoValidoPago(nuevoEstado)) {
                        JOptionPane.showMessageDialog(null, "El nuevo estado no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    icrudpago.editarEstadoPago(nuevoEstado, idPago);
                    cargarDatos();
                    JOptionPane.showMessageDialog(fmrGestionPago.this, "Estado de pago actualizado con éxito.");
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(fmrGestionPago.this, "Por favor, ingrese un ID de pago válido.");
                }
            }
        });
        //Crear comprobante de pago
        btnGenerarComprobante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaPagos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un ID de pago", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int idPedido = Integer.parseInt(tablaPagos.getValueAt(filaSeleccionada, 0).toString());
                    String estadoPago = tablaPagos.getValueAt(filaSeleccionada, 4).toString();
                    if (!estadoPago.equals("Completado")) {
                        JOptionPane.showMessageDialog(null, "El pago no está completado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String DNI;
                    boolean DNIesValido = false;

                    while (!DNIesValido) {
                        DNI = JOptionPane.showInputDialog(null, "Ingrese el número de DNI:", "Solicitud DNI", JOptionPane.QUESTION_MESSAGE);

                        if (DNI == null) {
                            JOptionPane.showMessageDialog(null, "Operación cancelada.", "Cancelado", JOptionPane.WARNING_MESSAGE);
                            return;
                        } else if (DNI.matches("\\d{8}")) {
                            DNIesValido = true;
                            ComprobantePDF comprobantePDF = new ComprobantePDF();
                            comprobantePDF.generarComprobanteDePagoPDF(idPedido, DNI);
                            JOptionPane.showMessageDialog(null, "Comprobante generado correctamente", "Información comprobante", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "DNI no válido. Debe contener 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error al procesar el ID de pago.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void prepararFormulario(){
        setTitle("Gestión de Pagos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 780, 360);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        txtTitulo = new JLabel("Gestión de Pagos");
        txtTitulo.setBounds(250, 10, 200, 30);
        txtTitulo.setFont(txtTitulo.getFont().deriveFont(18f));
        getContentPane().add(txtTitulo);

        txtIDpago = new JLabel("ID Pago:");
        txtIDpago.setBounds(10, 270, 100, 20);
        getContentPane().add(txtIDpago);

        cajaIdPago = new JTextField();
        cajaIdPago.setBounds(60, 270, 100, 20);
        getContentPane().add(cajaIdPago);

        txtNuevoEstado = new JLabel("Nuevo Estado:");
        txtNuevoEstado.setBounds(180, 270, 100, 20);
        getContentPane().add(txtNuevoEstado);

        cajaNuevoEstado = new JTextField();
        cajaNuevoEstado.setBounds(270, 270, 150, 20);
        getContentPane().add(cajaNuevoEstado);

        btnModificarPago = new JButton("Modificar Estado");
        btnModificarPago.setBounds(430, 270, 150, 30);
        getContentPane().add(btnModificarPago);

        btnGenerarComprobante = new JButton("Generar Comprobante");
        btnGenerarComprobante.setBounds(590, 270, 150, 30);
        getContentPane().add(btnGenerarComprobante);

        modelo.addColumn("ID Pago");
        modelo.addColumn("Monto");
        modelo.addColumn("Método de Pago");
        modelo.addColumn("Fecha");
        modelo.addColumn("Estado");
        modelo.addColumn("ID Pedido");

        tablaPagos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaPagos);
        scrollPane.setBounds(40, 50, 700, 200);
        getContentPane().add(scrollPane);

        revalidate();
        repaint();

        setVisible(true);
    }

    private void configurarTabla() {
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tablaPagos.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
        }
        tablaPagos.setFocusable(true);
    }

    private void cargarDatos() {
        List<Pago> listaPagos = icrudpago.listarPagos();
        modelo.setRowCount(0);
        for (Pago pago : listaPagos) {
            Object[] fila = new Object[6];
            fila[0] = pago.getIdPago();
            fila[1] = pago.getMontoPago();
            fila[2] = pago.getMetodoPago();
            fila[3] = pago.getFechaPago();
            fila[4] = pago.getEstadoPago();
            fila[5] = pago.getIdPedido();
            modelo.addRow(fila);
        }
    }

    private boolean estadoValidoPago(String estado) {
        String[] estadoPagoPermitidos = {"Pendiente", "Completado", "Cancelado"};
        for (String estadoPago : estadoPagoPermitidos) {
            if (estado.equalsIgnoreCase(estadoPago)) {
                return true;
            }
        }
        return false;
    }

    private void permitirSoloNumeros(JTextField campoTexto) {
        campoTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                    JOptionPane.showMessageDialog(
                            null,
                            "Solo se permiten números.",
                            "Entrada no válida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
    }
}
