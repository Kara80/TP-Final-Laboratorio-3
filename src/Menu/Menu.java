package Menu;

import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;
import Modelo.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public class Menu {


    public static void main() {

/*
        Hotel hotel = new Hotel();

        Habitacion habitacion1 = new Habitacion(1, 2);
        Habitacion habitacion2 = new Habitacion(2, 2);
        Habitacion habitacion3 = new Habitacion(3, 4);
        Habitacion habitacion4 = new Habitacion(4, 4);

        Cliente cliente1 = new Cliente("12345678","Juan Pérez", "argentina","racedo","mdp","juan@gmai.");
        Cliente cliente2 = new Cliente("12312342","kari Pérez", "argentina","gueme","mdp","kari@gmai.");
        Cliente cliente3 = new Cliente("45645632","luca Pérez", "argentina","39","mdp","luca@gmai.");

        LocalDate fechaInicio1 = LocalDate.of(2025, 6, 20);
        LocalDate fechaFin1 = LocalDate.of(2025, 6, 25);
        Reserva reserva1 = new Reserva(habitacion1, cliente1, fechaInicio1, fechaFin1);
        LocalDate fechaInicio2 = LocalDate.of(2025, 7, 25);
        LocalDate fechaFin2 = LocalDate.of(2025, 9, 30);
        Reserva reserva2 = new Reserva(habitacion2, cliente2, fechaInicio2, fechaFin2);
        LocalDate fechaInicio3 = LocalDate.of(2025, 6, 21);
        LocalDate fechaFin3 = LocalDate.of(2025, 6, 28);
        Reserva reserva3 = new Reserva(habitacion3, cliente3, fechaInicio3, fechaFin3);

        try {
            hotel.agregarReserva(reserva1);
            hotel.agregarReserva(reserva2);
            hotel.agregarReserva(reserva3);

        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al agregar reserva 1: " + e.getMessage());
        }

        Administrador administrador1 =new Administrador("7657435","Nica", "argentina","Utn","UTN10","Nica@gmail");
        Recepcionista recepcionista1 =new Recepcionista("63424124","Lucre","Brazil","utn",hotel,"contrasenia","lucre@gmail");

        hotel.getClientes().agregar(cliente1);
        hotel.getClientes().agregar(cliente2);
        hotel.getClientes().agregar(cliente3);

        hotel.getRecepcionistas().agregar(recepcionista1);
        hotel.getAdministradores().agregar(administrador1);

        hotel.getHabitaciones().agregar(habitacion1);
        hotel.getHabitaciones().agregar(habitacion2);
        hotel.getHabitaciones().agregar(habitacion3);
        hotel.getHabitaciones().agregar(habitacion4);

        hotel.getHabitaciones().mostrar();
        hotel.getRecepcionistas().mostrar();
        hotel.getClientes().mostrar();
        hotel.getAdministradores().mostrar();
        hotel.getReservas().mostrar();

        hotel.grabarClientes();
        hotel.grabarAdministradores();
        hotel.grabarHabitaciones();
        hotel.grabarReservas();
        hotel.grabarRecepcionistas();*/


        Hotel hotel = new Hotel();
        hotel.leerAdministradores();
        hotel.leerClientes();
        hotel.leerReservas();
        hotel.leerHabitaciones();
        hotel.leerRecepcionistas();
        hotel.getHabitaciones().mostrar();
        hotel.getReservas().mostrar();
        hotel.getClientes().mostrar();
        hotel.getAdministradores().mostrar();
        hotel.getRecepcionistas().mostrar();


        System.out.println(hotel.getHabitaciones().toString());

        LocalDate fechaInicio1 = LocalDate.of(2025, 6, 20);
        LocalDate fechaFin1 = LocalDate.of(2025, 6, 25);
        List<Habitacion> habitacionesDisponibles = hotel.obtenerHabitacionesDisponibles(fechaInicio1, fechaFin1);
        System.out.println("Todas las habitaciones disponibles entre el " + fechaInicio1 + " y el " + fechaFin1 + " : ");
        for (Habitacion h : habitacionesDisponibles){
            System.out.println("Habitacion numero: "+ h.getNumero() +
                    ", Capacidad para " + h.getCapacidad() + " personas" );
        }
/*
        LocalDate fechaInicio1 = LocalDate.of(2025, 6, 20);
        LocalDate fechaFin1 = LocalDate.of(2025, 6, 25);
        List<Habitacion> habitacionesDisponibles = hotel.obtenerHabitacionesDisponibles(fechaInicio1, fechaFin1);
        System.out.println(habitacionesDisponibles.toString());*/
        //Ejemplos para comprobar la funcionalidad de los metodos


        /// Acá creamos 3 habitaciones, la segunda está repetida, así podemos ver como se maneja la excepcion

/*
        Habitacion habitacion = new Habitacion(101, 2); // Supongamos que su constructor recibe un número
        Cliente cliente = new Cliente("12345678","Juan Pérez", "argentina","racedo","mdp","juan@gmai.");

        LocalDate fechaInicio1 = LocalDate.of(2025, 6, 20);
        LocalDate fechaFin1 = LocalDate.of(2025, 6, 25);
        Reserva reserva1 = new Reserva(habitacion, cliente, fechaInicio1, fechaFin1);

        try {
            habitacion.agregarReserva(reserva1);
            System.out.println("Reserva 1 agregada correctamente.");
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al agregar reserva 1: " + e.getMessage());
        }

        // Intentamos agregar otra reserva que se superpone
        LocalDate fechaInicio2 = LocalDate.of(2025, 6, 22);
        LocalDate fechaFin2 = LocalDate.of(2025, 6, 27);
        Reserva reserva2 = new Reserva(habitacion, cliente, fechaInicio2, fechaFin2);

        try {
            habitacion.agregarReserva(reserva2);
            System.out.println("Reserva 2 agregada correctamente.");
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al agregar reserva 2: " + e.getMessage());
        }

        // Intentamos agregar otra que no se superpone
        LocalDate fechaInicio3 = LocalDate.of(2025, 6, 26);
        LocalDate fechaFin3 = LocalDate.of(2025, 6, 28);
        Reserva reserva3 = new Reserva(habitacion, cliente, fechaInicio3, fechaFin3);

        try {
            habitacion.agregarReserva(reserva3);
            System.out.println("Reserva 3 agregada correctamente.");
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al agregar reserva 3: " + e.getMessage());
        }

*/


/*
    /// Pruebas de carga de datos en hotel,
        Hotel hotel = new Hotel();

        hotel.getHabitaciones().agregar(new Habitacion(12, 2));
        hotel.getHabitaciones().agregar(new Habitacion(24, 1));

        LocalDate inicio = LocalDate.of(2025, 6, 10);
        LocalDate fin = LocalDate.of(2025, 6, 15);
    ///Listado de habitaciones disponibles segun una fecha
        List<Habitacion> habitacionesDisponibles = hotel.obtenerHabitacionesDisponibles(inicio, fin);
        System.out.println("Todas las habitaciones disponibles entre el " + inicio + " y el " + fin + " : ");
        for (Habitacion h : habitacionesDisponibles){
            System.out.println("Habitacion numero: "+ h.getNumero() +
                    ", Capacidad para " + h.getCapacidad() + " personas" );
        }

        Cliente cliente = new Cliente("4124214","luca", "argentina","guemes","mdp","luca@gmai.");
        Habitacion h = new Habitacion(11, 3);
        LocalDate fechaInicio = LocalDate.of(2025, 6, 10);
        LocalDate fechaFin = LocalDate.of(2025, 6, 7);
        Reserva reserva = new Reserva(h, cliente, fechaInicio, fechaFin);
    /// Se intenta agregar una reserva mal cargada, aqui entra en juego la funcion chocaConFechas que maneja estos posibles errores
    /// con excepciones.
        try {
            hotel.agregarReserva(reserva);
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al hacer la reserva: " + e.getMessage());
        }
*/


/*
        /// Ahora mostraremos las funciones del recepcionista
        Hotel hotel = new Hotel();

        // Crear un recepcionista con referencia al hotel
        Recepcionista recepcionista = new Recepcionista("123", "Juan", "Argentina", "Calle Falsa 123", hotel, "juanjuan", "juan@gmail.com");


        // Crear un cliente sin reservas
        Cliente cliente1 = new Cliente("456", "Pedro", "Argentina", "Av. Siempreviva 742", "pep", "pep@gmail.com");

        try {
            // Intentar hacer check-in
            recepcionista.hacerCheckIn(cliente1);
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
*/

    }

}
