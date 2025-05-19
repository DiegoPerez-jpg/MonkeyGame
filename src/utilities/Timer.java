package utilities;

public class Timer {
    double startTime;

    public Timer() {this.startTime = System.nanoTime()/1_000_000.0;}

    public void start() {startTime = System.nanoTime()/1_000_000_000.0;}

    public double getTime() {return (System.nanoTime()/1_000_000_000.0) - startTime;}

}
