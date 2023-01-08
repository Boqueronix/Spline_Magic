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
                    StdDraw.setPenColor((i + 2 <= ( points.size() - (points.size() - 1) % 3))? colors[(int) (Math.random() * 7)] : new Color(0,0,0,50));
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
                for (int i = 0; i < points.size(); i++) {
                    StdDraw.setPenColor((points.get(i).moving)? StdDraw.RED : StdDraw.BLACK);
                    if (i % 2 == 0) {StdDraw.filledCircle(points.get(i).x,points.get(i).y,0.0075);}
                    else {StdDraw.circle(points.get(i).x,points.get(i).y,0.0075);}
                }
                for (int i = 0; i < points.size() - 1; i++) {
                    StdDraw.setPenColor((points.size() > 3)? colors[(int) (Math.random() * 7)] : new Color(0,0,0,50));
                    if (i % 2 == 0) {StdDraw.line(points.get(i).x,points.get(i).y,points.get(i + 1).x, points.get(i + 1).y);}
                }
                ArrayList<Point> psH = new ArrayList<>(0);
                for (int i = 0; i < (points.size() - (points.size() % 2)); i++) {
//                    System.out.println("adding " + points.get(i));
                    psH.add(points.get(i));
//                    System.out.println(ps.size());
                    if (psH.size() == 4){
                        doHermite(psH);
                        psH = new ArrayList<>(0);
//                        System.out.println("adding " + points.get(i));
                        psH.add(points.get(i - 1));
                        psH.add(points.get(i));
                    }
                }
                break;
            case CATMULL_ROM:
                for (int i = 0; i < points.size(); i++) {
                    StdDraw.setPenColor((points.get(i).moving)? StdDraw.RED : StdDraw.BLACK);
                    StdDraw.filledCircle(points.get(i).x,points.get(i).y,0.0075);
                }
                for (int i = 0; i < points.size() - 1; i++) {
                    StdDraw.setPenColor((points.size() > 3)? colors[(int) (Math.random() * 7)] : new Color(0,0,0,50));
                    StdDraw.line(points.get(i).x,points.get(i).y,points.get(i + 1).x, points.get(i + 1).y);
                }
                ArrayList<Point> psC = new ArrayList<>(0);
                if (points.size() >= 3) {
                    psC.add(new Point(2 * points.get(0).x - points.get(1).x, 2 * points.get(0).y - points.get(1).y));
                    psC.add(points.get(0));
                    psC.add(points.get(1));
                    psC.add(points.get(2));
                    doCatmull(psC);
                }
                for (int i = 3; i < points.size(); i++) {
                    psC = new ArrayList<>(0);
                    psC.add(points.get(i - 3));
                    psC.add(points.get(i - 2));
                    psC.add(points.get(i - 1));
                    psC.add(points.get(i));
                    doCatmull(psC);
                }
                if (points.size() >= 3) {
                    psC = new ArrayList<>();
                    psC.add(points.get(points.size() - 3));
                    psC.add(points.get(points.size() - 2));
                    psC.add(points.get(points.size() - 1));
                    psC.add(new Point(2 * points.get(points.size() - 1).x - points.get(points.size() - 2).x, 2 * points.get(points.size() - 1).y - points.get(points.size() - 2).y));
                    doCatmull(psC);
                    System.out.println("end");
                }
                break;
            case B_SPLINE:
                for (int i = 0; i < points.size(); i++) {
                    StdDraw.setPenColor((points.get(i).moving)? StdDraw.RED : StdDraw.BLACK);
                    if (i > 0 && i < points.size() - 1) {
                        StdDraw.filledCircle(points.get(i).x, points.get(i).y, 0.0075);
                    } else {
                        StdDraw.circle(points.get(i).x, points.get(i).y, 0.0075);
                    }
                }
                for (int i = 0; i < points.size() - 1; i++) {
                    StdDraw.setPenColor((points.size() > 3)? colors[(int) (Math.random() * 7)] : new Color(0,0,0,50));
                    StdDraw.line(points.get(i).x,points.get(i).y,points.get(i + 1).x, points.get(i + 1).y);
                }
                for (int i = 3; i < points.size(); i++) {
                    ArrayList<Point> psB = new ArrayList<>(0);
                    psB.add(points.get(i - 3));
                    psB.add(points.get(i - 2));
                    psB.add(points.get(i - 1));
                    psB.add(points.get(i));
                    doB(psB);
                }
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
        int counter = 0;
        for (double i = 0; i < 1; i += 1/time) {
            Point lerp01 = new Point((1 - i) * ps.get(0).x + i * ps.get(1).x, (1 - i) * ps.get(0).y + i * ps.get(1).y);
            Point lerp12 = new Point((1 - i) * ps.get(1).x + i * ps.get(2).x, (1 - i) * ps.get(1).y + i * ps.get(2).y);
            Point lerp23 = new Point((1 - i) * ps.get(2).x + i * ps.get(3).x, (1 - i) * ps.get(2).y + i * ps.get(3).y);
            Point lerp0112 = new Point((1 - i) * lerp01.x + i * lerp12.x, (1 - i) * lerp01.y + i * lerp12.y);
            Point lerp1223 = new Point((1 - i) * lerp12.x + i * lerp23.x, (1 - i) * lerp12.y + i * lerp23.y);
            Point lerp01121223 = new Point((1 - i) * lerp0112.x + i * lerp1223.x, (1 - i) * lerp0112.y + i * lerp1223.y);
//            System.out.println("drawing point" + lerp01121223);
            StdDraw.point(lerp01121223.x, lerp01121223.y);
            counter++;
            if (counter % 5 == 0){
                StdDraw.show();
            }
        }
    }
    public static void doHermite(ArrayList<Point> ps){
        double totalDistance = Math.sqrt(Math.pow(ps.get(0).x - ps.get(2).x, 2) + Math.pow(ps.get(0).y - ps.get(2).y, 2));
//        System.out.println(totalDistance);
        int time = (int) (1000 * totalDistance);
        double dx = ps.get(2).x - ps.get(0).x;
        double dy = ps.get(2).y - ps.get(0).y;
        double vx0 = ps.get(1).x - ps.get(0).x;
        double vy0 = ps.get(1).y - ps.get(0).y;
        double vx1 = ps.get(3).x - ps.get(2).x;
        double vy1 = ps.get(3).y - ps.get(2).y;
        StdDraw.setPenColor(StdDraw.BLACK);
        int counter = 0;
        for (double t = 0; t < 1; t += 1.0 / time) {
            double x = (2 * t * t * t - 3 * t * t + 1) * ps.get(0).x +
                       (t * t * t - 2 * t * t + t) * vx0 +
                       (-2 * t * t * t + 3 * t * t) * ps.get(2).x +
                       (t * t * t - t * t) * vx1;
            double y = (2 * t * t * t - 3 * t * t + 1) * ps.get(0).y +
                       (t * t * t - 2 * t * t + t) * vy0 +
                       (-2 * t * t * t + 3 * t * t) * ps.get(2).y +
                       (t * t * t - t * t) * vy1;
            System.out.println(x + ", " + y);
            StdDraw.point(x,y);
            counter++;
            if (counter % 5 == 0){
                StdDraw.show();
            }
        }
    }
    public static void doCatmull(ArrayList<Point> ps){
        double totalDistance = Math.sqrt(Math.pow(ps.get(1).x - ps.get(2).x, 2) + Math.pow(ps.get(0).y - ps.get(2).y, 2));
//        System.out.println(totalDistance);
        int time = (int) (1000 * totalDistance);
        double s = Main.scalar;
        StdDraw.setPenColor(StdDraw.BLACK);
        int counter = 0;
        for (double t = 0; t < 1; t += 1.0 / time) {
            double x = (- s * t * t * t + 2 * s * t * t - s * t) * ps.get(0).x +
                       ((2 - s) * t * t * t + (s - 3) * t * t + 1) *  ps.get(1).x +
                       ((s - 2) * t * t * t + (3 - 2 * s) * t * t + s * t) * ps.get(2).x +
                       (s * t * t * t - s * t * t) * ps.get(3).x;
            double y = (- s * t * t * t + 2 * s * t * t - s * t) * ps.get(0).y +
                       ((2 - s) * t * t * t + (s - 3) * t * t + 1) *  ps.get(1).y +
                       ((s - 2) * t * t * t + (3 - 2 * s) * t * t + s * t) * ps.get(2).y +
                       (s * t * t * t - s * t * t) * ps.get(3).y;
            System.out.println(x + ", " + y);
            StdDraw.point(x,y);
            counter++;
            if (counter % 5 == 0){
                StdDraw.show();
            }
        }
    }
    public static void doB(ArrayList<Point> ps){
        double totalDistance = Math.sqrt(Math.pow(ps.get(1).x - ps.get(2).x, 2) + Math.pow(ps.get(0).y - ps.get(2).y, 2));
//        System.out.println(totalDistance);
        int time = (int) (1000 * totalDistance);
        StdDraw.setPenColor(StdDraw.BLACK);
        int counter = 0;
        for (double t = 0; t < 1; t += 1.0 / time) {
            double x = (- t * t * t + 3 * t * t - 3 * t + 1) * ps.get(0).x +
                    (3 * t * t * t - 6 * t * t + 4) *  ps.get(1).x +
                    (- 3 * t * t * t + 3 * t * t + 3 * t + 1) * ps.get(2).x +
                    (t * t * t) * ps.get(3).x;
            double y = (- t * t * t + 3 * t * t - 3 * t + 1) * ps.get(0).y +
                    (3 * t * t * t - 6 * t * t + 4) *  ps.get(1).y +
                    (- 3 * t * t * t + 3 * t * t + 3 * t + 1) * ps.get(2).y +
                    (t * t * t) * ps.get(3).y;
            System.out.println(x + ", " + y);
            StdDraw.point(x / 6,y / 6);
            counter++;
            if (counter % 5 == 0){
                StdDraw.show();
            }
        }
    }
}
