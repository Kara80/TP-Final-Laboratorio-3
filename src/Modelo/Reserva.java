package Modelo;
import Enum.EstadoDeReserva;

import java.time.LocalDate;

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


}
