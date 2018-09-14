package in.anil.webapp.exception;

/**
 *
 * Created by AH00554631 on 6/15/2018.
 */
public class MyFileNotFoundException extends RuntimeException {
    public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
