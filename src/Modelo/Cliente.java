package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{

    private List<Reserva> reservas;

    public Cliente(String dni, String nombre, String nacionalidad, String domicilio) {
        super(dni, nombre, nacionalidad, domicilio);
        this.reservas = new ArrayList<>();
    }
}
