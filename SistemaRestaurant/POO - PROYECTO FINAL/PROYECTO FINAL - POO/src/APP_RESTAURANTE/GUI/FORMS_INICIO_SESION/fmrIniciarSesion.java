package APP_RESTAURANTE.GUI.FORMS_INICIO_SESION;
import APP_RESTAURANTE.CLASES.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import APP_RESTAURANTE.GUI.FORMS_USUARIOS.fmrAdministrador;
import APP_RESTAURANTE.GUI.FORMS_USUARIOS.fmrMesero;
import APP_RESTAURANTE.GUI.FORMS_USUARIOS.fmrRepartidor;

public class fmrIniciarSesion extends JDialog {
    private JLabel CartelBienvenido;
    private JLabel CartelA;
    private JLabel CartelRMS;
    private JLabel CartelINICIASESIO;
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton INICIARSESIONButton;
    private JButton CANCELARButton;
    private JLabel LabelContraseña;
    private JLabel LabelUsuario;
    private JPanel LoginPanel;

    public fmrIniciarSesion() {
        prepararFormulario();
        //Boton iniciar sesion
        INICIARSESIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = txtUsuario.getText();
                String contraseñaUsuario = new String(txtContraseña.getPassword());

                Usuario usuario = new Usuario();
                String rolDeUsuario = usuario.verificarUsuario(nombreUsuario, contraseñaUsuario);

                if (rolDeUsuario != null) {
                    JOptionPane.showMessageDialog(fmrIniciarSesion.this, "Bienvenido, " + rolDeUsuario, "Inicio de Sesión", JOptionPane.INFORMATION_MESSAGE);
                    mostrarMenuPorRol(rolDeUsuario);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(fmrIniciarSesion.this, "Usuario o contraseña incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                    txtUsuario.setText("");
                    txtContraseña.setText("");
                }

            }
        });
        //Boton para cancelar
        CANCELARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión cancelado", "Información", JOptionPane.WARNING_MESSAGE);
                txtUsuario.setText("");
                txtContraseña.setText("");
            }
        });
    }

    private void prepararFormulario() {
        setTitle("Inicio de Sesión");
        setContentPane(LoginPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(570, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        txtUsuario.setText("");
        txtContraseña.setText("");
        Font font = new Font("Arial", Font.PLAIN, 14);
        txtUsuario.setFont(font);
        txtContraseña.setFont(font);
    }

    private void mostrarMenuPorRol(String rolUsuario) {
        this.dispose();
        switch (rolUsuario) {
            case "Administrador":
                //Menu administrador
                fmrAdministrador admin = new fmrAdministrador();
                admin.setVisible(true);
                break;
            case "Repartidor":
                //Menu repartidor
                fmrRepartidor repartidor = new fmrRepartidor();
                repartidor.setVisible(true);
                break;
            case "Mesero":
                //Menu mesero
                fmrMesero mesero = new fmrMesero();
                mesero.setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Rol de usuario no encontrado");
        }
    }
}
