import java.util.ArrayList;
import java.util.Scanner;

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
            case BEZIER, CATMULL_ROM:
                ArrayList<Point> pointsE = new ArrayList<>(0);
                pointsE.add(new Point(0.25, 0.25));
                pointsE.add(new Point(0.25, 0.75));
                pointsE.add(new Point(0.75, 0.75));
                pointsE.add(new Point(0.75, 0.25));
                curves.add(new Curve(this, pointsE));
                break;
            case HERMITE, B_SPLINE:
                ArrayList<Point> pointsH = new ArrayList<>(0);
                pointsH.add(new Point(0.25, 0.5));
                pointsH.add(new Point(0.25, 0.75));
                pointsH.add(new Point(0.75, 0.5));
                pointsH.add(new Point(0.75, 0.25));
                curves.add(new Curve(this, pointsH));
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
        } else if (x < 0.2 && y > 0.9 && type == TYPE.CATMULL_ROM){
            Scanner scalarListener = new Scanner(System.in);
            double scalar = 2;
            while (!(scalar >= 0 && scalar <= 1)){
                System.out.println("Enter a new value");
                scalar = scalarListener.nextDouble();
            }
            Main.scalar = scalar;
            System.out.println("out");
        } else {
            switch (type) {
                case BEZIER, HERMITE, CATMULL_ROM, B_SPLINE, default -> curves.get(0).points.add(new Point(x, y));
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
