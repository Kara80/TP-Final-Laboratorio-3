package Menu;

import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;
import Modelo.*;

import java.time.LocalDate;
import java.util.List;

public class Menu {


    public static void main() {

            //Ejemplos para comprobar la funcionalidad de lso metodos


/*
        Habitacion habitacion = new Habitacion(101, 2); // Supongamos que su constructor recibe un número
        Cliente cliente = new Cliente("12345678Juan Pérez", "Juan Pérez","argentina", "mdp");

        LocalDate fechaInicio1 = LocalDate.of(2025, 6, 20);
        LocalDate fechaFin1 = LocalDate.of(2025, 6, 25);
        Reserva reserva1 = new Reserva(habitacion, cliente, fechaInicio1, fechaFin1);

        try {
            habitacion.agregarReserva(reserva1);
            System.out.println("Reserva 1 agregada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar reserva 1: " + e.getMessage());
        }

        // Intentamos agregar otra reserva que se superpone
        LocalDate fechaInicio2 = LocalDate.of(2025, 6, 22);
        LocalDate fechaFin2 = LocalDate.of(2025, 6, 27);
        Reserva reserva2 = new Reserva(habitacion, cliente, fechaInicio2, fechaFin2);

        try {
            habitacion.agregarReserva(reserva2);
            System.out.println("Reserva 2 agregada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar reserva 2: " + e.getMessage());
        }

        // Intentamos agregar otra que no se superpone
        LocalDate fechaInicio3 = LocalDate.of(2025, 6, 26);
        LocalDate fechaFin3 = LocalDate.of(2025, 6, 28);
        Reserva reserva3 = new Reserva(habitacion, cliente, fechaInicio3, fechaFin3);

        try {
            habitacion.agregarReserva(reserva3);
            System.out.println("Reserva 3 agregada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar reserva 3: " + e.getMessage());
        }
*/

/*
        Hotel hotel = new Hotel();

        hotel.getHabitaciones().agregar(new Habitacion(12, 2));
        hotel.getHabitaciones().agregar(new Habitacion(24, 1));

        LocalDate inicio = LocalDate.of(2025, 6, 10);
        LocalDate fin = LocalDate.of(2025, 6, 15);

        List<Habitacion> habitacionesDisponibles = hotel.obtenerHabitacionesDisponibles(inicio, fin);
        System.out.println("Todas las habitaciones disponibles entre el " + inicio + " y el " + fin + " : ");
        for (Habitacion h : habitacionesDisponibles){
            System.out.println("Habitacion numero: "+ h.getNumero() +
                    ", Capacidad para " + h.getCapacidad() + " personas" );
        }

        Cliente cliente = new Cliente("1234", "luca", "argetnina", "mdp");
        Habitacion h = new Habitacion(11, 3);
        LocalDate fechaInicio = LocalDate.of(2025, 6, 10);
        LocalDate fechaFin = LocalDate.of(2025, 6, 7);
        Reserva reserva = new Reserva(h, cliente, fechaInicio, fechaFin);

        try {
            hotel.agregarReserva(reserva);
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al hacer la reserva: " + e.getMessage());
        }
*/

        Hotel hotel = new Hotel();

        // Crear un recepcionista con referencia al hotel
        Recepcionista recepcionista = new Recepcionista("123", "Juan", "Argentina", "Calle Falsa 123", hotel, "juanjuan", "juan@gmail.com");


        // Crear un cliente sin reservas
        Cliente cliente = new Cliente("456", "Pedro", "Argentina", "Av. Siempreviva 742","pep", "pep@gmail.com");

        try {
            // Intentar hacer check-in
            recepcionista.hacerCheckIn(cliente);
        } catch (RuntimeException e) {
            // Captura el error lanzado y lo muestra
            System.out.println("⚠️ Error: " + e.getMessage());
        }

        //Creando la reserva
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.of(2025, 7, 15);
        Habitacion h = new Habitacion(11, 3);
        Reserva reserva = new Reserva(h, cliente1, inicio, fin);

        try {
            hotel.agregarReserva(reserva);
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al hacer la reserva: " + e.getMessage());
        }
        /// Acá si tendría que funcionar el checkin
        try {
            // Intentar hacer check-in
            recepcionista.hacerCheckIn(cliente1);
            System.out.println("Check-in realizado con exito");
        } catch (RuntimeException e) {
            // Captura el error lanzado y lo muestra
            System.out.println("⚠️ Error: " + e.getMessage());
        }

        /// Acá veremos un Error lanzado, puesto que no se encuentra una reserva que termine hoy.
        try {
            // Intentar hacer check-out
            recepcionista.hacerCheckOut(cliente1);
            System.out.println("Check-out realizado con exito");
        } catch (RuntimeException e) {
            // Captura el error lanzado y lo muestra
            System.out.println("⚠️ Error: " + e.getMessage());
        }


    }

}
