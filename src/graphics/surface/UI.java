package graphics.surface;

import graphics.Color;
import utilities.math.Point;

import java.util.ArrayList;

public class UI {
    public Color background, borderColor;
    public ArrayList<Point> corners;
    public int borderSize;

    public UI(Color color, ArrayList<Point> corners){ //Puntos en sentido antihorario empezando arriba a la izq
        this.background = color;
        this.corners = corners;
    }
    public UI(Color color, ArrayList<Point> corners, int borderSize, Color borderColor){
        this(color, corners);
        this.borderSize = borderSize;
        this.borderColor = borderColor;
    }

    public Point toGlobal(Point point){ //Pasa las coordenadas locales de la UI (respecto esquina inf izq) a globales
        return new Point(point.x + corners.get(1).x, point.y+corners.get(1).y);
    }
}
