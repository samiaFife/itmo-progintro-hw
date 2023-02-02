package expression;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Add(new Const(2), new Variable("x")).toString());
    }
}
