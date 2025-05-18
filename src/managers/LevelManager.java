package managers;

import graphics.terrain.Tile;

import java.util.ArrayList;

public class LevelManager {
    ArrayList<Tile> esquinas;

    public LevelManager(ArrayList<Tile> esquinas) {
        this.esquinas = esquinas;
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
}
