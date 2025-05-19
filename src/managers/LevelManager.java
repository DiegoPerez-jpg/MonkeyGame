package managers;

import graphics.terrain.Tile;

import java.io.File;
import java.util.ArrayList;
import levels.Level;

import org.w3c.dom.*;
import utilities.math.Point;

import javax.xml.parsers.*;
import java.io.File;

public class LevelManager {
    ArrayList<Level> levels;

    public LevelManager() {
        ArrayList<Tile> esquinas;
        this.levels = new ArrayList<>();
        try {
            File archivo = new File("src/levels/levels.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //Obj que crea el doc builder
            DocumentBuilder builder = dbf.newDocumentBuilder(); //Herramienta que parsea el xml a un formato legible para java
            Document doc = builder.parse(archivo); //Parseo a formato legible para java

            doc.getDocumentElement().normalize(); //Limpia el arbol eliminando nodos vacios y espacios en blanco

            NodeList ndLevels = doc.getElementsByTagName("level"); //Recibe cada nivel como una lista de nodos

            for (int i = 0; i < ndLevels.getLength(); i++) { //Itero por los niveles
                Node node = ndLevels.item(i); //Coge el nodo del nivel actual
                if (node.getNodeType() == Node.ELEMENT_NODE) { //Se asegura que es un nodo XML válido
                    Element level = (Element) node;
                    int id = Integer.parseInt(level.getElementsByTagName("id").item(0).getTextContent());
                    NodeList corners = level.getElementsByTagName("corners");
                    esquinas = new ArrayList<>(); //lista en la que se metarán los puntos de cada esquina
                    for (int j = 0; j < corners.getLength(); j++) { //Intera en cada esquina
                        Element corner = (Element) corners.item(j);
                        if (corner.getNodeType() == Node.ELEMENT_NODE) { //Se asegura que es un nodo XML válido
                            Point pos = parsePosition(corner.getTextContent()); //Convierto la info del XML en un punto
                            Tile tile = GameManager.getInstance().tileManager.searchTile(pos.x, pos.y);
                            esquinas.add(tile);
                        }
                    }
                    this.levels.add(new Level(id, esquinas));
                }
            }
        }
        catch (Exception e) {System.out.println("Error lectura en el XML de niveles");}
    }

    private Point parsePosition(String rawPosition){ //Convierte la posición "x-y" a un punto
        String x = "", y = "";
        boolean jump = false;
        for (char c : rawPosition.toCharArray()) {
            if (c == '-') jump = true;
            if (!jump) x += c;
            else y += c;
        }
        return new Point(Integer.parseInt(x), Integer.parseInt(y));
    }

    public Tile previousEsquina(Tile tile) {
        Tile tAnterior = null;
        for (Tile t : esquinas) {
            if (t == tile) {
                return tAnterior;
            }
            tAnterior = t;
        }
        return null;
    }
    public Tile nextEsquina(Tile tile) {
        Boolean encontrado = false;
        for (Tile t : esquinas) {
            if (encontrado) {
                return t;
            }
            if (t == tile) {
                encontrado = true;
            }

        }
        return null;
    }
    public void crearCamino(){
        Tile anterior = null;
        TileManager tm = GameManager.getInstance().tileManager;
        for (Tile tile : GameManager.getInstance().levelManager.esquinas){
            if (anterior == null) {
                anterior = tile;
                continue;
            }
            if(anterior.getCentre().x!= tile.getCentre().x){
                if(anterior.getCentre().x<tile.getCentre().x){
                    for (float i = anterior.getCentre().x; i < tile.getCentre().x; i++) {
                        tm.searchTile(i,anterior.getCentre().y).toRoad();
                    }
                } else {
                    for (float i = tile.getCentre().x; i < anterior.getCentre().x+1; i++) {
                        tm.searchTile(i,anterior.getCentre().y).toRoad();
                    }
                }

            }
            if(anterior.getCentre().y!= tile.getCentre().y){
                if(anterior.getCentre().y<tile.getCentre().y){
                    for (float i = anterior.getCentre().y; i < tile.getCentre().y; i++) {
                        tm.searchTile(anterior.getCentre().x,i).toRoad();
                    }
                } else {
                    for (float i = tile.getCentre().y; i < anterior.getCentre().y+1; i++) {
                        tm.searchTile(anterior.getCentre().x,i).toRoad();
                    }
                }

            }

            anterior = tile;
        }
    }
}
