package by.sivko.cashsaving.exceptions;

public class NotFoundEntityException extends RuntimeException {

    private static final long serialVersionUID = 2781411015176351522L;

    private static final String DEFAULT_MESSAGE = "Not found entity";

    public NotFoundEntityException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundEntityException(Long id) {
        super(String.format("Not found entity with id=%d", id));
    }
}
