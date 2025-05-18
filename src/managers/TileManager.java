package managers;

import graphics.Color;
import graphics.terrain.Tile;
import utilities.math.Point;

import java.util.ArrayList;

public class TileManager {
    public ArrayList<Tile> tiles;
    public TileManager() {
        this.tiles = new ArrayList<>();
        for (int i = 1; i<=15; i++){
            for (int j = 1; j<=15; i++){
                tiles.add(new Tile(Color.GRASS, new Point(15*i, 15*j)));
            }
        }
    }

    public Tile searchTile(int x, int y){
        for (Tile tile : tiles){if (tile.position.x == x && tile.position.y == y) return tile;}
        return null;
    }
}
