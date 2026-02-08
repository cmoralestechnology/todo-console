package exception;

public class TareaNoEncontradaException extends RuntimeException{

    public TareaNoEncontradaException(int id) {
        super("No se encontró tarea con id: " + id);
    }

}
