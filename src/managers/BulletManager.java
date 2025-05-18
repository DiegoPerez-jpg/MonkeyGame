package managers;

import entities.balloons.Balloon;
import entities.bullets.Bullet;

import java.util.ArrayList;

public class BulletManager {
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public BulletManager() {
        bullets = new ArrayList<>();
    }
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public void updateBullets(float t) {
        for (Bullet b : bullets) {
            b.avanzar(t);
        }
    }
}
