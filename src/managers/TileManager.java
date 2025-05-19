package managers;

import graphics.Color;
import graphics.terrain.Tile;
import utilities.math.Point;

import java.util.ArrayList;

public class TileManager {
    public ArrayList<Tile> tiles;
    public TileManager() {
        this.tiles = new ArrayList<>();
        int ts = GameManager.getInstance().tileSize;
        for (int i = 1; i<=GameManager.getInstance().width/ts; i++){
            for (int j = 1; j<=GameManager.getInstance().height/ts; j++){
                tiles.add(new Tile(Color.GRASS, new Point(i, j)));
            }
        }
    }

    public Tile searchTile(float x, float y){
        int ts = GameManager.getInstance().tileSize;
        for (Tile tile : tiles){if (tile.position.x == (x-1)*ts && tile.position.y == (y-1)*ts) return tile;}
        return null;
    }
}
