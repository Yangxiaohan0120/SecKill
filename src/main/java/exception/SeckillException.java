package exception;

/**
 * @author Chris Yang
 * created 2022-07-28 21:11
 **/
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
