package expression.exceptions;

public class IllegalConstException extends ParseException {
    public IllegalConstException(int pos) {
        super("Illegal const", pos);
    }
}
