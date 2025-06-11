package Modelo;

import Excepciones.HabitacionNoDisponibleException;
import Excepciones.ReservaNoEncontradaException;

public class Recepcionista extends Usuario{

    //maneja una referncia a Hotel para que pueda operar sobre reservas, checksIn/Out
    private Hotel hotel;

    public Recepcionista(String dni, String nombre, String nacionalidad, String domicilio, Hotel hotel) {
        super(dni, nombre, nacionalidad, domicilio);
        this.hotel = hotel;
    }

    public void hacerCheckIn(Cliente cliente){

        try{

            hotel.checkIn(cliente);
        } catch (HabitacionNoDisponibleException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ReservaNoEncontradaException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void hacerCheckOut(Cliente cliente){

        try{
            hotel.checkOut(cliente);
        } catch (HabitacionNoDisponibleException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ReservaNoEncontradaException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
