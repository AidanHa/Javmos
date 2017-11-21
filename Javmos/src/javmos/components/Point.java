package javmos.components;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.math.BigDecimal;
import javmos.JavmosGUI;
import javmos.enums.RootType;

public class Point extends JavmosComponent {

    public final int RADIUS = 10;
    public double x;
    public double y;
    public final RootType rootType;
    public Ellipse2D.Double point;

    //Class Constructor 
    public Point(JavmosGUI gui, RootType type, double x, double y) {
        super(gui);
        this.rootType = type;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Ellipse2D.Double rootOval = new Ellipse2D.Double(x * gui.getZoom() / gui.getDomainStep() + gui.getPlaneWidth() / 2 - RADIUS / 2, gui.getPlaneHeight() / 2 - y * gui.getZoom() / gui.getRangeStep() - RADIUS / 2, RADIUS, RADIUS);//RADIUS/2 is for centering dot
        this.point = rootOval;
        graphics2D.setColor(rootType.rootColor);
        graphics2D.draw(rootOval);
        graphics2D.fill(rootOval);
    }

    public Ellipse2D.Double getPoint() {
        return point;
    }

    public RootType getRootType() {
        return rootType;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return (int) x;
    }

    @Override
    public boolean equals(Object object) {
        return ((Point) object).x == x;
    }

    @Override
    public String toString() {
        BigDecimal resultX = new BigDecimal(x);
        resultX = resultX.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal resultY = new BigDecimal(y);
        resultY = resultY.setScale(2, BigDecimal.ROUND_HALF_UP);
        return rootType + ":(" + resultX + "," + resultY + ")";
    }

}
