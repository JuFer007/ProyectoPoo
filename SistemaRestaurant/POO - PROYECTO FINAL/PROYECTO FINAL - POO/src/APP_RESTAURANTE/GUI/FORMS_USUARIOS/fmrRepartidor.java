package APP_RESTAURANTE.GUI.FORMS_USUARIOS;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import APP_RESTAURANTE.GUI.FORMS_DELIVERY.fmrGestionDelivery;

public class fmrRepartidor extends JFrame {
    private JPanel JpRepartidor;
    private JButton btnGestionDelivery;

    public fmrRepartidor() {
        prepararFormulario();
        //Boton Gestion Delivery
        btnGestionDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrGestionDelivery gestionDelivery = new fmrGestionDelivery(fmrRepartidor.this);
                gestionDelivery.setVisible(true);
            }
        });
    }

    private void prepararFormulario() {
        setTitle("Menú Repartidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(JpRepartidor);
    }
}
