package entities.balloons;

import entities.Entity;
import graphics.Color;
import managers.BalloonManager;
import managers.GameManager;
import utilities.math.Point;

public class Balloon extends Entity {
    Double vida;
    Balloon previousBalloon;
    String tipo;
    BalloonManager bm;
    public Balloon(Color skin, Point position, int size, Double vida, Balloon previousBalloon, String tipo) {
        super(skin, position, size);
        this.vida = vida;
        this.previousBalloon = previousBalloon;
        this.tipo = tipo;
        this.bm = GameManager.getInstance().balloonManager;
    }
    public void avanzar(float t){

    }
    public void getHit(Double damage){
        this.vida = this.vida-damage;
        if(this.vida <= 0){
            if(this.previousBalloon != null){
                unTransformBalloon();
            }
            bm.removeBalloon(this);
        }
    }

    private void unTransformBalloon(){
        this.vida = this.previousBalloon.vida;
        this.tipo = this.previousBalloon.tipo;
        super.skin = this.previousBalloon.skin;
        super.size = this.previousBalloon.size;
        this.previousBalloon = this.previousBalloon.previousBalloon;
    }
}
