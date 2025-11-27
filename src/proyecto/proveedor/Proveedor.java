package proyecto.proveedor;

import java.io.Serializable;

public class Proveedor implements Serializable {
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private String rfc;
    private String formaPago;
    private String tipoProducto;
    private String fechaRegistro;

    public Proveedor(String nombre, String telefono, String correo, String direccion,
            String rfc, String formaPago, String tipoProducto, String fechaRegistro) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.rfc = rfc;
        this.formaPago = formaPago;
        this.tipoProducto = tipoProducto;
        this.fechaRegistro = fechaRegistro;
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

    public String getRfc() {
        return rfc;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setTelefono(String t) {
        this.telefono = t;
    }

    public void setCorreo(String c) {
        this.correo = c;
    }

    public void setDireccion(String d) {
        this.direccion = d;
    }

    public void setRfc(String r) {
        this.rfc = r;
    }

    public void setFormaPago(String f) {
        this.formaPago = f;
    }

    public void setTipoProducto(String t) {
        this.tipoProducto = t;
    }

    @Override
    public String toString() {
        return nombre + "|" + telefono + "|" + correo + "|" + direccion + "|" +
                rfc + "|" + formaPago + "|" + tipoProducto + "|" + fechaRegistro;
    }

    public static Proveedor fromString(String s) {
        String[] p = s.split("\\|");
        if (p.length == 8)
            return new Proveedor(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7]);
        return null;
    }
}
