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
            if(anterior.getCentre().x!= tile.getCentre().x){
                if(anterior.getCentre().x<tile.getCentre().x){
                    for (float i = anterior.getCentre().x; i < tile.getCentre().x; i++) {
                        tm.searchTile(i,anterior.getCentre().y).toRoad();
                    }
                } else {
                    for (float i = tile.getCentre().x; i < anterior.getCentre().x+1; i++) {
                        tm.searchTile(i,anterior.getCentre().y).toRoad();
                    }
                }

            }
            if(anterior.getCentre().y!= tile.getCentre().y){
                if(anterior.getCentre().y<tile.getCentre().y){
                    for (float i = anterior.getCentre().y; i < tile.getCentre().y; i++) {
                        tm.searchTile(anterior.getCentre().x,i).toRoad();
                    }
                } else {
                    for (float i = tile.getCentre().y; i < anterior.getCentre().y+1; i++) {
                        tm.searchTile(anterior.getCentre().x,i).toRoad();
                    }
                }

            }

            anterior = tile;
        }
    }
}
