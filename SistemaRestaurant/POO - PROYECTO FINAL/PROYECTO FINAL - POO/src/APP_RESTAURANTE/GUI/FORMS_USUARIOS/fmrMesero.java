package APP_RESTAURANTE.GUI.FORMS_USUARIOS;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import APP_RESTAURANTE.GUI.FORMS_PLATO.fmrGestiónDePlatos;
import APP_RESTAURANTE.GUI.FORMS_CLIENTE.fmrGestionClientes;
import APP_RESTAURANTE.GUI.FORMS_PEDIDO.fmrGestionPedidos;
import APP_RESTAURANTE.GUI.FORMS_PAGO.fmrGestionPago;

public class fmrMesero extends JFrame {
    private JPanel JpMesero;
    private JLabel txtMenuMesero;
    private JButton btnGestionClientes;
    private JButton btnGestionPlatos;
    private JButton btnGestionPedidos;
    private JButton btnGestionPagos;

    public fmrMesero() {
        PrepararFormulario();
        //Boton gestionarClientes
        btnGestionClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionClientes gestionClientes = new fmrGestionClientes(fmrMesero.this);
                gestionClientes.setVisible(true);
            }
        });
        //Boton gestionPlatos
        btnGestionPlatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestiónDePlatos gestiónDePlatos = new fmrGestiónDePlatos(fmrMesero.this);
                gestiónDePlatos.setVisible(true);
            }
        });
        //Boton gestionPedidos
        btnGestionPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionPedidos gestionPedidos = new fmrGestionPedidos(fmrMesero.this);
                gestionPedidos.setVisible(true);
            }
        });
        //Boton gestionPagos
        btnGestionPagos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionPago gestionPagos = new fmrGestionPago(fmrMesero.this);
                gestionPagos.setVisible(true);
            }
        });
    }

    private void PrepararFormulario() {
        setTitle("Menú Mesero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setContentPane(JpMesero);
        setLocationRelativeTo(null);
    }
}
