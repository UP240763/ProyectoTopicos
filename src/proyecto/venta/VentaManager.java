package proyecto.venta;

import java.io.*;
import java.util.ArrayList;

import proyecto.producto.Producto;
import proyecto.producto.ProductoManager;

public class VentaManager {

    private ArrayList<Venta> ventas = new ArrayList<>();
    private File archivo = new File("ventas.txt");
    private ProductoManager pm;

    public VentaManager(ProductoManager pm) {
        this.pm = pm;
        cargar();
    }

    public void registrarVenta(String cliente, ArrayList<ItemVenta> items) {
        Venta v = new Venta(cliente, java.time.LocalDate.now().toString());

        for (ItemVenta i : items) {
            v.agregarItem(i);

            // Actualizar inventario
            Producto p = i.getProducto();
            p.setCantidad(p.getCantidad() - i.getCantidad());
        }

        ventas.add(v);
        guardar();
    }

    public ArrayList<Venta> listarVentas() {
        return ventas;
    }

    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Venta v : ventas)
                pw.println(v.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        if (!archivo.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Venta v = Venta.fromString(linea, pm);
                if (v != null)
                    ventas.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
