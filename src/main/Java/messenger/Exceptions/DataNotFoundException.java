package Exceptions;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1664676915935162319L;

    public DataNotFoundException(String message) {
        super(message);
    }

}
