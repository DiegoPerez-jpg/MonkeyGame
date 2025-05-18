package managers;

import graphics.Color;
import graphics.terrain.Tile;
import utilities.math.Point;

import java.util.ArrayList;

public class TileManager {
    public ArrayList<Tile> tiles;
    public TileManager() {
        this.tiles = new ArrayList<>();
        for (int i = 0; i<GameManager.getInstance().width/32; i++){
            for (int j = 0; j<GameManager.getInstance().height/32; j++){
                tiles.add(new Tile(Color.GRASS, new Point((32*i)+16, (32*j)+16)));
            }
        }
    }

    public Tile searchTile(int x, int y){
        for (Tile tile : tiles){if (tile.position.x == (x-1)*32 && tile.position.y == (y-1)*32) return tile;}
        return null;
    }
}
