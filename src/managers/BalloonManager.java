package managers;

import entities.balloons.Balloon;

import java.util.ArrayList;

public class BalloonManager {
    ArrayList<Balloon> balloons = new ArrayList<Balloon>();
    public BalloonManager() {
        balloons = new ArrayList();
    }
    public void addBalloon(Balloon balloon) {balloons.add(balloon);}

    public void removeBalloon(Balloon balloon) {balloons.remove(balloon);}

    public ArrayList<Balloon> getBalloons() {return balloons;}




//    public void updateBalloons() {
//        float t = GameManager.getInstance().time;
//        for (Balloon b : balloons) {
//            b.avanzar(t);
//        }
//    }
}
