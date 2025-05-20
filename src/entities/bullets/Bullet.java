package entities.bullets;

import entities.Entity;
import entities.balloons.Balloon;
import entities.monkeys.Monkey;
import graphics.Texture;
import managers.GameManager;
import utilities.math.Point;

import utilities.math.Vector;
import utilities.Util;

import java.util.ArrayList;

import static utilities.Util.createVector;


public class Bullet extends Entity {
    float velocity;
    float damage;
    float bulletType;
    Balloon target;
    public Monkey monkey;
    public float time;
    public boolean homingShot = true;
    public int piercingShot = 1;
    Vector vectorAlGlobo;
    public ArrayList<Balloon> balloonsHiteados = new ArrayList<>();
    public float stateBulletCooldown = 0f; //0f is not state bullet
    public ArrayList<Float> balloonTimeGetHided = new ArrayList<>();

    public float rangeRicochet = 32*2f;
    public float amountRicochet = 2f;
    public Bullet(Point position, float velocity, int size, float damage, float bulletType, Balloon target,Monkey monkey) {
        super(new Texture(""), position, size);
        this.monkey = monkey;
        this.velocity = velocity;
        this.damage = damage;
        this.bulletType = bulletType;
        this.target = target;
        this.time = (float)GameManager.getInstance().timer.getTime();
    }
    public Bullet(BulletPrefab bp, float damage, Point position, Balloon target,Monkey monkey) {
        super(bp.skin, position, bp.size);
        this.velocity = bp.velocity;
        this.monkey = monkey;
        this.damage = damage;
        //this.bulletType = bp.bulletType;
        this.target = target;
        this.time = (float)GameManager.getInstance().timer.getTime();
    }

    protected Balloon getCloserBalloon(){
        for(Balloon b : GameManager.getInstance().balloonManager.getBalloons()) {
            if(b==target){
                continue;
            }
            Vector distance = Util.createVector(b.position, this.position);
            if (distance.getMod() < this.rangeRicochet) {
                return b;
            }
        }
        return null;
    }
    public Bullet rebotar(){
        if(rangeRicochet==0 || amountRicochet==0){
            return null;
        }
        Balloon b = this.getCloserBalloon();
        if(b!=null){
            System.out.println();
            return new Bullet(monkey.bp,this.damage , this.position, b,monkey);
        }
        return null;
    }
    public void statebulletFunction(float t) {
        if (stateBulletCooldown == 0) return;
        for (int i = 0; i < balloonsHiteados.size(); i++) {
            if (t >= balloonTimeGetHided.get(i) + stateBulletCooldown) {
                balloonsHiteados.remove(i);
                balloonTimeGetHided.remove(i);
                i--; // Ajustar índice tras eliminar
            }
        }
    }
    public boolean avanzar(float t) {
        if(GameManager.getInstance().balloonManager.searchBalloon(target) ==-1){
            return true;
        }
        //diferencia tiempo
        float dT = t - time;
        this.time = t;
        if(homingShot || vectorAlGlobo == null){
            vectorAlGlobo = createVector(target.position, this.position);
        }
        Vector vectorVelocidad = vectorAlGlobo.normalize().multiply(velocity);

        // x = x0 + vt
        this.position = this.position.add(vectorVelocidad.multiply(dT));
        statebulletFunction(t);
        for (int i = 0; i < GameManager.getInstance().balloonManager.getBalloons().size(); i++) {
            Balloon b = GameManager.getInstance().balloonManager.getBalloons().get(i);
            if(balloonsHiteados.contains(b)){
                continue;
            }
            Vector v = createVector(b.position, this.position);
            if (v.getMod() < 5) {
                piercingShot -= 1;
                b.getHit(damage);
                balloonsHiteados.add(b);
                if(stateBulletCooldown!=0){
                    balloonTimeGetHided.add(t);
                }
                Bullet bulletLocal = rebotar();
                if(bulletLocal != null) {
                    bulletLocal.setAmountRicochet(amountRicochet-1);
                    bulletLocal.balloonsHiteados.add(b);
                    bulletLocal.balloonTimeGetHided.add(t+100);
                    GameManager.getInstance().bulletManager.addBulletDuringRound(bulletLocal);
                }
                if (piercingShot <= 0) {
                    return true;
                }
                return false; // Se elimina la bala
            }
        }




        return false;
    }

    public void setAmountRicochet(float amountRicochet) {
        this.amountRicochet = amountRicochet;
    }

    public float getAmountRicochet() {
        return amountRicochet;
    }
}
