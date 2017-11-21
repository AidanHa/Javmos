package javmos.components;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.util.*;
import javmos.components.functions.Function;
import javmos.JavmosGUI;
import javmos.listeners.PointClickListener;

public class JavmosPanel extends JPanel {

    public final LinkedList<JavmosComponent> components;
    public final JavmosGUI gui;
    private CartesianPlane plane;
    private Function function;
    private boolean polynomialChanged = false;

    public JavmosPanel(JavmosGUI gui) {
        this.gui = gui;
        plane = null;
        function = null;
        components = new LinkedList();
    }

    public Function getFunction() {
        return function;
    }

    public void setPlane(CartesianPlane plane) {
        this.plane = plane;
    }

    public void setFunction(Function function) {
        this.function = function;
        polynomialChanged = true;
        components.clear();
        
        Point[] xIntercepts = new Point[function.getXIntercepts().size()];
        Point[] criticalPoints = new Point[function.getCriticalPoints().size()];
        Point[] inflectionPoints = new Point[function.getInflectionPoints().size()];

        function.getXIntercepts().toArray(xIntercepts);
        function.getCriticalPoints().toArray(criticalPoints);
        function.getInflectionPoints().toArray(inflectionPoints);
        
        components.addAll(Arrays.asList(xIntercepts));
        components.addAll(Arrays.asList(criticalPoints));
        components.addAll(Arrays.asList(inflectionPoints));

    }

    @Override
    public void paintComponent(Graphics graphics) {
        setPlane(new CartesianPlane(gui));
        super.paintComponent(graphics);
        plane.draw((Graphics2D) graphics);

        PointClickListener pointClicked = (PointClickListener) getListeners(MouseListener.class)[0];
        pointClicked.points.clear();

        if (polynomialChanged) {
            function.draw((Graphics2D) graphics);

            pointClicked.setPoints(components);

            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) != null) {
                    components.get(i).draw((Graphics2D) graphics);
                }
            }
        }
    }
}
