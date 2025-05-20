package graphics.terrain;

import graphics.Color;
import managers.GameManager;
import utilities.math.Point;
import entities.monkeys.Monkey;

import java.util.ArrayList;

public class Tile { // 32x32 px each
    public Color background;
    public Point position; //Posición relativa a la esquina inferior izq
    public ArrayList<Point> corners;
    boolean isGrass;
    Monkey monkey;
    public Tile(Color background, Point casilla) {
        int ts = GameManager.getInstance().tileSize; //Ancho del tile
        int ey = GameManager.getInstance().extension_y; //Extensión vertical de la UI
        this.background = background;
        this.position = new Point((casilla.x-1)*ts, ((casilla.y-1)*ts)+ey); //Posición relativa a abajo a la izq
        this.corners = new ArrayList<>();
        //Importante puntos en sentido antihorario empezando por arriba a la izq
        corners.add(new Point(position.x, position.y+ts));
        corners.add(position);
        corners.add(new Point(position.x+ts, position.y));
        corners.add(new Point(position.x+ts, position.y+ts));
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

    public boolean contain(Point p){
        if (p.x >= corners.get(0).x && p.x <= corners.get(3).x
                && p.y >= corners.get(1).y && p.y <= corners.get(3).y) {
            return true;
        }
        return false;
    }

    public Point getCasilla(){
        GameManager gm = GameManager.getInstance();
        return new Point(((this.getPosition().x/gm.tileSize)+1), ((this.getPosition().y-gm.extension_y)/gm.tileSize)+1);
    }
    public Point getPosition() {return position;}
    public Point getCentre() {return new Point(position.x+GameManager.getInstance().tileSize/2f, position.y+GameManager.getInstance().tileSize/2f);} //Posición relativa al centro del tile
}
