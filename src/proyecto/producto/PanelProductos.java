package proyecto.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto.proveedor.Proveedor;
import proyecto.proveedor.ProveedorManager;

import java.awt.*;

public class PanelProductos extends JPanel {

    private ProductoManager manager;
    private ProveedorManager proveedorManager;
    private DefaultTableModel modelo;

    public PanelProductos(ProductoManager manager, ProveedorManager proveedorManager) {
        this.manager = manager;
        this.proveedorManager = proveedorManager;

        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));

        // ======= TÍTULO =======
        JLabel titulo = new JLabel("GESTIÓN DE PRODUCTOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        // ======= TABLA =======
        modelo = new DefaultTableModel(new Object[] { "Nombre", "Descripción", "Proveedor", "Cantidad", "Precio" }, 0);

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 18));
        tabla.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(1500, 500));
        add(scroll, BorderLayout.CENTER);

        // ======= PANEL INFERIOR =======
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bottomPanel.setBackground(new Color(240, 240, 240));

        // ======= FORMULARIO ORDENADO (GRIDBAGLAYOUT) =======
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(240, 240, 240));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 15, 10, 15);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        txtNombre.setPreferredSize(new Dimension(200, 40));

        JTextArea txtDesc = new JTextArea(3, 20);
        txtDesc.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane descScroll = new JScrollPane(txtDesc);

        JComboBox<String> cbProv = new JComboBox<>();
        cbProv.setFont(new Font("Arial", Font.PLAIN, 18));
        for (Proveedor p : proveedorManager.getProveedores())
            cbProv.addItem(p.getNombre());

        JTextField txtCant = new JTextField();
        txtCant.setFont(new Font("Arial", Font.PLAIN, 18));

        JTextField txtPrecio = new JTextField();
        txtPrecio.setFont(new Font("Arial", Font.PLAIN, 18));

        // ===== POSICIÓN DE CAMPOS =====
        int row = 0;

        // Nombre
        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Nombre del producto:", JLabel.RIGHT), c);
        c.gridx = 1;
        form.add(txtNombre, c);

        // Proveedor
        c.gridx = 2;
        form.add(new JLabel("Proveedor:", JLabel.RIGHT), c);
        c.gridx = 3;
        form.add(cbProv, c);

        row++;

        // Cantidad
        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Cantidad:", JLabel.RIGHT), c);
        c.gridx = 1;
        form.add(txtCant, c);

        // Precio
        c.gridx = 2;
        form.add(new JLabel("Precio:", JLabel.RIGHT), c);
        c.gridx = 3;
        form.add(txtPrecio, c);

        row++;

        // Descripción
        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Descripción:", JLabel.RIGHT), c);
        c.gridx = 1;
        c.gridwidth = 3;
        form.add(descScroll, c);
        c.gridwidth = 1;

        bottomPanel.add(form, BorderLayout.CENTER);

        // ===== BOTONES ====
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        botones.setBackground(new Color(240, 240, 240));

        JButton btnAdd = new JButton("Agregar");
        JButton btnUpd = new JButton("Actualizar");
        JButton btnDel = new JButton("Eliminar");

        btnAdd.setPreferredSize(new Dimension(180, 45));
        btnUpd.setPreferredSize(new Dimension(180, 45));
        btnDel.setPreferredSize(new Dimension(180, 45));

        btnAdd.setBackground(new Color(25, 25, 255));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial Black", Font.BOLD, 18));

        btnUpd.setFont(new Font("Arial", Font.BOLD, 18));
        btnDel.setFont(new Font("Arial", Font.BOLD, 18));

        botones.add(btnAdd);
        botones.add(btnUpd);
        botones.add(btnDel);

        bottomPanel.add(botones, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== CARGA DE TABLA =====
        cargarTabla();

        // ===== EVENTOS =====
        btnAdd.addActionListener(e -> {
            try {
                Producto p = new Producto(
                        txtNombre.getText(),
                        txtDesc.getText(),
                        cbProv.getSelectedItem().toString(),
                        Integer.parseInt(txtCant.getText()),
                        Double.parseDouble(txtPrecio.getText()));
                if (manager.agregar(p)) {
                    JOptionPane.showMessageDialog(this, "Producto agregado");
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe un producto con ese nombre");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        btnUpd.addActionListener(e -> {
            try {
                if (manager.actualizar(
                        txtNombre.getText(),
                        txtDesc.getText(),
                        cbProv.getSelectedItem().toString(),
                        Integer.parseInt(txtCant.getText()),
                        Double.parseDouble(txtPrecio.getText()))) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado");
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "El producto no existe");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        btnDel.addActionListener(e -> {
            if (manager.eliminar(txtNombre.getText())) {
                JOptionPane.showMessageDialog(this, "Producto eliminado");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "El producto no existe");
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int r = tabla.getSelectedRow();
                txtNombre.setText(modelo.getValueAt(r, 0).toString());
                txtDesc.setText(modelo.getValueAt(r, 1).toString());
                cbProv.setSelectedItem(modelo.getValueAt(r, 2).toString());
                txtCant.setText(modelo.getValueAt(r, 3).toString());
                txtPrecio.setText(modelo.getValueAt(r, 4).toString());
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Producto p : manager.getProductos()) {
            modelo.addRow(new Object[] {
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getProveedor(),
                    p.getCantidad(),
                    p.getPrecio()
            });
        }
    }
}
