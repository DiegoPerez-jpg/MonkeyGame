package entities.bullets;

import entities.Entity;
import entities.balloons.Balloon;
import entities.monkeys.Monkey;
import graphics.Color;
import graphics.Texture;
import managers.GameManager;
import utilities.math.Point;

import utilities.math.Vector;

import static utilities.util.createVector;


public class Bullet extends Entity {
    float velocity;
    float damage;
    float bulletType;
    Balloon target;
    public Monkey monkey;
    public float time;

    public Bullet(Point position, float velocity, int size, float damage, float bulletType, Balloon target) {
        super(new Texture(""), position, size);
        this.velocity = velocity;
        this.damage = damage;
        this.bulletType = bulletType;
        this.target = target;
        this.time = GameManager.getInstance().time;
    }
    public Bullet(BulletPrefab bp, Point position, Balloon target) {
        super(bp.skin, position, bp.size);
        this.velocity = bp.velocity;
        this.damage = bp.damage;
        //this.bulletType = bp.bulletType;
        this.target = target;
        this.time = GameManager.getInstance().time;
    }
    public void avanzar(float t){
        float dT = time-t;
        Vector vectorAlglobo  = createVector(this.position,target.position);
        if(vectorAlglobo.getMod()<1){
            target.getHit((double)damage);
            GameManager.getInstance().bulletManager.removeBullet(this);
            return;
        }
        Vector vectorVelocidad = vectorAlglobo.normalize().multiply(velocity);

        //X = x0 + vt
        this.position = this.monkey.position.add(vectorVelocidad.multiply(dT));
    }
}
