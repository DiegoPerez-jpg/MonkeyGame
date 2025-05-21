package graphics;

public class UINeedsToBeEmptyEx extends RuntimeException {
    public UINeedsToBeEmptyEx() {
        super("Error el layout tiene que estar vacio para modificar su horientación/alineación");
    }
}
