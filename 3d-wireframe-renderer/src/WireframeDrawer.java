/*
Delegated by repaint() on a JPanel. This will draw a Wireframe instance onto a Graphics2D object.

Antialiasing will be applied according to the setting of the boolean parameter
 */


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WireframeDrawer {

    public static void draw(Graphics2D g2, Wireframe wired, boolean antiAlias) {

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF
        );

        double viewScale = 100.0;

        // --- Step 1: back-face cull, keep a depth for whatever survives ---
        List<double[]> visible = new ArrayList<>(); // each entry: {depth, triangleIndex}

        for (int t = 0; t < wired.numTriangles; t++) {
            int i1 = wired.vtx1[t];
            int i2 = wired.vtx2[t];
            int i3 = wired.vtx3[t];

            double x1 = wired.xv2[i1], y1 = wired.yv2[i1], z1 = wired.zv2[i1];
            double x2 = wired.xv2[i2], y2 = wired.yv2[i2], z2 = wired.zv2[i2];
            double x3 = wired.xv2[i3], y3 = wired.yv2[i3], z3 = wired.zv2[i3];

            double ax = x2 - x1, ay = y2 - y1;
            double bx = x3 - x1, by = y3 - y1;

            double nz = ax * by - ay * bx; // z-component of the cross product (edge1 x edge2)

            if (nz <= 0) {
                continue; // facing away from the eye on +z -- cull it
            }

            double depth = (z1 + z2 + z3) / 3.0; // simple depth metric for this triangle
            visible.add(new double[]{depth, t});
        }

        // --- Step 2: painter's algorithm -- draw farthest (smallest z) first ---
        visible.sort(Comparator.comparingDouble(a -> a[0]));

        // --- Step 3: project and draw the survivors, in that back-to-front order ---
        int[] xDraw = new int[3];
        int[] yDraw = new int[3];

        for (double[] entry : visible) {
            int t = (int) entry[1];
            int i1 = wired.vtx1[t];
            int i2 = wired.vtx2[t];
            int i3 = wired.vtx3[t];

            xDraw[0] = (int) Math.round(wired.xv2[i1] * viewScale);
            yDraw[0] = (int) Math.round(wired.yv2[i1] * viewScale);
            xDraw[1] = (int) Math.round(wired.xv2[i2] * viewScale);
            yDraw[1] = (int) Math.round(wired.yv2[i2] * viewScale);
            xDraw[2] = (int) Math.round(wired.xv2[i3] * viewScale);
            yDraw[2] = (int) Math.round(wired.yv2[i3] * viewScale);

            Polygon aTriangle = new Polygon(xDraw, yDraw, 3);

            g2.setColor(Color.gray);
            g2.fill(aTriangle);
            g2.setColor(Color.black);
            g2.draw(aTriangle);
        }
    }
}