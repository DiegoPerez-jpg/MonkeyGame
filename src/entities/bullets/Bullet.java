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
    public int piercingShot;
    Vector vectorAlGlobo;
    public boolean explisiveShot;
    public float deleteOnTime ;


    public ArrayList<Balloon> balloonsHiteados = new ArrayList<>();
    public float stateBulletCooldown = 0f; //0f is not state bullet
    public ArrayList<Float> balloonTimeGetHided = new ArrayList<>();


    public int cantidadDispersion=0;
    public Vector vectorDireccion;

    public float rangeRicochet = 32*2f;
    public float amountRicochet = 0f;
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
        this.explisiveShot = bp.explosiveShot;
        this.piercingShot = bp.piercingShot;
        this.deleteOnTime = bp.deleteOnTime;
        this.cantidadDispersion = bp.cantidadDispersion;
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
    public void rebotar(float t){
        if(rangeRicochet==0 || amountRicochet==0){
            return;
        }
        Balloon b = this.getCloserBalloon();
        if(b!=null){
            Bullet bulletLocal = new Bullet(monkey.bp,this.damage , this.position, b,monkey);

            bulletLocal.setAmountRicochet(amountRicochet-1);
            bulletLocal.balloonsHiteados.add(b);
            bulletLocal.balloonTimeGetHided.add(t+100);
            GameManager.getInstance().bulletManager.addBulletDuringRound(bulletLocal);

        }
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
    public void explotar(){
        if(!explisiveShot)return;
        Bullet b = new Bullet(BulletPrefab.EXPLOSION,1,this.position,target,monkey);
        GameManager.getInstance().bulletManager.addBulletDuringRound(b);

    }
    public void deleteOnTIme(float t){
        deleteOnTime -= t*100;
        if (0 > deleteOnTime) GameManager.getInstance().bulletManager.borrarBullets.add(this);

    }
    public void dividir(Vector v,float t, Balloon balloon){
        ArrayList<Vector> vectors= Util.generateNVectores(v,cantidadDispersion);
        if(vectors==null)return;
        for(Vector vector : vectors){
            Bullet b = new Bullet(BulletPrefab.BULLETSTRAIGHT,1,this.position,target,monkey);
            b.vectorDireccion = vector;
            b.balloonsHiteados.add(balloon);
            b.balloonTimeGetHided.add(t+100);
            GameManager.getInstance().bulletManager.addBulletDuringRound(b);
        }
    }
    public boolean avanzar(float t) {
        if(GameManager.getInstance().balloonManager.searchBalloon(target) ==-1){
            if(velocity != 0 && cantidadDispersion != 0){
                return true;
            }
        }
        //diferencia tiempo
        float dT = t - time;
        this.time = t;
        Vector vectorVelocidad;
        if(vectorDireccion == null){

            if(homingShot || vectorAlGlobo == null){
                vectorAlGlobo = createVector(target.position, this.position);
            }
            vectorVelocidad = vectorAlGlobo.normalize().multiply(velocity);
        } else {
            vectorVelocidad = vectorDireccion.normalize().multiply(velocity);
        }

        // x = x0 + vt
        this.position = this.position.add(vectorVelocidad.multiply(dT));
        statebulletFunction(t);
        for (int i = 0; i < GameManager.getInstance().balloonManager.getBalloons().size(); i++) {
            Balloon b = GameManager.getInstance().balloonManager.getBalloons().get(i);
            if(balloonsHiteados.contains(b)){
                continue;
            }
            Vector v = createVector(b.position, this.position);
            if (v.getMod() < (5*size)) {
                piercingShot -= 1;
                b.getHit(damage);
                balloonsHiteados.add(b);
                if(stateBulletCooldown!=0){
                    balloonTimeGetHided.add(t);
                }
                rebotar(t);
                explotar();
                dividir(vectorAlGlobo,t,b);
                deleteOnTIme(dT);
                if (piercingShot <= 0) {
                    return true;
                }
                 // Se elimina la bala
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

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
