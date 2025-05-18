package managers;

import graphics.Color;
import graphics.terrain.Tile;
import utilities.math.Point;

import java.util.ArrayList;

public class TileManager {
    public ArrayList<Tile> tiles;
    public TileManager() {
        this.tiles = new ArrayList<>();
        for (int i = 0; i<GameManager.getInstance().width/16; i++){
            for (int j = 0; j<GameManager.getInstance().height/16; j++){
                tiles.add(new Tile(Color.GRASS, new Point((16*i)+8, (16*j)+8)));
            }
        }
    }

    public Tile searchTile(int x, int y){
        for (Tile tile : tiles){if (tile.position.x == x && tile.position.y == y) return tile;}
        return null;
    }
}
