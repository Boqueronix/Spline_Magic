import java.util.ArrayList;

public class Curve {
    private Spline parent;
    private TYPE type;
    private ArrayList<Point> points;
    public Curve(Spline parent, ArrayList<Point> points){
        this.parent = parent;
        type = parent.type;
        this.points = points;
    }
    public void draw(){

    }
}
