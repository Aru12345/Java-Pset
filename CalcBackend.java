// Implement your calculator math logic in this class.

// You MUST initialize the calculator's state in the zero-arg constructor. You MUST NOT
// change feedChar's and getDisplayVal's signature or functionality. You MUST NOT have
// ANY other non-private members of this class.

// I will test your CalcBackend class via an expanded version of CalcBackendTest.java,
// which will create an instance of CalcBackend with the zero-arg constructor, pass
// simulated button clicks via feedChar, and retrieve resulting display Strings with
// getDisplayVal.

// Note that when I test your CalcBackend class, I am ONLY using your feedChar and
// getDisplayVal methods. That means that my tests COMPLETELY bypass your Calculator
// class. That's because  your Calculator class is not supposed to have ANY involvement
// with your calculator's computations. Your Calculator class should ONLY layout the
// calculator's JFrame, attach listeners, feed button clicks to CalcBackend via feedChar
// and then update the calculator's display via getDisplayVal.

// I would appreciate it if you would include comments like these (but with your actual char
// values) in CalcBackend.java, because it will let me test your code without investigating
// to see what character is passed to feedChar for the various operators:

//@@ CLEARCHAR =          'C',
//@@ SQRTCHAR  =          '\u221A',
//@@ MULTIPLICATIONCHAR = '*',
//@@ DIVISIONCHAR =       '/',
//@@ ADDITIONCHAR =       '+',
//@@ SUBTRACTIONCHAR =    '-';

public class CalcBackend {
    private double displayVal;
    private double currentVal;
    private char currentOperator;
    private boolean clearDisplay;
    private boolean hasDecimal;
    private double previousResult; // Variable to store the previous result

    public CalcBackend() {
        displayVal = 0.0;
        currentVal = 0.0;
        currentOperator = ' ';
        clearDisplay = false;
        hasDecimal = false;
        previousResult = 0.0; // Initialize previousResult to 0.0
    }

    public void feedChar(char c) {
        if (clearDisplay) {
            displayVal = 0.0;
            clearDisplay = false;
        }

        if (Character.isDigit(c)) {
            handleDigitInput(c);
        } else if (c == '.') {
            hasDecimal = true;
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            handleOperatorInput(c);
        } else if (c == '=') {
            if (currentOperator == '=') {
                // Repeat the previous result without performing a new calculation
                displayVal = previousResult;
            } else {
                calculate();
                previousResult = displayVal; // Remember the current result
                currentOperator = '='; // Update the current operator to '='
            }
            clearDisplay = true;
            hasDecimal = false; // Reset hasDecimal after calculation
        } else if (c == 'C') {
            clear();
        } else if (c == '\u221A') {
            handleSquareRoot();
        }
    }

    private void handleDigitInput(char c) {
        if (c == '.') {
            hasDecimal = true;
        } else {
            if (!hasDecimal) {
                displayVal = displayVal * 10 + Character.getNumericValue(c);
            } else {
                // Calculate the position of the new digit after the decimal point
                int numDigitsAfterDecimal = getNumDigitsAfterDecimal();
                // Adjust the display value based on the position of the new digit
                displayVal = displayVal + Character.getNumericValue(c) * Math.pow(10, -numDigitsAfterDecimal);
            }
        }
    }

    private void handleOperatorInput(char c) {
        if (currentOperator != ' ') {
            // If the operator is entered without a second operand, repeat the previous
            // calculation
            if (!clearDisplay) { // here
                calculate();
            }
        }

        currentVal = displayVal;

        currentOperator = c;
        clearDisplay = true;
        hasDecimal = false; // Reset hasDecimal when operator is entered
    }

    private void calculate() {
        if (currentOperator != '=' && currentOperator != ' ') {
            switch (currentOperator) {
                case '+':
                    displayVal = currentVal + displayVal;
                    break;
                case '-':
                    displayVal = currentVal - displayVal;
                    break;
                case '*':
                    // Multiply the currentVal by displayVal
                    displayVal = currentVal * displayVal;
                    break;
                case '/':
                    if (displayVal != 0.0) {
                        displayVal = currentVal / displayVal;
                    } else {
                        // Handle division by zero
                        if (currentVal > 0) {
                            displayVal = Double.POSITIVE_INFINITY;
                        } else if (currentVal < 0) {
                            displayVal = Double.NEGATIVE_INFINITY;
                        } else {
                            displayVal = Double.NaN;
                        }
                    }
                    break;
            }
            currentVal = displayVal; // Update currentVal for subsequent operations
        }
    }

    private void handleSquareRoot() {
        if (currentVal >= 0) {
            double result = Math.sqrt(currentVal);
            displayVal = result;
        } else {
            displayVal = Double.NaN; // Square root of negative number
        }

        clearDisplay = true;
        hasDecimal = false; // Reset hasDecimal after square root operation
    }

    private void clear() {
        displayVal = 0.0;
        currentVal = 0.0;
        currentOperator = ' ';
        clearDisplay = false;
        hasDecimal = false;
    }

    private int getNumDigitsAfterDecimal() {
        String valStr = Double.toString(displayVal);
        int dotIndex = valStr.indexOf('.');
        if (dotIndex == -1) {
            return 0;
        }
        return valStr.length() - dotIndex - 1;
    }

    public String getDisplayVal() {
        if (Double.isNaN(displayVal)) {
            return "NaN";
        } else {
            return Double.toString(displayVal);
        }
    }

}
