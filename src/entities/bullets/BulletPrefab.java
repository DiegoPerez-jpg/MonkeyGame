package entities.bullets;

import graphics.Color;
import graphics.Texture;

public enum BulletPrefab {
    BULLET(1, 1, 1, (new Texture("")));

    public final int size, damage, velocity;
    public final Texture skin;

    BulletPrefab(int size, int damage, int velocity, Texture skin) {
        this.size = size;
        this.damage = damage;
        this.velocity = velocity;
        this.skin = skin;
    }
}