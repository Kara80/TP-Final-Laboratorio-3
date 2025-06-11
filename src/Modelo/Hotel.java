package Modelo;

import Contenedores.Gestor;
import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;

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
