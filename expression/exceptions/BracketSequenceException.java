package expression.exceptions;

public class BracketSequenceException extends ParseException {
    public BracketSequenceException(char bracket, int pos) {
        super(new StringBuilder("Expected '").append(bracket).append('\'').toString(), pos);
    }
}
