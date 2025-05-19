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

    public Tile searchTile(float x, float y){
        for (Tile tile : tiles){if (tile.position.x == (x-1)*32 && tile.position.y == (y-1)*32) return tile;}
        return null;
    }
    public void crearCamino(){
        Tile anterior = null;
        for (Tile tile : GameManager.getInstance().levelManager.esquinas){
            if (anterior == null) {
                anterior = tile;
                continue;
            }
            if(anterior.posCentrada.x!= tile.posCentrada.x){
                if(anterior.posCentrada.x<tile.posCentrada.x){
                    for (float i = anterior.posCentrada.x; i < tile.posCentrada.x; i++) {
                        searchTile(i,anterior.posCentrada.y).toRoad();
                    }
                } else {
                    for (float i = tile.posCentrada.x; i < anterior.posCentrada.x+1; i++) {
                        searchTile(i,anterior.posCentrada.y).toRoad();
                    }
                }

            }
            if(anterior.posCentrada.y!= tile.posCentrada.y){
                if(anterior.posCentrada.y<tile.posCentrada.y){
                    for (float i = anterior.posCentrada.y; i < tile.posCentrada.y; i++) {
                        searchTile(anterior.posCentrada.x,i).toRoad();
                    }
                } else {
                    for (float i = tile.posCentrada.y; i < anterior.posCentrada.y+1; i++) {
                        searchTile(anterior.posCentrada.x,i).toRoad();
                    }
                }

            }

            anterior = tile;
        }
    }
}
