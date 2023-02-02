package expression.exceptions;

public class MissingOperandException extends ParseException {
    public MissingOperandException(int pos) {
        super("Missing operand", pos);
    }
}
