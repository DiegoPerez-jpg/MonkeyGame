package entities.monkeys;

import entities.Entity;
import entities.balloons.Balloon;
import entities.bullets.Bullet;
import graphics.Color;
import managers.GameManager;
import utilities.math.Point;

public class Monkey extends Entity {
    Color skin;
    String nombre;
    float range;
    float cost;
    float rate;
    BulletPrefab bulletPrefab;
    GameManager gm;
    float lastShotTime;

    public Monkey(int size, BulletPrefab bala, float rate, float cost, float range, String nombre, Color skin, Point position) {
        super(skin,position,size);
        this.bala = bala;
        this.rate = rate;
        this.cost = cost;
        this.range = range;
        this.nombre = nombre;
    }
    private Balloon getCloserBalloon(){
        for(Balloon b : gm.balloonManager.getBalloons()) {
            Vector distance = CreateVector(b.position, this.position);
            if (distance.getMod() < this.range) {
                return b;
            }

        }
    }
    public Bullet disparar(float time){
        if(time-lastShotTime>rate){
            Balloon b = this.getCloserBalloon();
            if(b!=null){
                lastShotTime = time;
                return new Bullet(bulletPrefab,this.position,b);
            }

        }
        return null;
    }
}
