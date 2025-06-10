package Excepciones;

public class FechaReservaInvalidaException extends Exception{

    //problemas con las fechas de la reserva

    public FechaReservaInvalidaException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
