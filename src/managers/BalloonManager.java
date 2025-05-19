package managers;

import entities.balloons.Balloon;
import graphics.terrain.Tile;

import java.util.ArrayList;

public class BalloonManager {
    ArrayList<Balloon> balloons;
    GameManager gm;

    public BalloonManager() {
        balloons = new ArrayList();
        this.gm = GameManager.getInstance();
    }
    public void addBalloon(Balloon balloon) {balloons.add(balloon);}

    public void removeBalloon(Balloon balloon) {balloons.remove(balloon);}

    public ArrayList<Balloon> getBalloons() {return balloons;}


    public void updateBalloons() {

        float t = (float)gm.timer.getTime();
        for (Balloon b : balloons) {
            b.avanzar(t);
        }
    }

    public Tile previousEsquina(Tile tile) {
        Tile tAnterior = null;
        for (Tile t : gm.getCurrentLevel().getEquinas()) {
            if (t == tile) {
                return tAnterior;
            }
            tAnterior = t;
        }
        return null;
    }
    public Tile nextEsquina(Tile tile) {
        Boolean encontrado = false;
        for (Tile t : gm.getCurrentLevel().getEquinas()) {
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
