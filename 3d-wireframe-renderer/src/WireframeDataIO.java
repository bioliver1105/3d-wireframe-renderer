
/*
Utility for reading data from the triangle mesh files.

Complete the implementation of the read() method to return a Wireframe instance from a File object

The Scanner utility is highly recommended

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WireframeDataIO {
    public static Wireframe read(File myFile) {

        Wireframe wired = new Wireframe();

        // ... details needed here ...
        try{
            Scanner scanner = new Scanner(myFile);

            String line = scanner.nextLine();
            int numVertices = Integer.parseInt(line);

            wired.numVertices = numVertices;
            wired.xv = new double[numVertices];
            wired.yv = new double[numVertices];
            wired.zv = new double[numVertices];

            for (int i = 0; i < numVertices; i++){
                line = scanner.nextLine();
                StringTokenizer strtok = new StringTokenizer(line, " \t");

                strtok.nextToken();
                double x = Double.parseDouble(strtok.nextToken());
                double y = Double.parseDouble(strtok.nextToken());
                double z = Double.parseDouble(strtok.nextToken());

                wired.xv[i] = x;
                wired.yv[i] = y;
                wired.zv[i] = z;
            }

            line = scanner.nextLine();
            int numTriangles = Integer.parseInt(line);

            wired.numTriangles = numTriangles;
            wired.vtx1 = new int[numTriangles];
            wired.vtx2 = new int[numTriangles];
            wired.vtx3 = new int[numTriangles];

            for (int i = 0; i < numTriangles; i++){
                line = scanner.nextLine();
                StringTokenizer strtok =new StringTokenizer(line, " \t");

                strtok.nextToken();
                int v1 = Integer.parseInt(strtok.nextToken());
                int v2 = Integer.parseInt(strtok.nextToken());
                int v3 = Integer.parseInt(strtok.nextToken());

                wired.vtx1[i] = v1;
                wired.vtx2[i] = v2;
                wired.vtx3[i] = v3;
            }
            scanner.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return wired;
    }
}
