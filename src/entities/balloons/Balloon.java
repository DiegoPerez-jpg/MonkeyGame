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
    Balloon previousBallon;
    String tipo;
    public Tile target;
    float velocity;
    float time;
    public Balloon(Point position, int size, Double vida, String tipo, float velocity) {
        super(new Texture("src/assets/Balloon.png"), position, size);
        this.vida = vida;
        this.velocity = velocity;
        //this.previousBallon = previousBallon;
        this.tipo = tipo;
        this.time = (float)GameManager.getInstance().timer.getTime();
        this.target = GameManager.getInstance().levelManager.getEquinas().get(0);
    }
    public Double getAllDamage(){
        return vida + previousBallon.getAllDamage();
    }
    public void avanzar(float t){
        float dT = t-time;
        Vector vectorALaesquina = util.createVector(target.getPosition(),this.position);
        if(vectorALaesquina.getMod()<1){

            target = GameManager.getInstance().levelManager.nextEsquina(target);
            this.time = t;

            if(target==null){

                GameManager.getInstance().doDamage( this.getAllDamage());
                GameManager.getInstance().balloonManager.removeBalloon(this);
            }
            return;
        }
        Point original = GameManager.getInstance().levelManager.previousEsquina(target).getPosition();
        Vector vectorVelocidad = vectorALaesquina.normalize().multiply(velocity);
//        //X = x0 + vt
        this.position = original.add(vectorVelocidad.multiply(dT));

    }
    public void getHit(double damage){
        this.vida = this.vida-damage;
        if(this.vida <= 0){
            if(this.previousBallon != null){
                unTransformBalloon();
            }
            GameManager.getInstance().balloonManager.removeBalloon(this);
        }
    }
    private void unTransformBalloon(){
        this.vida = this.previousBallon.vida;
        this.tipo = this.previousBallon.tipo;
        super.skin = this.previousBallon.skin;
        super.size = this.previousBallon.size;
        this.previousBallon = this.previousBallon.previousBallon;
    }
}