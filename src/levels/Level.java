package levels;

import graphics.terrain.Tile;
import utilities.math.Point;
import java.util.ArrayList;

public class Level {
    int id;
    ArrayList<Tile> corners;
    public Level(int id, ArrayList<Tile> corners) {
        this.corners = corners;
        this.id = id;
    }
}

