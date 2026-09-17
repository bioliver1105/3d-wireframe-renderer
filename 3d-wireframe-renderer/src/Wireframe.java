

/* Class to hold wireframe data

  COMPLETE THE IMPLEMENTATION OF THIS MODULE

  You can add constructors, data, and any methods as you see fit

 */

public class Wireframe {

    public int numTriangles = 0;
    public int numVertices = 0;

    // Vertex coordinates in the world scene for each vertex of the triangle
    public double[] xv, yv, zv;

    // Triangle mapping
    public int[] vtx1, vtx2, vtx3;

    // Add any data, properties or methods as appropriate
    // Transformed (viewed) vertex coordinates, computed by toView()
    public double[] xv2, yv2, zv2;

    // Here we apply the rotations and scaling before drawing the display
    public void toView(double[][] tmx, double scale) {
        xv2 = new double[numVertices];
        yv2 = new double[numVertices];
        zv2 = new double[numVertices];

        for (int i = 0; i < numVertices; i++){
            double x = xv[i];
            double y = yv[i];
            double z = zv[i];

            xv2[i] = scale * (tmx[0][0] * x + tmx[0][1] * y + tmx[0][2] * z);
            yv2[i] = scale * (tmx[1][0] * x + tmx[1][1] * y + tmx[1][2] * z);
            zv2[i] = scale * (tmx[2][0] * x + tmx[2][1] * y + tmx[2][2] * z);
        }
    }


}
