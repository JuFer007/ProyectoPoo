package APP_RESTAURANTE.GUI.FORMS_REPORTE;
import APP_RESTAURANTE.CONEXIONBD.ICRUDreportes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class fmrReporteDeVentas extends JFrame {
    private JPanel JpPanelReporteVentas;
    private JTextField textNombrePlato;
    private JTextField textClienteFrecuente;
    private JLabel textPlato;
    private JLabel txtCliente;
    private JTable tablaModelo;
    private JLabel txtTotal;
    private JTextField txrTotalVendido;
    private JButton btnPaginaAnterior;
    private JButton btnSiguientePagina;
    private int paginaActual = 1;
    private int registroPorPagina = 30;
    private int mes = 1;
    private ICRUDreportes icruDreportes;
    private JFrame formularioPrincipal;

    public fmrReporteDeVentas(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        icruDreportes = new ICRUDreportes();
        PrepararFormulario();
        configurarTabla();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
        cargarDatos();
        //Acción para siguiente página
        btnSiguientePagina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mes < 12) {
                    mes++;
                    paginaActual++;
                    cargarDatos();
                }
            }
        });
        //Acción para anterior página
        btnPaginaAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mes > 1) {
                    mes--;
                    paginaActual--;
                    cargarDatos();
                }
            }
        });
    }

    private void PrepararFormulario() {
        setTitle("Reporte de Ventas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        JpPanelReporteVentas = new JPanel();
        JpPanelReporteVentas.setLayout(new BoxLayout(JpPanelReporteVentas, BoxLayout.Y_AXIS));
        JpPanelReporteVentas.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        JLabel tituloFormulario = new JLabel("Reporte de Ventas", JLabel.CENTER);
        tituloFormulario.setFont(new Font("Arial", Font.BOLD, 18));
        tituloFormulario.setAlignmentX(Component.CENTER_ALIGNMENT);
        JpPanelReporteVentas.add(tituloFormulario);
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3, 2, 10, 10));
        textPlato = new JLabel("Plato más vendido:");
        textNombrePlato = new JTextField(20);
        textClienteFrecuente = new JTextField(20);
        txtCliente = new JLabel("Cliente más frecuente:");
        txtTotal = new JLabel("Total recaudado:");
        txrTotalVendido = new JTextField(20);
        panelSuperior.add(textPlato);
        panelSuperior.add(textNombrePlato);
        panelSuperior.add(txtCliente);
        panelSuperior.add(textClienteFrecuente);
        panelSuperior.add(txtTotal);
        panelSuperior.add(txrTotalVendido);
        JpPanelReporteVentas.add(panelSuperior);
        JpPanelReporteVentas.add(Box.createVerticalStrut(20));
        tablaModelo = new JTable(10, 5);
        JScrollPane scrollPane = new JScrollPane(tablaModelo);
        JpPanelReporteVentas.add(scrollPane);
        JpPanelReporteVentas.add(Box.createVerticalStrut(20));
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        btnPaginaAnterior = new JButton("Página Anterior");
        btnSiguientePagina = new JButton("Página Siguiente");
        panelInferior.add(btnPaginaAnterior, BorderLayout.WEST);
        panelInferior.add(btnSiguientePagina, BorderLayout.EAST);
        JpPanelReporteVentas.add(panelInferior);
        add(JpPanelReporteVentas);
        setVisible(true);
        cargarDatos();
    }

    private void configurarTabla() {
        String[] columnNames = {"Código Pedido", "Fecha Pedido", "Hora Pedido", "ID Cliente", "Total Pedido"};
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
        tablaModelo.setModel(tableModel);
    }

    private void cargarDatos() {
        try {
            String platoMasVendido = icruDreportes.listarPlatoMasVendidoPorMes(mes);
            if (platoMasVendido == null || platoMasVendido.isEmpty()) {
                textNombrePlato.setText("No disponible");
            } else {
                textNombrePlato.setText(platoMasVendido);
            }

            String clienteFrecuente = icruDreportes.listarClienteMasFrecuente(mes);
            if (clienteFrecuente == null || clienteFrecuente.isEmpty()) {
                textClienteFrecuente.setText("No disponible");
            } else {
                textClienteFrecuente.setText(clienteFrecuente);
            }

            double totalVendido = icruDreportes.obtenerTotalVendido(mes);
            if (totalVendido < 0) {
                txrTotalVendido.setText("0.00");
            } else {
                txrTotalVendido.setText(String.format("%.2f", totalVendido));
            }

            ArrayList<String> pedidos = icruDreportes.listarPedidosDelMes(mes);
            if (pedidos == null || pedidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay pedidos para el mes seleccionado", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                cargarPedidosEnTabla(pedidos);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPedidosEnTabla(ArrayList<String> pedidos) {
        DefaultTableModel model = (DefaultTableModel) tablaModelo.getModel();
        model.setRowCount(0);
        for (String pedido : pedidos) {
            String[] detalles = pedido.split(", ");
            if (detalles.length == 5) {
                model.addRow(detalles);
            }
        }
    }
}
