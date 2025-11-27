package proyecto.usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    private final File archivo = new File("usuarios.txt");
    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;

    public UsuarioManager() {
        cargar();

        // crea admin por defecto si no hay usuarios
        if (usuarios.isEmpty()) {
            Usuario admin = new Usuario("admin", "admin@dagnet.com", "admin123", "Administrador");
            usuarios.add(admin);
            guardar();
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public Usuario buscar(String usuario) {
        for (Usuario u : usuarios)
            if (u.getUsuario().equalsIgnoreCase(usuario))
                return u;
        return null;
    }

    public boolean agregar(Usuario u) {
        if (buscar(u.getUsuario()) != null)
            return false;
        usuarios.add(u);
        guardar();
        return true;
    }

    public boolean eliminar(String usuario) {
        Usuario u = buscar(usuario);
        if (u == null)
            return false;
        usuarios.remove(u);
        guardar();
        return true;
    }

    public boolean actualizar(String usuario, String correo, String pass, String rol) {
        Usuario u = buscar(usuario);
        if (u == null)
            return false;
        u.setCorreo(correo);
        u.setPassword(pass);
        u.setRol(rol);
        guardar();
        return true;
    }

    public boolean login(String user, String pass) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(user) && u.getContraseña().equals(pass)) {
                usuarioActual = u;
                return true;
            }
        }
        return false;
    }

    public List<Usuario> buscarTodos() {
        return usuarios;
    }

    private void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : usuarios)
                pw.println(u.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        usuarios.clear();
        if (!archivo.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario u = Usuario.fromString(linea);
                if (u != null)
                    usuarios.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
