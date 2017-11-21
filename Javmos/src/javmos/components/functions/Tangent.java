/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import java.util.HashSet;
import javmos.JavmosGUI;
import javmos.components.Point;
import javmos.enums.FunctionType;

/**
 *
 * @author Sufyan Khan
 */
public class Tangent extends Trigonometric {

    public Tangent(JavmosGUI gui, String function) {
        super(gui, function, "tan");
    }
    
    @Override
    public HashSet<Point> getCriticalPoints() {
        return new HashSet<>();
    }

    @Override
    public HashSet<Point> getInflectionPoints() {
        return new HashSet<>();
    }

    @Override
    public String getFirstDerivative() {
        return "f'(x)= " + a * k + "sec^2(" + k + "x)";
    }

    @Override
    public String getSecondDerivative() {
        return "f''(x)= " + 2 * a * k * k + "sec^2(" + k + "x)tan(" + k + "x)";
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        switch (functionType) {
            case ORIGINAL:
                return a * Math.tan(k * x);
            default:
                return a * k * Math.pow(1 / Math.cos(k * x), 2);
        }
    }
}
