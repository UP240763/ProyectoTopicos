package proyecto.venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto.producto.Producto;
import proyecto.producto.ProductoManager;

import java.awt.*;
import java.util.ArrayList;

public class PanelVentas extends JPanel {

    private VentaManager ventaManager;
    private ProductoManager productoManager;
    private JTable tablaVentas;
    private DefaultTableModel modeloVentas;
    private JButton btnRealizarVenta;

    public PanelVentas(VentaManager vm, ProductoManager pm) {
        this.ventaManager = vm;
        this.productoManager = pm;
        setLayout(new BorderLayout());

        // === MOVER BOTÓN A ABAJO DERECHA ===
        btnRealizarVenta = new JButton("Realizar Venta");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(btnRealizarVenta, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        btnRealizarVenta.addActionListener(e -> abrirVentanaVenta());

        modeloVentas = new DefaultTableModel(
                new String[] { "Cliente", "Producto", "Cantidad", "Precio Unitario", "Subtotal" }, 0);
        tablaVentas = new JTable(modeloVentas);
        add(new JScrollPane(tablaVentas), BorderLayout.CENTER);

        refrescarTabla();
    }

    private void abrirVentanaVenta() {
        JDialog ventana = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Realizar Venta", true);
        ventana.setSize(600, 500);
        ventana.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Cliente
        c.gridx = 0;
        c.gridy = fila;
        panel.add(new JLabel("Cliente:"), c);

        c.gridx = 1;
        JTextField txtCliente = new JTextField();
        panel.add(txtCliente, c);

        fila++;

        // Número o correo
        c.gridx = 0;
        c.gridy = fila;
        panel.add(new JLabel("Número / Correo:"), c);

        c.gridx = 1;
        JTextField txtContacto = new JTextField();
        panel.add(txtContacto, c);

        fila++;

        // Producto
        c.gridx = 0;
        c.gridy = fila;
        panel.add(new JLabel("Producto:"), c);

        c.gridx = 1;
        JComboBox<String> cbProducto = new JComboBox<>();

        for (Producto p : productoManager.listarProductos())
            cbProducto.addItem(p.getNombre());

        panel.add(cbProducto, c);

        fila++;

        // Cantidad
        c.gridx = 0;
        c.gridy = fila;
        panel.add(new JLabel("Cantidad:"), c);

        c.gridx = 1;
        JTextField txtCantidad = new JTextField();
        panel.add(txtCantidad, c);

        fila++;

        // Botón agregar producto
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 2;
        JButton btnAgregar = new JButton("Agregar Producto");
        panel.add(btnAgregar, c);

        fila++;

        // Tabla temporal
        DefaultTableModel modeloTemp = new DefaultTableModel(
                new String[] { "Producto", "Cantidad", "Precio Unitario", "Subtotal" }, 0);

        JTable tablaTemp = new JTable(modeloTemp);

        ventana.add(panel, BorderLayout.NORTH);
        ventana.add(new JScrollPane(tablaTemp), BorderLayout.CENTER);

        // Botón finalizar venta
        JButton btnFinalizar = new JButton("Finalizar Venta");
        ventana.add(btnFinalizar, BorderLayout.SOUTH);

        // Acción agregar producto
        btnAgregar.addActionListener(ev -> {
            try {
                String nombreProd = cbProducto.getSelectedItem().toString();
                Producto p = productoManager.buscar(nombreProd);
                int cant = Integer.parseInt(txtCantidad.getText());

                if (cant <= 0) {
                    JOptionPane.showMessageDialog(ventana, "Cantidad inválida");
                    return;
                }
                if (cant > p.getCantidad()) {
                    JOptionPane.showMessageDialog(ventana, "Inventario insuficiente");
                    return;
                }

                double precio = p.getPrecio();
                double subtotal = precio * cant;

                modeloTemp.addRow(new Object[] { nombreProd, cant, precio, subtotal });
                txtCantidad.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventana, "Error al agregar producto");
            }
        });

        // Acción finalizar venta
        btnFinalizar.addActionListener(ev -> {
            String nombreCliente = txtCliente.getText().trim();
            String contacto = txtContacto.getText().trim();

            if (nombreCliente.isEmpty() || contacto.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Debe llenar cliente y contacto.");
                return;
            }

            ArrayList<ItemVenta> items = new ArrayList<>();

            for (int i = 0; i < modeloTemp.getRowCount(); i++) {
                String nombre = modeloTemp.getValueAt(i, 0).toString();
                int cant = Integer.parseInt(modeloTemp.getValueAt(i, 1).toString());
                Producto p = productoManager.buscar(nombre);
                items.add(new ItemVenta(p, cant));
            }

            if (items.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Debe agregar productos");
                return;
            }

            ventaManager.registrarVenta(nombreCliente + " | " + contacto, items);
            refrescarTabla();
            ventana.dispose();
        });

        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }

    // Número o correo

    private void refrescarTabla() {
        modeloVentas.setRowCount(0);
        for (Venta v : ventaManager.listarVentas()) {
            for (ItemVenta i : v.getItems()) {
                modeloVentas.addRow(new Object[] {
                        v.getCliente(),
                        i.getProducto().getNombre(),
                        i.getCantidad(),
                        i.getPrecioUnitario(),
                        i.getSubtotal()
                });
            }
        }
    }
}
