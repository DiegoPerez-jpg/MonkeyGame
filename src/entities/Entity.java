package entities;

import graphics.Color;
import utilities.math.Point;

public class Entity {
    Color Skin;
    Point position;
    int size;
    public Entity(Color Skin, Point position,int size) {
        this.Skin = Skin;
        this.position = position;
        this.size = size;
    }
}
