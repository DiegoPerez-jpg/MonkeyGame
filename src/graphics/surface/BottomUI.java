package graphics.surface;

import graphics.Color;
import utilities.math.Point;

import java.util.ArrayList;

public class BottomUI extends UI{
    ArrayList<UI> layouts;
    public int padding, margin;
    public BottomUI(Color background, ArrayList<Point> corners){
        super(background, corners);
        this.layouts = new ArrayList<>();
        this.padding = 4;
        this.margin = 10;
        UI s1 = new UI(Color.BLUEUI, new ArrayList<>() {{

        }}, 2, Color.BLACK);

        UI s2 = new UI(Color.BLUEUI2, new ArrayList<>() {{

        }}, 2, Color.BLACK);
    }
}
