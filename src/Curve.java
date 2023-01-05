import java.awt.*;
import java.util.ArrayList;

public class Curve {
    public static final int A = 150;
    public static Color[] colors = new Color[]{new Color(255,0,0,A),
                                               new Color(255,136,0,A),
                                               new Color(255,255,0,A),
                                               new Color(0,255,0,A),
                                               new Color(0,115,255,A),
                                               new Color(0,0,255,A),
                                               new Color(115,0,115,A)
    };
    private Spline parent;
    private TYPE type;
    public ArrayList<Point> points;
    public Curve(Spline parent, ArrayList<Point> points){
        this.parent = parent;
        type = parent.type;
        this.points = points;
    }
    public void draw(){
        switch (type) {
            case BEZIER:
                for (int i = 0; i < points.size(); i++) {
                    StdDraw.setPenColor((points.get(i).moving)? StdDraw.RED : StdDraw.BLACK);
                    if ((i - 1) % 3 == 2 || i == 0) {StdDraw.filledCircle(points.get(i).x,points.get(i).y,0.0075);}
                    else {StdDraw.circle(points.get(i).x,points.get(i).y,0.0075);}
                }
                for (int i = 0; i < points.size() - 1; i++) {
                    StdDraw.setPenColor((i + 2 <= ( points.size() - (points.size() - 1) % 3))? colors[(i / 3) % 7] : new Color(0,0,0,50));
                    if (i % 3 != 1) {StdDraw.line(points.get(i).x,points.get(i).y,points.get(i + 1).x, points.get(i + 1).y);}
                }
                ArrayList<Point> ps = new ArrayList<>(0);
                for (int i = 0; i <= (- 1 + points.size() - (points.size() - 1) % 3); i++) {
//                    System.out.println("adding " + points.get(i));
                    ps.add(points.get(i));
//                    System.out.println(ps.size());
                    if (ps.size() == 4){
                        doBezier(ps);
                        ps = new ArrayList<>(0);
//                        System.out.println("adding " + points.get(i));
                        ps.add(points.get(i));
                    }
                }
                break;
            case HERMITE:
            case CATMULL_ROM:
            case B_SPLINE:
            default:
                for (int i = 0; i < points.size(); i++) {
                    StdDraw.setPenColor((points.get(i).moving)? StdDraw.RED : StdDraw.BLACK);
                    StdDraw.filledCircle(points.get(i).x,points.get(i).y,0.0075);
                }
                for (int i = 0; i < points.size() - 1; i++) {
                    StdDraw.setPenColor((type == TYPE.LINEAR)? new Color(0,0,0) : (i + 2 <= ( points.size() - (points.size() - 1) % 3))? new Color(0,255,0, 100) : new Color(0,0,0,50));
                    StdDraw.line(points.get(i).x,points.get(i).y,points.get(i + 1).x, points.get(i + 1).y);
                }
                break;
        }
    }
    public static void doBezier(ArrayList<Point> ps){
        double totald = 0;
        totald += Math.sqrt(Math.pow(ps.get(0).x - ps.get(1).x, 2) + Math.pow(ps.get(0).y - ps.get(1).y, 2));
        totald += Math.sqrt(Math.pow(ps.get(1).x - ps.get(2).x, 2) + Math.pow(ps.get(1).y - ps.get(2).y, 2));
        totald += Math.sqrt(Math.pow(ps.get(2).x - ps.get(3).x, 2) + Math.pow(ps.get(2).y - ps.get(3).y, 2));
        System.out.println(totald);
        double time = totald * 1000;
        StdDraw.setPenColor(StdDraw.BLACK);
        for (double i = 0; i < 1; i += 1/time) {
            Point lerp01 = new Point((1 - i) * ps.get(0).x + i * ps.get(1).x, (1 - i) * ps.get(0).y + i * ps.get(1).y);
            Point lerp12 = new Point((1 - i) * ps.get(1).x + i * ps.get(2).x, (1 - i) * ps.get(1).y + i * ps.get(2).y);
            Point lerp23 = new Point((1 - i) * ps.get(2).x + i * ps.get(3).x, (1 - i) * ps.get(2).y + i * ps.get(3).y);
            Point lerp0112 = new Point((1 - i) * lerp01.x + i * lerp12.x, (1 - i) * lerp01.y + i * lerp12.y);
            Point lerp1223 = new Point((1 - i) * lerp12.x + i * lerp23.x, (1 - i) * lerp12.y + i * lerp23.y);
            Point lerp01121223 = new Point((1 - i) * lerp0112.x + i * lerp1223.x, (1 - i) * lerp0112.y + i * lerp1223.y);
            System.out.println("drawing point" + lerp01121223);
            StdDraw.point(lerp01121223.x, lerp01121223.y);
        }
    }
}
