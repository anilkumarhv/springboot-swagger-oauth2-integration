package in.anil.webapp.exception;

/**
 *
 * Created by AH00554631 on 6/15/2018.
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String msg){
        super(msg);
    }

    public FileStorageException(String s, Exception ex) {
        super(s,ex);
    }
}
