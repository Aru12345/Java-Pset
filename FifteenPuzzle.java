import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class FifteenPuzzle {
    public static void main(String[] args) {
        String title = "Fifteen Puzzle Game";
        MyWindow window = new MyWindow(title);
        window.setVisible(true);
    }
}

class MyWindow extends JFrame {
    private JPanel onePanel = new JPanel();
    private JPanel twoPanel = new JPanel();
    private JButton[][] btngrid = new JButton[1][16];
    private JButton shuffle = new JButton("Shuffle");
    private JButton exit = new JButton("Exit");
    private JLabel level=new JLabel("");
    private boolean shuffleOccured = false;


    public MyWindow() {
    }

    public MyWindow(String title) {
        super(title);
        layoutComponents();
        addListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
    }

    private void layoutComponents() {
        onePanel.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {
            btngrid[0][i] = new JButton(String.valueOf(i + 1));
            onePanel.add(btngrid[0][i]);
        }
        btngrid[0][15].setText("");
        onePanel.add(btngrid[0][15]);
        twoPanel.add(level);
        twoPanel.add(shuffle);
        twoPanel.add(exit);

        add(onePanel);
        add(twoPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {

        
       
        for (int i = 0; i < 16; i++) {
           // final int index = i;
            btngrid[0][i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                   // methodClicked=clickedButton;
                    JButton blankButton = getBlankButton();
                   // methodBlank=blankButton;
                    checkSwap(clickedButton, blankButton);
                    
                }

          
                
            });

            
        }

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        shuffle.addActionListener(new ActionListener(){
       
            private int count = 0;
            public void actionPerformed(ActionEvent e){
                    if(count ==0){
                        for(int i =0;i<1;i++){
                        performShuffles(getBlankButton());
                      
                        }
                          shuffleOccured = true;
                    level.setText("Easy Level.");
                        count++;
                    }else if(count==1){
                        for(int i =0;i<2;i++){
                            performShuffles(getBlankButton());
                        }
                          shuffleOccured = true;
                        level.setText("Medium Level.");
                        //afterShuffle((JButton) e.getSource();,getBlankButton());
                        count++;
                    }else{
                        for(int i =0;i<3;i++){
                            performShuffles(getBlankButton());
                        }
                          shuffleOccured = true;
                        level.setText("Hard Level");
                       // afterShuffle((JButton) e.getSource();,getBlankButton());
                        count++;
                    }     
            }
     
        });

    }

    private JButton getBlankButton() {
        for (int i = 0; i < 16; i++) {
            if (btngrid[0][i].getText().isEmpty()) {
                return btngrid[0][i];
            }
        }
        return null;
    }

    private void checkSwap(JButton clicked, JButton blank) {
      
        int clickedRow = -1;
        int clickedCol = -1;
        int blankRow = -1;
        int blankCol = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (btngrid[0][i * 4 + j] == clicked) {
                    clickedRow = i;
                    clickedCol = j;
                }
                if (btngrid[0][i * 4 + j] == blank) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

       if ((Math.abs(clickedRow - blankRow) == 1 && clickedCol == blankCol) || 
            (Math.abs(clickedCol - blankCol) == 1 && clickedRow == blankRow)) {
            String tempText = clicked.getText();
            clicked.setText(blank.getText());
            blank.setText(tempText);

        
        }

        if(shuffleOccured == true){
            afterShuffle();
        }

    }

    private void performShuffles(JButton blankButton){
       Random random = new Random();
       JButton randomValue= null;
       JButton currentBlank = blankButton;

       while(randomValue == null){
          int row = random.nextInt(4); // Generate a random row index
        int col = random.nextInt(4); // Generate a random column index

        // Check if the randomly selected button is adjacent to the blank button
        if (isAdjacent(row, col, currentBlank)) {
            randomValue = btngrid[0][row * 4 + col]; // Get the randomly selected button
        }

         
       }

    String tempText = randomValue.getText();
    randomValue.setText(currentBlank.getText());
    currentBlank.setText(tempText);

       
    }
   
   private boolean isAdjacent(int row, int col, JButton blankButton) {
    int blankRow = -1;
    int blankCol = -1;

    // Find the row and column of the blank button
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (btngrid[0][i * 4 + j] == blankButton) {
                blankRow = i;
                blankCol = j;
                break;
            }
        }
    }

    // Check if the given button is adjacent to the blank button
    return (Math.abs(row - blankRow) == 1 && col == blankCol) || (Math.abs(col - blankCol) == 1 && row == blankRow);

   }

        private void afterShuffle(){
      boolean iteration = true;
      for(int i = 0;i<16;i++){
            
            if(btngrid[0][i].getText()!=""){
                int value =Integer.valueOf(btngrid[0][i].getText());
          
            if(value==i+1){
                iteration = true;
            }else{
                iteration=false;
            }
            }
        }
         if(iteration==true){
            level.setText("Congratulations! You completed the game.");
        }    

   }
   

   
    
}
