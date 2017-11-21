/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import javmos.*;
import javmos.enums.FunctionType;

/**
 *
 * @author Sufyan Khan
 */
public class Cosine extends Trigonometric {

    public Cosine(JavmosGUI gui, String function) {
        super(gui, function, "cos");
    }

    @Override
    public String getFirstDerivative() {
        if ((a > 0 && k > 0) || (a < 0 && k < 0)) {
            return "f'(x)= -" + a * k + "sin(" + k + "x)";
        } else {
            return "f'(x)= " + -a * k + "sin(" + k + "x)";
        }
    }

    @Override
    public String getSecondDerivative() {
        if ((a > 0 && k > 0)) {
            return "f''(x)= -" + a * k * k + "cos(" + k + "x)";
        } else if (a < 0 && k < 0) {
            return "f''(x)= " + a * -1 * k * k + "cos(" + k + "x)";
        } else {
            return "f'(x)= " + -1 * a * k * k + "cos(" + k + "x)";
        }
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        switch (functionType) {
            case ORIGINAL:
                return a * Math.cos(k * x);
            case FIRST_DERIVATIVE:
                return -a * k * Math.sin(k * x);
            case SECOND_DERIVATIVE:
                return -a * Math.pow(k, 2) * Math.cos(k * x);
            default:
                return a * Math.pow(k, 3) * Math.sin(k * x);
        }
    }

}
