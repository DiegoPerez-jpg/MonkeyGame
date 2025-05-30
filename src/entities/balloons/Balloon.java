package entities.balloons;

import entities.Entity;
import graphics.Texture;
import graphics.Tile;
import managers.GameManager;
import utilities.math.Point;
import utilities.Util;
import utilities.math.Vector;

import java.util.ArrayList;

public class Balloon extends Entity {
    double vida;
    String tipo;
    public Tile target;
    float velocity;
    float time;
    public boolean isACamoBalloon = false;
    ArrayList<Texture> srcBalloons;
    
    public Balloon(int size, double vida, String tipo, float velocity) {
        super(new Texture("src/assets/baloon/Balloon.png"), GameManager.getInstance().getCurrentLevel().getEquinas().get(0).getPosition(), size);
        this.vida = vida;
        this.velocity = velocity;
        //this.previousBallon = previousBallon;
        this.tipo = tipo;
        this.time = (float)GameManager.getInstance().timer.getTime();
        this.target = GameManager.getInstance().getCurrentLevel().getEquinas().get(0);
        actualizar_skin();
    }
    public Balloon(int size, double vida, String tipo, float velocity,Boolean isACamoBalloon) {
        super(new Texture("src/assets/Balloon.png"), GameManager.getInstance().getCurrentLevel().getEquinas().get(0).getPosition(), size);
        this.vida = vida;
        this.velocity = velocity;
        //this.previousBallon = previousBallon;
        this.tipo = tipo;
        this.time = (float)GameManager.getInstance().timer.getTime();
        this.target = GameManager.getInstance().getCurrentLevel().getEquinas().get(0);
        this.isACamoBalloon = isACamoBalloon;
        actualizar_skin();
    }
    
    public void avanzar(float t){
        float dT = t-time;
        Vector vectorALaesquina = Util.createVector(target.getPosition(),this.position);
        if(vectorALaesquina.getMod()<1){

            target = GameManager.getInstance().balloonManager.nextEsquina(target);
            this.time = t;

            if(target==null){

                GameManager.getInstance().doDamage( this.vida);
                GameManager.getInstance().balloonManager.removeBalloon(this);
            }
            return;
        }
        Point original = GameManager.getInstance().balloonManager.previousEsquina(target).getPosition();
        Vector vectorVelocidad = vectorALaesquina.normalize().multiply(velocity);
//        //X = x0 + vt
        this.position = original.add(vectorVelocidad.multiply(dT));

    }
    public void getHit(double damage){
        this.vida = this.vida-damage;
        if(this.vida <= 0){
            GameManager.getInstance().balloonManager.removeBalloon(this);
        } else {
            this.actualizar_skin();
        }
    }
    public void actualizar_skin(){
        if(srcBalloons==null){
            srcBalloons = new ArrayList<>();
            srcBalloons.add(new Texture("src/assets/baloon/Balloon.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon2.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon3.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon4.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon5.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon6.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon7.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon8.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon9.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon10.png"));
            srcBalloons.add(new Texture("src/assets/baloon/baloon11.png"));
        }
        this.setSkin(srcBalloons.get((int) vida -1));
    }
}