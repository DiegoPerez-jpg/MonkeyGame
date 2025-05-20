package entities.bullets;

import graphics.Color;
import graphics.Texture;

public enum BulletPrefab {
    BULLETTRIBE(1, 400, (new Texture("src/assets/dardo.png")));

    public final int size, velocity;
    public final Texture skin;

    BulletPrefab(int size, int velocity, Texture skin) {
        this.size = size;
        this.velocity = velocity;
        this.skin = skin;
    }
}