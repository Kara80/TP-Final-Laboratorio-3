package Modelo;

import Contenedores.Gestor;
import Excepciones.*;
import Enum.EstadoDeHabitacion;
import JSONUtiles.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private Gestor<Administrador> administradores;
    private Gestor<Recepcionista> recepcionistas;
    private Gestor<Cliente> clientes;
    private Gestor<Habitacion> habitaciones;
    private Gestor<Reserva> reservas;

    public Hotel() {
        this.administradores = new Gestor<>();
        this.recepcionistas = new Gestor<>();
        this.clientes = new Gestor<>();
        this.habitaciones = new Gestor<>();
        this.reservas = new Gestor<>();
    }

    public Gestor<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public Gestor<Administrador> getAdministradores() {
        return administradores;
    }

    public Gestor<Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    public Gestor<Cliente> getClientes() {
        return clientes;
    }

    public Gestor<Reserva> getReservas() {
        return reservas;
    }



    public void agregarUsuario(Usuario usuario) throws UsuarioDuplicadoException{
        if (usuario == null ||
            usuario.getDni() == null || usuario.getDni().isBlank() ||
            usuario.getNombre() == null || usuario.getNombre().isBlank() ||
            usuario.getContraseña() == null || usuario.getContraseña().isBlank() ||
            usuario.getMail() == null || usuario.getMail().isBlank() ||
            usuario.getDomicilio() == null || usuario.getDomicilio().isBlank() ||
            usuario.getNacionalidad() == null || usuario.getNacionalidad().isBlank()){

            throw new IllegalArgumentException("El usuario tiene datos incompletos o nulos");
        }

        verificarDatosUnicos(usuario);
        if (usuario instanceof Cliente) clientes.agregar((Cliente) usuario);
        if (usuario instanceof Administrador) administradores.agregar((Administrador) usuario);
        if (usuario instanceof Recepcionista) recepcionistas.agregar((Recepcionista) usuario);

    }

    private void verificarDatosUnicos(Usuario nuevoUsuario) throws UsuarioDuplicadoException {
        if (dniExiste(nuevoUsuario.getDni())) {
            throw new UsuarioDuplicadoException("El DNI ya está registrado.");
        }
        if (mailExiste(nuevoUsuario.getMail())) {
            throw new UsuarioDuplicadoException("El mail ya está registrado.");
        }
        if (contrasenaExiste(nuevoUsuario.getContraseña())) {
            throw new UsuarioDuplicadoException("La contraseña ya está registrada.");
        }
    }

    private List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> todos = new ArrayList<>();
        todos.addAll(clientes.getElementos());
        todos.addAll(recepcionistas.getElementos());
        todos.addAll(administradores.getElementos());
        return todos;
    }

    private boolean dniExiste(String dni) {
        for (Usuario u : obtenerTodosLosUsuarios()) {
            if (u.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    private boolean mailExiste(String mail) {
        for (Usuario u : obtenerTodosLosUsuarios()) {
            if (u.getMail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

    private boolean contrasenaExiste(String contrasena) {
        for (Usuario u : obtenerTodosLosUsuarios()) {
            if (u.getContraseña().equals(contrasena)) {
                return true;
            }
        }
        return false;
    }

    public Habitacion buscarHabitacionPorNumero(int numero){

        for (Habitacion h : habitaciones.obtenerTodos()){

            if (h.getNumero() == numero){
                return h;
            }
        }
        return null;
    }

    public void eliminarReserva(String dniCliente, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) throws ReservaNoEncontradaException, ClienteNoEncontradoException {

        if (dniCliente == null || dniCliente.isBlank() ||
            fechaInicio == null || fechaFin == null){
            throw new IllegalArgumentException("Datos invalidos para eliminar una reserva.");
        }

        Cliente cliente = new Cliente();
        for (Cliente c : clientes.obtenerTodos()){
            if (c.getDni().equals(dniCliente)){
                cliente = c;
                break;
            }
        }

        if (cliente == null){
            throw new ClienteNoEncontradoException("No se encontro un cliente con el dni " + cliente.getDni());
        }

        Reserva reservaAEliminar = new Reserva();

        for (Reserva r : reservas.obtenerTodos()){
            if (r.getCliente().equals(cliente) &&
                r.getHabitacion().getNumero() == numeroHabitacion &&
                r.getFechaInicio().isEqual(fechaInicio) &&
                r.getFechaFin().isEqual(fechaFin)){
                reservaAEliminar = r;
                break;
            }
        }

        if (reservaAEliminar == null){
            throw new ReservaNoEncontradaException("No se encontro una reserva que coincida con los datos pasados", cliente, fechaInicio);
        }

        //eliminar de la lista del cliente, lista de la habitacion y de la lista del hotel


        //cliente.eliminarReserva(reservaAEliminar);
        reservaAEliminar.getCliente().eliminarReserva(reservaAEliminar);


        Habitacion habitacionDeReserva = reservaAEliminar.getHabitacion();
        habitacionDeReserva.eliminarReserva(reservaAEliminar);


        reservas.eliminar(reservaAEliminar);

    }


    /*
        Recorre la lista de habitaciones y devuelve una lista con
        todas las habitaciones disponibles entre los dias dados.
          */
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechafinal){
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();

        for (Habitacion h : habitaciones.obtenerTodos()){

            if (h.estaDisponible(fechaInicio, fechafinal)){
                habitacionesDisponibles.add(h);
            }
        }

        return habitacionesDisponibles;
    }

    /*
    Intenta agregar una reserva al hotel.
    Delega toda la validacion a la habitacion.
    Si no hay errores se registra en la lista de reservas.
     */
    public void agregarReserva(Reserva reserva)throws FechaReservaInvalidaException, HabitacionNoDisponibleException{

        //getHabitacion() devuelve una referencia a una habitacion que ya existe en el hotel
        Habitacion habitacionDeReserva = reserva.getHabitacion();

        //modifica directamente esa habitacion de la lista del hotel
        habitacionDeReserva.agregarReserva(reserva);

        //agrega la reserva a la lista de reservas que tiene un cliente a modo de historial
        reserva.getCliente().agregarReserva(reserva);

        //agrega la reserva a la lista de reservas
        reservas.agregar(reserva);

    }

    /*
    Recorre la lista de reservas y verifica si existe una reserva cuyo cliente coincida
    con el pasado por parametro y que la fecha de inicio coincida tambien.
    Tambien se verifica si la habitacion esta disponible
    La va a llamar Recepcionista
     */
    public void checkIn(Cliente cliente) throws HabitacionNoDisponibleException , ReservaNoEncontradaException {

        //un atributo con el dia de hoy
        LocalDate fechaHoy = LocalDate.now();

        for (Reserva r : reservas.obtenerTodos()){

            //si el cliente en el que estoy parado en la lista de reservas es igual al cliente pasado y
            //la fecha del 1er dia de la reserva coincide con la fecha de hoy
            if (r.getCliente().equals(cliente) && r.getFechaInicio().isEqual(fechaHoy)){

                Habitacion habitacion = r.getHabitacion();

                //si la habitacion esta disponible
                if (habitacion.getEstadoHabitacion() == EstadoDeHabitacion.disponible){

                    //el estado de la habitacion pasa a ocupada
                    habitacion.setEstadoHabitacion(EstadoDeHabitacion.ocupada);
                    return;
                }
                //si la habitacion no esta disponible (ocupada o en mantenimiento)
                else{
                    throw new HabitacionNoDisponibleException("La habitacion no esta disponible" , habitacion.getNumero());
                }
            }
        }
        //si ya recorri toda la lista y no encontro ni una reserva del cliente
        throw new ReservaNoEncontradaException("No se encontro una reserva de hoy para el cliente" , cliente, fechaHoy);
    }

    /*
    Recorre la lista de reservas y verifica si existe una reserva cuyo cliente coincidad
    con el pasado por parametro y que la fecha del ultimo dia sea hoy.
    Cambia el estado de ocupada a disponible
    La llama recepcionista
     */
    public void checkOut(Cliente cliente) throws HabitacionNoDisponibleException , ReservaNoEncontradaException{

        LocalDate fechaHoy = LocalDate.now();

        for (Reserva r : reservas.obtenerTodos()){

            if (r.getCliente().equals(cliente) && r.getFechaFin().isEqual(fechaHoy)){

                Habitacion habitacion = r.getHabitacion();

                if (habitacion.getEstadoHabitacion()== EstadoDeHabitacion.ocupada){
                    habitacion.setEstadoHabitacion(EstadoDeHabitacion.disponible);
                    return;
                }
                else{
                    throw new HabitacionNoDisponibleException("La habitacion no esta ocupada", habitacion.getNumero());
                }

            }
        }

        //si ya recorrio toda la lista y no encontro ni una reserva del cliente
        throw new ReservaNoEncontradaException("No se encontro una reserva que termine hoy del cliente", cliente, fechaHoy);

    }

    //--------------------------- JAVA A JSON ---------------------------//


    public JSONArray administradoresAJson(){
        JSONArray jsonAdministradores = new JSONArray();

        for (Administrador a : administradores.obtenerTodos()){
            JSONObject jsonAdministrador = a.adminAJson();
            jsonAdministradores.put(jsonAdministrador);
        }

        return jsonAdministradores;
    }

    public JSONArray recepcionistasAJson(){
        JSONArray jsonRecepcionistas = new JSONArray();

        for (Recepcionista r : recepcionistas.obtenerTodos()){
            JSONObject jsonRecepcionista = r.recepcionistaAJson();
            jsonRecepcionistas.put(jsonRecepcionista);
        }

        return jsonRecepcionistas;
    }

    public JSONArray clientesAJson(){
        JSONArray jsonClientes = new JSONArray();

        for (Cliente c : clientes.obtenerTodos()){
            JSONObject jsonCliente = c.clienteAJsonConReservas();
            jsonClientes.put(jsonCliente);
        }

        return jsonClientes;
    }

    public JSONArray habitacionAJsonConReservas(){
        JSONArray jsonHabitaciones = new JSONArray();

        for (Habitacion h : habitaciones.obtenerTodos()){
            JSONObject jsonHabitacion = h.habitacionAJsonConReservas();
            jsonHabitaciones.put(jsonHabitacion);
        }

        return jsonHabitaciones;
    }

    public JSONArray reservasAJson(){
        JSONArray jsonReservas = new JSONArray();

        for (Reserva r : reservas.obtenerTodos()){
            JSONObject jsonReserva = r.reservaAJson();
            jsonReservas.put(jsonReserva);
        }

        return jsonReservas;
    }

    //------------------------------------------------------------------//
    //--------------------------- JSON A Hotel ---------------------------//
    /*public static Hotel jsonAHotel(JSONArray jsonHotel) {
        Hotel hotel = new Hotel();
        //Haciendo la deserialización
        try {
            //Pasando Administradores de Json a
            JSONArray jsonAdministradores = jsonHotel.getJSONArray("administradores");
            for (int i = 0; i < jsonAdministradores.length(); i++) {
                JSONObject jsonAdmin = jsonAdministradores.getJSONObject(i);
                Administrador admin = Administrador.jsonAAdministrador(jsonAdmin);
                hotel.administradores.agregar(admin);
            }

            // 2. Recepcionistas
            JSONArray jsonRecepcionistas = jsonHotel.getJSONArray("recepcionistas");
            for (int i = 0; i < jsonRecepcionistas.length(); i++) {
                JSONObject jsonRecep = jsonRecepcionistas.getJSONObject(i);
                Recepcionista recep = Recepcionista.jsonARecepcionista(jsonRecep);
                hotel.recepcionistas.agregar(recep);
            }

            // 3. Clientes
            JSONArray jsonClientes = jsonHotel.getJSONArray("clientes");
            for (int i = 0; i < jsonClientes.length(); i++) {
                JSONObject jsonCliente = jsonClientes.getJSONObject(i);
                Cliente cliente = Cliente.jsonACliente(jsonCliente); // reconstruye reservas internas
                hotel.clientes.agregar(cliente);
            }

            // 4. Habitaciones
            JSONArray jsonHabitaciones = jsonHotel.getJSONArray("habitaciones");
            for (int i = 0; i < jsonHabitaciones.length(); i++) {
                JSONObject jsonHabitacion = jsonHabitaciones.getJSONObject(i);
                Habitacion habitacion = Habitacion.jsonAHabitacion(jsonHabitacion); // sin reservas
                hotel.habitaciones.agregar(habitacion);
            }

            // 5. Reservas (conectadas a cliente y habitación ya existentes)
            JSONArray jsonReservas = jsonHotel.getJSONArray("reservas");
            for (int i = 0; i < jsonReservas.length(); i++) {
                JSONObject jsonReserva = jsonReservas.getJSONObject(i);
                Reserva reserva = Reserva.jsonAReserva(jsonReserva);    //me parece q se puede romper.
                hotel.reservas.agregar(reserva);

                // reconectar referencias
                reserva.getCliente().agregarReserva(reserva);
                try {
                    reserva.getHabitacion().agregarReserva(reserva);
                } catch (FechaReservaInvalidaException | HabitacionNoDisponibleException e) {
                    System.out.println("Error al agregar reserva a la habitación: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            System.out.println("Error al parsear JSON del hotel: " + e.getMessage());
        }

        return hotel;
    }*/


    //-------------------------Lectura JSON-----------------------------//
    public void leerHabitaciones(){

        try{
            JSONArray jsonHabitaciones = new JSONArray(JsonUtiles.leerUnJson("Habitaciones.json"));
            for (int i = 0; i < jsonHabitaciones.length(); i++){
                Habitacion h = Habitacion.jsonAHabitacion(jsonHabitaciones.getJSONObject(i));
                habitaciones.agregar(h);
            }

        }
        catch (JSONException e){
            System.out.println("No se ha podido leer el archivo");
        }
    }

    public void leerReservas(){

        try{
            JSONArray jsonReservas = new JSONArray(JsonUtiles.leerUnJson("Reservas.json"));
            for (int i = 0; i < jsonReservas.length(); i++){
                Reserva r = Reserva.jsonAReserva(jsonReservas.getJSONObject(i));
                reservas.agregar(r);
            }
        }
        catch(JSONException e){
            System.out.println("No se ha podido leer el archivo");
        }
    }

    public void leerClientes(){

        try{
            JSONArray jsonClientes = new JSONArray(JsonUtiles.leerUnJson("Clientes.json"));
            for (int i = 0; i < jsonClientes.length(); i++){
                Cliente c = Cliente.jsonACliente(jsonClientes.getJSONObject(i));
                clientes.agregar(c);
            }
        }
        catch(JSONException e){
            System.out.println("No se ha podido leer el archivo");
        }
    }

    public void leerAdministradores(){

        try{
            JSONArray jsonAdministradores = new JSONArray(JsonUtiles.leerUnJson("Administradores.json"));
            for (int i = 0; i < jsonAdministradores.length(); i++){
                Administrador a = Administrador.jsonAAdministrador(jsonAdministradores.getJSONObject(i));
                administradores.agregar(a);
            }
        }
        catch(JSONException e){
            System.out.println("No se ha podido leer el archivo");
        }
    }

    public void leerRecepcionistas(){

        try{
            JSONArray jsonRecepcionistas = new JSONArray(JsonUtiles.leerUnJson("Recepcionistas.json"));
            for (int i = 0; i < jsonRecepcionistas.length(); i++){
                Recepcionista r = Recepcionista.jsonARecepcionista(jsonRecepcionistas.getJSONObject(i));
                recepcionistas.agregar(r);
            }
        }
        catch(JSONException e){
            System.out.println("No se ha podido leer el archivo");
        }
    }

    //-------------------------Grabar JSON-----------------------------//
    public void grabarHabitaciones(){

        try{
            JsonUtiles.grabarUnJson(habitacionAJsonConReservas(),"Habitaciones.json");
        }
        catch(Exception e){
            System.out.println("No se ha podido grabar el archivo");
        }
    }

    public void grabarReservas(){

        try{
            JsonUtiles.grabarUnJson(reservasAJson(),"Reservas.json");
        }
        catch(Exception e){
            System.out.println("No se ha podido grabar el archivo");
        }
    }

    public void grabarClientes(){

        try{
            JsonUtiles.grabarUnJson(clientesAJson(),"Clientes.json");
        }
        catch(Exception e){
            System.out.println("No se ha podido grabar el archivo");
        }
    }

    public void grabarAdministradores(){

        try{
            JsonUtiles.grabarUnJson(administradoresAJson(),"Administradores.json");
        }
        catch(Exception e){
            System.out.println("No se ha podido grabar el archivo");
        }
    }

    public void grabarRecepcionistas(){

        try {
            JsonUtiles.grabarUnJson(recepcionistasAJson(),"Recepcionistas.json");
        }
        catch(Exception e){
            System.out.println("No se ha podido grabar el archivo");
        }
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
