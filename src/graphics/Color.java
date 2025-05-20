package graphics;

public enum Color {
    RED(255f, 0f, 0f, 1f),
    GREEN(0f, 255f, 0f, 1f),
    BLUE(0f, 0f, 255f, 1f),
    BLACK(0f, 0f, 0f, 1f),
    WHITE(255f, 255f, 255f, 1f),
    YELLOW(255f, 255f, 0f, 1f),
    CYAN(0f, 255f, 255f, 1f),
    MAGENTA(255f, 0f, 255f, 1f),
    GREY(128f, 128f, 128f, 1f),
    ORANGE(255f, 165f, 0f, 1f),
    GRASS(167f, 201f, 87f, 1f),
    ROAD(229, 206, 144, 1f),
    BLUEUI(110, 145, 185, 1f),
    BLUEUI2(95, 125, 160, 1f),
    TRANSPARENT(0f, 0f, 0f, 0f);

    public final float r, g, b, a;

    Color(float r, float g, float b, float alpha) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = alpha;
    }
}
