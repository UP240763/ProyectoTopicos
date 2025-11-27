package proyecto.producto;

import java.util.ArrayList;

public class ProductoManager {
    private ArrayList<Producto> productos;

    public ProductoManager() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public boolean agregar(Producto p) {
        if (buscar(p.getNombre()) != null)
            return false;
        productos.add(p);
        return true;
    }

    public boolean actualizar(String nombre, String desc, String prov, int cant, double precio) {
        Producto p = buscar(nombre);
        if (p == null)
            return false;
        p.setDescripcion(desc);
        p.setProveedor(prov);
        p.setCantidad(cant);
        p.setPrecio(precio);
        return true;
    }

    public boolean eliminar(String nombre) {
        Producto p = buscar(nombre);
        if (p == null)
            return false;
        productos.remove(p);
        return true;
    }

    public ArrayList<Producto> listarProductos() {
        return productos;
    }

    public Producto buscar(String nombre) {
        for (Producto p : productos)
            if (p.getNombre().equalsIgnoreCase(nombre))
                return p;
        return null;
    }

    public void modificarInventario(String nombreProducto, int cantidad) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombreProducto)) {
                int nuevoValor = p.getCantidad() + cantidad;

                if (nuevoValor < 0)
                    nuevoValor = 0;

                p.setCantidad(nuevoValor);
                return;
            }
        }
    }

}
