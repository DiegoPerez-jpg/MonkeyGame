package entities.bullets;

import graphics.Texture;

public enum BulletPrefab {
    BULLETTRIBE(1, 400, (new Texture("src/assets/dardo.png")),1,0.25f,false,5),
    EXPLOSION(100,0,(new Texture("src/assets/dardo.png")),1000000,20f,false,0),
    BULLETSTRAIGHT(1,400,(new Texture("src/assets/dardo.png")),1,20f,false,0),
    BULLETCLAVO(10,0,(new Texture("src/assets/dardo.png")),1,50f,false,0);
    public final int size, velocity, piercingShot,cantidadDispersion;
    public final Texture skin;
    public final float deleteOnTime;
    public final boolean explosiveShot;

    BulletPrefab(int size, int velocity, Texture skin, int piercingShot,float deleteOnTime,boolean explosiveShot, int cantidadDispersion) {
        this.explosiveShot = explosiveShot;
        this.size = size;
        this.velocity = velocity;
        this.skin = skin;
        this.piercingShot = piercingShot;
        this.deleteOnTime = deleteOnTime;
        this.cantidadDispersion = cantidadDispersion;
    }
}