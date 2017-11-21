package javmos.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javmos.JavmosGUI;

public class CartesianPlane extends JavmosComponent {


    public CartesianPlane(JavmosGUI gui) {
        super(gui);
    }

    public void draw(Graphics2D graphics2D) {
        // Draws the Cartesian plane that includes the major axes, grids and axis labels. Modified to match exactly the plane in the program
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setFont(new Font("default", Font.BOLD, 16));

        int stopFactor;//usefull when the set width of the plane is not equal to the set Width of the height
        if (gui.getPlaneWidth() > gui.getPlaneHeight()) {
            stopFactor = gui.getPlaneWidth();
        } else {
            stopFactor = gui.getPlaneHeight();
        }
        
        graphics2D.drawLine(gui.getPlaneWidth() / 2, 0, gui.getPlaneWidth() / 2, gui.getPlaneHeight()); // Draws y-Axis
        graphics2D.drawLine(0, gui.getPlaneHeight() / 2, gui.getPlaneWidth(), gui.getPlaneHeight() / 2); // Draws x-Aixs
        graphics2D.setStroke(new BasicStroke(1));
        
        for (int x = gui.getPlaneHeight() / 2; x < stopFactor; x += (gui.getZoom())) {
            graphics2D.drawLine(0, x, gui.getPlaneWidth(), x);// draws Horizontal lines below the x-Axis
            graphics2D.drawLine(0, gui.getPlaneHeight() - x, gui.getPlaneWidth(), gui.getPlaneHeight() - x);//draws Horizontal lines above the x-Axis
            
            if (x != gui.getPlaneHeight() / 2) {//to exclude zero from the plane
                double yPlane = ((x - gui.getPlaneHeight() / 2) * gui.getRangeStep()) / gui.getZoom();

                if (Math.floor(yPlane) == yPlane) {//draws vertical numbers for integers
                    graphics2D.drawString(Integer.toString(-(int) yPlane), gui.getPlaneWidth() / 2, x);
                    graphics2D.drawString(Integer.toString((int) yPlane), gui.getPlaneWidth() / 2, gui.getPlaneHeight() - x);
                } else {//draws vertical numbers for doubles/decimals
                    graphics2D.drawString(Double.toString(-yPlane), gui.getPlaneWidth() / 2, x);
                    graphics2D.drawString(Double.toString(yPlane), gui.getPlaneWidth() / 2, gui.getPlaneHeight() - x); //vert              
                }
            }
        }
        
        for (int x = gui.getPlaneWidth() / 2; x < stopFactor; x += (gui.getZoom())) {
            graphics2D.drawLine(x, 0, x, gui.getPlaneHeight());// draws vertical lines on the right of the y-axis
            graphics2D.drawLine(gui.getPlaneWidth() - x, 0, gui.getPlaneWidth() - x, gui.getPlaneHeight());//draws vertical line on the left of the y-axis
            double xPlane = ((x - gui.getPlaneWidth() / 2) * gui.getDomainStep()) / gui.getZoom();

            if (xPlane != 0) {//to exclude zero from the plane

                if (Math.floor(xPlane) == xPlane) {//draws horizontal numbers for integers
                    graphics2D.drawString(Integer.toString((int) xPlane), x, gui.getPlaneHeight() / 2);
                    graphics2D.drawString(Integer.toString(-(int) (xPlane)), gui.getPlaneWidth() - x, gui.getPlaneHeight() / 2);
                } else {//draws horizontal numbers for doubles/decimals
                    graphics2D.drawString(Double.toString(xPlane), x, gui.getPlaneHeight() / 2);
                    graphics2D.drawString(Double.toString(-xPlane), gui.getPlaneWidth() - x, gui.getPlaneHeight() / 2);
                }
            }
        }

    }
}
