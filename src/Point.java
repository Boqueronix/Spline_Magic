public class Point {
    public double x;
    public double y;
    public boolean moving;
    public Point(double x, double y){
        moving = false;
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "Point at (" + x + ", " + y + ")";
    }
}
