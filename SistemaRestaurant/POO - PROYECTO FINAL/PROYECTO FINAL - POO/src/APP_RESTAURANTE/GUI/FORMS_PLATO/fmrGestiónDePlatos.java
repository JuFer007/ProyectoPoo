package APP_RESTAURANTE.GUI.FORMS_PLATO;
import APP_RESTAURANTE.CLASES.Plato;
import APP_RESTAURANTE.CONEXIONBD.ICRUDPlato;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class fmrGestiónDePlatos extends JFrame {
    private JPanel jpPrincipal;
    private JLabel txtTitulo;
    private JTextField txtNuevoNombrePlato;
    private JLabel txtNombrePlato;
    private JTextField txtNuevoPrecio;
    private JLabel txtPrecioPlato;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JTable tablePlatos;
    private JButton btnModificar;
    private JLabel txtIDmenuPlato;
    private JTextField txtNuevoIDmenuPlato;
    private ICRUDPlato icrudPlato;
    private JFrame formularioPrincipal;

    public fmrGestiónDePlatos(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        icrudPlato = new ICRUDPlato();
        prepararFormulario();
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

    public void prepararFormulario() {
        setContentPane(jpPrincipal);
        setTitle("Gestión de Platos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        DefaultTableModel tablamodel = new DefaultTableModel(new Object[]{"Nombre del Plato", "Precio", "ID Menú"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePlatos.setModel(tablamodel);
        cargarPlatos(tablamodel);
        JScrollPane scrollPane = new JScrollPane(tablePlatos);

        //Configurar etiquetas y campos de texto
        txtTitulo = new JLabel("Gestión de Platos");
        txtTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        txtNombrePlato = new JLabel("Nombre del Plato:");
        txtPrecioPlato = new JLabel("Precio:");
        txtIDmenuPlato = new JLabel("ID Menú:");

        txtNuevoNombrePlato = new JTextField(15);
        txtNuevoPrecio = new JTextField(10);
        txtNuevoIDmenuPlato = new JTextField(5);

        //Crear botones
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        //Configurar el layout
        jpPrincipal = new JPanel();
        GroupLayout layout = new GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(txtTitulo)
        .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(txtNombrePlato)
        .addComponent(txtPrecioPlato)
        .addComponent(txtIDmenuPlato))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(txtNuevoNombrePlato)
        .addComponent(txtNuevoPrecio)
        .addComponent(txtNuevoIDmenuPlato))
        )
        .addGroup(layout.createSequentialGroup()
        .addComponent(btnAgregar)
        .addComponent(btnModificar)
        .addComponent(btnEliminar)
        )
        .addComponent(scrollPane)
        );

        layout.setVerticalGroup(
        layout.createSequentialGroup()
        .addComponent(txtTitulo)
        .addGap(20)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(txtNombrePlato)
        .addComponent(txtNuevoNombrePlato))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(txtPrecioPlato)
        .addComponent(txtNuevoPrecio))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(txtIDmenuPlato)
        .addComponent(txtNuevoIDmenuPlato))
        .addGap(20)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(btnAgregar)
        .addComponent(btnModificar)
        .addComponent(btnEliminar))
        .addGap(20)
        .addComponent(scrollPane)
        );

        setContentPane(jpPrincipal);
        configurarBotones(tablamodel);
    }

    private void cargarPlatos(DefaultTableModel tablamodel) {
        for (Plato plato : icrudPlato.listarPlato()) {
            tablamodel.addRow(new Object[]{plato.getNombrePlato(), plato.getPrecioPlato(), plato.getIdMenu()});
        }
    }

    private void configurarBotones(DefaultTableModel tablamodel) {
        //Boton para agregar plato
        btnAgregar.addActionListener(e -> {
            String nombrePlato = txtNuevoNombrePlato.getText();
            String precioPlato = txtNuevoPrecio.getText();
            String idMenuText = txtNuevoIDmenuPlato.getText();

            if (!nombrePlato.isEmpty() || precioPlato.isEmpty() || idMenuText.isEmpty()){
                JOptionPane.showMessageDialog(jpPrincipal, "Todos los campos son obligatorios", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double precio = Double.parseDouble(precioPlato);
                int idMenu = Integer.parseInt(idMenuText);

                if (precio <= 0){
                    JOptionPane.showMessageDialog(jpPrincipal, "Precio no valido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Plato nuevoPlato = new Plato(nombrePlato, precio, idMenu);
                icrudPlato.crear(nuevoPlato);
                tablamodel.addRow(new Object[]{nuevoPlato.getNombrePlato(), nuevoPlato.getPrecioPlato(), nuevoPlato.getIdMenu()});
                txtNuevoNombrePlato.setText("");
                txtNuevoPrecio.setText("");
                txtNuevoIDmenuPlato.setText("");

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(jpPrincipal, "Operación cancelada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        //Boton para modficiar plato
        btnModificar.addActionListener(e -> {
            int selectedRow = tablePlatos.getSelectedRow();
            if (selectedRow == -1){
                JOptionPane.showMessageDialog(jpPrincipal, "Seleccione un plato para modificar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String nombrePlato = (String) tablePlatos.getValueAt(selectedRow, 0);

            JTextField nuevoPrecioField = new JTextField("");
            JTextField nuevoIDmenuField = new JTextField("");

            Object[] message = {
                    "Nombre del Plato:", nombrePlato,
                    "Nuevo Precio:", nuevoPrecioField,
                    "Nuevo ID Menú:", nuevoIDmenuField
            };

            int opcion = JOptionPane.showConfirmDialog(jpPrincipal, message, "Modificar Plato", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                try {
                    double nuevoPrecio = Double.parseDouble(nuevoPrecioField.getText());
                    int nuevoIDMenu = Integer.parseInt(nuevoIDmenuField.getText());

                    Plato platoModificado = new Plato(nombrePlato, nuevoPrecio, nuevoIDMenu);
                    icrudPlato.actualizar(platoModificado);

                    tablamodel.setValueAt(nuevoPrecio, selectedRow, 1);
                    tablamodel.setValueAt(nuevoIDMenu, selectedRow, 2);

                    JOptionPane.showMessageDialog(jpPrincipal, "Plato modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(jpPrincipal, "Por favor ingrese valores válidos para el precio y el ID del menú.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(jpPrincipal, "Precio e ID Menú deben ser valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        //Boton para eliminar
        btnEliminar.addActionListener(e -> {
            int selectedRow = tablePlatos.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(jpPrincipal, "Seleeccione un plato para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombrePlato = (String) tablePlatos.getValueAt(selectedRow, 0);
            int confimacion = JOptionPane.showConfirmDialog(jpPrincipal, "¿Está seguro de eliminar este plato?", "Confirmacion", JOptionPane.YES_NO_OPTION);

            if (confimacion == JOptionPane.YES_OPTION) {
                icrudPlato.eliminar(nombrePlato);
                tablamodel.setValueAt(selectedRow, selectedRow, 0);
                JOptionPane.showMessageDialog(jpPrincipal, "Plato eliminado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
