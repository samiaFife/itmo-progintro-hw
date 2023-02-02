package expression.exceptions;

public class UnknownOperationException extends ParseException {
    public UnknownOperationException(int pos) {
        super("Unknown operation", pos);
    }
}
