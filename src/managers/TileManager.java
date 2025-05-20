package managers;

import graphics.Color;
import graphics.terrain.Tile;
import utilities.math.Point;

import java.util.ArrayList;

public class TileManager {
    public ArrayList<Tile> tiles;
    public TileManager() {
        this.tiles = new ArrayList<>();
        GameManager gm = GameManager.getInstance();
        int ts = gm.tileSize;
        for (int i = 1; i<=gm.gameWidth/ts; i++){
            for (int j = 1; j<=gm.gameHeight/ts; j++){
                tiles.add(new Tile(Color.GRASS, new Point(i, j)));
            }
        }
    }

    public Tile searchTile(float x, float y){
        for (Tile tile : tiles){if (tile.getCasilla().x == x && tile.getCasilla().y == y) return tile;}
        return null;
    }

    public Tile searchTileGC(float x, float y){
        for (Tile tile : tiles){if (tile.getPosition().x == x && tile.getPosition().y == y) return tile;}
        return null;
    }

    public Tile searchTile(Point p){return searchTile(p.x, p.y);}
    public Tile searchTileGC(Point p){return searchTileGC(p.x, p.y);}
}
