package entities.monkeys;

import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.bullets.BulletPrefab;
import graphics.Tile;

public class MonoCanon extends Monkey implements UpgradableMonkey{
    private int cantidadDispersion;
    private boolean explosiveShot;
    private int rangeExplosiveShot;
    private int damage;

    public MonoCanon(Tile tile){
        super(1, BulletPrefab.BULLETCANON,1f,500,5,"Mono cañon","src/assets/monkeys/MonoCanon.png",tile);
    }

    @Override
    public Bullet disparar(float time){
        if(time-lastShotTime>rate || lastShotTime==0){
            for (int i = 0; i < cantidadBalasDisparadas; i++) {
                Balloon b = this.getCloserBalloon();
                if(b!=null){
                    lastShotTime = time;
                    Bullet bullet = new Bullet(bp,1 , this.position, b,this);
                    bullet.cantidadDispersion = cantidadDispersion;
                    bullet.explisiveShot = explosiveShot;
                    bullet.size+=rangeExplosiveShot;
                    bullet.setDamage(bullet.getDamage()+damage);
                    return bullet;
                }
            }
        }
        return null;
    }

    public void upgradeFirst(){
        if(this.mejoras[0]==5)return;
        this.mejoras[0] = this.mejoras[0]+1;
        switch (this.mejoras[0]){
            case 1:
                this.explosiveShot = true;
                break;
            case 2:
                this.rangeExplosiveShot++;
                break;
            case 3:
                this.rangeExplosiveShot++;
                break;
            case 4:
                this.rangeExplosiveShot++;
                break;
            case 5:
                this.rangeExplosiveShot++;
                break;
        }


    }
    public void upgradeSecond(){
        if(this.mejoras[1]==5)return;
        this.mejoras[1] = this.mejoras[1]+1;
        if(this.cantidadDispersion==0)cantidadDispersion=2;
        this.cantidadDispersion++;
    }
    public void upgradeThird(){
        if(this.mejoras[2]==5)return;
        this.mejoras[2] = this.mejoras[2]+1;
        this.damage++;
    }

}
