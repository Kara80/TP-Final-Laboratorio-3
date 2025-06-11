package Excepciones;

public class HabitacionNoDisponibleException extends Exception{

    //indica que la habitacion esta ocupada o no disponible
    //y no puede reservarse en tal periodo

    private int numero;

    public HabitacionNoDisponibleException(String mensaje, int numero){
        super(mensaje);
        this.numero = numero;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " - Habitacion: " + numero;
    }
}
