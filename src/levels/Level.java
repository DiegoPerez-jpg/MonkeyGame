package levels;

import graphics.Tile;
import managers.GameManager;
import managers.TileManager;
import java.util.ArrayList;

public class Level {
    public int id;
    public ArrayList<Tile> corners;
    public Level(int id, ArrayList<Tile> corners) {
        this.corners = corners;
        this.id = id;
    }
    public void crearCamino(){
        Tile anterior = null;
        TileManager tm = GameManager.getInstance().tileManager;
        for (Tile tile : corners){
            if (anterior == null) {
                anterior = tile;
                continue;
            }
            if(anterior.getCasilla().x!= tile.getCasilla().x){
                if(anterior.getCasilla().x<tile.getCasilla().x){
                    for (float i = anterior.getCasilla().x; i <= tile.getCasilla().x; i++) {
                        tm.searchTile(i,anterior.getCasilla().y).toRoad();
                    }
                } else {
                    for (float i = tile.getCasilla().x; i <= anterior.getCasilla().x; i++) {
                        tm.searchTile(i,anterior.getCasilla().y).toRoad();
                    }
                }

            }
            if(anterior.getCasilla().y!= tile.getCasilla().y){
                if(anterior.getCasilla().y<tile.getCasilla().y){
                    for (float i = anterior.getCasilla().y; i <= tile.getCasilla().y; i++) {
                        tm.searchTile(anterior.getCasilla().x,i).toRoad();
                    }
                } else {
                    for (float i = tile.getCasilla().y; i <= anterior.getCasilla().y; i++) {
                        tm.searchTile(anterior.getCasilla().x,i).toRoad();
                    }
                }
            }
            anterior = tile;
        }
    }
    public ArrayList<Tile> getEquinas() {return corners;}
}

