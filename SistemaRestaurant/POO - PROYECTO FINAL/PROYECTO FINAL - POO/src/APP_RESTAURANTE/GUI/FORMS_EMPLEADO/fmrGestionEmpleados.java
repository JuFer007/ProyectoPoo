package APP_RESTAURANTE.GUI.FORMS_EMPLEADO;
import APP_RESTAURANTE.CLASES.Empleado;
import APP_RESTAURANTE.CONEXIONBD.ICRUDEmpleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class fmrGestionEmpleados extends JFrame {
    private JPanel jpPanerPrincipal = new JPanel();
    private JButton btnAgregarEmpleado;
    private JButton btnModificarEmpleado;
    private JButton btnEliminarEmpleado;
    private JTable tableEmpleados = new JTable();
    private JLabel txtGestionEmpleados = new JLabel("Gestion empleados");
    fmrAgregarEmpleado agregarEmpleado = new fmrAgregarEmpleado(fmrGestionEmpleados.this);
    fmrModificarEmpleado modificarEmpleado;
    ICRUDEmpleado crudEmpleado = new ICRUDEmpleado();
    private JFrame formularioPrincipal;

    public fmrGestionEmpleados(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        prepararFormulario();
        agregarTexto();
        cargarEmpleadosEnTabla();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
        btnAgregarEmpleado.addActionListener(new ActionListener() {
            //Botón agregar empleado
            @Override
            public void actionPerformed(ActionEvent e) {
                fmrAgregarEmpleado agregarEmpleado = new fmrAgregarEmpleado(fmrGestionEmpleados.this);
                agregarEmpleado.setVisible(true);
            }
        });

        btnModificarEmpleado.addActionListener(new ActionListener() {
            //Botón modificar empleado
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableEmpleados.getSelectedRow();
                if (filaSeleccionada <= -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                fmrModificarEmpleado modificarEmpleado = new fmrModificarEmpleado(fmrGestionEmpleados.this, filaSeleccionada);
                modificarEmpleado.setVisible(true);
            }
        });

        btnEliminarEmpleado.addActionListener(new ActionListener() {
            //Botón eliminar empleado
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableEmpleados.getSelectedRow();
                if (filaSeleccionada <= -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                eliminarEmpleado(filaSeleccionada);
            }
        });
    }

    private void prepararFormulario() {
        setContentPane(jpPanerPrincipal);
        setSize(800, 500);
        setTitle("Gestion de empleados");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        jpPanerPrincipal.setLayout(null);

        DefaultTableModel tablamodel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablamodel.addColumn("ID Empleado");
        tablamodel.addColumn("Nombre");
        tablamodel.addColumn("DNI");
        tablamodel.addColumn("Salario");
        tablamodel.addColumn("Cargo");

        tableEmpleados.setModel(tablamodel);

        JScrollPane scrollPane = new JScrollPane(tableEmpleados);
        scrollPane.setBounds(20, 100, 750, 300);

        jpPanerPrincipal.setLayout(null);
        jpPanerPrincipal.add(scrollPane);

        int buttonWidth = 150;
        int buttonHeight = 30;
        int gap = 20;

        int totalWidth = 3 * buttonWidth + 2 * gap;
        int startX = (getWidth() - totalWidth) / 2;

        btnAgregarEmpleado = new JButton("Agregar Empleado");
        btnAgregarEmpleado.setBounds(startX, 420, buttonWidth, buttonHeight);
        jpPanerPrincipal.add(btnAgregarEmpleado);

        btnModificarEmpleado = new JButton("Modificar Empleado");
        btnModificarEmpleado.setBounds(startX + buttonWidth + gap, 420, buttonWidth, buttonHeight);
        jpPanerPrincipal.add(btnModificarEmpleado);

        btnEliminarEmpleado = new JButton("Eliminar Empleado");
        btnEliminarEmpleado.setBounds(startX + 2 * (buttonWidth + gap), 420, buttonWidth, buttonHeight);
        jpPanerPrincipal.add(btnEliminarEmpleado);
    }

    private void cargarEmpleadosEnTabla() {
        List<Empleado> empleados = crudEmpleado.listarEmpleado();
        DefaultTableModel modelo = (DefaultTableModel) tableEmpleados.getModel();
        modelo.setNumRows(0);

        for (Empleado empleado : empleados) {
            Object[] fila = {
                    empleado.getIdEmpleado(),
                    empleado.getNombreEmpleado(),
                    empleado.getDniEmpleado(),
                    empleado.getSalarioEmpleado(),
                    empleado.getCargoEmpleado(),
            };
            modelo.addRow(fila);
        }
    }

    private void agregarTexto() {
        txtGestionEmpleados.setBounds(20, 20, 200, 30);
        txtGestionEmpleados.setFont(new Font("Tahoma", Font.BOLD, 16));

        FontMetrics fontMetrics = txtGestionEmpleados.getFontMetrics(txtGestionEmpleados.getFont());
        int textoWidth = fontMetrics.stringWidth(txtGestionEmpleados.getText());
        int textoHeight = fontMetrics.getHeight();

        int ventanaWidth = getWidth();
        int ventanaHeight = getHeight();
        int x = (ventanaWidth - textoWidth) / 2;
        int y = 20;

        txtGestionEmpleados.setBounds(x, y, textoWidth, textoHeight);
        jpPanerPrincipal.add(txtGestionEmpleados);
    }

    private void eliminarEmpleado(int filaseleccionada) {
        String dniEmpleado = tableEmpleados.getValueAt(filaseleccionada, 2).toString();
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este empleado?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            crudEmpleado.eliminar(dniEmpleado);
            cargarEmpleadosEnTabla();
            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
        }
    }
}