package proyecto.venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {

    private VentaManager ventaManager;
    private DefaultTableModel modelo;

    private JLabel lblSubtotal, lblIVA, lblTotal;

    public PanelReportes(VentaManager ventaManager) {
        this.ventaManager = ventaManager;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // --------------------------
        // TITULO
        // --------------------------
        JLabel titulo = new JLabel("REPORTE DE VENTAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // --------------------------
        // TABLA DE VENTAS
        // --------------------------
        modelo = new DefaultTableModel(
                new Object[] { "Folio", "Descripción", "Cantidad", "P.Unitario", "Subtotal" }, 0);

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);

        JScrollPane scrTabla = new JScrollPane(tabla);
        scrTabla.setPreferredSize(new Dimension(900, 400));
        add(scrTabla, BorderLayout.CENTER);

        // --------------------------
        // TOTALES
        // --------------------------
        JPanel panelTot = new JPanel(new GridLayout(3, 2, 10, 10));
        panelTot.setBackground(new Color(240, 240, 240));

        lblSubtotal = new JLabel("$0.00");
        lblIVA = new JLabel("$0.00");
        lblTotal = new JLabel("$0.00");

        lblSubtotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblIVA.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setFont(new Font("Arial Black", Font.BOLD, 18));

        panelTot.add(new JLabel("Subtotal:"));
        panelTot.add(lblSubtotal);
        panelTot.add(new JLabel("IVA (16%):"));
        panelTot.add(lblIVA);
        panelTot.add(new JLabel("TOTAL:"));
        panelTot.add(lblTotal);

        add(panelTot, BorderLayout.SOUTH);

        // --------------------------
        // BOTÓN DE RECARGA
        // --------------------------
        JButton btnRecargar = new JButton("Recargar Ventas");
        btnRecargar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRecargar.addActionListener(e -> recargarTabla());
        add(btnRecargar, BorderLayout.EAST);

        // Inicial
        recargarTabla();
    }

    // --------------------------
    // MÉTODOS
    // --------------------------
    private void recargarTabla() {
        modelo.setRowCount(0);
        double subtotalTotal = 0;

        for (Venta v : ventaManager.listarVentas()) {
            for (ItemVenta i : v.getItems()) {
                double sub = i.getSubtotal();
                modelo.addRow(new Object[] {
                        v.getCliente(), // Cliente
                        i.getProducto().getNombre(), // Producto/Descripción
                        i.getCantidad(),
                        i.getPrecioUnitario(),
                        sub
                });
                subtotalTotal += sub;
            }
        }

        double iva = subtotalTotal * 0.16;
        double total = subtotalTotal + iva;

        lblSubtotal.setText(String.format("$%.2f", subtotalTotal));
        lblIVA.setText(String.format("$%.2f", iva));
        lblTotal.setText(String.format("$%.2f", total));
    }
}
