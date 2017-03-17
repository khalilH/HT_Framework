package exception;

/**
 * Exception levee lorsque la methode HTTP utilise n'est pas la bonne
 */
public class MethodNotAllowedException extends Exception {
    public MethodNotAllowedException(String msg) {
        super(msg);
    }
}
