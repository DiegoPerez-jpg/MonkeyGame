package utilities.math;

public class Vector {
    public float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public double getMod() { // Devuelve el módulo del vector
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector normalize() {
        double mod = getMod();
        return new Vector(this.x / mod, this.y / mod, this.z / mod);
    }

}
