package managers;

import entities.balloons.Balloon;
import graphics.Tile;

import java.util.ArrayList;

public class BalloonManager {
    ArrayList<Balloon> balloons;
    GameManager gm;
    ArrayList<ArrayList<Integer>> amountBalloon;
    ArrayList<Float> waveDurantion;
    ArrayList<Float> lastBalloonTimeSpawned;
    float startWaveTime;
    int waveCount;
    boolean playerCheckNextWave = false;
    boolean waveDone;
    public BalloonManager() {
        balloons = new ArrayList();
        this.gm = GameManager.getInstance();
        this.amountBalloon = new ArrayList<>();
        this.lastBalloonTimeSpawned = new ArrayList<>();
        amountBalloon.add(new ArrayList<>());
        amountBalloon.add(new ArrayList<>());
        amountBalloon.get(0).add(100);
        amountBalloon.get(0).add(100);
        this.waveDurantion = new ArrayList<>();
        waveDurantion.add(25f);
        waveDurantion.add(100f);
    }
    public void addBalloon(Balloon balloon) {balloons.add(balloon);}

    public void removeBalloon(Balloon balloon) {balloons.remove(balloon);}

    public ArrayList<Balloon> getBalloons() {return balloons;}

    public int searchBalloon(Balloon b) {
        for (int i = 0; i < balloons.size(); i++) {
            if (balloons.get(i)==b) {
                return i;
            }
        }
        return -1;
    }
    public void updateBalloons() {
        avanzarGlobos();
        spawnearGlobos();
    }

    private void spawnearGlobos(){
        float t = (float) GameManager.getInstance().timer.getTime();
        if(t>=startWaveTime+waveDurantion.get(waveCount)) {
            if(balloons.isEmpty()){
                if(playerCheckNextWave) {
                    playerCheckNextWave = false;
                    lastBalloonTimeSpawned.clear();
                    waveCount++;
                    startWaveTime = t;
                }
                return;
            }
            return;
        }
        if(startWaveTime==0){
            startWaveTime = t;
        }

        if(lastBalloonTimeSpawned.isEmpty()){

            for (int i = 0; i < amountBalloon.size(); i++) {
                lastBalloonTimeSpawned.add(0f);
            }
        }
        for (int i = 0; i < amountBalloon.get(waveCount).size(); i++) {
            float cantidad_de_tipo = amountBalloon.get(waveCount).get(i);
            float intervalo = waveDurantion.get(waveCount)/cantidad_de_tipo;

            if(t - lastBalloonTimeSpawned.get(i) >= intervalo ){
                lastBalloonTimeSpawned.set(i,t);
                balloons.add(new Balloon(1,(double) i+1,"Normal",100));
            }
        }
    }
    private void avanzarGlobos(){
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
