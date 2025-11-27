package proyecto.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class PanelInventario extends JPanel {

    private ProductoManager productoManager;
    private DefaultTableModel modelo;


    public PanelInventario(ProductoManager productoManager) {
        this.productoManager = productoManager;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));

        JLabel titulo = new JLabel("INVENTARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "Nombre", "Descripción", "Proveedor", "Cantidad", "Precio" }, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(1200, 520));
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(240, 240, 240));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(new JLabel("Cantidad (+/-):"));
        JTextField txtCant = new JTextField(8);
        bottom.add(txtCant);


        JButton btnAumentar = new JButton("Aumentar");
        btnAumentar.setBackground(new Color(25, 25, 255));
        btnAumentar.setForeground(Color.white);
        JButton btnDisminuir = new JButton("Disminuir");
        bottom.add(btnAumentar);
        bottom.add(btnDisminuir);
        add(bottom, BorderLayout.SOUTH);

        cargarTabla();

        btnAumentar.addActionListener(e -> {
            int r = tabla.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto");
                return;
            }
            try {
                int delta = Integer.parseInt(txtCant.getText());
                String nombre = modelo.getValueAt(r, 0).toString();
                productoManager.modificarInventario(nombre, delta);
                cargarTabla();
                txtCant.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida");
            }
        });

        btnDisminuir.addActionListener(e -> {
            int r = tabla.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto");
                return;
            }
            try {
                int delta = Integer.parseInt(txtCant.getText());
                String nombre = modelo.getValueAt(r, 0).toString();
                productoManager.modificarInventario(nombre, -Math.abs(delta));
                cargarTabla();
                txtCant.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida");
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Producto p : productoManager.getProductos()) {
            modelo.addRow(new Object[] { p.getNombre(), p.getDescripcion(), p.getProveedor(), p.getCantidad(),
                    p.getPrecio() });
        }
    }
}
