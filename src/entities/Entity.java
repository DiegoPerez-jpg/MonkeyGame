package entities;

import entities.monkeys.Monkey;
import graphics.Color;
import graphics.Texture;
import utilities.math.Point;

public class Entity {
    public Texture skin;
    public Point position;
    public int size;
    public Entity(Texture skin, Point position,int size) {
        this.skin = skin;
        this.position = position;
        this.size = size;
    }

    public void setSkin(String skin) {
        this.skin = new Texture(skin);
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
