package managers;

import graphics.terrain.Tile;
import java.util.ArrayList;

import managers.TileManager;

public class LevelManager {
    ArrayList<Tile> esquinas;

    public LevelManager(ArrayList<Tile> esquinas) {
        this.esquinas = esquinas;
    }
    public void add(Tile tile) {
        esquinas.add(tile);
    }
    public ArrayList<Tile> getEquinas() {
        return esquinas;
    }
    public Tile previousEsquina(Tile tile) {
        Tile tAnterior = null;
        for (Tile t : esquinas) {
            if (t == tile) {
                return tAnterior;
            }
            tAnterior = t;
        }
        return null;
    }
    public Tile nextEsquina(Tile tile) {
        Boolean encontrado = false;
        for (Tile t : esquinas) {
            if (encontrado) {
                return t;
            }
            if (t == tile) {
                encontrado = true;
            }

        }
        return null;
    }
    public void crearCamino(){
        Tile anterior = null;
        TileManager tm = GameManager.getInstance().tileManager;
        for (Tile tile : GameManager.getInstance().levelManager.esquinas){
            if (anterior == null) {
                anterior = tile;
                continue;
            }
            if(anterior.getCasilla().x!= tile.getCasilla().x){
                if(anterior.getCasilla().x<tile.getCasilla().x){
                    for (float i = anterior.getCasilla().x; i < tile.getCasilla().x; i++) {
                        tm.searchTile(i,anterior.getCasilla().y).toRoad();
                    }
                } else {
                    for (float i = tile.getCasilla().x; i < anterior.getCasilla().x+1; i++) {
                        tm.searchTile(i,anterior.getCasilla().y).toRoad();
                    }
                }

            }
            if(anterior.getCasilla().y!= tile.getCasilla().y){
                if(anterior.getCasilla().y<tile.getCasilla().y){
                    for (float i = anterior.getCasilla().y; i < tile.getCasilla().y; i++) {
                        tm.searchTile(anterior.getCasilla().x,i).toRoad();
                    }
                } else {
                    for (float i = tile.getCasilla().y; i < anterior.getCasilla().y+1; i++) {
                        tm.searchTile(anterior.getCasilla().x,i).toRoad();
                    }
                }

            }

            anterior = tile;
        }
    }
}
