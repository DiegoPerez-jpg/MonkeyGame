package entities.monkeys;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;

public class MonkeyXMLLoader {

    public ArrayList<String> nombres = new ArrayList<>();
    public ArrayList<String> srcs = new ArrayList<>();

    public ArrayList<ArrayList<ArrayList<String>>> tituloMejoras = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<String>>> descripcionMejoras = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<String>>> srcMejoras = new ArrayList<>();

    public MonkeyXMLLoader() {
        cargarDatosDesdeXML("src/entities/monkeys/Stats/stats.xml");
    }

    public void cargarDatosDesdeXML(String rutaXML) {
        try {
            File inputFile = new File(rutaXML);
            if (!inputFile.exists()) {
                System.out.println("Archivo no encontrado: " + rutaXML);
                return;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList monoList = doc.getElementsByTagName("Mono");

            for (int i = 0; i < monoList.getLength(); i++) {
                Node monoNode = monoList.item(i);

                if (monoNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element monoElement = (Element) monoNode;

                    String nombre = monoElement.getElementsByTagName("nombre").item(0).getTextContent();
                    String src = monoElement.getElementsByTagName("src").item(0).getTextContent();
                    nombres.add(nombre);
                    srcs.add(src);

                    ArrayList<ArrayList<String>> titulosPorMejora = new ArrayList<>();
                    ArrayList<ArrayList<String>> descripcionesPorMejora = new ArrayList<>();
                    ArrayList<ArrayList<String>> srcsPorMejora = new ArrayList<>();

                    NodeList mejoras = monoElement.getElementsByTagName("mejora");

                    for (int j = 0; j < mejoras.getLength(); j++) {
                        Node mejoraNode = mejoras.item(j);

                        if (mejoraNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element mejoraElement = (Element) mejoraNode;
                            NodeList niveles = mejoraElement.getElementsByTagName("nivel");

                            ArrayList<String> titulos = new ArrayList<>();
                            ArrayList<String> descripciones = new ArrayList<>();
                            ArrayList<String> srcsNiveles = new ArrayList<>();

                            for (int k = 0; k < niveles.getLength(); k++) {
                                Element nivel = (Element) niveles.item(k);

                                String titulo = nivel.getElementsByTagName("titulo").item(0).getTextContent();
                                String descripcion = nivel.getElementsByTagName("descripcion").item(0).getTextContent();
                                String srcNivel = nivel.getElementsByTagName("src").item(0).getTextContent();

                                titulos.add(titulo);
                                descripciones.add(descripcion);
                                srcsNiveles.add(srcNivel);
                            }

                            titulosPorMejora.add(titulos);
                            descripcionesPorMejora.add(descripciones);
                            srcsPorMejora.add(srcsNiveles);
                        }
                    }

                    tituloMejoras.add(titulosPorMejora);
                    descripcionMejoras.add(descripcionesPorMejora);
                    srcMejoras.add(srcsPorMejora);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public ArrayList<String> getSrcs() {
        return srcs;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getTituloMejoras() {
        return tituloMejoras;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getDescripcionMejoras() {
        return descripcionMejoras;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getSrcMejoras() {
        return srcMejoras;
    }
}
