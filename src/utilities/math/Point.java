package utilities.math;

public class Point{
    public float x, y;
    String name;

    public Point(float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Point(float x, float y) {
        this(x, y,null);
    }
}
