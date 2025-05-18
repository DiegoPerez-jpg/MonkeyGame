package graphics;

public enum Color {
    RED(255f, 0f, 0f),
    GREEN(0f, 255f, 0f),
    BLUE(0f, 0f, 255f),
    BLACK(0f, 0f, 0f),
    WHITE(255f, 255f, 255f),
    YELLOW(255f, 255f, 0f),
    CYAN(0f, 255f, 255f),
    MAGENTA(255f, 0f, 255f),
    GREY(128f, 128f, 128f),
    ORANGE(255f, 165f, 0f);

    public final float r, g, b;

    Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
