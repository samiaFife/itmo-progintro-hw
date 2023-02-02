package game;

public class Direction {
    Boolean flag;
    Boolean toLeft;
    Boolean toRight;
    Boolean toUp;
    Boolean toDown;
    int[] coeff;

    public Direction(Boolean flag, Boolean toLeft, Boolean toRight, Boolean toUp, Boolean toDown, int[] coeff) {
        this.flag = flag;
        this.toLeft = toLeft;
        this.toRight = toRight;
        this.toUp = toUp;
        this.toDown = toDown;
        this.coeff = coeff;
    }
}
