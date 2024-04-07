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

    public CalcBackend() {
        displayVal = 0;
        currentVal = 0;
        currentOperator = ' ';
        clearDisplay = false;
    }

    public void feedChar(char c) {
        if (clearDisplay) {
            displayVal = 0;
            clearDisplay = false;
        }

        if (Character.isDigit(c)) {
            displayVal = displayVal * 10 + Character.getNumericValue(c);
        } else if (c == '.') {
            // Handle decimal point
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            if (currentOperator != ' ') {
                calculate();
            }
            currentVal = displayVal;
            currentOperator = c;
            clearDisplay = true;
        } else if (c == '=') {
            calculate();
            currentOperator = ' ';
            clearDisplay = true;
        } else if (c == 'C') {
            displayVal = 0;
            currentVal = 0;
            currentOperator = ' ';
            clearDisplay = false;
        }
    }

    private void calculate() {
        switch (currentOperator) {
            case '+':
                displayVal = currentVal + displayVal;
                break;
            case '-':
                displayVal = currentVal - displayVal;
                break;
            case '*':
                displayVal = currentVal * displayVal;
                break;
            case '/':
                displayVal = currentVal / displayVal;
                break;
        }
    }

    public String getDisplayVal() {
        return String.valueOf(displayVal);
    }
}
