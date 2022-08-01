package exception;

/**
 * 重复秒杀异常（运行期异常）
 * @author Chris Yang
 * created 2022-07-28 21:09
 **/
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
