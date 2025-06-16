package APP_RESTAURANTE.GUI.FORMS_MESAS;
import APP_RESTAURANTE.CLASES.Cliente;
import APP_RESTAURANTE.CLASES.Empleado;
import APP_RESTAURANTE.CLASES.Mesa;
import APP_RESTAURANTE.CONEXIONBD.ICRUDCliente;
import APP_RESTAURANTE.CONEXIONBD.ICRUDEmpleado;
import APP_RESTAURANTE.CONEXIONBD.ICRUDMesa;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class fmrGestionMesas extends JFrame {
    private JPanel JpPrincipalGestionMesas;
    private JLabel titulo;
    private JTable tablaMesas;
    private JButton btnModificarMesas;
    private JButton btnAsignarMesero;
    private JComboBox comboBoxTipoListado;
    private JButton btnReservaDeMesa;
    private ICRUDMesa crudMesa;
    private int idMesaSeleccionada = -1;
    private ICRUDEmpleado icrudEmpleado = new ICRUDEmpleado();
    private JFrame formularioPrincipal;

    public fmrGestionMesas(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        crudMesa = new ICRUDMesa();
        prepararFormulario();
        modificarTabla();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formularioPrincipal != null) {
                    formularioPrincipal.setVisible(true);
                }
                dispose();
            }
        });
        //Obtener id de la mesa al hacer click en la tabla
        tablaMesas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaMesas.getSelectedRow();
                if (filaSeleccionada != -1) {
                    idMesaSeleccionada = (int) tablaMesas.getValueAt(filaSeleccionada, 0);
                }
            }
        });

        //Modificar capacidad de la mesa
        btnModificarMesas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idMesaSeleccionada == -1){
                    mostrarMensajeError("Por favor, seleccione una mesa de la tabla");
                    return;
                }

                String nuevaCapacidadMesa = JOptionPane.showInputDialog(fmrGestionMesas.this, "Ingrese la nueva capacidad de la mesa", "Modficiar capacidad", JOptionPane.QUESTION_MESSAGE);
                if (nuevaCapacidadMesa == null || nuevaCapacidadMesa.trim().isEmpty()) {
                    mostrarMensajeError("Debe ingresar una capacidad");
                    return;
                }

                try {
                    int nuevaCapacidad = Integer.parseInt(nuevaCapacidadMesa.trim());
                    if (nuevaCapacidad <= 0) {
                        mostrarMensajeError("Ingrese un valor valido");
                        return;
                    }
                    if (nuevaCapacidad > 10) {
                        mostrarMensajeError("La capacidad no puede ser mayor que 10");
                        return;
                    }

                    boolean actualizada = crudMesa.modificarCapacidadMesa(idMesaSeleccionada, nuevaCapacidad);
                    if (actualizada) {
                        mostrarMensajeExito("Capacidad de mesa modificada exitosamente");
                        modificarTabla();
                    } else {
                        mostrarMensajeError("No se pudo actualizar la capacidad");
                    }
                } catch (Exception exception) {
                    mostrarMensajeError("Por favor, ingrese un número valido");
                }
            }
        });

        //Asignar mesero a mesa vacía
        btnAsignarMesero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idMesaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, seleccione una mesa de la tabla.", "Error", JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                int filaSeleccionada = tablaMesas.getSelectedRow();
                if (filaSeleccionada != -1){
                    int idMesero = (int) tablaMesas.getValueAt(filaSeleccionada, 4);
                    if (idMesero != 0){
                        JOptionPane.showMessageDialog(null, "Seleccione otra mesa, está ya tiene un mesero", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                mostrarDialogoAsignarMesero(idMesaSeleccionada);
            }
        });

        //Reservar mesa
        btnReservaDeMesa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idMesaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, seleccione una mesa de la tabla.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int filaSeleccionada = tablaMesas.getSelectedRow();
                if (filaSeleccionada != -1){
                    String estadoReserva = (String) tablaMesas.getValueAt(filaSeleccionada, 5);
                    if (!estadoReserva.equalsIgnoreCase("No reservado")){
                        JOptionPane.showMessageDialog(null, "La mesa seleccionada ya esta reservada", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                mostrarDialogAsignarReserva(idMesaSeleccionada);
            }
        });
    }

    private void prepararFormulario() {
        setTitle("Gestión de Mesas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        titulo = new JLabel("Gestión de Mesas");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        tablaMesas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaMesas);
        btnModificarMesas = new JButton("Modificar Mesa");
        btnAsignarMesero = new JButton("Asignar Mesero");
        btnReservaDeMesa = new JButton("Realizar Reserva");
        String[] listadoOpciones = {"Todas las Mesas", "Mesas Disponibles", "Mesas Ocupadas"};
        comboBoxTipoListado = new JComboBox<>(listadoOpciones);

        //Opción del listado de comboBox
        comboBoxTipoListado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    modificarTabla();
                }
            }
        });

        JpPrincipalGestionMesas = new JPanel();
        JpPrincipalGestionMesas.setLayout(new BorderLayout());
        JpPrincipalGestionMesas.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        JpPrincipalGestionMesas.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(comboBoxTipoListado);
        panelBotones.add(btnModificarMesas);
        panelBotones.add(btnAsignarMesero);
        panelBotones.add(btnReservaDeMesa);

        JpPrincipalGestionMesas.add(panelBotones, BorderLayout.SOUTH);
        JpPrincipalGestionMesas.add(scrollPane, BorderLayout.CENTER);
        add(JpPrincipalGestionMesas);
        setVisible(true);
    }

    private void modificarTabla() {
        String[] columnas = {"ID", "Número", "Capacidad", "Estado", "ID Mesero", "Estado Reserva", "ID Cliente Reserva"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaMesas.setModel(model);

        List<Mesa> mesas = obtenerMesas();
        for (Mesa mesa : mesas) {
            Object[] fila = {mesa.getIdMesa(), mesa.getNumeroMesa(), mesa.getCapacidadMesa(), mesa.getEstadoMesa(), mesa.getIdMesero(), mesa.getEstadoReserva(), mesa.getIdClienteReserva()};
            model.addRow(fila);
        }
    }

    private List<Mesa> obtenerMesas() {
        List<Mesa> mesas = new ArrayList<>();
        String tipoListado = (String) comboBoxTipoListado.getSelectedItem();
        switch (tipoListado) {
            case "Todas las Mesas":
                mesas = crudMesa.listarTodasLasMesas();
                break;
            case "Mesas Disponibles":
                mesas = crudMesa.listarMesasLibres();
                break;
            case "Mesas Ocupadas":
                mesas = crudMesa.listarMesasOcupadas();
                break;
        }
        return mesas;
    }

    private void mostrarDialogoAsignarMesero(int idMesa) {
        List<Empleado> meseros = icrudEmpleado.listarMeseros();
        if (meseros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay meseros disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JComboBox<String> comboBoxMeseros = new JComboBox<>();
        for (Empleado mesero : meseros) {
            comboBoxMeseros.addItem(mesero.getIdEmpleado() + " - " + mesero.getNombreEmpleado());
        }
        int opcion = JOptionPane.showConfirmDialog(
                null, comboBoxMeseros, "Seleccionar Mesero", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.OK_OPTION) {
            String seleccionado = (String) comboBoxMeseros.getSelectedItem();
            int idMesero = Integer.parseInt(seleccionado.split(" - ")[0]);
            try {
                crudMesa.asignarMeseroAmesa(idMesero, idMesa);
                JOptionPane.showMessageDialog(null, "Mesero asignado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                modificarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al asignar mesero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarDialogAsignarReserva(int idMesa) {
        ICRUDCliente icrudCliente = new ICRUDCliente();
        List<Cliente> clientes = icrudCliente.listarClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay una lista de clientes definidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JComboBox<String> comboBoxMeseros = new JComboBox<>();
        for (Cliente cliente : clientes) {
            comboBoxMeseros.addItem(cliente.getIdCliente() + " - " + cliente.getNombreCliente());
        }

        int opcion = JOptionPane.showConfirmDialog(
                null,
                comboBoxMeseros,
                "Seleccionar Cliente",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.OK_OPTION) {
            String seleccionado = (String) comboBoxMeseros.getSelectedItem();
            int idCliente = Integer.parseInt(seleccionado.split(" - ")[0]);
            try {
                crudMesa.agregarReservacion(idCliente, idMesa);
                JOptionPane.showMessageDialog(null, "Reserva realizada de manera exitosa.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                modificarTabla();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void mostrarMensajeError (String mensaje){
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensajeExito(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
}
