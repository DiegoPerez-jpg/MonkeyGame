package utilities.math;

public class Vector {
    public float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getMod() { // Devuelve el módulo del vector
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public Vector normalize() {
        float mod = getMod();
        return new Vector(this.x/mod, this.y/mod);
    }

}
