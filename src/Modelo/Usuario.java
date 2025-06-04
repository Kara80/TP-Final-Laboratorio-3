package Modelo;

public abstract class Usuario {
    private String dni;
    private String nombre;
    private String nacionalidad;
    private String domicilio;

    public Usuario(String dni, String nombre, String nacionalidad, String domicilio) {
        this.dni = dni;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.domicilio = domicilio;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}
