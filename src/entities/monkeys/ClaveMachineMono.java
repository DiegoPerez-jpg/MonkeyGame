package entities.monkeys;

import entities.balloons.Balloon;
import entities.bullets.Bullet;
import entities.bullets.BulletPrefab;
import graphics.Color;
import graphics.Tile;
import managers.GameManager;
import utilities.Util;
import utilities.math.Vector;

import java.util.ArrayList;

public class ClaveMachineMono extends Monkey implements UpgradableMonkey {



    ArrayList<Bullet> clavosSpawneados = new ArrayList<>();
    int maxClavosSpawneados = 20;
    int piercingShotDamage = 0;
    int damage = 0;
    boolean balaExplosiva = false;
    int rangeBalaExplosiva = 0;
    public ClaveMachineMono(Tile tile) {
        super(1,1, BulletPrefab.BULLETCLAVO,0.1f,100,2,"Maquina de Clavos","src/assets/creadora de clavos.png",tile);
    }

    public Tile buscarTileCercana(){
        for(Tile b : GameManager.getInstance().tileManager.tiles){
            if(b.background == Color.ROAD){
                Vector distancia = Util.createVector(position,b.getPosition());
                if(range>distancia.getMod()){
                    return b;
                }
            };

        }
        return null;
    }


    public void setupClavosSpawneados() {
        ArrayList<Bullet> bullets= GameManager.getInstance().bulletManager.getBullets();
        ArrayList<Bullet> bulletDelete= new ArrayList<>();
        for(Bullet b : clavosSpawneados){
            if(!(bullets.contains(b))){
                bulletDelete.add(b);
            }
        }
        clavosSpawneados.removeAll(bulletDelete);
    }
    @Override
    public Bullet disparar(float time){
        this.setupClavosSpawneados();
        if(clavosSpawneados.size()>maxClavosSpawneados)return null;
        if(time-lastShotTime>rate || lastShotTime==0){
            for (int i = 0; i < cantidadBalasDisparadas; i++) {
                lastShotTime = time;
                Tile t = buscarTileCercana();
                if(t==null) return null;
                Bullet b = new Bullet(bp,1 , buscarTileCercana().getPosition(), new Balloon(1,1.0,"si",1),this);
                b.piercingShot += this.piercingShotDamage;
                b.explisiveShot = balaExplosiva;
                b.setDamage(b.getDamage()+damage);
                b.size+=rangeBalaExplosiva;
                clavosSpawneados.add(b);
                return b;

            }
        }
        return null;
    }
    @Override
    public void upgradeFirst(){
        if(this.mejoras[0]==5)return;
        setRate(this.rate-0.12f);
        this.mejoras[0] = this.mejoras[0]+1;
    }
    @Override
    public void upgradeSecond(){
        if(this.mejoras[1]==5)return;
        this.piercingShotDamage+=1;
        this.mejoras[1] = this.mejoras[1]+1;
    }
    @Override
    public void upgradeThird(){
        if(this.mejoras[2]==5)return;
        this.mejoras[3] = this.mejoras[3]+1;
        switch (this.mejoras[3]){
            case 1:
               damage++;
               break;
            case 2:
                damage++;
                break;
            case 3:
                balaExplosiva = true;
                break;
            case 4:
                rangeBalaExplosiva++;
                break;
            case 5:
                rangeBalaExplosiva++;
                break;
        }
    }



}
