package entities.balloons;

import entities.Entity;
import graphics.Color;
import graphics.Texture;
import graphics.terrain.Tile;
import managers.BalloonManager;
import managers.GameManager;
import utilities.math.Point;
import utilities.util;
import utilities.math.Vector;

public class Balloon extends Entity {
    Double vida;
    String tipo;
    public Tile target;
    float velocity;
    float time;
    public boolean isACamoBalloon = false;
    public Balloon(int size, Double vida, String tipo, float velocity) {
        super(new Texture("src/assets/Balloon.png"), GameManager.getInstance().getCurrentLevel().getEquinas().get(0).getPosition(), size);
        this.vida = vida;
        this.velocity = velocity;
        //this.previousBallon = previousBallon;
        this.tipo = tipo;
        this.time = (float)GameManager.getInstance().timer.getTime();
        this.target = GameManager.getInstance().getCurrentLevel().getEquinas().get(0);
        actualizar_skin();
    }
    public Balloon(int size, Double vida, String tipo, float velocity,Boolean isACamoBalloon) {
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
        Vector vectorALaesquina = util.createVector(target.getPosition(),this.position);
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
        this.actualizar_skin();
        if(this.vida <= 0){
            GameManager.getInstance().balloonManager.removeBalloon(this);
        }
    }
    public void actualizar_skin(){
        this.setSkin("src/assets/Balloon.png");
    }
}