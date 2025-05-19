package graphics.surface;

import graphics.Color;
import utilities.math.Point;

import java.util.ArrayList;

public class AsideUI extends UI{
    public ArrayList<UI> layouts;
    public int padding, margin;
    public AsideUI(Color background, ArrayList<Point> corners){
        super(background, corners);
        this.layouts = new ArrayList<>();
        this.padding = 4;
        this.margin = 10;


    }
}
