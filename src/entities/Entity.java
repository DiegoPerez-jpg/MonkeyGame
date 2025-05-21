package entities;

import entities.monkeys.Monkey;
import graphics.Color;
import graphics.Texture;
import managers.GameManager;
import utilities.math.Point;

public abstract class Entity {
    public Texture skin;
    public Point position;
    public int size;
    public Entity(Texture skin, Point position,int size) {
        this.skin = skin;
        this.position = position;
        this.size = size;
    }

    public void setSkin(Texture skin) {
        this.skin = skin;
        System.out.println("xd");
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size * GameManager.getInstance().tileSize;
    }
}
