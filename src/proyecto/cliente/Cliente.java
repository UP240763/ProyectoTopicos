package proyecto.cliente;

public class Cliente {
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private String fechaRegistro;
    private String rfc;

    public Cliente(String nombre, String telefono, String correo, String direccion,
            String fechaRegistro, String rfc) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public String getRfc() {
        return rfc;
    }

    public void setTelefono(String t) {
        this.telefono = t;
    }
}
