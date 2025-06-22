package Modelo;

import Interface.Identificable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public abstract class Usuario implements Identificable {
    private String dni;
    private String nombre;
    private String nacionalidad;
    private String domicilio;

    private String contraseña;
    private String mail;

    public Usuario(String dni, String nombre, String nacionalidad, String domicilio, String contraseña, String mail) {
        this.dni = dni;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.domicilio = domicilio;
        this.contraseña = contraseña;
        this.mail = mail;
    }
    public Usuario(){}

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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    //-------------- JAVA A JSON ------------//
    public JSONObject usuarioAJson(){
        JSONObject jsonUsuario = new JSONObject();

        try{

            jsonUsuario.put("nombre", getNombre());
            jsonUsuario.put("dni", getDni());
            jsonUsuario.put("domicilio", getDomicilio());
            jsonUsuario.put("nacionalidad", getNacionalidad());
            jsonUsuario.put("contraseña", getContraseña());
            jsonUsuario.put("mail", getMail());
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return jsonUsuario;
    }
    //--------------- JSON A JAVA (solo carga datos comunes, no se instancia un Usuario) ----------------//
    public void cargarDesdeJson(JSONObject json){
        try{
            this.setDni(json.getString("dni"));
            this.setNombre(json.getString("nombre"));
            this.setNacionalidad(json.getString("nacionalidad"));
            this.setDomicilio(json.getString("domicilio"));
            this.setContraseña(json.getString("contraseña"));
            this.setMail(json.getString("mail"));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }




    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return  Objects.equals(dni, usuario.dni) &&
                Objects.equals(nacionalidad, usuario.nacionalidad) &&
                Objects.equals(contraseña, usuario.contraseña) &&
                Objects.equals(mail, usuario.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, nacionalidad, contraseña, mail);
    }


    @Override
    public String obtenerIdentificador() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ").append(getNombre());
        sb.append(" - Nacionalidad: ").append(getNacionalidad());
        sb.append(" - DNI: ").append(getDni());
        sb.append(" - Domicilio: ").append(getDomicilio());
        sb.append(" - Mail: ").append(getMail());


        return sb.toString();
    }
}
