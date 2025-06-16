package APP_RESTAURANTE.GUI.FORMS_MENU;
import APP_RESTAURANTE.CLASES.Plato;
import APP_RESTAURANTE.CONEXIONBD.ICRUDmenu;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class fmrGestionMenu extends JFrame{
    private JPanel JpPanelGestionMenu;
    private JLabel titulo;
    private JComboBox comboBoxListarMenu;
    private JButton btnModificarMenu;
    private JTable table1;
    private ICRUDmenu icrudMenu = new ICRUDmenu();
    private ArrayList<Plato> platos = new ArrayList<>();
    private JFrame formularioPrincipal;

    public fmrGestionMenu(JFrame formularioPrincipal) {
        this.formularioPrincipal = formularioPrincipal;
        prepararFormulario();
        cargarMenus();
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

    private void prepararFormulario() {
        setTitle("Gestión de Menú");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);

        JpPanelGestionMenu = new JPanel();
        JpPanelGestionMenu.setLayout(new BorderLayout(10, 10));
        JpPanelGestionMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(JpPanelGestionMenu);

        titulo = new JLabel("Gestión de Menú", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.BLACK);
        JpPanelGestionMenu.add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(10, 10));

        JPanel panelComboBox = new JPanel();
        panelComboBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelMenu = new JLabel("Seleccione un Menú:");
        labelMenu.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBoxListarMenu = new JComboBox<>();
        comboBoxListarMenu.setPreferredSize(new Dimension(200, 30));
        panelComboBox.add(labelMenu);
        panelComboBox.add(comboBoxListarMenu);
        panelCentral.add(panelComboBox, BorderLayout.NORTH);

        table1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        JpPanelGestionMenu.add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cargarMenus() {
        String[] nombresMenus = {"Menú Del Mar", "Menú Criollo", "Menú Parrillada", "Menú Andino"};
        for (int i = 0; i < nombresMenus.length; i++) {
            comboBoxListarMenu.addItem(nombresMenus[i]);
        }
        comboBoxListarMenu.setSelectedIndex(0);
        cargarDatosTabla("Menú Del Mar", 1);

        comboBoxListarMenu.addActionListener(e -> {
            int selectedIndex = comboBoxListarMenu.getSelectedIndex();
            String nombreMenu = nombresMenus[selectedIndex];
            int idMenu = selectedIndex + 1;
            cargarDatosTabla(nombreMenu, idMenu);
        });
    }

    private void cargarDatosTabla(String nombreMenu, int idMenu) {
        platos = icrudMenu.listarMenu(nombreMenu, idMenu);

        if (platos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron platos para este menú.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] columnNames = {"ID Menú", "Nombre", "Precio"};
        Object[][] data = new Object[platos.size()][3];

        for (int i = 0; i < platos.size(); i++) {
            data[i][0] = platos.get(i).getIdMenu();
            data[i][1] = platos.get(i).getNombrePlato();
            data[i][2] = platos.get(i).getPrecioPlato();
        }

        table1.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        table1.revalidate();
        table1.repaint();
    }
}
