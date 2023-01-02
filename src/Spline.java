import java.util.ArrayList;

public class Spline {
    private ArrayList<Curve> curves;
    public TYPE type;
    public Spline (TYPE type){
        this.type = type;
        addCurve();
    }
    public void addCurve(ArrayList<Point> points){
        curves.add(new Curve(this, points));
    }
    public void addCurve(){
        switch (type){
            case BEZIER :
                ArrayList<Point> pointsB = new ArrayList<>(0);
                pointsB.add(new Point(0.5, 0.5));
                curves.add(new Curve(this, pointsB));
            case HERMITE:
                ArrayList<Point> pointsS = new ArrayList<>(0);
                pointsS.add(new Point(0.5, 0.5));
                curves.add(new Curve(this, pointsS));
            default:
                //yes
        }
    }

    public ArrayList<Curve> getCurves() {
        return curves;
    }
}
