package technikal.task.fishmarket.exception;

public class ImagePersistException extends Exception {
    public ImagePersistException() {
    }

    public ImagePersistException(String message) {
        super(message);
    }

    public ImagePersistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImagePersistException(Throwable cause) {
        super(cause);
    }

    public ImagePersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
