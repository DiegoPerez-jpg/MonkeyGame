package entities.bullets;

import graphics.Color;

public enum BulletPrefab {
    BULLET(1, 1, 1, Color.RED);

    public final int size, damage, velocity;
    public final Color skin;

    BulletPrefab(int size, int damage, int velocity, Color skin) {
        this.size = size;
        this.damage = damage;
        this.velocity = velocity;
        this.skin = skin;
    }
}