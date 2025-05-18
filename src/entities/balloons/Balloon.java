package entities.balloons;

import entities.Entity;
import graphics.Color;
import utilities.math.Point;

public class Balloon extends Entity {
    Double vida;
    Balloon previousBallon;
    String tipo;

    public Balloon(Color Skin, Point position, int size, Double vida, Balloon previousBallon, String tipo) {
        super(Skin, position, size);
        this.vida = vida;
        this.previousBallon = previousBallon;
        this.tipo = tipo;
    }
}
