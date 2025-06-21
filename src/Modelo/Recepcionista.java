package Modelo;

import Excepciones.HabitacionNoDisponibleException;
import Excepciones.ReservaNoEncontradaException;
import org.json.JSONException;
import org.json.JSONObject;

public class Recepcionista extends Usuario{

    //maneja una referncia a Hotel para que pueda operar sobre reservas, checksIn/Out
    private Hotel hotel;

    public Recepcionista(String dni, String nombre, String nacionalidad, String domicilio, Hotel hotel, String contrasenia, String mail) {
        super(dni, nombre, nacionalidad, domicilio, contrasenia, mail);
        this.hotel = hotel;
    }

    public Recepcionista() {

    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void hacerCheckIn(Cliente cliente){

        try{

            hotel.checkIn(cliente);
        } catch (HabitacionNoDisponibleException e) {
            e.printStackTrace();
        } catch (ReservaNoEncontradaException e) {
            e.printStackTrace();
        }
    }

    public void hacerCheckOut(Cliente cliente){

        try{
            hotel.checkOut(cliente);
        } catch (HabitacionNoDisponibleException e) {
            e.printStackTrace();
        } catch (ReservaNoEncontradaException e) {
            e.printStackTrace();
        }
    }

    //------------ JAVA A HOTEL ------------//
    /*
    Este metodo no guarda la referencia al Hotel,
    Porque la referencia al Hotel se asigna manualmente en el Menu despues de cargar el JSON del Hotel.
    Recorriendo todos los recepcionistas con hotel.getRecepcionistas() y aplicando un setHotel(hotel).
    Al iniciar el programa y cargar el Hotel, se vuelve a vincular cada Recepcionista con su Hotel.
     */
    public JSONObject recepcionistaAJson(){
        JSONObject jsonRecepcionista = new JSONObject();

        try{

            jsonRecepcionista = usuarioAJson();
            jsonRecepcionista.put("tipo", "recepcionista");
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return  jsonRecepcionista;
    }

    //--------------- JSON A Recepcionista ----------------//
    public static Recepcionista jsonARecepcionista(JSONObject json){
        Recepcionista recep = new Recepcionista();
        recep.cargarDesdeJson(json);
        // El hotel se setea aparte luego manualmente
        return recep;
    }

    @Override
    public String obtenerIdentificador() {
        StringBuilder sb = new StringBuilder();

        sb.append("----------------------------------------------------------------------------------\n");
        sb.append(super.obtenerIdentificador());
        sb.append("\n----------------------------------------------------------------------------------");

        return sb.toString();
    }
}
