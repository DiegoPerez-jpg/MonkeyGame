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

    public Bullet(Point position, float velocity, int size, float damage, float bulletType, Balloon target,Monkey monkey) {
        super(new Texture(""), position, size);
        this.monkey = monkey;
        this.velocity = velocity;
        this.damage = damage;
        this.bulletType = bulletType;
        this.target = target;
        this.time = (float)GameManager.getInstance().timer.getTime();
    }
    public Bullet(BulletPrefab bp, Point position, Balloon target,Monkey monkey) {
        super(bp.skin, position, bp.size);
        this.velocity = bp.velocity;
        this.monkey = monkey;
        this.damage = bp.damage;
        //this.bulletType = bp.bulletType;
        this.target = target;
        this.time = (float)GameManager.getInstance().timer.getTime();
    }
    public boolean avanzar(float t){
        float dT = t-time;
        Vector vectorAlglobo  = createVector(target.position,this.position);
        if(vectorAlglobo.getMod()<1){
            target.getHit(damage);
            return true;
        }
        Vector vectorVelocidad = vectorAlglobo.normalize().multiply(velocity);

        //X = x0 + vt
        this.position = this.monkey.position.add(vectorVelocidad.multiply(dT));
        return false;
    }
}
