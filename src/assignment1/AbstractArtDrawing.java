package assignment1;

import java.awt.Color;
import java.util.Random;

import biuoop.*;
import geometry.*;

public class AbstractArtDrawing {

    private final int WIDTH = 400;
    private final int HEIGHT = 300;
    private final int NUM_OF_LINES = 10;
    private final int POINT_SIZE = 3;

    public void drawRandomLines() {
        GUI gui = new GUI("Abstract Art Drawing", WIDTH, HEIGHT);
        DrawSurface ds = gui.getDrawSurface();

        // initialize lines
        Line[] lines = new Line[NUM_OF_LINES];
        for (int i = 0; i < NUM_OF_LINES; i++) {
            lines[i] = generateRandomLine();
        }

        // draw lines
        ds.setColor(Color.BLACK);
        for (int i = 0; i < NUM_OF_LINES; i++) {
            int startX = (int) lines[i].start().getX();
            int startY = (int) lines[i].start().getY();
            int endX = (int) lines[i].end().getX();
            int endY = (int) lines[i].end().getY();

            ds.drawLine(startX, startY, endX, endY);
        }

        // draw middle points
        ds.setColor(Color.BLUE);
        for (int i = 0; i < NUM_OF_LINES; i++) {
            Point middle = lines[i].middle();
            int middleX = (int) middle.getX();
            int middleY = (int) middle.getY();

            ds.fillCircle(middleX, middleY, POINT_SIZE);
        }

        // draw intersections
        ds.setColor(Color.RED);
        for (int i = 0; i < NUM_OF_LINES; i++) {
            for (int j = i + 1; j < NUM_OF_LINES; j++) {
                Point intersection = lines[i].intersectionWith(lines[j]);

                if (intersection == null) {
                    continue;
                }

                int intersectionX = (int) intersection.getX();
                int intersectionY = (int) intersection.getY();

                ds.fillCircle(intersectionX, intersectionY, POINT_SIZE);
            }
        }

        gui.show(ds);
    }

    private Line generateRandomLine() {
        Random rand = new Random();

        Point startPoint = new Point(rand.nextInt(WIDTH) + 1, rand.nextInt(HEIGHT) + 1);
        Point endPoint = new Point(rand.nextInt(WIDTH) + 1, rand.nextInt(HEIGHT) + 1);

        return new Line(startPoint, endPoint);
    }

    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.drawRandomLines();
    }
}
