import javax.swing.*;
import java.awt.event.MouseEvent;

public class Main {
    private static boolean choosing;
    private static TYPE type;
    public static Spline active;
    public static double scalar = 0.5;
    private static boolean mousePressed = false;
    private static boolean started = false;
    public static void main(String[] args) throws InterruptedException {
        start();
        while (choosing){Thread.sleep(1);}
        active = new Spline(type);
        StdDraw.init();
        StdDraw.enableDoubleBuffering();
        Screen.init();
        StdDraw.pause(1000);
        //buttons and choices
        while (active != null){
            if (!StdDraw.frame.isActive() && started){
                main(new String[] {});
            }
            // choosing draw click checker
            if (StdDraw.isMousePressed() && !mousePressed){
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                if (!started) {started = true;}
                mousePressed = true;
                StdDraw.pause(500);
                if (StdDraw.isMousePressed()){
                    Point p = active.findPoint(x, y);
                    if (p != null){
                        p.moving = true;
                    }
                    //being held
                    while (StdDraw.isMousePressed()){
                        if (p != null && type == TYPE.LINEAR){
                            p.x = StdDraw.mouseX();
                            p.y = StdDraw.mouseY();
                            Screen.init();
                        }
                        else {
                            Thread.sleep(1);
                        }
                    }
                    if (p != null){
                        p.moving = false;
                        p.x = StdDraw.mouseX();
                        p.y = StdDraw.mouseY();
                        Screen.init();
                    }
                    mousePressed = false;
                } else {
                    mousePressed = false;
                    active.tap(x, y);
                    Screen.init();
                }
            } else if (!StdDraw.isMousePressed() && mousePressed) {
                mousePressed = false;
            }
        }
    }
    private static void start(){
        choosing = true;
        JFrame begin = new JFrame("Welcome!, Choose a Spline:");
        begin.setSize(750,75);
        begin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        begin.getContentPane().add(panel);
        JButton be = new JButton("Cubic Bezier");
        JButton he = new JButton("Hermite");
        JButton ca = new JButton("Catmull-Rom");
        JButton bs = new JButton("B-Spline");
        JButton li = new JButton("Linear");
        be.addActionListener(e -> {
            begin.dispose();
            type = TYPE.BEZIER;
            choosing = false;
        });
        he.addActionListener(e -> {
            begin.dispose();
            type = TYPE.HERMITE;
            choosing = false;
        });
        ca.addActionListener(e -> {
            begin.dispose();
            type = TYPE.CATMULL_ROM;
            choosing = false;
        });
        bs.addActionListener(e -> {
            begin.dispose();
            type = TYPE.B_SPLINE;
            choosing = false;
        });
        li.addActionListener(e -> {
            begin.dispose();
            type = TYPE.LINEAR;
            choosing = false;
        });
        panel.add(be);
        panel.add(he);
        panel.add(ca);
        panel.add(bs);
        panel.add(li);
        begin.setVisible(true);
    }

    public static void mousePressed(MouseEvent e) throws InterruptedException {
        System.out.println("test");

    }
}