import java.awt.*;

public class Screen {
    public static void init(){
        StdDraw.clear();
        for (Curve c: Main.active.getCurves()) {
            c.draw();
        }
        if (Main.active.type == TYPE.CATMULL_ROM){
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle(0.1, 0.95,0.1,0.05);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(0.1,0.95,"Alter Scalar");
        }
        StdDraw.show();

    }
}
