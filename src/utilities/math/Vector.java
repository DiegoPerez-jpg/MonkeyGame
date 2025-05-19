package utilities.math;

public class Vector {
    public float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return " (" + x + ", " + y + ")";
    }
    public float getMod() { // Devuelve el módulo del vector
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public Vector normalize() {
        float mod = getMod();
        return new Vector(this.x/mod, this.y/mod);
    }
    public Vector multiply(Point p) {
        return new Vector(x*p.x, y*p.y);
    }

    public Vector multiply(Float f) {
        return new Vector(x*f, y*f);
    }
    public Vector multiply(Vector p) {
        return new Vector(x*p.x,y*p.y);
    }
    public Vector add(Point p) {
        return new Vector(x+p.x, y+p.y);
    }

    public Vector add(Float f) {
        return new Vector(x+f, y+f);
    }
    public Vector add(Vector p) {
        return new Vector(x+p.x,y+p.y);
    }
}
