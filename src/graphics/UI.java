package graphics;

import managers.GameManager;
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
    public boolean trigger; //variable activada cuando ocurre un evento con el layout
    public boolean selected;
    public Texture texture, texture2;
    public UI(Color color, ArrayList<Point> corners, boolean vertical){ //Puntos en sentido antihorario empezando arriba a la izq
        this.background = color;
        this.corners = corners;
        this.layouts = new ArrayList<>();
        this.borderSize = 0;
        this.borderColor = Color.BLACK;
        this.padding = 0;
        this.margin = 0;
        this.space = 0;
        this.verticalAlign = vertical;
        this.support = false;
        this.trigger = false;
        this.selected = false;
        this.texture = null;
        this.texture2 = null;
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
    /*
    * Transforma un punto de coordenadas locales dentro del layout
    * a coordenadas globales
     */
    public Point toGlobal(Point point){ //Pasa las coordenadas locales de la UI (respecto esquina inf izq) a globales
        return new Point(point.x + corners.get(1).x, point.y + corners.get(1).y);
    }
    /*
     * Transforma un punto de coordenadas locales dentro del layout
     * a coordenadas globales
     */
    public Point toGlobal(float x, float y){return toGlobal(new Point(x, y));}

    public void setVerticalAlign(boolean vertical) throws UINeedsToBeEmptyEx{
        if (space == 0) this.verticalAlign = vertical;
        else throw new UINeedsToBeEmptyEx();
    }

    public void setJustifyContent(boolean value) throws UINeedsToBeEmptyEx{
        if (space == 0) this.alignCenter = value;
        else throw new UINeedsToBeEmptyEx();
    }

    /*
    * Establece un layout como soporte (será invisible
    *  y solo servirá de apoyo a otros layouts)
    * */
    public void setSupport(boolean value){this.support = value;}

    /*
     * Añade un layout dentro de la interfaz
     *  @param appendSapce Valor entre 0-1 que determina la proporción que aumentará en el layout
     *  @param color Color del layout
     *  @param borderSize grosor del borde
     *  @param borderColor color del borde
     *  @param childPadding margen de los layouts hijos con el borde del padre
     * */
    public UI addLayout(float appendSpace, Color color, int borderSize, Color borderColor, int childPadding) throws OutOfBoundEx{
        if (space+appendSpace > 1) throw new OutOfBoundEx();
        UI ui = null;
        int fatherWidth = Math.round(Util.createVector(corners.get(0), corners.get(3)).getMod());
        int fatherHeight = Math.round(Util.createVector(corners.get(0), corners.get(1)).getMod());
        if (verticalAlign){
            int start_y = Math.round(fatherHeight*(1-space)) - margin;
            int end_y = Math.round(fatherHeight*(1-appendSpace-space)) + margin;
            ui = new UI(color, new ArrayList<>() {{
                add(toGlobal(margin, start_y));
                add(toGlobal(margin, end_y));
                add(toGlobal(fatherWidth-margin, end_y));
                add(toGlobal(fatherWidth-margin, start_y));
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
        GameManager.getInstance().uiManager.divs.add(ui);
        return ui;
    }
    /*
     * Añade un layout dentro de la interfaz
     *  @param appendSapce Valor entre 0-1 que determina la proporción que aumentará en el layout
     *  @param color Color del layout
     * */
    public UI addLayout(float appendSpace, Color color){
        return addLayout(appendSpace, color, 0, Color.BLACK, 0);
    }

    /*
     * Añade un layout vacio dentro de la interfaz para usar de soporte
     *  @param appendSapce Valor entre 0-1 que determina la proporción que aumentará en el layout
     * */
    public UI addSupport(float appendSpace){
        UI ui = addLayout(appendSpace, Color.BLACK);
        ui.setSupport(true);
        return ui;
    }
    /*Dibuja/renderiza el layout*/
    public void draw(){
        if (!support) {
            if (texture == null) {
                if (borderSize == 0) Draw.drawPoly(corners, background);
                else Draw.drawBorderPoly(corners, background, borderSize, borderColor);
            }
            else {
                texture.render(corners.get(1));
                if (borderSize != 0) {
                    Draw.drawBorderPoly(corners, Color.TRANSPARENT, borderSize, borderColor);
                }
            }
        }
    }

    /*Dibuja/renderiza el layout y sus descendientes*/
    public void drawRecursively(){
        this.draw();
        for (UI div : layouts){div.drawRecursively();}
    }

    /*Devuelve true/false dependiendo de si un punto es contenido o no en una interfaz*/
    public boolean contain(Point p){
        if (p.x >= corners.get(0).x && p.x <= corners.get(3).x
                && p.y >= corners.get(1).y && p.y <= corners.get(3).y) {
            return true;
        }
        return false;
    }

    public void select(int thickness){
        this.borderSize = thickness;
        this.selected = true;
    }

    public void setTexture(Texture texture){this.texture = texture;}
    public void setTexture2(Texture texture){this.texture2 = texture;}
}
