package proyecto.usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class PanelUsuarios extends JPanel {

    private UsuarioManager usuarioManager;
    private DefaultTableModel modelo;

    public PanelUsuarios(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));

        JLabel titulo = new JLabel("GESTIÓN DE USUARIOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "Usuario", "Correo", "Contraseña", "Rol" }, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 16));
        tabla.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(1100, 520)); // espacio amplio
        add(scroll, BorderLayout.CENTER);

        // formulario y botones abajo
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        abajo.setBackground(new Color(240, 240, 240));

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBackground(new Color(240, 240, 240));
        JTextField txtUsuario = new JTextField();
        JTextField txtCorreo = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JComboBox<String> cbRol = new JComboBox<>(new String[] { "Administrador", "Empleado" });

        form.add(new JLabel("Usuario:"));
        form.add(txtUsuario);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(new JLabel("Contraseña:"));
        form.add(txtPass);
        form.add(new JLabel("Rol:"));
        form.add(cbRol);

        abajo.add(form, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        btnAgregar.setBackground(new Color(25, 25, 255));
        btnAgregar.setForeground(Color.white);
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        abajo.add(botones, BorderLayout.SOUTH);
        add(abajo, BorderLayout.SOUTH);

        // permisos: empleados no pueden tocar
        Usuario actual = usuarioManager.getUsuarioActual();
        if (actual != null && actual.getRol().equalsIgnoreCase("Empleado")) {
            btnAgregar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            txtUsuario.setEnabled(false);
            txtCorreo.setEnabled(false);
            txtPass.setEnabled(false);
            cbRol.setEnabled(false);
        }

        cargarTabla();

        btnAgregar.addActionListener(e -> {
            String u = txtUsuario.getText().trim();
            String c = txtCorreo.getText().trim();
            String p = new String(txtPass.getPassword()).trim();
            String r = cbRol.getSelectedItem().toString();
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuario y contraseña obligatorios");
                return;
            }
            if (usuarioManager.agregar(new Usuario(u, c, p, r))) {
                JOptionPane.showMessageDialog(this, "Usuario agregado");
                cargarTabla();
            } else
                JOptionPane.showMessageDialog(this, "Usuario ya existe");
        });

        btnActualizar.addActionListener(e -> {
            String u = txtUsuario.getText().trim();
            String c = txtCorreo.getText().trim();
            String p = new String(txtPass.getPassword()).trim();
            String r = cbRol.getSelectedItem().toString();
            if (usuarioManager.actualizar(u, c, p, r)) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado");
                cargarTabla();
            } else
                JOptionPane.showMessageDialog(this, "Usuario no existe");
        });

        btnEliminar.addActionListener(e -> {
            String u = txtUsuario.getText().trim();
            if (usuarioManager.eliminar(u)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado");
                cargarTabla();
            } else
                JOptionPane.showMessageDialog(this, "Usuario no existe");
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                txtUsuario.setText(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                txtCorreo.setText(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
                txtPass.setText(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
                cbRol.setSelectedItem(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Usuario u : usuarioManager.getUsuarios()) {
            modelo.addRow(new Object[] { u.getUsuario(), u.getCorreo(), u.getContraseña(), u.getRol() });
        }
    }
}
