package proyecto.proveedor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorManager {

    private final File archivo = new File("proveedores.txt");
    private List<Proveedor> proveedores = new ArrayList<>();

    public ProveedorManager() {
        cargar();
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public Proveedor buscar(String nombre) {
        for (Proveedor p : proveedores) {
            if (p.getNombre().equalsIgnoreCase(nombre))
                return p;
        }
        return null;
    }

    public boolean agregar(Proveedor p) {
        if (buscar(p.getNombre()) != null)
            return false;
        proveedores.add(p);
        guardar();
        return true;
    }

    public boolean actualizar(String nombre, String tel, String correo,
            String dir, String rfc, String formaPago, String tipoProd) {

        Proveedor p = buscar(nombre);
        if (p == null)
            return false;

        p.setTelefono(tel);
        p.setCorreo(correo);
        p.setDireccion(dir);
        p.setRfc(rfc);
        p.setFormaPago(formaPago);
        p.setTipoProducto(tipoProd);

        guardar();
        return true;
    }

    public boolean eliminar(String nombre) {
        Proveedor p = buscar(nombre);
        if (p == null)
            return false;

        proveedores.remove(p);
        guardar();
        return true;
    }

    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Proveedor p : proveedores) {
                pw.println(p.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        proveedores.clear();
        if (!archivo.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Proveedor p = Proveedor.fromString(linea);
                if (p != null)
                    proveedores.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
