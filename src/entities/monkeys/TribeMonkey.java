package entities.monkeys;

import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.bullets.BulletPrefab;
import graphics.terrain.Tile;
import managers.GameManager;
import managers.MonkeyManager;

public class TribeMonkey extends Monkey implements UpgradableMonkey{
    boolean recibo= false;
    boolean aportoDamage= false;
    boolean aportoRate= false;
    float rateAportado = 0;
    float damageAportado = 0;
    double tasaAportado =1;
    MonkeyManager mm;
    public TribeMonkey(Tile tile) {
        super(1, BulletPrefab.BULLETTRIBE,1,200,7,"Mono de tribu","src/assets/monkeys/monkeyDarderolvl1.png",tile);
        mm = GameManager.getInstance().monkeyManager;
    }
    public void upgradeFirst(){
        if(this.mejoras[0]==5)return;
        this.mejoras[0] = this.mejoras[0]+1;
        if(this.mejoras[0]==1){

            recibo = true;}
        if(this.mejoras[0]==2){
            aportoDamage = true;
            this.mm.dañoTribu += damageAportado ;
        }
        if(this.mejoras[0]==3){
            aportoRate = true;
            this.mm.dañoTribu += rateAportado;
        }
        if(this.mejoras[0]==5){
            tasaAportado = 2;
            this.mm.dañoTribu += damageAportado;
            this.mm.dañoTribu += rateAportado;
        }


    }
    public void upgradeSecond(){
        if(this.mejoras[1]==5)return;
        this.mejoras[1] = this.mejoras[1]+1;
        this.rateAportado++;
        this.setRate(this.rate+1);
        if(aportoRate){
            mm.rateTribu += 1 * tasaAportado;
        }
    }
    public void upgradeThird(){
        if(this.mejoras[2]==5)return;
        this.mejoras[2] = this.mejoras[2]+1;
        this.damageAportado++;
        if(aportoDamage){
            mm.dañoTribu += 1 * tasaAportado;
        }
    }

    @Override
    public Bullet disparar(float time){
        float rateLocal= this.rate;
        float damageLocal= this.damageAportado+1;
        if(recibo){
            rateLocal += mm.rateTribu;
            damageLocal += mm.dañoTribu;
        }
        if(time-lastShotTime>rateLocal|| lastShotTime==0){
            Balloon b = this.getCloserBalloon();
            if(b!=null){
                lastShotTime = time;
                return new Bullet(bp,damageLocal, this.position, b,this);
            }
        }
        return null;
    }
}
