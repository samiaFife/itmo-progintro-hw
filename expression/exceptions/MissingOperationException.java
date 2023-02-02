package expression.exceptions;

public class MissingOperationException extends ParseException {
    public MissingOperationException(int pos) {
        super("Missing operation", pos);
    }
}
