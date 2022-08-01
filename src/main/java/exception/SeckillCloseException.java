package exception;

/**
 * 秒杀关闭异常
 * @author Chris Yang
 * created 2022-07-28 21:10
 **/
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
