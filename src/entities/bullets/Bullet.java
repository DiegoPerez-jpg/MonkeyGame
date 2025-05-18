package entities.bullets;

import entities.Entity;
import entities.balloons.Balloon;
import graphics.Color;
import utilities.math.Point;


public class Bullet extends Entity {
    float velocity;
    float damage;
    float bulletType;
    Balloon target;

    public Bullet(Point position, float velocity, Color skin, int size, float damage, float bulletType, Balloon target) {
        super(skin,position,size);
        this.velocity = velocity;
        this.damage = damage;
        this.bulletType = bulletType;
        this.target = target;
    }
    public Bullet(BulletPrefab bt, Point position, Balloon target) {
        super(bt.skin,position,bt.size);
        this.velocity = bt.velocity;
        this.damage = bt.damage;
        //this.bulletType = bt.bulletType;
        this.target = target;
    }
    public void avanzar(float t){

    }
}
