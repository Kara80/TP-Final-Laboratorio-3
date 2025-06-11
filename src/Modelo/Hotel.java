package Modelo;

import Contenedores.Gestor;
import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;
import Enum.EstadoDeHabitacion;
import Excepciones.ReservaNoEncontradaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private Gestor<Administrador> administradores;
    private Gestor<Recepcionista> recepcionistas;
    private Gestor<Cliente> clientes;
    private Gestor<Habitacion> habitaciones;
    private Gestor<Reserva> reservas;

    public Hotel() {
        this.administradores = new Gestor<>();
        this.recepcionistas = new Gestor<>();
        this.clientes = new Gestor<>();
        this.habitaciones = new Gestor<>();
        this.reservas = new Gestor<>();
    }


    public Gestor<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /*
    Recorre la lista de habitaciones y devuelve una lista con
    todas las habitaciones disponibles entre los dias dados.
      */
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechafinal){
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();

        for (Habitacion h : habitaciones.obtenerTodos()){

            if (h.estaDisponible(fechaInicio, fechafinal)){

                habitacionesDisponibles.add(h);
            }
        }

        return habitacionesDisponibles;
    }

    /*
    Intenta agregar una reserva al hotel.
    Delega toda la validacion a la habitacion.
    Si no hay errores se registra en la lista de reservas.
     */
    public void agregarReserva(Reserva reserva)throws FechaReservaInvalidaException, HabitacionNoDisponibleException{

        //getHabitacion() devuelve una referencia a una habitacion que ya existe en el hotel
        Habitacion habitacionDeReserva = reserva.getHabitacion();

        //modifica directamente esa habitacion de la lista del hotel
        habitacionDeReserva.agregarReserva(reserva);

        //agrega la reserva a la lista de reservas
        reservas.agregar(reserva);

    }

    /*
    Recorre la lista de reservas y verifica si existe una reserva cuyo cliente coincida
    con el pasado por parametro y que la fecha de inicio coincida tambien.
    Tambien se verifica si la habitacion esta disponible
    La va a llamar Recepcionista
     */
    public void checkIn(Cliente cliente) throws HabitacionNoDisponibleException , ReservaNoEncontradaException {

        //un atributo con el dia de hoy
        LocalDate fechaHoy = LocalDate.now();

        for (Reserva r : reservas.obtenerTodos()){

            //si el cliente en el que estoy parado en la lista de reservas es igual al cliente pasado y
            //la fecha del 1er dia de la reserva coincide con la fecha de hoy
            if (r.getCliente().equals(cliente) && r.getFechaInicio().isEqual(fechaHoy)){

                Habitacion habitacion = r.getHabitacion();

                //si la habitacion esta disponible
                if (habitacion.getEstadoHabitacion() == EstadoDeHabitacion.disponible){

                    //el estado de la habitacion pasa a ocupada
                    habitacion.setEstadoHabitacion(EstadoDeHabitacion.ocupada);
                    return;
                }
                //si la habitacion no esta disponible (ocupada o en mantenimiento)
                else{
                    throw new HabitacionNoDisponibleException("La habitacion no esta disponible" , habitacion.getNumero());
                }
            }
        }
        //si ya recorri toda la lista y no encontro ni una reserva del cliente
        throw new ReservaNoEncontradaException("No se encontro una reserva de hoy para el cliente" , cliente, fechaHoy);
    }

    /*
    Recorre la lista de reservas y verifica si existe una reserva cuyo cliente coincidad
    con el pasado por parametro y que la fecha del ultimo dia sea hoy.
    Cambia el estado de ocupada a disponible
    La llama recepcionista
     */
    public void checkOut(Cliente cliente) throws HabitacionNoDisponibleException , ReservaNoEncontradaException{

        LocalDate fechaHoy = LocalDate.now();

        for (Reserva r : reservas.obtenerTodos()){

            if (r.getCliente().equals(cliente) && r.getFechaFin().isEqual(fechaHoy)){

                Habitacion habitacion = r.getHabitacion();

                if (habitacion.getEstadoHabitacion()== EstadoDeHabitacion.ocupada){
                    habitacion.setEstadoHabitacion(EstadoDeHabitacion.disponible);
                    return;
                }
                else{
                    throw new HabitacionNoDisponibleException("La habitacion no esta ocupada", habitacion.getNumero());
                }

            }
        }

        //si ya recorrio toda la lista y no encontro ni una reserva del cliente
        throw new ReservaNoEncontradaException("No se encontro una reserva que termine hoy del cliente", cliente, fechaHoy);

    }



    @Override
    public String toString() {
        return "Hotel{" +
                "administradores=" + administradores +
                ", recepcionistas=" + recepcionistas +
                ", clientes=" + clientes +
                ", habitaciones=" + habitaciones +
                ", reservas=" + reservas +
                '}';
    }
}
