package javmos.components.functions;

import javmos.components.JavmosComponent;
import javmos.components.Point;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javmos.enums.FunctionType;
import javmos.JavmosGUI;
import javmos.enums.RootType;

public abstract class Function extends JavmosComponent {

    protected Function(JavmosGUI gui) {
        super(gui);
    }

    public abstract String getFirstDerivative();

    public abstract String getSecondDerivative();

    public abstract double getValueAt(double x, FunctionType functionType);

    public HashSet<Point> getCriticalPoints() {
        return RootType.CRITICAL_POINT.getRoots(gui, this, gui.getMinDomain(), gui.getMaxDomain());
    }

    public HashSet<Point> getInflectionPoints() {
        return RootType.INFLECTION_POINT.getRoots(gui, this, gui.getMinDomain(), gui.getMaxDomain());
    }

    public HashSet<Point> getXIntercepts() {
        return RootType.X_INTERCEPT.getRoots(gui, this, gui.getMinDomain(), gui.getMaxDomain());
    }

    @Override
    public void draw(java.awt.Graphics2D graphics2D) {
        graphics2D.setColor(Color.MAGENTA);

        for (double x = 0; x < gui.getPlaneWidth() / 2; x += 0.01) {//change for size
            if (getValueAt(x, FunctionType.ORIGINAL) > gui.getMinRange() & getValueAt(x, FunctionType.ORIGINAL) < gui.getMaxRange() & x > gui.getMinDomain() & x < gui.getMaxDomain()) {
                Ellipse2D.Double ovalPositive = new Ellipse2D.Double(x * gui.getZoom() / gui.getDomainStep() + gui.getPlaneWidth() / 2, gui.getPlaneHeight() / 2 - getValueAt(x, FunctionType.ORIGINAL) * gui.getZoom() / gui.getRangeStep(), 1, 1);
                graphics2D.fill(ovalPositive);
                graphics2D.draw(ovalPositive);
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.draw(new Line2D.Double((x - 0.01) * gui.getZoom() / gui.getDomainStep() + gui.getPlaneWidth() / 2, gui.getPlaneHeight() / 2 - getValueAt(x - 0.01, FunctionType.ORIGINAL) * gui.getZoom() / gui.getRangeStep(), x * gui.getZoom() / gui.getDomainStep() + gui.getPlaneWidth() / 2, gui.getPlaneHeight() / 2 - getValueAt(x, FunctionType.ORIGINAL) * gui.getZoom() / gui.getRangeStep()));
            }

            if (getValueAt(-x, FunctionType.ORIGINAL) > gui.getMinRange() & getValueAt(-x, FunctionType.ORIGINAL) < gui.getMaxRange() & -x > gui.getMinDomain() & -x < gui.getMaxDomain()) {
                Ellipse2D.Double ovalNegative = new Ellipse2D.Double(gui.getPlaneWidth() / 2 - x * gui.getZoom() / gui.getDomainStep(), gui.getPlaneHeight() / 2 - getValueAt(-x, FunctionType.ORIGINAL) * gui.getZoom() / gui.getRangeStep(), 1, 1);
                graphics2D.fill(ovalNegative);
                graphics2D.draw(ovalNegative);
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.draw(new Line2D.Double(gui.getPlaneWidth() / 2 - (x - 0.01) * gui.getZoom() / gui.getDomainStep(), gui.getPlaneHeight() / 2 - getValueAt(-x + 0.01, FunctionType.ORIGINAL) * gui.getZoom() / gui.getRangeStep(), gui.getPlaneWidth() / 2 - x * gui.getZoom() / gui.getDomainStep(), gui.getPlaneHeight() / 2 - getValueAt(-x, FunctionType.ORIGINAL) * gui.getZoom() / gui.getRangeStep()));
            }
        }

    }
}
