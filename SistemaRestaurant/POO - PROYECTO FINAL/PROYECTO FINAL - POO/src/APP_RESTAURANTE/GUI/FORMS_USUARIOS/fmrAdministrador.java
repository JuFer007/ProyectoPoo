package APP_RESTAURANTE.GUI.FORMS_USUARIOS;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import APP_RESTAURANTE.GUI.FORMS_EMPLEADO.fmrGestionEmpleados;
import APP_RESTAURANTE.GUI.FORMS_PLATO.fmrGestiónDePlatos;
import APP_RESTAURANTE.GUI.FORMS_CLIENTE.fmrGestionClientes;
import APP_RESTAURANTE.GUI.FORMS_PEDIDO.fmrGestionPedidos;
import APP_RESTAURANTE.GUI.FORMS_DELIVERY.fmrGestionDelivery;
import APP_RESTAURANTE.GUI.FORMS_MESAS.fmrGestionMesas;
import APP_RESTAURANTE.GUI.FORMS_REPORTE.fmrReporteDeVentas;
import APP_RESTAURANTE.GUI.FORMS_PAGO.fmrGestionPago;
import APP_RESTAURANTE.GUI.FORMS_MENU.fmrGestionMenu;

public class fmrAdministrador extends JFrame {
    private JPanel JpPrincipal;
    private JLabel txtMenuAdministrador;
    private JButton btnGestionEmpleados;
    private JButton btnGestionPlatos;
    private JButton btnGestionClientes;
    private JButton btnGestionPedidos;
    private JButton btnGestionDelivery;
    private JButton btnGestionDeMesas;
    private JButton btnGestionDeVentas_y_Reportes;
    private JButton btnGestionPagos;
    private JButton btnGestionMenu;

    public fmrAdministrador() {
        prepararFormulario();
        //Gestion Empleados
        btnGestionEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionEmpleados gestionEmpleados = new fmrGestionEmpleados(fmrAdministrador.this);
                gestionEmpleados.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion Platos
        btnGestionPlatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestiónDePlatos gestionPlatos = new fmrGestiónDePlatos(fmrAdministrador.this);
                gestionPlatos.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion Clientes
        btnGestionClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionClientes gestionClientes = new fmrGestionClientes(fmrAdministrador.this);
                gestionClientes.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion Pedidos
        btnGestionPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionPedidos gestionPedidos = new fmrGestionPedidos(fmrAdministrador.this);
                gestionPedidos.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion Delivery
        btnGestionDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionDelivery gestionDelivery = new fmrGestionDelivery(fmrAdministrador.this);
                gestionDelivery.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion Mesas
        btnGestionDeMesas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionMesas gestionMesas = new fmrGestionMesas(fmrAdministrador.this);
                gestionMesas.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion de Ventas y Reportes
        btnGestionDeVentas_y_Reportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrReporteDeVentas gestionVentas = new fmrReporteDeVentas(fmrAdministrador.this);
                gestionVentas.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion de Pagos
        btnGestionPagos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionPago gestionPago = new fmrGestionPago(fmrAdministrador.this);
                gestionPago.setVisible(true);
                setVisible(false);
            }
        });
        //Gestion de Menu
        btnGestionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionMenu gestionMenu = new fmrGestionMenu(fmrAdministrador.this);
                gestionMenu.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void prepararFormulario(){
        setTitle("Menú Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 750);
        setContentPane(JpPrincipal);
        setLocationRelativeTo(null);
    }
}
