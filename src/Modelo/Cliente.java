package Modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{

    private List<Reserva> reservas;

    public Cliente(String dni, String nombre, String nacionalidad, String domicilio, String contrasenia, String mail) {
        super(dni, nombre, nacionalidad, domicilio, contrasenia, mail);
        this.reservas = new ArrayList<>();
    }

    public Cliente() {

    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    private void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void agregarReserva(Reserva reserva){

        if (reserva != null){
            reservas.add(reserva);
        }
    }

    public void eliminarReserva(Reserva reserva){

        if (reserva != null){
            reservas.remove(reserva);
        }
    }

    public List<String> mostrarReservas(){
        List<String> descripciones = new ArrayList<>();

        if (this.getReservas().isEmpty()) {
            descripciones.add("No tienes reservas registradas.");
            return descripciones;
        }

        for (Reserva reserva : this.getReservas()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Estado de reserva: ").append(reserva.getEstadoDeReserva()).append("\n");
            sb.append("Fecha de inicio: ").append(reserva.getFechaInicio()).append("\n");
            sb.append("Fecha de fin: ").append(reserva.getFechaFin()).append("\n");

            Habitacion h = reserva.getHabitacion();
            sb.append("CNumero de habitacion: ").append(h.getNumero());
            sb.append("Capacidad: ").append(h.getCapacidad()).append("\n");
            sb.append("Estado de habitación: ").append(h.getEstadoHabitacion()).append("\n");

            descripciones.add(sb.toString());
        }

        return descripciones;

    }


    //-------------------------- JAVA A JSON --------------------------//
    /*
    Pasa cliente a json pero no incluye la de pasar reservas para evitar un bucle porque
    este metodo se llamara en Reserva
     */
    public JSONObject clienteAJson(){
        JSONObject jsonCliente = new JSONObject();

        try{

            jsonCliente = usuarioAJson();
            jsonCliente.put("tipo", "cliente");
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return jsonCliente;
    }

    /*
    este es el metodo a invocar en Hotel
     */
    public JSONObject clienteAJsonConReservas(){
        JSONObject jsonCliente = new JSONObject();

        try{

            jsonCliente = usuarioAJson();
            jsonCliente.put("tipo", "cliente");

            JSONArray jsonReservas = new JSONArray();
            for (Reserva r : reservas){
                JSONObject jsonReserva = r.reservaAJsonSinCliente();
                jsonReservas.put(jsonReserva);
            }
            jsonCliente.put("reservas", jsonReservas);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return jsonCliente;
    }

    //--------------- JSON A Cliente ----------------//

    public static Cliente jsonACliente(JSONObject json){
        Cliente cliente = new Cliente();
        cliente.cargarDesdeJson(json);  // ← carga comunes

        try {
            if (json.has("reservas")) {
                JSONArray jsonReservas = json.getJSONArray("reservas");
                for (int i = 0; i < jsonReservas.length(); i++){
                    Reserva r = Reserva.jsonAReserva(jsonReservas.getJSONObject(i));
                    cliente.agregarReserva(r);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return cliente;
    }

    @Override
    public String toString() {
        return "Cliente: " + getNombre() + " - DNI: " + getDni() + " - mail: "+getMail();
    }
}
