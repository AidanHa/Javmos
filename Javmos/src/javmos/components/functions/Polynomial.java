package javmos.components.functions;

//import java.util.HashSet;
import javmos.*;
import javmos.enums.*;
import javmos.exceptions.PolynomialException;

public class Polynomial extends Function {

    public final double[] coefficients;
    public final int[] degrees;

    // Class Constructor
    public Polynomial(JavmosGUI gui, String function) throws PolynomialException {
        super(gui);

        if (function.substring(function.indexOf("=") + 1).replace("x", "").matches(".*[a-zA-Z]+.*")) {
            throw new PolynomialException(function + " is not a valid polynomial!");
        }

        String polynomial = function.substring(function.indexOf("=") + 1);

        //Checks if null
        if ("".equals(polynomial)) {
            throw new PolynomialException("Please enter a function!");
        }

        //Check if any characters in function does not match a number, "+", "-", "^" or "="
        int size = polynomial.length();
        for (int i = 0; i < size; i++) {
            if ((char) polynomial.indexOf(i) < 48 && (char) polynomial.indexOf(i) > 57 && (char) polynomial.indexOf(i) != 120 && (char) polynomial.indexOf(i) != 121 && (char) polynomial.indexOf(i) != 43 && (char) polynomial.indexOf(i) != 45 && (char) polynomial.indexOf(i) != 94 && (char) polynomial.indexOf(i) != 61) {
                throw new PolynomialException(function + " is not a valid polynomial!");
            }
        }

        // Sets the size of the coefficients array by checking all the occurrences of '+' and '-' operators
        if (polynomial.indexOf("-") == 0 && (polynomial.contains("+") || polynomial.substring(polynomial.indexOf("-") + 1).contains("-"))) {
            this.coefficients = new double[(size - polynomial.replace("-", "").length()) + (size - polynomial.replace("+", "").length())];
        } else if (polynomial.charAt(0) != '-' && (polynomial.contains("+") || polynomial.contains("-"))) {
            this.coefficients = new double[(size - polynomial.replace("-", "").length()) + (size - polynomial.replace("+", "").length()) + 1];
        } else {
            this.coefficients = new double[1];
        }

        this.degrees = new int[coefficients.length];

        //String array for each term in the function
        String[] terms = new String[coefficients.length];

        //Checks if the first term is a negative or positive
        if (polynomial.charAt(0) == '-' && terms.length > 1) {

            //Compares first occurrence of a positive sign with second occurrence of a negative sign to extract first term 
            //and add the respetive sign to the subsequent term
            if ((polynomial.substring(1).indexOf("+") < polynomial.substring(1).indexOf("-") || !polynomial.substring(1).contains("-")) && polynomial.contains("+")) {
                terms[0] = polynomial.substring(0, polynomial.indexOf("+"));
                terms[1] = "+";
            } else {
                terms[0] = polynomial.substring(0, polynomial.indexOf("-", 1));
                terms[1] = "-";
            }
            polynomial = polynomial.substring(polynomial.indexOf("-") + 1);

        } else if (terms.length <= 1) {
            terms[0] = polynomial;
        } else //Identical to above, except compares first occurrence of a positive sign and first occurrence of a negative sign
        {
            if (polynomial.contains("+") && (polynomial.indexOf("+") < polynomial.indexOf("-") || !polynomial.contains("-"))) {
                terms[0] = "+" + polynomial.substring(0, polynomial.indexOf("+"));
                terms[1] = "+";
            } else {
                terms[0] = "+" + polynomial.substring(0, polynomial.indexOf("-"));
                terms[1] = "-";
            }
        }

        if (terms.length > 1) {

            for (int i = 1; i < terms.length; i++) {

                //Changes the initial value of the function string
                if ((polynomial.indexOf("+") < polynomial.indexOf("-") || !polynomial.contains("-")) && polynomial.contains("+")) {
                    polynomial = polynomial.substring(polynomial.indexOf("+", 1) + 1, polynomial.length());
                } else {
                    polynomial = polynomial.substring(polynomial.indexOf("-", 1) + 1, polynomial.length());
                }

                try {
                    //Checks occurrences of negative signs and positive signs as above, but for the following terms 
                    //and stores the values in the corresponding elements of the terms array
                    if ((polynomial.indexOf("+") < polynomial.indexOf("-") || !polynomial.contains("-")) && polynomial.contains("+")) {
                        terms[i] += polynomial.substring(0, polynomial.indexOf("+"));
                        terms[i + 1] = "+";
                    } else {
                        terms[i] += polynomial.substring(0, polynomial.indexOf("-"));
                        terms[i + 1] = "-";
                    }

                } catch (StringIndexOutOfBoundsException e) {
                    terms[i] += polynomial;
                    break;
                }
            }
        }

        //Checks for occurrences of specific characters in the previously initialized terms array and adds the corresponding values to both  
        //the coefficients array and degrees array
        for (int i = 0; i < terms.length && terms[i] != null; i++) {

            if (!terms[i].contains("x")) {
                coefficients[i] = Double.parseDouble(terms[i]);
            } else if (terms[i].contains("-") && !terms[i].substring(0, terms[i].indexOf("x")).matches(".*\\d+.*")) {
                coefficients[i] = -1;
            } else if (!terms[i].contains("-") && !terms[i].substring(0, terms[i].indexOf("x")).matches(".*\\d+.*")) {
                coefficients[i] = 1;
            } else {
                coefficients[i] = Double.parseDouble(terms[i].substring(0, terms[i].indexOf("x")));
            }

            if (terms[i].contains("^")) {
                degrees[i] = Integer.parseInt(terms[i].substring(terms[i].indexOf("^") + 1));
            } else if (terms[i].contains("x")) {
                degrees[i] = 1;
            }
        }

    }

    //Overload Constructor
    public Polynomial(JavmosGUI gui, double[] coefficients, int[] degrees) throws PolynomialException {
        super(gui);
        this.coefficients = coefficients;
        this.degrees = degrees;
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        //Returns the f(x) value of this polynomial
        double retValue = 0;
        double[] derivativeC = new double[coefficients.length];
        int[] derivativeD = new int[coefficients.length];

        double[] derivativeC2 = new double[coefficients.length];
        int[] derivativeD2 = new int[coefficients.length];

        double[] derivativeC3 = new double[coefficients.length];
        int[] derivativeD3 = new int[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {

            if (degrees[i] == 1) {
                derivativeC[i] = coefficients[i];
                derivativeD[i] = 0;
            } else if (degrees[i] > 1) {
                derivativeC[i] = coefficients[i] * degrees[i];
                derivativeD[i] = degrees[i] - 1;
            } else {
                derivativeC[i] = 0;
                derivativeD[i] = 0;
            }
        }

        for (int i = 0; i < coefficients.length; i++) {

            if (derivativeD[i] == 1) {
                derivativeC2[i] = derivativeC[i];
                derivativeD2[i] = 0;
            } else if (derivativeD[i] > 1) {
                derivativeC2[i] = derivativeC[i] * derivativeD[i];
                derivativeD2[i] = derivativeD[i] - 1;
            } else {
                derivativeC2[i] = 0;
                derivativeD2[i] = 0;
            }
        }

        for (int i = 0; i < coefficients.length; i++) {
            if (derivativeD2[i] == 1) {
                derivativeC3[i] = derivativeC2[i];
                derivativeD3[i] = 0;
            } else if (derivativeD2[i] > 1) {
                derivativeC3[i] = derivativeC2[i] * derivativeD2[i];
                derivativeD3[i] = derivativeD2[i] - 1;
            } else {
                derivativeC3[i] = 0;
                derivativeD3[i] = 0;
            }
        }

        switch (functionType) {
            case ORIGINAL:
                for (int i = 0; i < degrees.length; i++) {
                    retValue += coefficients[i] * Math.pow(x, degrees[i]);
                }
                break;
            case FIRST_DERIVATIVE:
                for (int i = 0; i < degrees.length; i++) {
                    retValue += derivativeC[i] * Math.pow(x, derivativeD[i]);
                }
                break;
            case SECOND_DERIVATIVE:
                for (int i = 0; i < degrees.length; i++) {
                    retValue += derivativeC2[i] * Math.pow(x, derivativeD2[i]);
                }
                break;
            default:
                for (int i = 0; i < degrees.length; i++) {
                    retValue += derivativeC3[i] * Math.pow(x, derivativeD3[i]);
                }
                break;
        }
        return retValue;
    }

    @Override
    public String getFirstDerivative() {
        //Return first derivative of this polynomial in the form f'(x)=

        double[] derivativeC = new double[coefficients.length];
        int[] derivativeD = new int[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {

            if (degrees[i] == 1) {
                derivativeC[i] = coefficients[i];
                derivativeD[i] = 0;
            } else if (degrees[i] > 1) {
                derivativeC[i] = coefficients[i] * degrees[i];
                derivativeD[i] = degrees[i] - 1;
            } else {
                derivativeC[i] = 0;
                derivativeD[i] = 0;
            }
        }

        String function = "";
        String[] terms = new String[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {
            switch (derivativeD[i]) {
                case 0:
                    terms[i] = String.valueOf(derivativeC[i]);
                    break;
                case 1:
                    terms[i] = String.valueOf(derivativeC[i]) + "x";
                    break;
                default:
                    terms[i] = String.valueOf(derivativeC[i]) + "x^" + String.valueOf(derivativeD[i]);
                    break;
            }

            if (i > 0 && derivativeC[i] > 0) {
                terms[i] = "+" + terms[i];
            } else if (derivativeC[i] == 0) {
                terms[i] = "";
            }

            function += terms[i];
        }

        return "f'(x) =" + function;
    }

    @Override
    public String getSecondDerivative() {
        //Return second derivative of this polynomial in the form f''(x)=

        double[] derivativeC = new double[coefficients.length];
        int[] derivativeD = new int[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {

            if (degrees[i] == 1) {
                derivativeC[i] = coefficients[i];
                derivativeD[i] = 0;
            } else if (degrees[i] > 1) {
                derivativeC[i] = coefficients[i] * degrees[i];
                derivativeD[i] = degrees[i] - 1;
            } else {
                derivativeC[i] = 0;
                derivativeD[i] = 0;
            }
        }

        double[] derivativeC1 = new double[coefficients.length];
        int[] derivativeD1 = new int[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {

            if (derivativeD[i] == 1) {
                derivativeC1[i] = derivativeC[i];
                derivativeD1[i] = 0;
            } else if (derivativeD[i] > 1) {
                derivativeC1[i] = derivativeC[i] * derivativeD[i];
                derivativeD1[i] = derivativeD[i] - 1;
            } else {
                derivativeC1[i] = 0;
                derivativeD1[i] = 0;
            }
        }

        String function = "";
        String[] terms = new String[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {
            switch (derivativeD1[i]) {
                case 0:
                    terms[i] = String.valueOf(derivativeC1[i]);
                    break;
                case 1:
                    terms[i] = String.valueOf(derivativeC1[i]) + "x";
                    break;
                default:
                    terms[i] = String.valueOf(derivativeC1[i]) + "x^" + String.valueOf(derivativeD1[i]);
                    break;
            }

            if (i > 0 && derivativeC1[i] > 0) {
                terms[i] = "+" + terms[i];
            } else if (derivativeC1[i] == 0) {
                terms[i] = "";
            }

            function += terms[i];
        }

        return "f''(x) =" + function;
    }

}
