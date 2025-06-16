package APP_RESTAURANTE.GUI.FORMS_EMPLEADO;
import APP_RESTAURANTE.CLASES.Empleado;
import APP_RESTAURANTE.CONEXIONBD.ICRUDEmpleado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fmrAgregarEmpleado extends JFrame{
    private JLabel txtTitulo;
    private JTextField textID_Empleado;
    private JTextField txtNombreApellidoEmpleado;
    private JTextField txtDniEmpleado;
    private JTextField textCargoEmpleado;
    private JTextField txtSueldoEmpleado;
    private JButton btnAgregar;
    private ICRUDEmpleado crudEmpleado;
    private JFrame formularioPrincipal;

    public fmrAgregarEmpleado(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        this.crudEmpleado = new ICRUDEmpleado();
        PrepararFormularioAgregarEmpleado();
        btnAgregarEmpleado();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
    }

    private void PrepararFormularioAgregarEmpleado() {
        setTitle("Agregar Empleado");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 460, 350);
        getContentPane().setLayout(null);

        //Título
        txtTitulo = new JLabel("Agregar Nuevo Empleado");
        txtTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        txtTitulo.setBounds(130, 20, 500, 30);
        getContentPane().add(txtTitulo);

        //ID Empleado
        JLabel lblIDEmpleado = new JLabel("ID Empleado:");
        lblIDEmpleado.setBounds(20, 60, 100, 20);
        getContentPane().add(lblIDEmpleado);

        textID_Empleado = new JTextField();
        textID_Empleado.setBounds(130, 60, 200, 20);
        getContentPane().add(textID_Empleado);

        //Nombre y Apellido del Empleado
        JLabel lblNombreApellidoEmpleado = new JLabel("Nombre Completo:");
        lblNombreApellidoEmpleado.setBounds(20, 100, 120, 20);
        getContentPane().add(lblNombreApellidoEmpleado);

        txtNombreApellidoEmpleado = new JTextField();
        txtNombreApellidoEmpleado.setBounds(130, 100, 200, 20);
        getContentPane().add(txtNombreApellidoEmpleado);

        //DNI del Empleado
        JLabel lblDniEmpleado = new JLabel("DNI:");
        lblDniEmpleado.setBounds(20, 140, 100, 20);
        getContentPane().add(lblDniEmpleado);

        txtDniEmpleado = new JTextField();
        txtDniEmpleado.setBounds(130, 140, 200, 20);
        getContentPane().add(txtDniEmpleado);

        //Cargo del Empleado
        JLabel lblCargoEmpleado = new JLabel("Cargo:");
        lblCargoEmpleado.setBounds(20, 180, 100, 20);
        getContentPane().add(lblCargoEmpleado);

        textCargoEmpleado = new JTextField();
        textCargoEmpleado.setBounds(130, 180, 200, 20);
        getContentPane().add(textCargoEmpleado);

        //Sueldo del Empleado
        JLabel lblSueldoEmpleado = new JLabel("Salario:");
        lblSueldoEmpleado.setBounds(20, 220, 100, 20);
        getContentPane().add(lblSueldoEmpleado);

        txtSueldoEmpleado = new JTextField();
        txtSueldoEmpleado.setBounds(130, 220, 200, 20);
        getContentPane().add(txtSueldoEmpleado);

        //Botón Agregar
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(150, 250, 150, 30);
        getContentPane().add(btnAgregar);
    }

    private void btnAgregarEmpleado(){
        btnAgregar.addActionListener(e -> {
            //Validar que no haya campos vacíos
            if (textID_Empleado.getText().isEmpty() || txtNombreApellidoEmpleado.getText().isEmpty() ||
                    txtDniEmpleado.getText().isEmpty() || textCargoEmpleado.getText().isEmpty() ||
                    txtSueldoEmpleado.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return;
            }

            //Validar nombre
            String nombreEmpleado = txtNombreApellidoEmpleado.getText().trim();
            if (!nombreEmpleado.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Validar cargo: 'Administrador', 'Mesero' o 'Repartidor'
            String cargoEmpleado = textCargoEmpleado.getText().trim();
            if (cargoEmpleado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El cargo no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!(cargoEmpleado.equals("Administrador") || cargoEmpleado.equals("Mesero") || cargoEmpleado.equals("Repartidor"))) {
                JOptionPane.showMessageDialog(this, "El cargo debe ser 'Administrador', 'Mesero' o 'Repartidor'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Validar salario
            double salarioEmpleado;
            try {
                salarioEmpleado = Double.parseDouble(txtSueldoEmpleado.getText().trim());
                if (salarioEmpleado <= 1000) {
                    JOptionPane.showMessageDialog(this, "El salario debe ser mayor a 1000 soles.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un salario válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Validar DNI
            String dniEmpleado = txtDniEmpleado.getText().trim();
            if (!dniEmpleado.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this, "El DNI debe contener solo 8 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Validar nuevo ID
            String IDempleado = textID_Empleado.getText().trim();
            if (!IDempleado.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this, "ID incorrecto, solo ingrese números", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idEmpleado = Integer.parseInt(textID_Empleado.getText().trim());
                Empleado nuevoEmpleado = new Empleado(nombreEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado, idEmpleado);
                crudEmpleado.crear(nuevoEmpleado);
                JOptionPane.showMessageDialog(this, "Empleado agregado correctamente!");

                //Limpiar los textos
                textID_Empleado.setText("");
                txtNombreApellidoEmpleado.setText("");
                txtDniEmpleado.setText("");
                textCargoEmpleado.setText("");
                txtSueldoEmpleado.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
