package utilities.math;

import utilities.math.Vector;

public class Point{
    public float x, y;
    String name;

    public Point(float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    @Override
    public String toString(){
        if(name==null){
            return x+","+y;
        }
        return name + " (" + x + ", " + y + ")";
    }
    public Point(float x, float y) {
        this(x, y,null);
    }
    public Point multiply(Point p) {
        return new Point(x*p.x, y*p.y);
    }
    public Point multiply(Float f) {
        return new Point(x*f, y*f);
    }
    public Point multiply(Vector p) {
        return new Point(x*p.x,y*p.y);
    }
    public Point add(Point p) {
        return new Point(x+p.x, y+p.y);
    }
    public Point add(Float f) {
        return new Point(x+f, y+f);
    }
    public Point add(Vector p) {
        return new Point(x+p.x,y+p.y);
    }
}
