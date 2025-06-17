package Excepciones;

public class UsuarioDuplicadoException extends Exception{

    public UsuarioDuplicadoException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
