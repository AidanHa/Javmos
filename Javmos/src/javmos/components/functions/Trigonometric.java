/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import javmos.*;

/**
 *
 * @author Sufyan Khan
 */
public abstract class Trigonometric extends Function {
    
    protected double a, k;
    
    public Trigonometric(JavmosGUI gui, String function, String name) {
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
            if (function.charAt(i) == name.charAt(0)) {
                a = Double.parseDouble(function.substring(0, i));

            }
        }
        k = Double.parseDouble(function.substring(function.indexOf("(") + 1, function.indexOf(")") - 1));
        
    }
    
}
