package flertrådatProgrammeringL1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Patrik Lind on 2016-11-13.
 *
 * This class is a JPanel that draws a triangle by overriding the paintComponent(Graphics g) method.
 * It has rotate() method which rotates the triangle on its axis by 6 degrees.
 */
class DrawTrianglePanel extends JPanel{

    //The x points.
    private int[] xPoints = new int[]{0, 50, 100};

    //The y points
    private int[] yPoints = new int[]{100, 0, 100};

    double angle = 0f;

    private Point centerPoint;

    public void rotate(){
        angle-=6;
        repaint();
    }

    /**
     * Instantiates this object.
     */
    public DrawTrianglePanel() {
        centerPoint = new Point(100, 100);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(198, 190);
    }

    protected Dimension getTriangleSize() {

        int maxX = 0;
        int maxY = 0;
        for (int index = 0; index < xPoints.length; index++) {
            maxX = Math.max(maxX, xPoints[index]);
        }
        for (int index = 0; index < yPoints.length; index++) {
            maxY = Math.max(maxY, yPoints[index]);
        }
        return new Dimension(maxX, maxY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform();
        Dimension size = getTriangleSize();
        int x = centerPoint.x - (size.width / 2);
        int y = centerPoint.y - (size.height / 2);
        at.translate(x, y);
        at.rotate(Math.toRadians(angle), centerPoint.x - x, centerPoint.y - y);
        g2d.setTransform(at);
        g2d.drawPolygon(xPoints, yPoints, 3);
        // Guide
        g2d.dispose();

    }
}



