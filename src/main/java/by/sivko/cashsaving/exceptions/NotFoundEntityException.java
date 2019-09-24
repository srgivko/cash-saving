package by.sivko.cashsaving.exceptions;

public class NotFoundEntityException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not found entity";

    public NotFoundEntityException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundEntityException(Long id) {
        super(String.format("Not found entity with id=%d", id));
    }
}
