package javmos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javmos.JavmosGUI;
import javmos.components.JavmosPanel;
import javmos.components.functions.*;
import javmos.exceptions.PolynomialException;

public class DrawListener implements ActionListener {

    private final JavmosGUI gui;
    private final JavmosPanel panel;

    public DrawListener(JavmosGUI gui, JavmosPanel panel) {
        this.gui = gui;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        
        if (gui.getEquationField().contains("log") || gui.getEquationField().contains("ln")) {
            panel.setFunction(new Logarithmic(gui, gui.getEquationField()));
        } else if (gui.getEquationField().contains("sin")) {
            panel.setFunction(new Sine(gui, gui.getEquationField()));
        } else if (gui.getEquationField().contains("cos")) {
            panel.setFunction(new Cosine(gui, gui.getEquationField()));
        } else if (gui.getEquationField().contains("tan")) {
            panel.setFunction(new Tangent(gui, gui.getEquationField()));
        } else {
            try {
                panel.setFunction(new Polynomial(gui, gui.getEquationField()));
            } catch (PolynomialException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Polynomial Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        gui.setFirstDerivativeLabel(panel.getFunction().getFirstDerivative());
        gui.setSecondDerivativeLabel(panel.getFunction().getSecondDerivative());
        
        panel.repaint();
    }
}
