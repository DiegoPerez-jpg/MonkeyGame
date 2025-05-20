package managers;

import entities.balloons.Balloon;
import entities.bullets.Bullet;

import java.util.ArrayList;

public class BulletManager {
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> addNextRoundBullets = new ArrayList<>();
    public ArrayList<Bullet> borrarBullets = new ArrayList<>();
    public BulletManager() {
        bullets = new ArrayList<>();
    }
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public void addBulletDuringRound(Bullet bullet) {
        addNextRoundBullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public void updateBullets() {
        float t = (float)GameManager.getInstance().timer.getTime();
        borrarBullets.clear();
        for (Bullet b : bullets) {
            if(b.avanzar(t)){
                borrarBullets.add(b);
            }
        }
        this.bullets.addAll(addNextRoundBullets);
        this.addNextRoundBullets.clear();
        this.bullets.removeAll(borrarBullets);
    }
}
