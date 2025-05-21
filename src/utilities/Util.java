package utilities;

import utilities.math.Point;
import utilities.math.Vector;

import java.util.ArrayList;

public class Util {
    /**
     * @param pf punto final del vector creado
     * @param p0 punto origen del vector creado
     * @return esta funcion devuelve un Vector creado por los dos puntos
     *
     * **/
    public static Vector createVector(Point pf, Point p0) {return new Vector(pf.x - p0.x, pf.y - p0.y);}

    /**
     * @param v1 primer término del producto escalar
     * @param v2 segundo término del producto escalar
     * @return devuelve el producto escalar entre v1 y v2 pull origin main
     * **/
    public static float scalarProduct(Vector v1, Vector v2) {
        return (v1.x * v2.x + v1.y * v2.y);
    }

    public static float getAngle(Vector v1, Vector v2) {
        //Producto escalar v1*v2 = Mod(v1) * Mod(v2) * cos(angúlo entre los vectores)
        //Ángulo entre vectores = arccos(v1*v2/Mod(v1) * Mod(v2))
        return (float) Math.acos((scalarProduct(v1, v2))/(v1.getMod()*v2.getMod()));
    }

    public static Vector rotate(Vector v, float angle) {
        float radians = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        float x2 = v.x * cos - v.y * sin;
        float y2 = v.x * sin + v.y * cos;
        return new Vector(x2, y2);
    }
    public static ArrayList<Vector> generateNVectores(Vector v, int n){
        if (n==0)return null;
        float angulo = 360/n;
        ArrayList<Vector> salida = new ArrayList<>();
        Vector anteriorVector = v;
        for (int i = 0; i < n; i++) {
            anteriorVector = rotate(anteriorVector, i * angulo);
            salida.add(anteriorVector);
        }
        return salida;
    }
}
