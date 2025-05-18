package entities.balloons;

import entities.Entity;
import graphics.Color;
import managers.GameManager;
import utilities.math.Point;

import java.util.Vector;

public class Balloon extends Entity {
    Double vida;
    Balloon previousBallon;
    String tipo;
    BalloonManager bm;
    Tile target;
    float time;
    public Balloon(Color skin, Point position, int size, Double vida, Balloon previousBallon, String tipo) {
        super(skin, position, size);
        this.vida = vida;
        this.previousBallon = previousBallon;
        this.tipo = tipo;
        this.time = GameManager.getInstance().time;
    }
    public Double getAllDamage(){
        return vida + previousBallon.getAllDamage();
    }
    public void avanzar(float t){
        float dT = time-t;
        Vector vectorALaesquina = CreateVector(this.position,target.posCentrada);

        if(vectorALaesquina.getMod()<1){

            target = GameManager.getInstance().tileManager.nextEsquina(target);
            if(target!=null){

                GameManager.doDamage(this.getAllDamage());
            }
            GameManager.getInstance().balloonManager.removeBalloon(this);
            return;
        }
        Vector vectorVelocidad = vectorALaesquina.normalize().multiply(velocity);

        //X = x0 + vt
        this.position = this.monkey.position.add(vectorVelocidad.multiply(dT));
    }
    public void getHit(Double damage){
        this.vida = this.vida-damage;
        if(this.vida <= 0){
            if(this.previousBallon != null){
                unTransformBalloon();
            }
            bm.removeBalloon(this);
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
