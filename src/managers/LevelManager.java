package managers;

import graphics.Tile;

import java.io.File;
import java.util.ArrayList;
import levels.Level;

import org.w3c.dom.*;
import utilities.math.Point;

import javax.xml.parsers.*;

public class LevelManager {
    ArrayList<Level> levels;
    public Level currentLevel;

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
                    Element cornersElement = (Element) level.getElementsByTagName("corners").item(0); //Extrae la única etiqueta corners que hay
                    NodeList cornerNodes = cornersElement.getElementsByTagName("corner");
                    esquinas = new ArrayList<>();
                    for (int j = 0; j < cornerNodes.getLength(); j++) {
                        Element corner = (Element) cornerNodes.item(j);
                        if (corner.getNodeType() == Node.ELEMENT_NODE) {
                            Point pos = parsePosition(corner.getTextContent());
                            Tile tile = GameManager.getInstance().tileManager.searchTile(pos.x, pos.y);
                            esquinas.add(tile);
                        }
                    }
                    this.levels.add(new Level(id, esquinas));



                    ArrayList<ArrayList<Integer>> todasLasWaves = new ArrayList<>();

                    Element wavesElement = (Element) level.getElementsByTagName("waves").item(0);
                    NodeList waveNodes = wavesElement.getElementsByTagName("wave");

                    ArrayList<Float> tiempos = new ArrayList<>();

                    for (int j = 0; j < waveNodes.getLength(); j++) {
                        Element waveElement = (Element) waveNodes.item(j);

                        String tiempoTexto = waveElement.getElementsByTagName("tiempo").item(0).getTextContent();
                        int tiempo = Integer.parseInt(tiempoTexto);
                        tiempos.add((float) tiempo);
                        ArrayList<Integer> globosWave = new ArrayList<>();
                        NodeList balloonNodes = waveElement.getElementsByTagName("balloon");

                        for (int k = 0; k < balloonNodes.getLength(); k++) {
                            String textoGlobo = balloonNodes.item(k).getTextContent();
                            int globo = Integer.parseInt(textoGlobo);
                            globosWave.add(globo);
                        }

                        todasLasWaves.add(globosWave);
                        GameManager.getInstance().balloonManager.amountBalloon = todasLasWaves;
                        GameManager.getInstance().balloonManager.waveDurantion = tiempos;
                    }

                }
            }
        }
        catch (Exception e) {System.out.println("Error: " + e.getMessage());}
        this.currentLevel = this.searchLevel(1);
    }

    private Point parsePosition(String rawPosition){ //Convierte la posición "x-y" a un punto
        String x = "", y = "";
        boolean jump = false;
        for (char c : rawPosition.toCharArray()) {
            if (c == '-') jump = true;
            else {
                if (!jump) x += c;
                else y += c;
            }
        }
        return new Point(Integer.parseInt(x), Integer.parseInt(y));
    }

    public Level searchLevel(int id) {
        for (Level level : levels) {if (level.id == id) {return level;}}
        return null;
    }
}
