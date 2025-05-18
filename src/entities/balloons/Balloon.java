package entities.balloons;

import entities.Entity;
import graphics.Color;
import managers.GameManager;
import utilities.math.Point;

public class Balloon extends Entity {
    Double vida;
    Balloon previousBallon;
    String tipo;
    BalloonManager bm;
    public Balloon(Color skin, Point position, int size, Double vida, Balloon previousBallon, String tipo) {
        super(skin, position, size);
        this.vida = vida;
        this.previousBallon = previousBallon;
        this.tipo = tipo;
    }
    public void avanzar(float t){

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
