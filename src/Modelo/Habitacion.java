package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Enum.EstadoDeHabitacion;
import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;

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
            throw new HabitacionNoDisponibleException("La habitacion no se encuentra disponible.");
        }

        //si la habitacion esta reservada entre esas fechas se arroja la excepcion y se avisa
        if (!estaDisponible(nuevaReserva.getFechaInicio(), nuevaReserva.getFechaFin())){
            throw new HabitacionNoDisponibleException("La habitacion se encuentra reservada entre las fechas " +
                        nuevaReserva.getFechaInicio() + " y " + nuevaReserva.getFechaFin() + " .");
        }

        //si no se entro a ningun if se puede agregar una nueva reserva a la Habitacion
        reservas.add(nuevaReserva);

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
}
