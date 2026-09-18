# Wireframe Data Viewer

A Java Swing application that renders 3D triangle wireframe mesh models, with interactive rotation, scaling, back-face culling, depth-sorted rendering, and anti-aliasing -- a simple CAD-style viewer built as Assignment 2 for **159.235 (Computer Graphics)**, Massey University, 2026 S02.



## Features

- Loads triangle mesh models from a simple text-based `.tri` file format
- Independent rotation in the XY, XZ, and YZ planes via sliders, with the figure rotating live as each slider is dragged
- Uniform scaling via `+`/`-` buttons
- Orthographic projection onto the xy-plane, with the eye positioned on the positive z-axis
- Back-face culling to remove triangles facing away from the viewer
- Painter's algorithm depth-sorting so nearer triangles are drawn over farther ones, giving the figure a solid appearance
- Toggleable anti-aliasing for smoother edges

## Requirements

- Java Development Kit (JDK) 8 or later

## Running it

### From IntelliJ IDEA

1. Open the project folder in IntelliJ.
2. Run `Main.java` (it contains the `main()` entry point).

### From the command line

From the directory containing the five `.java` source files:

```bash
javac -d out *.java
java -cp out Main
```

## Usage

1. **File → Open** and select a `.tri` mesh file.
2. Drag the **XY Plane**, **XZ Plane**, or **YZ Plane** sliders to rotate the figure.
3. Click **+** / **-** to scale the figure up or down.
4. Toggle **Anti-aliasing** on/off to compare rendering quality.

## The `.tri` file format

Mesh files are plain text, laid out as:

```
<number of vertices>
<index> <x> <y> <z>          (one line per vertex)
<number of triangles>
<index> <v1> <v2> <v3>       (one line per triangle, referencing vertex indices above)
```

The leading index on each line is redundant (it always matches that line's position) but present in every sample file.

## Project structure

| File | Responsibility |
|---|---|
| `Main.java` | The GUI controller — builds the window, sliders, and buttons, and wires their events to update the model and repaint the display. |
| `Wireframe.java` | Holds the loaded mesh data (vertex coordinates and triangle-to-vertex index mapping) and applies the current rotation/scale transform to produce the coordinates actually drawn. |
| `Transform3d.java` | Builds and combines the rotation matrices for each of the three planes into a single transform matrix. |
| `WireframeDataIO.java` | Parses a `.tri` file into a `Wireframe` instance. |
| `WireframeDrawer.java` | Renders a `Wireframe` onto the display panel: back-face culling, depth sorting (painter's algorithm), projection, and drawing. |

## Implementation notes

- Viewing model: the eye sits on the positive z-axis looking toward the origin; figures are drawn by orthographic projection onto the xy-plane (z is dropped for drawing, but retained for culling and depth sorting).
- Rotation: each of the three sliders controls rotation within one coordinate plane (equivalent to rotation about the perpendicular axis); the three per-plane matrices are multiplied together into one combined transform, recomputed whenever any slider changes.
- Back-face culling: for each triangle, the z-component of the cross product of two of its edges (computed from the transformed vertices) determines whether it faces the eye; triangles facing away are skipped.
- Painter's algorithm: surviving triangles are sorted by average z (depth) and drawn farthest-to-nearest, so closer geometry correctly occludes farther geometry.

## Acknowledgements

Starter/skeleton code (`Main.java`'s GUI scaffolding, and the class stubs) was provided as part of 159.235 at Massey University. The implementation of the data model, transforms, file parsing, and rendering pipeline was completed by Ollie.

