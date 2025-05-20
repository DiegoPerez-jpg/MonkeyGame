package utilities;

import utilities.math.Point;
import utilities.math.Vector;

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
        float alpha = getAngle(v, new Vector(1,0)); //Ángulo con respecto al eje de abscisas
        float beta = (float) Math.toRadians(angle);
        float x2 = (float) (v.x*Math.cos(alpha+beta)/Math.cos(alpha));
        float y2 = (float) (v.y*Math.sin(alpha+beta)/Math.sin(alpha));
        return new Vector(x2, y2);
    }
}
