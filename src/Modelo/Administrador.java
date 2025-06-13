package Modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Administrador extends Usuario{


    public Administrador(String dni, String nombre, String nacionalidad, String domicilio, String contrasenia, String mail) {
        super(dni, nombre, nacionalidad, domicilio, contrasenia, mail);
    }

    public Administrador() {

    }

    //------------ JAVA A JSON ------------//
    public JSONObject adminAJson(){
        JSONObject jsonAdmin = new JSONObject();

        try{

            jsonAdmin = usuarioAJson();
            jsonAdmin.put("tipo", "administrador");

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return jsonAdmin;
    }

    //--------------- JSON A Administrador ----------------//
    public static Administrador jsonAAdministrador(JSONObject json){
        Administrador admin = new Administrador();
        admin.cargarDesdeJson(json);
        return admin;
    }


}
