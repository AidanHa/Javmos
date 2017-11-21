package javmos.enums;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.*;
import javmos.JavmosGUI;
import javmos.components.Point;
import javmos.components.functions.*;

public enum RootType {
    X_INTERCEPT(Color.GRAY, "X-Intercept", FunctionType.ORIGINAL, FunctionType.FIRST_DERIVATIVE),
    CRITICAL_POINT(Color.RED, "Critical Point", FunctionType.FIRST_DERIVATIVE, FunctionType.SECOND_DERIVATIVE),
    INFLECTION_POINT(Color.ORANGE, "Inflection Point", FunctionType.SECOND_DERIVATIVE, FunctionType.THIRD_DERIVATIVE);

    public final int ATTEMPTS = 15;
    public final Color rootColor;
    public final String rootName;
    public final FunctionType functionOne;
    public final FunctionType functionTwo;

    RootType(Color rootColor, String rootName, FunctionType functionOne, FunctionType functionTwo) {
        this.rootColor = rootColor;
        this.rootName = rootName;
        this.functionOne = functionOne;
        this.functionTwo = functionTwo;
    }

    public Color getRootColor() {
        return rootColor;
    }

    public String getRootName() {
        return rootName;
    }

    public java.lang.Double newtonsMethod(Function function, double guess, int attempts) {
        double functionValue = function.getValueAt(guess, functionOne);
        double derivativeValue = function.getValueAt(guess, functionTwo);

        double root = guess - functionValue / derivativeValue;

        if (Math.abs(root - guess) <= 0.001) {
            return guess;
        } else if (attempts == 0) {
            return null;
        }
        attempts--;
        return newtonsMethod(function, root, attempts);
    }

    public HashSet<Point> getRoots(JavmosGUI gui, Function function, double minDomain, double maxDomain) {
        // Stores the unique roots of the function in a HashSet

        HashSet<Point> roots = new HashSet<>();

        for (double i = minDomain; i < maxDomain; i += 0.1) {
            if (newtonsMethod(function, i, ATTEMPTS) != null) {
                double root = Double.parseDouble(new DecimalFormat("#.###").format(newtonsMethod(function, i, ATTEMPTS)));

                Point rootPoint = new Point(gui, RootType.this, root, function.getValueAt(root, FunctionType.ORIGINAL));
                roots.add(rootPoint);
            }
        }

        return roots;
    }
}
