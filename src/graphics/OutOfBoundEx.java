package graphics;

public class OutOfBoundEx extends RuntimeException {
    public OutOfBoundEx() {
        super("La proporción total de layouts no debe ser mayor que 1");
    }
}
