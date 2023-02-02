package expression.exceptions;

public class ParseException extends Exception {

    public ParseException(String message, int pos) {
        super(new StringBuilder(message).append(" at the pos: ").append(pos).toString());
    }
}
