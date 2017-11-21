/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import java.util.*;
import javmos.JavmosGUI;
import javmos.components.Point;
import javmos.enums.FunctionType;

/**
 *
 * @author Sufyan Khan
 */
public class Logarithmic extends Function {

    public double a, base, k;

    public Logarithmic(JavmosGUI gui, String function) {
        super(gui);

        function = function.substring(function.indexOf("=") + 1);

        //Removes 'y='
        for (int i = 0; i < function.length(); i++) {
            if ((function.charAt(i) > 47 && function.charAt(i) < 57) || function.charAt(i) == 45) {
                function = function.substring(i);
                break;
            }
        }
        //Assign variables a and k
        for (int i = 0; i < function.length(); i++) {
            if (function.charAt(i) == 'l') {
                a = Double.parseDouble(function.substring(0, i));
            }
        }
        k = Double.parseDouble(function.substring(function.indexOf("(") + 1, function.indexOf(")") - 1));
        
        if (function.contains("g")) {
            base = Double.parseDouble(function.substring(function.indexOf("g") + 1, function.indexOf("(")));
        } else {
            base = Math.E;
        }
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
        if (base == Math.E) {
            return "f'(x)= " + a + "/x";
        } else {
            return "f'(x)= " + a + "/xln" + base;
        }
    }

    @Override
    public String getSecondDerivative() {
        if (base == Math.E) {
            return "f''(x)= " + -a + "/x^2";
        } else {
            return "f''(x)= " + -a + "/(xln" + base + ")";
        }
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        switch (functionType) {
            case ORIGINAL:
                return (a * Math.log(k * x)) / Math.log(base);
            default:
                return a / (Math.log(base) * x);
        }
    }

}
