package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Enum.EstadoDeHabitacion;

public class Habitacion {

    private List<Reserva> reservas;
    private int numero;
    private int capacidad;
    private EstadoDeHabitacion estadoHabitacion;

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
