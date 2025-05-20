package graphics;

import utilities.Util;
import utilities.math.Point;

import java.util.ArrayList;

public class UI {
    public Color background, borderColor;
    public ArrayList<Point> corners;
    public int borderSize, padding, margin;
    public ArrayList<UI> layouts;
    private boolean verticalAlign, alignCenter, support;
    private float space; //variable entre 0-1 que representa el espacio ocupado la UI
    public UI(Color color, ArrayList<Point> corners, boolean vertical){ //Puntos en sentido antihorario empezando arriba a la izq
        this.background = color;
        this.corners = corners;
        this.layouts = new ArrayList<>();
        this.borderSize = 0;
        this.padding = 0;
        this.margin = 0;
        this.space = 0;
        this.verticalAlign = vertical;
        this.support = false;
    }
    public UI(Color color, ArrayList<Point> corners, boolean vertical, int borderSize, Color borderColor){
        this(color, corners, vertical);
        this.borderSize = borderSize;
        this.borderColor = borderColor;
    }
    public UI(Color color, ArrayList<Point> corners, boolean vertical, int margin, int padding){
        this(color, corners, vertical);
        this.margin = margin;
        this.padding = padding;
    }
    public UI(Color color, ArrayList<Point> corners, boolean vertical, int margin, int padding, int borderSize, Color borderColor){
        this(color, corners, vertical, borderSize, borderColor);
        this.margin = margin;
        this.padding = padding;
    }

    public Point toGlobal(Point point){ //Pasa las coordenadas locales de la UI (respecto esquina inf izq) a globales
        return new Point(point.x + corners.get(1).x, point.y + corners.get(1).y);
    }

    public Point toGlobal(float x, float y){return toGlobal(new Point(x, y));}

    public void setVerticalAlign(boolean vertical){
        if (space == 0) this.verticalAlign = vertical;
        else System.out.println("Para modificar la horientación la UI debe estar vacia");
    }

    public void setJustifyContent(boolean value){
        if (space == 0) this.alignCenter = value;
        else System.out.println("Para modificar alignCenter la UI debe estar vacia");
    }

    public void setSupport(boolean value){this.support = value;}

    public UI addLayout(float appendSpace, Color color, int borderSize, Color borderColor, int childPadding){
        if (space+appendSpace > 1){
            System.out.println("No cabe en el layout");
            return null;
        }
        UI ui = null;
        int fatherWidth = Math.round(Util.createVector(corners.get(0), corners.get(3)).getMod());
        int fatherHeight = Math.round(Util.createVector(corners.get(0), corners.get(1)).getMod());
        if (verticalAlign){
            int start_y = Math.round(fatherHeight*(1-space)) - margin;
            int end_y = Math.round(fatherHeight*(1-appendSpace-space)) + margin;
            ui = new UI(color, new ArrayList<>() {{
                add(toGlobal(margin, end_y));
                add(toGlobal(margin, start_y));
                add(toGlobal(fatherWidth-margin, start_y));
                add(toGlobal(fatherWidth-margin, end_y));
            }}, true, padding, childPadding, borderSize, borderColor);
        }
        else{
            int start_x = Math.round(fatherWidth*space) + margin;
            int end_x = Math.round(fatherWidth*(appendSpace+space)) - margin;
            ui = new UI(color, new ArrayList<>() {{
                add(toGlobal(start_x, fatherHeight-margin));
                add(toGlobal(start_x, margin));
                add(toGlobal(end_x, margin));
                add(toGlobal(end_x, fatherHeight-margin));
            }}, false, padding, childPadding, borderSize, borderColor);
        }
        this.space += appendSpace;
        layouts.add(ui);
        return ui;
    }
    public UI addLayout(float appendSpace, Color color){
        return addLayout(appendSpace, color, 0, Color.BLACK, 0);
    }
    public UI addSupport(float appendSpace){
        UI ui = addLayout(appendSpace, Color.BLACK);
        ui.setSupport(true);
        return ui;
    }

    public void draw(){
        if (!support) {
            if (borderSize == 0) Draw.drawPoly(corners, background);
            else Draw.drawBorderPoly(corners, background, borderSize, borderColor);
        }
    }

    public void drawRecursively(){
        this.draw();
        for (UI div : layouts){div.drawRecursively();}
    }
}
