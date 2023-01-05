import java.util.ArrayList;

public class Spline {
    private ArrayList<Curve> curves = new ArrayList<>(0);
    public TYPE type;
    public Spline (TYPE type){
        this.type = type;
        addCurve();
    }
    public void addCurve(ArrayList<Point> points){
        curves.add(new Curve(this, points));
    }
    public void addCurve() {
        System.out.println(type);
        switch (type) {
            case BEZIER:
                ArrayList<Point> pointsE = new ArrayList<>(0);
                pointsE.add(new Point(0.25, 0.25));
                pointsE.add(new Point(0.25, 0.75));
                pointsE.add(new Point(0.75, 0.75));
                pointsE.add(new Point(0.75, 0.25));
                curves.add(new Curve(this, pointsE));
                break;
            case HERMITE:
                ArrayList<Point> pointsH = new ArrayList<>(0);
                pointsH.add(new Point(0.5, 0.5));
                curves.add(new Curve(this, pointsH));
                break;
            case CATMULL_ROM:
                ArrayList<Point> pointsC = new ArrayList<>(0);
                pointsC.add(new Point(0.5, 0.5));
                curves.add(new Curve(this, pointsC));
                break;
            case B_SPLINE:
                ArrayList<Point> pointsB = new ArrayList<>(0);
                pointsB.add(new Point(0.5, 0.5));
                curves.add(new Curve(this, pointsB));
                break;
            default:
                ArrayList<Point> pointsL = new ArrayList<>(0);
                pointsL.add(new Point(0.5, 0.5));
                curves.add(new Curve(this, pointsL));
                break;
        }
    }
    public void tap(double x, double y){
        boolean tooClose = false;
        int indexI = -1;
        int indexJ = -1;
        for (int i = 0; i < curves.size(); i++) {
            for (int j = 0; j < curves.get(i).points.size(); j++) {
                if (Math.abs(curves.get(i).points.get(j).x - x) <= 0.01 && Math.abs(curves.get(i).points.get(j).y - y) <= 0.01){
                    tooClose = true;
                    indexI = i;
                    indexJ = j;
                }
            }
        }
        if (tooClose) {
            curves.get(indexI).points.remove(indexJ);
        } else {
            switch (type) {
                case BEZIER:
                case HERMITE:
                case CATMULL_ROM:
                case B_SPLINE:
                default:
                    curves.get(0).points.add(new Point(x, y));
            }
        }
    }
    public ArrayList<Curve>  getCurves() {
        return curves;
    }
    public Point findPoint(double x, double y){
        for (int i = 0; i < curves.size(); i++) {
            for (int j = 0; j < curves.get(i).points.size(); j++) {
                if (Math.abs(curves.get(i).points.get(j).x - x) <= 0.01 && Math.abs(curves.get(i).points.get(j).y - y) <= 0.01){
                    return curves.get(i).points.get(j);
                }
            }
        }
        return null;
    }
}
