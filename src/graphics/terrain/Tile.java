package graphics.terrain;

import graphics.Color;
import utilities.math.Point;
import entities.monkeys.Monkey;

import java.util.ArrayList;

public class Tile { // 16x16 px each
    public Color background;
    public Point posCentrada; //Posición relativa al centro del tile
    public Point position; //Posición relativa a la esquina inferior izq
    public ArrayList<Point> corners;
    boolean isGrass;
    Monkey monkey;
    public Tile(Color background, Point posCentrada) {
        this.background = background;
        this.position = new Point(posCentrada.x-8, posCentrada.y-8);
        this.posCentrada = posCentrada;
        this.corners = new ArrayList<>();
        corners.add(position);
        corners.add(new Point(position.x+16, position.y));
        corners.add(new Point(position.x+16, position.y+16));
        corners.add(new Point(position.x, position.y+16));
        isGrass = (background == Color.GRASS);
        monkey = null;
    }
    public Tile(Point position) {this(null, position);}

    public void toRoad(){
        this.background = Color.ROAD;
        this.isGrass = false;
    }
    public void toGrass(){
        this.background = Color.GRASS;
        this.isGrass = true;
    }
}
