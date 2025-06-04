package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Administrador> administradores;
    private List<Recepcionista> recepcionistas;
    private List<Cliente> clientes;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    public Hotel() {
        this.administradores = new ArrayList<>();
        this.recepcionistas = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
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
