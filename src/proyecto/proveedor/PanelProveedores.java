package proyecto.proveedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class PanelProveedores extends JPanel {

    private ProveedorManager manager;
    private DefaultTableModel modelo;

    public PanelProveedores(ProveedorManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));

        JLabel titulo = new JLabel("PROVEEDORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[] { "Nombre", "Tel", "Correo", "Dirección", "RFC", "Pago", "Tipo", "Fecha" }, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(1100, 520));
        add(scroll, BorderLayout.CENTER);

        // formulario sencillo abajo
        JPanel abajo = new JPanel(new GridLayout(2, 1));
        abajo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        abajo.setBackground(new Color(240, 240, 240));

        JPanel form = new JPanel(new GridLayout(4, 4, 8, 8));
        JTextField txtNombre = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtDir = new JTextField();
        JTextField txtRFC = new JTextField();
        JComboBox<String> cbPago = new JComboBox<>(new String[] { "Efectivo", "Transferencia", "Crédito" });
        JComboBox<String> cbTipo = new JComboBox<>(new String[] { "Electrónica", "Accesorios", "Ropa", "General" });
        JTextField txtFecha = new JTextField(java.time.LocalDate.now().toString());

        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTel);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(new JLabel("Dirección:"));
        form.add(txtDir);
        form.add(new JLabel("RFC:"));
        form.add(txtRFC);
        form.add(new JLabel("Forma pago:"));
        form.add(cbPago);
        form.add(new JLabel("Tipo producto:"));
        form.add(cbTipo);
        form.add(new JLabel("Fecha registro:"));
        form.add(txtFecha);

        abajo.add(form);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Agregar");
        btnAdd.setBackground(new Color(25, 25, 255));
        btnAdd.setForeground(Color.white);
        JButton btnUpd = new JButton("Actualizar");
        JButton btnDel = new JButton("Eliminar");
        botones.add(btnAdd);
        botones.add(btnUpd);
        botones.add(btnDel);
        abajo.add(botones);

        add(abajo, BorderLayout.SOUTH);

        cargarTabla();

        btnAdd.addActionListener(e -> {
            Proveedor p = new Proveedor(txtNombre.getText(), txtTel.getText(), txtCorreo.getText(), txtDir.getText(),
                    txtRFC.getText(), cbPago.getSelectedItem().toString(), cbTipo.getSelectedItem().toString(),
                    txtFecha.getText());
            if (manager.agregar(p)) {
                JOptionPane.showMessageDialog(this, "Proveedor agregado");
                cargarTabla();
            } else
                JOptionPane.showMessageDialog(this, "Proveedor ya existe");
        });

        btnUpd.addActionListener(e -> {
            if (manager.actualizar(txtNombre.getText(), txtTel.getText(), txtCorreo.getText(), txtDir.getText(),
                    txtRFC.getText(),
                    cbPago.getSelectedItem().toString(), cbTipo.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Proveedor actualizado");
                cargarTabla();
            } else
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
        });

        btnDel.addActionListener(e -> {
            if (manager.eliminar(txtNombre.getText())) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado");
                cargarTabla();
            } else
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int r = tabla.getSelectedRow();
                txtNombre.setText(modelo.getValueAt(r, 0).toString());
                txtTel.setText(modelo.getValueAt(r, 1).toString());
                txtCorreo.setText(modelo.getValueAt(r, 2).toString());
                txtDir.setText(modelo.getValueAt(r, 3).toString());
                txtRFC.setText(modelo.getValueAt(r, 4).toString());
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Proveedor p : manager.getProveedores()) {
            modelo.addRow(new Object[] { p.getNombre(), p.getTelefono(), p.getCorreo(), p.getDireccion(), p.getRfc(),
                    p.getFormaPago(), p.getTipoProducto(), p.getFechaRegistro() });
        }
    }
}
