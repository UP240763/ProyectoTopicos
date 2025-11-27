package proyecto.venta;

import java.util.ArrayList;

import proyecto.producto.Producto;
import proyecto.producto.ProductoManager;

public class Venta {
    private String cliente;
    private ArrayList<ItemVenta> items;
    private String fecha;
    private double total;

    public Venta(String cliente, String fecha) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.items = new ArrayList<>();
    }

    public String getCliente() {
        return cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<ItemVenta> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public void agregarItem(ItemVenta i) {
        items.add(i);
        total += i.getSubtotal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(cliente).append(";").append(fecha).append(";");

        for (ItemVenta i : items) {
            sb.append(i.getProducto().getNombre()).append(",")
                    .append(i.getCantidad()).append(",");
        }

        return sb.toString();
    }

    public static Venta fromString(String linea, ProductoManager pm) {
        try {
            String[] partes = linea.split(";");
            Venta v = new Venta(partes[0], partes[1]);

            String[] itemsStr = partes[2].split(",");
            for (int i = 0; i < itemsStr.length - 1; i += 2) {
                Producto p = pm.buscar(itemsStr[i]);
                int cant = Integer.parseInt(itemsStr[i + 1]);
                v.agregarItem(new ItemVenta(p, cant));
            }
            return v;
        } catch (Exception e) {
            return null;
        }
    }
}
