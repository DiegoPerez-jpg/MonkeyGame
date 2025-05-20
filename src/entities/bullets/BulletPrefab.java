package entities.bullets;

import graphics.Color;
import graphics.Texture;

public enum BulletPrefab {
    BULLETTRIBE(1, 400, (new Texture("src/assets/dardo.png")),1,0.25f,true),
    EXPLOSION(100,0,(new Texture("src/assets/dardo.png")),1000000,20f,false);
    public final int size, velocity, piercingShot;
    public final Texture skin;
    public final float deleteOnTime;
    public final boolean explosiveShot;

    BulletPrefab(int size, int velocity, Texture skin, int piercingShot,float deleteOnTime,boolean explosiveShot) {
        this.explosiveShot = explosiveShot;
        this.size = size;
        this.velocity = velocity;
        this.skin = skin;
        this.piercingShot = piercingShot;
        this.deleteOnTime = deleteOnTime;
    }
}