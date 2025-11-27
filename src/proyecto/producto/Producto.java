package proyecto.producto;

import java.io.Serializable;

public class Producto implements Serializable {
    private String nombre;
    private String descripcion;
    private String proveedor;
    private int cantidad;
    private double precio;

    public Producto(String nombre, String descripcion, String proveedor, int cantidad, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setDescripcion(String s) {
        descripcion = s;
    }

    public void setProveedor(String p) {
        proveedor = p;
    }

    public void setCantidad(int c) {
        cantidad = c;
    }

    public void setPrecio(double p) {
        precio = p;
    }

    @Override
    public String toString() {
        return nombre + "|" + descripcion + "|" + proveedor + "|" + cantidad + "|" + precio;
    }

    public static Producto fromString(String s) {
        String[] p = s.split("\\|");
        if (p.length != 5)
            return null;
        return new Producto(p[0], p[1], p[2], Integer.parseInt(p[3]), Double.parseDouble(p[4]));
    }
}
