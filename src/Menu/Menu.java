package Menu;

import Excepciones.*;
import JSONUtiles.JsonUtiles;
import Modelo.*;
import Enum.EstadoDeReserva;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import Enum.EstadoDeHabitacion;

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
            System.out.println("6. Eliminar recepcionista");
            System.out.println("7. Eliminar administrador");
            System.out.println("8. Salir");
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
                    eliminarRecepcionista();
                    hotel.grabarRecepcionistas();
                    hotel.grabarHabitaciones();
                    hotel.grabarReservas();
                    break;
                case "7":
                    eliminarAdministrador();
                    hotel.grabarAdministradores();
                    hotel.grabarHabitaciones();
                    hotel.grabarReservas();
                    break;
                case "8":
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
            System.out.println("8. Eliminar Reserva");
            System.out.println("9. Eliminar cliente");
            System.out.println("10. Marcar/Desmarcar habitacion en mantenimiento");
            System.out.println("11. Salir");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    Cliente clienteIn = seleccionarCliente();
                    if (clienteIn != null) {
                        recepcionista.hacerCheckIn(clienteIn);
                    }
                    hotel.grabarReservas();
                    hotel.grabarHabitaciones();
                    hotel.grabarClientes();
                    break;
                case "2":
                    Cliente clienteOut = seleccionarCliente();
                    if (clienteOut != null) {
                        recepcionista.hacerCheckOut(clienteOut);
                    }
                    hotel.grabarReservas();
                    hotel.grabarHabitaciones();
                    hotel.grabarClientes();
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
                    eliminarReserva();
                    hotel.grabarReservas();
                    hotel.grabarHabitaciones();
                    hotel.grabarClientes();
                    break;
                case "9":
                    eliminarCliente();
                    hotel.grabarClientes();
                    hotel.grabarHabitaciones();
                    hotel.grabarReservas();
                    break;
                case "10":
                    alternarEstadoMantenimientoHabitacion();
                    hotel.grabarClientes();
                    hotel.grabarHabitaciones();
                    hotel.grabarReservas();
                    break;
                case "11":
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

            if (habitacion.getEstadoHabitacion() == EstadoDeHabitacion.en_mantenimiento){
                System.out.println("La habitacion actualmente esta en mantenimiento.");
                System.out.println("Seguro que desea continuar con la reserva? (s/n):");
                char confirmacion = scanner.nextLine().charAt(0);
                if (confirmacion == 's'){
                    System.out.println("continuando con la reserva");
                }
                else{
                    System.out.println("cancelando reserva");
                    return;
                }
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

    public void eliminarReserva(){

        try{
            System.out.println(" --- Eliminar Una Reserva --- ");

            Cliente cliente = seleccionarCliente();
            String dni = cliente.getDni();
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

            LocalDate fechaInicio = null;
            LocalDate fechaFin = null;
            try{
                 System.out.println("Ingrese fecha de inicio (YYYY-MM-DD):");
                 fechaInicio = LocalDate.parse(scanner.nextLine());

                 System.out.print("Ingrese fecha de fin (YYYY-MM-DD): ");
                 fechaFin = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: formato de reserva invalido");
                return;
            }

            hotel.eliminarReserva(dni, numeroHabitacion, fechaInicio, fechaFin);
            System.out.println("Reserva eliminada con exito");

        }catch (ClienteNoEncontradoException | ReservaNoEncontradaException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch (Exception e){
            System.out.println("Error inesperado: " + e.getMessage());
        }

    }

    private Usuario buscarUsuarioPorDni(String dni) {
        for (Administrador admin : hotel.getAdministradores().obtenerTodos()) {
            if (admin.getDni().equals(dni)) {
                return admin;
            }
        }

        for (Recepcionista recep : hotel.getRecepcionistas().obtenerTodos()) {
            if (recep.getDni().equals(dni)) {
                return recep;
            }
        }

        for (Cliente cliente : hotel.getClientes().obtenerTodos()) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }

        return null;
    }

    private void eliminarRecepcionista() {
        System.out.print("Ingrese DNI del recepcionista a eliminar: ");
        String dni = scanner.nextLine();

        Usuario usuario = buscarUsuarioPorDni(dni);
        if (usuario instanceof Recepcionista) {
            hotel.eliminarUsuario(usuario);

            System.out.println("Recepcionista eliminado correctamente.");
        } else {
            System.out.println("No se encontró un recepcionista con ese DNI.");
        }

    }

    private void eliminarAdministrador() {
        System.out.print("Ingrese DNI del administrador a eliminar: ");
        String dni = scanner.nextLine();

        Usuario usuario = buscarUsuarioPorDni(dni);
        if (usuario instanceof Administrador) {
            hotel.eliminarUsuario(usuario);

            System.out.println("Administrador eliminado correctamente.");
        } else {
            System.out.println("No se encontró un administrador con ese DNI.");
        }
    }

    private void eliminarCliente() {
        System.out.print("Ingrese DNI del cliente a eliminar: ");
        String dni = scanner.nextLine();

        Usuario usuario = buscarUsuarioPorDni(dni);

        if (usuario instanceof Cliente) {

            Cliente cliente = (Cliente) usuario;

            boolean tieneReservaOcupadaHoy = false;
            LocalDate fechaHoy = LocalDate.now();

            for (Reserva r : cliente.getReservas()){
                Habitacion habitacion = r.getHabitacion();
                if (habitacion != null &&
                    habitacion.getEstadoHabitacion() == EstadoDeHabitacion.ocupada &&
                    !fechaHoy.isBefore(r.getFechaInicio()) && //si hoy es igual o despues que el 1er dia de la reserva
                    !fechaHoy.isAfter(r.getFechaFin())){ //si hoy es igual o antes que el ultimo dia de la reserva

                    tieneReservaOcupadaHoy = true;
                    break;
                }
            }

            if (tieneReservaOcupadaHoy){
                System.out.println("No se puede borrar el cliente porque esta ocupando una habitacion actualmente");
                return;
            }

            hotel.eliminarUsuario(usuario);

            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se encontró un cliente con ese DNI.");
        }
    }

    private void alternarEstadoMantenimientoHabitacion(){
        System.out.println("Ingrese el numero de la habitacion: ");
        int num = 0;

        try{
            num = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Numero invalido");
            return;
        }

        Habitacion habitacion = hotel.buscarHabitacionPorNumero(num);

        if (habitacion == null){
            System.out.println("No se encontro una habitacion con el numero: " + num);
            return;
        }

        EstadoDeHabitacion estadoDeHabitacionActual = habitacion.getEstadoHabitacion();

        if (estadoDeHabitacionActual == EstadoDeHabitacion.en_mantenimiento){
            habitacion.setEstadoHabitacion(EstadoDeHabitacion.disponible);
            System.out.println("La habitacion num " + num + " paso de en mantenimiento a diponible.");
        }
        else if (estadoDeHabitacionActual == EstadoDeHabitacion.disponible){
            habitacion.setEstadoHabitacion(EstadoDeHabitacion.en_mantenimiento);
            System.out.println("La habitacion num " + num + " paso de disponible a en mantenimiento.");
        }
        else{
            System.out.println("La habitacion num " + num + " esta reservada. No se puede cambiar el estado.");
        }

    }



}
