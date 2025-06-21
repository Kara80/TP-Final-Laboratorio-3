package Menu;

import Excepciones.FechaReservaInvalidaException;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.UsuarioDuplicadoException;
import JSONUtiles.JsonUtiles;
import Modelo.*;
import Enum.EstadoDeReserva;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu{
    private Hotel hotel;
    private Scanner scanner;

    public Menu(){
        this.hotel = new Hotel();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu(){
        cargarDatos();
        
        System.out.println("===== Bienvenido al sistema del Hotel =====");

        Usuario usuario = null;

        while (usuario == null){
            System.out.print("Ingrese su mail: ");
            String mail = scanner.nextLine();

            System.out.print("Ingrese su contraseña: ");
            String contrasenia = scanner.nextLine();

            // Buscar usuario
            usuario = buscarUsuario(mail, contrasenia);

            if (usuario == null ){
                System.out.println("Mail o contraseña incorrectos. Intente de nuevo.\n");
            }
        }

        if (usuario instanceof Administrador) {
            menuAdministrador((Administrador) usuario);
        } else if (usuario instanceof Recepcionista) {
            Recepcionista r = (Recepcionista) usuario;
            r.setHotel(hotel); // vincular el hotel
            menuRecepcionista(r);
        } else if (usuario instanceof Cliente) {
            menuCliente((Cliente) usuario);
        }
    }

    private Usuario buscarUsuario(String mail, String contrasenia) {
        for (Administrador admin : hotel.getAdministradores().obtenerTodos()) {
            if (admin.getMail().equals(mail) && admin.getContraseña().equals(contrasenia)) {
                return admin;
            }
        }

        for (Recepcionista recep : hotel.getRecepcionistas().obtenerTodos()) {
            if (recep.getMail().equals(mail) && recep.getContraseña().equals(contrasenia)) {
                return recep;
            }
        }

        for (Cliente cliente : hotel.getClientes().obtenerTodos()) {
            if (cliente.getMail().equals(mail) && cliente.getContraseña().equals(contrasenia)) {
                return cliente;
            }
        }

        return null;
    }

    private void menuAdministrador(Administrador admin) {
        System.out.println("\nBienvenido, Administrador " + admin.getNombre());

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Ver habitaciones");
            System.out.println("2. Ver clientes");
            System.out.println("3. Ver recepcionistas");
            System.out.println("4. Agregar administradores");
            System.out.println("5. Agregar Recepcionistas");
            System.out.println("6. Salir");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println(hotel.getHabitaciones().mostrar());
                    break;
                case "2":
                    System.out.println(hotel.getClientes().mostrar());
                    break;
                case "3":
                    System.out.println(hotel.getRecepcionistas().mostrar());
                    break;
                case "4":
                    agregarAdmin();
                    hotel.grabarAdministradores();
                    break;
                case "5":
                    agregarRecepcionista();
                    hotel.grabarRecepcionistas();
                    break;
                case "6":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuRecepcionista(Recepcionista recepcionista) {
        System.out.println("\nBienvenido, Recepcionista " + recepcionista.getNombre());

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ RECEPCIONISTA ---");
            System.out.println("1. Hacer check-in");
            System.out.println("2. Hacer check-out");
            System.out.println("3. Ver reservas");
            System.out.println("4. Agregar cliente");
            System.out.println("5. Agregar reserva");
            System.out.println("6. Ver clientes");
            System.out.println("7. Ver Habitaciones");
            System.out.println("8. Salir");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    Cliente clienteIn = seleccionarCliente();
                    if (clienteIn != null) {
                        recepcionista.hacerCheckIn(clienteIn);
                    }
                    break;
                case "2":
                    Cliente clienteOut = seleccionarCliente();
                    if (clienteOut != null) {
                        recepcionista.hacerCheckOut(clienteOut);
                    }
                    break;
                case "3":
                    System.out.println(hotel.getReservas().mostrar());
                    break;
                case "4":
                    agregarCliente();
                    hotel.grabarClientes();
                    break;
                case "5":
                    agregarReserva();
                    hotel.grabarReservas();
                    hotel.grabarHabitaciones();
                    hotel.grabarClientes();
                    break;
                case "6":
                    System.out.println(hotel.getClientes().mostrar());
                    break;
                case "7":
                    System.out.println(hotel.getHabitaciones().mostrar());
                    break;
                case "8":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuCliente(Cliente cliente) {
        System.out.println("\nBienvenido, Cliente " + cliente.getNombre());

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver mis reservas");
            System.out.println("2. Ver habitaciones disponibles");
            System.out.println("3. Salir");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println(cliente.mostrarReservas());
                    break;
                case "2":
                    verHabitacionesDisponibles();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private Cliente seleccionarCliente() {
        System.out.println("Ingrese DNI del cliente: ");
        String dni = scanner.nextLine();

        for (Cliente c : hotel.getClientes().obtenerTodos()) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        System.out.println("Cliente no encontrado.");
        return null;
    }

    private void verHabitacionesDisponibles() {
        try {
            System.out.print("Ingrese fecha de inicio (YYYY-MM-DD): ");
            LocalDate inicio = LocalDate.parse(scanner.nextLine());

            System.out.print("Ingrese fecha de fin (YYYY-MM-DD): ");
            LocalDate fin = LocalDate.parse(scanner.nextLine());

            var disponibles = hotel.obtenerHabitacionesDisponibles(inicio, fin);

            if (disponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles en esas fechas.");
            } else {
                System.out.println("Habitaciones disponibles:");
                for (Habitacion h : disponibles) {
                    System.out.println(h);
                }
            }

        } catch (Exception e) {
            System.out.println("Formato de fecha inválido.");
        }
    }

    private void cargarDatos() {
        hotel.leerAdministradores();
        hotel.leerRecepcionistas();
        hotel.leerClientes();
        hotel.leerHabitaciones();
        hotel.leerReservas();
    }

    private void agregarCliente(){

        try{
            System.out.println("--Registro de nuevo cliente--\n");
            System.out.println("Ingrese el nombre del cliente: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese el DNI del cliente:");
            String dni = scanner.nextLine();

            System.out.print("Ingrese la nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Ingrese el domicilio: ");
            String domicilio = scanner.nextLine();

            System.out.print("Ingrese el mail: ");
            String mail = scanner.nextLine();

            System.out.print("Ingrese la contraseña: ");
            String contraseña = scanner.nextLine();

            Cliente nuevoCliente = new Cliente(dni, nombre, nacionalidad, domicilio, contraseña, mail);

            hotel.agregarUsuario(nuevoCliente);
            System.out.println("Cliente registrado con exito");
        }
        catch (UsuarioDuplicadoException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarAdmin(){

        try{
            System.out.println("--Registro de nuevo admin--\n");
            System.out.println("Ingrese el nombre del admin: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese el DNI del admin:");
            String dni = scanner.nextLine();

            System.out.print("Ingrese la nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Ingrese el domicilio: ");
            String domicilio = scanner.nextLine();

            System.out.print("Ingrese el mail: ");
            String mail = scanner.nextLine();

            System.out.print("Ingrese la contraseña: ");
            String contraseña = scanner.nextLine();

            Administrador nuevoAdmin = new Administrador(dni, nombre, nacionalidad, domicilio, contraseña, mail);

            hotel.agregarUsuario(nuevoAdmin);
            System.out.println("Administrador registrado con exito");
        }
        catch (UsuarioDuplicadoException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarRecepcionista(){

        try{
            System.out.println("--Registro de nuevo recepcionista--\n");
            System.out.println("Ingrese el nombre del recepcionista: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese el DNI del recepcionista:");
            String dni = scanner.nextLine();

            System.out.print("Ingrese la nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Ingrese el domicilio: ");
            String domicilio = scanner.nextLine();

            System.out.print("Ingrese el mail: ");
            String mail = scanner.nextLine();

            System.out.print("Ingrese la contraseña: ");
            String contraseña = scanner.nextLine();

            Recepcionista nuevoRecepcionista = new Recepcionista(dni, nombre, nacionalidad, domicilio, hotel, contraseña, mail);

            hotel.agregarUsuario(nuevoRecepcionista);
            System.out.println("Recepcionista registrado con exito");
        }
        catch (UsuarioDuplicadoException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarReserva(){

        try{
            System.out.println(" ----- Crear nueva Reserva ----- ");

            Cliente cliente = seleccionarCliente();
            if (cliente == null) return;

            System.out.println("Ingrese el numero de la habitacion: ");
            String numString = scanner.nextLine();
            int numeroHabitacion = 0;
                try{
                    numeroHabitacion = Integer.parseInt(numString);
                } catch (NumberFormatException e) {
                    System.out.println("El numero ingresado no es valido");
                    return;
                }

            Habitacion habitacion = hotel.buscarHabitacionPorNumero(numeroHabitacion);
            if (habitacion == null){
                System.out.println("No se encontro una habitacion con el numero: " + numeroHabitacion);
                return;
            }

            LocalDate inicio = null;
            LocalDate fin = null;
            try{
                System.out.print("Ingrese fecha de inicio (YYYY-MM-DD): ");
                inicio = LocalDate.parse(scanner.nextLine());

                System.out.print("Ingrese fecha de fin (YYYY-MM-DD): ");
                fin = LocalDate.parse(scanner.nextLine());
            } catch (Exception e){
                System.out.println("Error: formato de reserva invalido");
                return;
            }

            Reserva reserva  = new Reserva(habitacion, cliente, inicio, fin);

            //arroja la excepcion
            hotel.agregarReserva(reserva);
            System.out.println("Reserva agregada con exito");

        } catch (FechaReservaInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch (HabitacionNoDisponibleException e){
            System.out.println("Error: " + e.getMessage());
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
