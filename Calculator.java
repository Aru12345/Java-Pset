import java.awt.*;
import javax.swing.*;

public class Calculator {
    private CalcBackend calcBackend; // Instance of the backend

    public Calculator() {
        calcBackend = new CalcBackend();
        MyWindow window = new MyWindow();
        window.setTitle("Calculator");
        window.setSize(300, 400);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    class MyWindow extends JFrame {
        private JPanel view = new JPanel();
        private JLabel displayLabel = new JLabel("0.0");
        private JPanel buttonPanel = new JPanel();

        public MyWindow() {
            layoutComponents();
            addListeners();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private void layoutComponents() {
            view.setLayout(new FlowLayout(FlowLayout.RIGHT));
            view.setPreferredSize(new Dimension(300, 70));
            view.setBackground(Color.YELLOW);

            buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

            // Add buttons to buttonPanel
            buttonPanel.add(new JButton("C"));
            buttonPanel.add(new JButton("√"));
            buttonPanel.add(new JButton("/"));
            buttonPanel.add(new JButton("*"));

            buttonPanel.add(new JButton("7"));
            buttonPanel.add(new JButton("8"));
            buttonPanel.add(new JButton("9"));
            buttonPanel.add(new JButton("-"));

            buttonPanel.add(new JButton("4"));
            buttonPanel.add(new JButton("5"));
            buttonPanel.add(new JButton("6"));
            buttonPanel.add(new JButton("+"));

            buttonPanel.add(new JButton("1"));
            buttonPanel.add(new JButton("2"));
            buttonPanel.add(new JButton("3"));
            buttonPanel.add(new JButton("="));

            JButton zeroButton = new JButton("0");
            zeroButton.setPreferredSize(
                    new Dimension(zeroButton.getPreferredSize().width * 2, zeroButton.getPreferredSize().height));
            buttonPanel.add(zeroButton);

            buttonPanel.add(new JButton("."));

            // Set font and border for displayLabel
            displayLabel.setFont(new Font(displayLabel.getName(), Font.BOLD, 20));
            displayLabel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0)); // margin at top

            // Add components to view and frame
            view.add(displayLabel, BorderLayout.NORTH);
            add(view, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.CENTER);
        }

        private void addListeners() {
            for (Component component : buttonPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.addActionListener(e -> {
                        String buttonText = button.getText();
                        calcBackend.feedChar(buttonText.charAt(0)); // Feed the button's text character to the backend
                        displayLabel.setText(calcBackend.getDisplayVal()); // Update the display with the result
                    });
                }
            }
        }
    }
}
