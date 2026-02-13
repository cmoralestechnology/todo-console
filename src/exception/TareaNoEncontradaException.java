package exception;
// Excepción personalizada que se lanza cuando una tarea no existe
public class TareaNoEncontradaException extends RuntimeException{

    public TareaNoEncontradaException(int id) {
        super("No se encontró tarea con id: " + id);
    }

}
