/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import javmos.JavmosGUI;
import javmos.enums.FunctionType;

/**
 *
 * @author Sufyan Khan
 */
public class Sine extends Trigonometric {

    public Sine(JavmosGUI gui, String function) {
        super(gui, function, "sin");
    }

    @Override
    public String getFirstDerivative() {
        return "f'(x)= " + a * k + "cos(" + k + "x)";
    }

    @Override
    public String getSecondDerivative() {
        if (a > 0 && k > 0) {
            return "f''(x)= -" + a * k * k + "sin(" + k + "x)";
        } else {
            return "f''(x)= " + -a * k * k + "sin(" + k + "x)";
        }
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        switch (functionType) {
            case ORIGINAL:
                return a * Math.sin(k * x);
            case FIRST_DERIVATIVE:
                return a * k * Math.cos(k * x);
            case SECOND_DERIVATIVE:
                return -a * Math.pow(k, 2) * Math.sin(k * x);
            default:
                return -a * Math.pow(k, 3) * Math.cos(k * x);
        }
    }

}
