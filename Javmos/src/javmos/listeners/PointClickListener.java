package javmos.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.LinkedList;
import javmos.JavmosGUI;
import javmos.components.JavmosComponent;
import javmos.components.Point;

public class PointClickListener implements MouseListener {

    private final JavmosGUI gui;
    public final HashSet<Point> points;

    public PointClickListener(JavmosGUI gui) {
        this.gui = gui;
        this.points = new HashSet<>();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (!points.isEmpty()) {
            points.stream().filter((point) -> (point.getPoint().contains(event.getX(), event.getY()))).forEach((point) -> {
                gui.setPointLabel(point.toString(), point.getRootType());
            });
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Not needed!
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Not needed!
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Not needed!
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Not needed!
    }

    public void setPoints(LinkedList<JavmosComponent> points) {
        for (int i = 0; i < points.size(); i++) {
            this.points.add((Point) points.get(i));
        }
        
    }

}
