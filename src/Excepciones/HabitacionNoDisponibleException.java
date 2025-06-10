package Excepciones;

public class HabitacionNoDisponibleException extends Exception{

    //indica que la habitacion esta ocupada o no disponible
    //y no puede reservarse en tal periodo

    public HabitacionNoDisponibleException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
