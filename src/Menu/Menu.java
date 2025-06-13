package Menu;

import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;
import Modelo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Hotel hotel;
    private Scanner scanner;


    public Menu(Hotel hotel) {
        this.hotel = hotel;
        this.scanner = new Scanner(System.in);
    }

    public Menu() {
    }

    public void mostrarMenu() {
        int opcion = -1;

        do {
            System.out.println("\n========= MENÚ PRINCIPAL =========");
            System.out.println("1. Listar habitaciones disponibles");
            System.out.println("2. Agregar una reserva");
            System.out.println("3. Hacer check-in");
            System.out.println("4. Hacer check-out");
            System.out.println("5. Mostrar todos los clientes");
            System.out.println("6. Mostrar todas las reservas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1 -> listarHabitacionesDisponibles();
                case 2 -> agregarReserva();
                case 3 -> hacerCheckIn();
                case 4 -> hacerCheckOut();
                case 5 -> mostrarClientes();
                case 6 -> mostrarReservas();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listarHabitacionesDisponibles() {
        try {
            System.out.println("Ingrese la fecha de inicio (YYYY-MM-DD): ");
            LocalDate inicio = LocalDate.parse(scanner.nextLine());

            System.out.println("Ingrese la fecha de fin (YYYY-MM-DD): ");
            LocalDate fin = LocalDate.parse(scanner.nextLine());

            List<Habitacion> disponibles = hotel.obtenerHabitacionesDisponibles(inicio, fin);

            if (disponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles en ese período.");
            } else {
                System.out.println("Habitaciones disponibles:");
                for (Habitacion h : disponibles) {
                    System.out.println(h);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar fechas: " + e.getMessage());
        }
    }

    private void agregarReserva() {
        try {
            System.out.println("Ingrese el DNI del cliente:");
            String dniCliente = scanner.nextLine();

            Cliente cliente = null;
            for (Cliente c : hotel.getClientes().obtenerTodos()) {
                if (c.getDni().equals(dniCliente)) {
                    cliente = c;
                    break;
                }
            }

            if (cliente == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            System.out.println("Ingrese el número de habitación:");
            int numeroHabitacion = Integer.parseInt(scanner.nextLine());

            Habitacion habitacion = null;
            for (Habitacion h : hotel.getHabitaciones().obtenerTodos()) {
                if (h.getNumero() == numeroHabitacion) {
                    habitacion = h;
                    break;
                }
            }

            if (habitacion == null) {
                System.out.println("Habitación no encontrada.");
                return;
            }

            System.out.println("Ingrese la fecha de inicio (YYYY-MM-DD):");
            LocalDate inicio = LocalDate.parse(scanner.nextLine());

            System.out.println("Ingrese la fecha de fin (YYYY-MM-DD):");
            LocalDate fin = LocalDate.parse(scanner.nextLine());

            Reserva reserva = new Reserva(habitacion, cliente, inicio, fin);
            hotel.agregarReserva(reserva);

            System.out.println("Reserva agregada con éxito.");
        } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.out.println("Error al agregar reserva: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }

    private void hacerCheckIn() {
        try {
            System.out.println("Ingrese el DNI del cliente para hacer Check-In:");
            String dni = scanner.nextLine();

            Cliente cliente = null;
            for (Cliente c : hotel.getClientes().obtenerTodos()) {
                if (c.getDni().equals(dni)) {
                    cliente = c;
                    break;
                }
            }

            if (cliente == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            // Simulamos que lo hace el primer recepcionista
            Recepcionista recepcionista = hotel.getRecepcionistas().obtenerTodos().get(0);
            recepcionista.setHotel(hotel);
            recepcionista.hacerCheckIn(cliente);

            System.out.println("Check-in realizado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error al hacer Check-in: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void hacerCheckOut() {
        try {
            System.out.println("Ingrese el DNI del cliente para hacer Check-Out:");
            String dni = scanner.nextLine();

            Cliente cliente = null;
            for (Cliente c : hotel.getClientes().obtenerTodos()) {
                if (c.getDni().equals(dni)) {
                    cliente = c;
                    break;
                }
            }

            if (cliente == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            Recepcionista recepcionista = hotel.getRecepcionistas().obtenerTodos().get(0);
            recepcionista.setHotel(hotel);
            recepcionista.hacerCheckOut(cliente);

            System.out.println("Check-out realizado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error al hacer Check-out: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void mostrarClientes() {
        System.out.println("\nLista de clientes:");
        for (Cliente c : hotel.getClientes().obtenerTodos()) {
            System.out.println(c);
        }
    }

    private void mostrarReservas() {
        System.out.println("\nLista de reservas:");
        for (Reserva r : hotel.getReservas().obtenerTodos()) {
            System.out.println("Cliente: " + r.getCliente().getNombre() + ", Habitación: " +
                    r.getHabitacion().getNumero() + ", Desde: " + r.getFechaInicio() + ", Hasta: " + r.getFechaFin());
        }
    }
}

//    public static void main() {




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

        /*
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

*/
   // }

//}
