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
            if(anterior.posCentrada.x!= tile.posCentrada.x){
                if(anterior.posCentrada.x<tile.posCentrada.x){
                    for (float i = anterior.posCentrada.x; i < tile.posCentrada.x; i++) {
                        tm.searchTile(i,anterior.posCentrada.y).toRoad();
                    }
                } else {
                    for (float i = tile.posCentrada.x; i < anterior.posCentrada.x+1; i++) {
                        tm.searchTile(i,anterior.posCentrada.y).toRoad();
                    }
                }

            }
            if(anterior.posCentrada.y!= tile.posCentrada.y){
                if(anterior.posCentrada.y<tile.posCentrada.y){
                    for (float i = anterior.posCentrada.y; i < tile.posCentrada.y; i++) {
                        tm.searchTile(anterior.posCentrada.x,i).toRoad();
                    }
                } else {
                    for (float i = tile.posCentrada.y; i < anterior.posCentrada.y+1; i++) {
                        tm.searchTile(anterior.posCentrada.x,i).toRoad();
                    }
                }

            }

            anterior = tile;
        }
    }
}
