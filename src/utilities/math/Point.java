package utilities.math;

public class Point{
    public float x, y, z;
    String name;

    public Point(float x, float y, float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Point(float x, float y, float z) {
        this(x, y, z, null);
    }
}
