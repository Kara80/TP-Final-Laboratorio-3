package Excepciones;

import Modelo.Cliente;

import java.time.LocalDate;

public class ReservaNoEncontradaException extends Exception {

    private Cliente cliente;
    private LocalDate fechaHoy;

    public ReservaNoEncontradaException(String mensaje ,Cliente cliente, LocalDate fechaHoy){
        super(mensaje);
        this.cliente = cliente;
        this.fechaHoy = fechaHoy;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " - Cliente: " + cliente.getNombre() + ", Dni: " + cliente.getDni() +
                " - fecha de hoy: " + fechaHoy;
    }
}
