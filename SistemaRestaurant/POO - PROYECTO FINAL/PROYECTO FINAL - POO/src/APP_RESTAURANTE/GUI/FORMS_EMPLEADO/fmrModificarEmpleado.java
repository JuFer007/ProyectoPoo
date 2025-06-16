package APP_RESTAURANTE.GUI.FORMS_EMPLEADO;
import APP_RESTAURANTE.CLASES.Empleado;
import APP_RESTAURANTE.CONEXIONBD.ICRUDEmpleado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fmrModificarEmpleado extends JFrame{
    private JLabel txtModificarDatos;
    private JTextField textNuevoID;
    private JTextField txtNuevoNombreApellido;
    private JTextField txtNuevoDNI;
    private JTextField txtNuevoSalario;
    private JTextField txtNuevoCargo;
    private JButton btnModificarEmpleado;
    private JLabel txtCargo;
    private JLabel txtSalario;
    private JLabel txtDNI;
    private JLabel txtNombreApellido;
    private JLabel txtID;
    private JFrame formularioPrincipal;
    ICRUDEmpleado crudEmpleado;

    public fmrModificarEmpleado(JFrame formularioPrincipal, int filaSeleccionada) {
        this.formularioPrincipal = formularioPrincipal;
        crudEmpleado = new ICRUDEmpleado();
        PrepararFormularioModificarEmpleado();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
        if (filaSeleccionada == 0){
            JOptionPane.showMessageDialog(null, "Seleccione un ID del empleado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Modificar empleado
        btnModificarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoID = textNuevoID.getText();
                String nuevoNombreApellido = txtNuevoNombreApellido.getText();
                String nuevoDNI = txtNuevoDNI.getText();
                String nuevoCargo = txtNuevoCargo.getText();
                String nuevoSalarioStr = txtNuevoSalario.getText();

                //Validar que los campos no esten vacios
                if (nuevoID.isEmpty() || nuevoNombreApellido.isEmpty() || nuevoDNI.isEmpty() || nuevoCargo.isEmpty() || nuevoSalarioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                //Validar nombre
                if (!nuevoNombreApellido.matches("[a-zA-Z\\s]+")) {
                    JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Validar de cargo
                if (!(nuevoCargo.equals("Administrador") || nuevoCargo.equals("Mesero") || nuevoCargo.equals("Repartidor"))) {
                    JOptionPane.showMessageDialog(null, "El cargo debe ser 'Administrador', 'Mesero' o 'Repartidor'.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Validar salario
                double nuevoSalario;
                try {
                    nuevoSalario = Double.parseDouble(nuevoSalarioStr);
                    if (nuevoSalario <= 1000) {
                        JOptionPane.showMessageDialog(null, "El salario debe ser mayor a 1000 soles.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Validar DNI
                if (!nuevoDNI.matches("\\d{8}")) {
                    JOptionPane.showMessageDialog(null, "El DNI debe contener 8 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Empleado empleado = new Empleado();
                    empleado.setIdEmpleado(Integer.parseInt(nuevoID));
                    empleado.setNombreEmpleado(nuevoNombreApellido);
                    empleado.setDniEmpleado(nuevoDNI);
                    empleado.setCargoEmpleado(nuevoCargo);
                    empleado.setSalarioEmpleado(nuevoSalario);
                    crudEmpleado.actualizar(empleado);
                    JOptionPane.showMessageDialog(null, "Empleado modificado con éxito.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El salario debe ser un número válido.");
                }
            }
        });
    }

    private void PrepararFormularioModificarEmpleado() {
        setTitle("Modificar Empleado");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 460, 350);
        getContentPane().setLayout(null);

        //Título
        txtModificarDatos = new JLabel("Modificar Datos del Empleado");
        txtModificarDatos.setFont(new Font("Tahoma", Font.BOLD, 16));
        txtModificarDatos.setBounds(130, 20, 500, 30);
        getContentPane().add(txtModificarDatos);

        //ID Empleado
        JLabel lblIDEmpleado = new JLabel("ID Empleado:");
        lblIDEmpleado.setBounds(20, 60, 100, 20);
        getContentPane().add(lblIDEmpleado);

        textNuevoID = new JTextField();
        textNuevoID.setBounds(130, 60, 200, 20);
        getContentPane().add(textNuevoID);

        //Nombre y Apellido del Empleado
        JLabel lblNombreApellido = new JLabel("Nombre Completo:");
        lblNombreApellido.setBounds(20, 100, 120, 20);
        getContentPane().add(lblNombreApellido);

        txtNuevoNombreApellido = new JTextField();
        txtNuevoNombreApellido.setBounds(130, 100, 200, 20);
        getContentPane().add(txtNuevoNombreApellido);

        //DNI del Empleado
        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setBounds(20, 140, 100, 20);
        getContentPane().add(lblDNI);

        txtNuevoDNI = new JTextField();
        txtNuevoDNI.setBounds(130, 140, 200, 20);
        getContentPane().add(txtNuevoDNI);

        //Cargo del Empleado
        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setBounds(20, 180, 100, 20);
        getContentPane().add(lblCargo);

        txtNuevoCargo = new JTextField();
        txtNuevoCargo.setBounds(130, 180, 200, 20);
        getContentPane().add(txtNuevoCargo);

        //Salario del Empleado
        JLabel lblSalario = new JLabel("Salario:");
        lblSalario.setBounds(20, 220, 100, 20);
        getContentPane().add(lblSalario);

        txtNuevoSalario = new JTextField();
        txtNuevoSalario.setBounds(130, 220, 200, 20);
        getContentPane().add(txtNuevoSalario);

        //Botón Modificar
        btnModificarEmpleado = new JButton("Modificar");
        btnModificarEmpleado.setBounds(150, 270, 150, 30);
        getContentPane().add(btnModificarEmpleado);
    }
}