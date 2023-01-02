import javax.swing.*;

public class Main {
    private static boolean choosing = false;

    private static TYPE type;
    public static Spline active;
    public static boolean mousePressed = false;
    public static void main(String[] args) {
        start();
        while (choosing){StdDraw.pause(1);}
        active = new Spline(type);
        Screen.init();
        //buttons and choices
        while (active != null){
            // choosing draw click checker
            if (StdDraw.isMousePressed() && !mousePressed){
                mousePressed = true;
                StdDraw.pause(500);
                if (StdDraw.isMousePressed()){
                    //being held
                    while (StdDraw.isMousePressed()){
                        System.out.println("Holding shit");
                    }
                    mousePressed = false;
                    System.out.println("released");
                } else {
                    mousePressed = false;
                    System.out.println("tap");
                }
            } else if (!StdDraw.isMousePressed() && mousePressed) {
                mousePressed = false;
            }
        }
        System.out.println("end");
    }
    private static void start(){
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
}