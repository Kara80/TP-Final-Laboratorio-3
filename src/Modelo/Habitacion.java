package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Enum.EstadoDeHabitacion;
import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Habitacion {

    private List<Reserva> reservas;
    private int numero;
    private int capacidad;
    private EstadoDeHabitacion estadoHabitacion; //estado actual de la habitacion

    public Habitacion(int numero, int capacidad) {
        this.reservas = new ArrayList<>();
        this.numero = numero;
        this.capacidad = capacidad;
        this.estadoHabitacion = EstadoDeHabitacion.disponible;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public EstadoDeHabitacion getEstadoHabitacion() {
        return estadoHabitacion;
    }

    public void setEstadoHabitacion(EstadoDeHabitacion estadoHabitacion) {
        this.estadoHabitacion = estadoHabitacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }


    /*
    Verifica si entre las fechas pasadas por parametro hay alguna reserva ya hecha para esta habitacion.
     */
    public boolean estaDisponible(LocalDate fechaInicio, LocalDate fechaFinal){

        for (Reserva r : reservas){
            if (r.chocaConFechas(fechaInicio, fechaFinal)){
                //hay una reserva ya hecha entre estas fechas
                return false;
            }
        }

        //ninguna de las fechas de las reservas se superpone con las fechas de la nueva reserva
        return true;
    }


    /*
    Agrega una reserva a la lista reservas
     */
    public void agregarReserva(Reserva nuevaReserva) throws FechaReservaInvalidaException, HabitacionNoDisponibleException {

        //primero verifica que la fecha inicio sea anterior a la fecha final
        //segundo verifica que sean iguales
        //si algo de esto pasa se arroja una excepcion pidiendo fechas coherentes
        if (nuevaReserva.getFechaInicio().isAfter(nuevaReserva.getFechaFin()) ||
            nuevaReserva.getFechaInicio().isEqual(nuevaReserva.getFechaFin())){
                throw new FechaReservaInvalidaException("La fecha inicio debe ser anterior a la fecha final.");
        }

        //si la habitacion no este disponible (ocupada o en_mantenimiento) no se puede reservar
        if (this.estadoHabitacion != EstadoDeHabitacion.disponible){
            throw new HabitacionNoDisponibleException("La habitacion no se encuentra disponible." , nuevaReserva.getHabitacion().getNumero());
        }

        //si la habitacion esta reservada entre esas fechas se arroja la excepcion y se avisa
        if (!estaDisponible(nuevaReserva.getFechaInicio(), nuevaReserva.getFechaFin())){
            throw new HabitacionNoDisponibleException("La habitacion se encuentra reservada entre las fechas " +
                        nuevaReserva.getFechaInicio() + " y " + nuevaReserva.getFechaFin() + " ." , nuevaReserva.getHabitacion().getNumero());
        }

        //si no se entro a ningun if se puede agregar una nueva reserva a la Habitacion
        reservas.add(nuevaReserva);

    }

    //----------------------- JAVA A JSON -----------------------//
    //sin lista de reservas para evitar bucle en Reserva
    public JSONObject habitacionAJson(){
        JSONObject jsonHabitacion = new JSONObject();

        try{

            jsonHabitacion.put("numero", getNumero());
            jsonHabitacion.put("capacidad", getCapacidad());
            jsonHabitacion.put("estadoHbitacion", getEstadoHabitacion());
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return jsonHabitacion;
    }

    /*
    este es el metodo a invocar en Hotel
     */
    public JSONObject habitacionAJsonConReservas(){
        JSONObject jsonHabitacion = new JSONObject();

        try{

            jsonHabitacion.put("numero", getNumero());
            jsonHabitacion.put("capacidad", getCapacidad());
            jsonHabitacion.put("estadoHbitacion", getEstadoHabitacion());

            JSONArray jsonReservas = new JSONArray();
            for (Reserva r : reservas){
                JSONObject jsonReserva = r.reservaAJsonSinHabitacion();
                jsonReservas.put(jsonReserva);
            }
            jsonHabitacion.put("reservas", jsonReservas);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return jsonHabitacion;
    }

    // ------------------------- JSON A Habitacion -------------------------//
    //Esta se va a invocar en Hotel :D
    public static Habitacion jsonAHabitacion(JSONObject json){
        Habitacion habitacion = new Habitacion(0, 0);  // valores provisorios, los seteamos enseguida

        try {
            habitacion.setNumero(json.getInt("numero"));
            habitacion.setCapacidad(json.getInt("capacidad"));

            // EstadoDeHabitacion desde String
            EstadoDeHabitacion estado = EstadoDeHabitacion.valueOf(json.getString("estadoHbitacion"));
            habitacion.setEstadoHabitacion(estado);

            JSONArray jsonReservas = json.getJSONArray("reservas");
            ArrayList<Reserva>reservasAux = new ArrayList<>();
            for (int i = 0 ; i< jsonReservas.length(); i++){
                Reserva r = Reserva.jsonAReservaSinHabitacion(jsonReservas.getJSONObject(i));
                r.setHabitacion(habitacion);
                reservasAux.add(r);
            }
            habitacion.setReservas(reservasAux);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return habitacion;
    }
    //Esta se va a invocar en Reserva, no carga Reservas
    public static Habitacion jsonAHabitacionSinReserva(JSONObject json){
        Habitacion habitacion = new Habitacion(0, 0);  // valores provisorios, los seteamos enseguida

        try {
            habitacion.setNumero(json.getInt("numero"));
            habitacion.setCapacidad(json.getInt("capacidad"));

            // EstadoDeHabitacion desde String
            EstadoDeHabitacion estado = EstadoDeHabitacion.valueOf(json.getString("estadoHbitacion"));
            habitacion.setEstadoHabitacion(estado);

            // No cargamos reservas para evitar bucle

        } catch (JSONException e){
            e.printStackTrace();
        }

        return habitacion;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    //Muestra habitacion sin entrar en bucle con reserva
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------------------\n");

        sb.append("Habitacion N°: ").append(getNumero()).
                append(" - Capacidad: ").append(getCapacidad()).
                append("- Estado actual: ").append(getEstadoHabitacion());

        if (reservas != null && !reservas.isEmpty()){
            sb.append("\nReservas: ");

            for (Reserva r : reservas){
                sb.append("- Ocupada por DNI:" ).append(r.getCliente().getDni())
                        .append(" desde ").append(r.getFechaInicio()).append(" hasta ").append(r.getFechaFin());
            }
        }
        else{
            sb.append("\nSin reservas.");
        }
        sb.append("\n----------------------------------------------------");


        return sb.toString();
    }
}
