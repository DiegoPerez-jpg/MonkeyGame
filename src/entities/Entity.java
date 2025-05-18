package entities;

import graphics.Color;
import utilities.math.Point;

public class Entity {
    public Color skin;
    public Point position;
    public int size;
    public Entity(Color skin, Point position,int size) {
        this.skin = skin;
        this.position = position;
        this.size = size;
    }
}
