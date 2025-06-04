package Modelo;
import Enum.EstadoDeReserva;
public class Reserva {
 private EstadoDeReserva estadoDeReserva;

    public Reserva(EstadoDeReserva estadoDeReserva) {
        this.estadoDeReserva = estadoDeReserva;
    }

    public EstadoDeReserva getEstadoDeReserva() {
        return estadoDeReserva;
    }

    public void setEstadoDeReserva(EstadoDeReserva estadoDeReserva) {
        this.estadoDeReserva = estadoDeReserva;
    }
}
