package proyecto.usuario;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String usuario;
    private String correo;
    private String password;
    private String rol; // "Administrador" | "Empleado"

    public Usuario(String usuario, String correo, String password, String rol) {
        this.usuario = usuario;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        // guardamos 4 campos (usuario|correo|password|rol)
        return usuario + "|" + correo + "|" + password + "|" + rol;
    }

    public static Usuario fromString(String linea) {
        String[] p = linea.split("\\|");
        if (p.length == 4) {
            return new Usuario(p[0], p[1], p[2], p[3]);
        }
        return null;
    }
}
