package Modelo;
import Enum.EstadoDeReserva;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public class Reserva {

    private EstadoDeReserva estadoDeReserva;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;


    public Reserva(Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.estadoDeReserva = EstadoDeReserva.reservada;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Reserva() {

    }

    public EstadoDeReserva getEstadoDeReserva() {
        return estadoDeReserva;
    }

    public void setEstadoDeReserva(EstadoDeReserva estadoDeReserva) {
        this.estadoDeReserva = estadoDeReserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /*
    Verifica si en el rango de fechas pasadas por parametro se superpone con esta reserva.
    Retorna true si las fechas de la reserva actual se interponen con el periodo de tiempo pasado por parametro.
    Retorna false si no.

    Este metodo se usara en la clase Habitacion (que tiene una lista de reservas).
    Este metodo se va a implementar recorriendo la lista de reservas para verificar si
    las fechas de la nueva reserva se superponen con las fechas de alguna reserva.
     */
    public boolean chocaConFechas(LocalDate fechaInicio, LocalDate fechaFin){

        //si esta reserva no esta reservada o ya tomada no se interpone con las fechas
        if (this.estadoDeReserva != EstadoDeReserva.reservada){
            return false;
        }

        //si el final de las fechas pasadas por parametro es antes del dia que
        //empieza la reserva actual (osea no se superponen) retorna false
        if (fechaFin.isBefore(this.fechaInicio)){
            return false;
        }

        //si el 1er dia de las fechas pasadas por parametro es despues del ultimo
        //dia de la reserva actual (no se superponen) retorna false
        if (fechaInicio.isAfter(this.fechaFin)){
            return false;
        }

        //si no entro a ningun if es porque las fechas de la reserva actual
        //y las fechas del parametro se superponen
        return true;
    }

    // ------------------------- JAVA A JSON -------------------------//
    //este es el metodo a invocar en Hotel
    public JSONObject reservaAJson(){
        JSONObject jsonReserva = new JSONObject();

        try{

            JSONObject jsonCliente = getCliente().clienteAJson();
            jsonReserva.put("cliente", jsonCliente);

            jsonReserva.put("estadoReserva", getEstadoDeReserva());
            jsonReserva.put("fechaInicio", getFechaInicio().toString());
            jsonReserva.put("fechaFin", getFechaFin());

            JSONObject jsonHabitacion = new JSONObject();
            jsonHabitacion = getHabitacion().habitacionAJson();
            jsonReserva.put("habitacion", jsonHabitacion);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return jsonReserva;
    }

    //no pasa cliente para evitar bucle cuando se la llame en Cliente
    public JSONObject reservaAJsonSinCliente(){
        JSONObject jsonReserva = new JSONObject();

        try{
            jsonReserva.put("estadoReserva", getEstadoDeReserva());
            jsonReserva.put("fechaInicio", getFechaInicio().toString());
            jsonReserva.put("fechaFin", getFechaFin());

            JSONObject jsonHabitacion = getHabitacion().habitacionAJson();
            jsonReserva.put("habitacion", jsonHabitacion);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return jsonReserva;
    }

    //no pasa habitacion para evitar bucle cuando se la llame en Habitacion
    public JSONObject reservaAJsonSinHabitacion(){
        JSONObject jsonReserva = new JSONObject();

        try{
            JSONObject jsonCliente = getCliente().clienteAJson();
            jsonReserva.put("cliente", jsonCliente);

            jsonReserva.put("estadoReserva", getEstadoDeReserva());
            jsonReserva.put("fechaInicio", getFechaInicio().toString());
            jsonReserva.put("fechaFin", getFechaFin());
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return jsonReserva;
    }
    // ------------------------- Json A Reserva -------------------------//
    public static Reserva jsonAReserva(JSONObject json){
        Reserva reserva = new Reserva();
        try {
            // Estado de reserva (como string)
            EstadoDeReserva estado = EstadoDeReserva.valueOf(json.getString("estadoReserva"));
            reserva.setEstadoDeReserva(estado);

            // Fechas
            reserva.setFechaInicio(LocalDate.parse(json.getString("fechaInicio")));
            reserva.setFechaFin(LocalDate.parse(json.getString("fechaFin")));

            // Habitacion
            JSONObject jsonHabitacion = json.getJSONObject("habitacion");
            Habitacion habitacion = Habitacion.jsonAHabitacionSinReserva(jsonHabitacion);
            reserva.setHabitacion(habitacion);

            //Cliente
            JSONObject jsonCliente = json.getJSONObject("cliente");
            Cliente clientito = Cliente.jsonAClienteSinReserva(jsonCliente);
            reserva.setCliente(clientito);

        } catch (JSONException e){
            e.printStackTrace();
        }
        return reserva;
    }

    public static Reserva jsonAReservaSinCliente(JSONObject json){
        Reserva reserva = new Reserva();

        try{
            // Estado de reserva (como string)
            EstadoDeReserva estado = EstadoDeReserva.valueOf(json.getString("estadoReserva"));
            reserva.setEstadoDeReserva(estado);

            // Fechas
            reserva.setFechaInicio(LocalDate.parse(json.getString("fechaInicio")));
            reserva.setFechaFin(LocalDate.parse(json.getString("fechaFin")));

            // Habitacion
            JSONObject jsonHabitacion = json.getJSONObject("habitacion");
            Habitacion habitacion = Habitacion.jsonAHabitacionSinReserva(jsonHabitacion);
            reserva.setHabitacion(habitacion);


        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return reserva;
    }


    public static Reserva jsonAReservaSinHabitacion(JSONObject json){
        Reserva reserva = new Reserva();

        try{
            // Estado de reserva (como string)
            EstadoDeReserva estado = EstadoDeReserva.valueOf(json.getString("estadoReserva"));
            reserva.setEstadoDeReserva(estado);

            // Fechas
            reserva.setFechaInicio(LocalDate.parse(json.getString("fechaInicio")));
            reserva.setFechaFin(LocalDate.parse(json.getString("fechaFin")));


            // Cliente
            JSONObject jsonCliente = json.getJSONObject("cliente");
            Cliente clientito = Cliente.jsonAClienteSinReserva(jsonCliente);
            reserva.setCliente(clientito);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return reserva;
    }

    /*
    Toma un JSONObject que representa una reserva y una lista de clientes y una lista de habitaciones ya cargadas.
    En vez de crear instancias nuevas, busca referencias reales a los objetos ya existentes (ya sea por dni o por
    num de habitacion) y las usa para asignarlas a la reserva, evitando duplicaciones.
     */
    public static Reserva jsonAReservaConReferencias(JSONObject json, List<Cliente> clientes, List<Habitacion> habitaciones) {
        Reserva reserva = new Reserva();

        try {
            // estado de reserva
            EstadoDeReserva estado = EstadoDeReserva.valueOf(json.getString("estadoReserva"));
            reserva.setEstadoDeReserva(estado);

            // fechas
            reserva.setFechaInicio(LocalDate.parse(json.getString("fechaInicio")));
            reserva.setFechaFin(LocalDate.parse(json.getString("fechaFin")));

            // se obtiene el dni del cliente desde el JSON anidado
            String dni = json.getJSONObject("cliente").getString("dni");

            //se busca un cliente en la lista de clientes que tenga ese dni
            Cliente cliente = clientes.stream()
                    .filter(c -> c.getDni().equals(dni))//comparan el dni
                    .findFirst()
                    .orElse(null); //si no lo encuentra retorna null

            // se obtiene el numero de la habitacion desde el JSON anidado
            int numero = json.getJSONObject("habitacion").getInt("numero");

            // se busca en la lista de habitaciones una que tenga ese numero
            Habitacion habitacion = habitaciones.stream()
                    .filter(h -> h.getNumero() == numero) //compara el numero
                    .findFirst()
                    .orElse(null); //si no lo encuentra retorna null

            // asigno las referencias
            reserva.cliente = cliente;
            reserva.habitacion = habitacion;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // retorna le reserva cargada correctamente armada con referencias existentes
        return reserva;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Estado de Reserva: ").append(getEstadoDeReserva()).
                append(" - Fechas: desde ").append(getFechaInicio()).
                append(" a ").append(getFechaFin());
        sb.append(" - DNI del cliente: ").append(getCliente().getDni());
        sb.append(" - Habitacion N°: ").append(getHabitacion().getNumero());


        return sb.toString();
    }

}
