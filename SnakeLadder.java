import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Timer;
import java.util.TimerTask;
public class SnakeLadder implements ActionListener
{
    //JFrame components' size define
    private final int row = 5;
    private final int column = 10;
    private final int buttonSize = 90;
    private final int screenWidth = 800;
    private final int boardHeight = 400;
    private final int messageHeight = 80;
    private final int Portrait = 290;
    //Board Location and Button Array match
    private final int[] LocationNo = {1, 2, 3, 4, 5, 6, 7, 8, 9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50};
    private final String[] buttonNo = { "4.0","4.1","4.2","4.3","4.4","4.5","4.6","4.7","4.8","4.9",
            "3.9","3.8","3.7","3.6","3.5","3.4","3.3","3.2","3.1","3.0",
            "2.0","2.1","2.2","2.3","2.4","2.5","2.6","2.7","2.8","2.9",
            "1.9","1.8","1.7","1.6","1.5","1.4","1.3","1.2","1.1","1.0",
            "0.0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9" };
    //Create Jframe, JPanel and message labels
    private JFrame frame;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel messagePanel = new JPanel();
    private JPanel playPanel = new JPanel();
    private JLabel messageLabel;
    private JLabel secondaryLabel;
    //Create pieces for game board
    private JButton buttons[][] = new JButton[row][column];
    private JLabel p1Tag[][] = new JLabel[row][column];
    private JLabel p2Tag[][] = new JLabel[row][column];
    //player 1 button components
    private JButton P1Button;
    private JLabel p1Messager;
    private JLabel p1Indicator;
    //player 2 button components
    private JButton P2Button;
    private JLabel p2Messager;
    private JLabel p2Indicator;
    //main game button
    private JButton diceFace;
    private JLabel mainGameLabel;
    //main game set up factors
    private Boolean gameStart = false;
    private Boolean finshed = false;
    private Die roller;
    private Board gameBoard;
    private int playerNum;
    private int p1position;
    private int p2position;
    private int p1PositionInTheory;
    private int pressedLoc;
    private Boolean p1Moved=false;
    private String extraInfo;
    private String winner;
    private Player player1;
    private Player player2;
    private JButton replayButton;

    /**
     * constructor for the game
     */
    public SnakeLadder() {
        makeFrame();
    }
    private void makeFrame(){
        frame = new JFrame("Snake and Ladder: By Yufeng Lin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        //Set the icon of the program, only works in Windows.
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./img/icon.png")));
        makeMenuBar(frame);
        /**
         * Add  buttons on the board
         */
        panel1.setLayout(new GridLayout(row, column));
        panel1.setSize(new Dimension(screenWidth,boardHeight));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++){
                //Add 50 buttons on the game board
                buttons[i][j] = new JButton();
                buttons[i][j].setLayout(new BorderLayout());
                buttons[i][j].addActionListener(this);
                buttons[i][j].setPreferredSize(new Dimension(buttonSize,buttonSize));
                String fileLocation = "./img/"+i+j+".jpg";
                buttons[i][j].setIcon(new ImageIcon(getClass().getResource(fileLocation)));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setActionCommand(i + "." + j);
                //Add tags on each button for displaying player 1 or player 2 character
                p1Tag[i][j] = new JLabel();
                p1Tag[i][j].setFont(new Font("Cooper Black",Font.BOLD,12));
                p1Tag[i][j].setForeground(Color.BLUE);
                p1Tag[i][j].setBackground(Color.WHITE);
                p1Tag[i][j].setHorizontalAlignment(JLabel.CENTER);
                p1Tag[i][j].setOpaque(true);
                p2Tag[i][j] = new JLabel();
                p2Tag[i][j].setFont(new Font("Cooper Black",Font.BOLD,12));
                p2Tag[i][j].setForeground(Color.RED);
                p2Tag[i][j].setBackground(Color.WHITE);
                p2Tag[i][j].setHorizontalAlignment(JLabel.CENTER);
                p2Tag[i][j].setOpaque(true);
                buttons[i][j].add(p1Tag[i][j],BorderLayout.SOUTH);
                buttons[i][j].add(p2Tag[i][j],BorderLayout.NORTH);
                //add the buttons to the panel
                panel1.add(buttons[i][j]);
            }
        }
        frame.add(panel1, BorderLayout.CENTER);
        /**
            Add the message window to the game
         */
        messageLabel = new JLabel("Welcome to Snake and Ladder Game!");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        secondaryLabel = new JLabel("Presented to you by Yufeng Lin.");
        secondaryLabel.setHorizontalAlignment(JLabel.CENTER);
        messagePanel.setPreferredSize(new Dimension(screenWidth,messageHeight));
        messagePanel.setBorder(BorderFactory.createTitledBorder("Message") );
        messagePanel.setLayout(new GridLayout(2,1));
        messageLabel.setFont(new Font("Cooper Black",Font.BOLD,15));
        secondaryLabel.setFont(new Font("Cooper Black",Font.BOLD,12));
        messagePanel.add(messageLabel);
        messagePanel.add(secondaryLabel);
        panel2.add(messagePanel);
        frame.add(panel2,BorderLayout.NORTH);
        /**
         * Add main buttons in the game, player 1, player 2 and play button
         */
        //Player 1 button
        P1Button = new JButton("Player 1 (You)");
        P1Button.setFont(new Font("Cooper Black",Font.BOLD,15));
        P1Button.setLayout(new BorderLayout());
        P1Button.setOpaque(true);
        p1Messager = new JLabel();
        p1Indicator = new JLabel();
        p1Indicator.setHorizontalAlignment(JLabel.CENTER);
        p1Indicator.setFont(new Font("Cooper Black",Font.BOLD,15));
        P1Button.add(p1Indicator,BorderLayout.NORTH);
        p1Messager.setHorizontalAlignment(JLabel.CENTER);
        p1Messager.setFont(new Font("Cooper Black",Font.BOLD,15));
        P1Button.add(p1Messager,BorderLayout.SOUTH);
        //Player 2 button
        P2Button = new JButton("Player 2 (PC)");
        P2Button.setFont(new Font("Cooper Black",Font.BOLD,15));
        P2Button.setLayout(new BorderLayout());
        P2Button.setOpaque(true);
        p2Messager = new JLabel();
        p2Indicator = new JLabel();
        p2Indicator.setHorizontalAlignment(JLabel.CENTER);
        p2Indicator.setFont(new Font("Cooper Black",Font.BOLD,15));
        P2Button.add(p2Indicator,BorderLayout.NORTH);
        p2Messager.setHorizontalAlignment(JLabel.CENTER);
        p2Messager.setFont(new Font("Cooper Black",Font.BOLD,15));
        P2Button.add(p2Messager,BorderLayout.SOUTH);
        //Main play button
        diceFace = new JButton("Tap Here To Play");
        diceFace.setLayout(new BorderLayout());
        mainGameLabel = new JLabel();
        mainGameLabel.setHorizontalAlignment(JLabel.CENTER);
        mainGameLabel.setFont(new Font("Cooper Black",Font.BOLD,15));
        diceFace.add(mainGameLabel,BorderLayout.SOUTH);
        diceFace.setFont(new Font("Cooper Black",Font.BOLD,18));
        diceFace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonPressed();
            }
        });
        P1Button.setPreferredSize(new Dimension(Portrait,Portrait));
        P2Button.setPreferredSize(new Dimension(Portrait,Portrait));
        diceFace.setPreferredSize(new Dimension(Portrait,Portrait));
        playPanel.setLayout(new GridLayout(1,3));
        playPanel.add(P1Button);
        playPanel.add(diceFace);
        playPanel.add(P2Button);
        panel3.setLayout(new BorderLayout());
        panel3.add(playPanel,BorderLayout.NORTH);
        replayButton = new JButton("Re-Start the Game (DANGEROUS)");
        replayButton.setBackground(Color.RED);
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWrapUp();
            }
        });
        panel3.add(replayButton,BorderLayout.SOUTH);
        panel3.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        frame.add(panel3,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private void makeMenuBar(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Game");
        JMenu aboutMenu = new JMenu("About");
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        JMenuItem startItem = new JMenuItem("Start");
        startItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameStart){
                    startGame();
                }
            }
        });
        fileMenu.add(startItem);
        JMenuItem restartItem = new JMenuItem("Re-Start");
        restartItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWrapUp();
            }
        });
        fileMenu.add(restartItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        fileMenu.add(quitItem);

        JMenuItem aboutItem = new JMenuItem("About the author");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
        aboutMenu.add(aboutItem);
    }
    private void playButtonPressed()
    {
        if(gameStart == false){
            startGame();
        }
        else if(gameStart == true && playerNum == 1){
            mainGameLabel.setIcon(null);
            playerNo(1);
        }
    }
    private void prepareButton(){
        diceFace.setText("Game start!");
        diceFace.setBackground(new Color(255, 150, 150));
        diceFace.setOpaque(true);
        diceFace.setEnabled(true);
        p1Messager.setText("");
        p2Messager.setText("");
    }
    private int selectStarter(){
        int starter;
        int chooseStartP1 = roller.roll();
        int chooseStartP2 = roller.roll();
        while(chooseStartP1 == chooseStartP2){
            chooseStartP1 = roller.roll();
            chooseStartP2 = roller.roll();
        }
        System.out.println("P1 side: "+chooseStartP1+" P2 side: "+chooseStartP2);
        String message = "Player2 rolled: "+ chooseStartP2 + " You rolled: "+ chooseStartP1;
        if(chooseStartP1 < chooseStartP2){
            starter = 2;
            message += ". Player2 will play first.";
            P2Button.setBackground(Color.GREEN);
        } else{
            message += ". You will play first.";
            P1Button.setBackground(Color.GREEN);
            starter = 1;
            diceFace.setText("Your turn! Tap here to roll");
        }
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Bigger dice number will play first",
                JOptionPane.PLAIN_MESSAGE);
        return starter;
    }
    private void startGame() {
        player1= new Player();
        player2= new Player();
        p1position = 0;
        p2position = 0;
        gameStart = true;
        roller = new Die();
        gameBoard = new Board();
        prepareButton();
        secondaryLabel.setText("Game start!");
        playerNum = selectStarter();
        if (playerNum == 1){
            messageLabel.setText("You start! It's your turn.");
            mainGameLabel.setIcon(new ImageIcon(getClass().getResource("./img/DiceFace.png")));
        }else if (playerNum == 2){
            playerNo(2);
        }
    }
    private void playerNo(int player) {
        int dieRolled;
        int lastPosition;
        String message1;
        String message2;
        String str = "";
        extraInfo = "";
        lastPosition = gameBoard.getLastSquare();
        message2 = "Current Player: " + player;
        System.out.println(message2);
        secondaryLabel.setText(message2);
        dieRolled = roller.roll();
        switch (player) {
            case 1:
                p1Moved = false;
                player1.setStartingPoint(p1position);
                player1.setDiceRolled(dieRolled);
                p1PositionInTheory = player1.getStartingPoint() + player1.getDiceRolled();
                player1.setPositionOnDiceRolled(p1PositionInTheory);
                p1position = moveOn(p1position, dieRolled);
                player1.setFinishingPoint(p1position);
                String dicePng1 = "./img/d"+player1.getDiceRolled()+ ".png";
                System.out.println(dicePng1);
                p1Messager.setIcon(new ImageIcon(getClass().getResource(dicePng1)));
                System.out.println("Start at position: " + player1.getStartingPoint());
                System.out.println("Die roll is: " + player1.getDiceRolled());
                System.out.println("Now at position: " + player1.getFinishingPoint());
                System.out.println("TEST: START: "+ player1.getStartingPoint() + "Rolled: "+ player1.getDiceRolled() + "Theory point: "+ player1.getPositionOnDiceRolled() + "Actuall position: "+ player1.getFinishingPoint());
                message1 = "Player 1 | Start: " + player1.getStartingPoint() + ", Rolled: " + player1.getDiceRolled() + ", Now: " + player1.getFinishingPoint() + " "  + extraInfo;
                messageLabel.setText(message1);
                diceFace.setBackground(new Color(255, 217, 137));
                if(player1.getPositionOnDiceRolled() <= 50){
                    String tap = "Tap Square no." +player1.getPositionOnDiceRolled() +" on board.";
                    diceFace.setText(tap);
                } else{
                    p1TurnFlowControl();
                }
                diceFace.setEnabled(false);
                break;
            case 2:
                diceFace.setEnabled(false);
                diceFace.setText("Player 2's turn");
                player2.setStartingPoint(p2position);
                player2.setDiceRolled(dieRolled);
                p2position = moveOn(p2position, dieRolled);
                player2.setFinishingPoint(p2position);
                p2TurnFlowControl();
                String dicePng2 = "./img/d"+player2.getDiceRolled()+ ".png";
                p2Messager.setIcon(new ImageIcon(getClass().getResource(dicePng2)));
                displayP2Tag();
                System.out.println("Start at position: " + player2.getStartingPoint());
                System.out.println("Die roll is: " + player2.getDiceRolled());
                System.out.println("Now at position: " + player2.getFinishingPoint());
                message1 = "Player 2 | Start: " + player2.getStartingPoint() + ", Rolled: " + player2.getDiceRolled() + ", Now: " + player2.getFinishingPoint() + " " + extraInfo;
                messageLabel.setText(message1);
                break;
        }
        finshed =  player1.getFinishingPoint() == lastPosition || player2.getFinishingPoint() == lastPosition;
        if(!finshed){
            swapPlayer();
        } else{
            if(player1.getFinishingPoint() == lastPosition){
                str = " You rolled " + player1.getDiceRolled() + ", reached "+ player1.getFinishingPoint() +". ";
                winner = "Player 1";
            } else if(player2.getFinishingPoint() == lastPosition){
                str = " Player 2 rolled " + player2.getDiceRolled()+ ", reached "+ player2.getFinishingPoint()  + ". ";
                winner = "Player 2";
            }
            String winningMessaage = str + "Winner is "+ winner;
            System.out.println();
            messageLabel.setText(winningMessaage);
            secondaryLabel.setText("Congratulation!!!");
            gameWrapUp();
        }
    }
    public void actionPerformed(ActionEvent ae){
        String ac = ae.getActionCommand();
        if(!p1Moved){
            pressedLoc = buttonToLocationNo(ac);
            if(pressedLoc == player1.getPositionOnDiceRolled()){
                if (player1.getPositionOnDiceRolled() == player1.getFinishingPoint()){
                    p1Indicator.setText("");
                    String rowStr = String.valueOf(ac.charAt(0));
                    String colStr = String.valueOf(ac.charAt(2));
                    int rowNo = Integer.parseInt(rowStr);
                    int colNo = Integer.parseInt(colStr);
                    player1.setFinishRowNo(rowNo);
                    player1.setFinishColNo(colNo);
                } else{
                    String buttonNoStr = locationNoToButton(player1.getFinishingPoint());
                    String rowStr = String.valueOf(buttonNoStr.charAt(0));
                    String colStr = String.valueOf(buttonNoStr.charAt(2));
                    int rowNo = Integer.parseInt(rowStr);
                    int colNo = Integer.parseInt(colStr);
                    player1.setFinishRowNo(rowNo);
                    player1.setFinishColNo(colNo);
                    if(player1.getPositionOnDiceRolled() < player1.getFinishingPoint()){
                        System.out.println("Theory dice: "+ player1.getPositionOnDiceRolled());
                        System.out.println("Finish point: "+ player1.getFinishingPoint());
                        p1Indicator.setText("You just climbed a ladder!!");
                        p1Indicator.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(
                                frame,
                                "You just climbed a ladder!! Jump to " + player1.getFinishingPoint(),
                                "Yeah! It's a ladder!",
                                JOptionPane.PLAIN_MESSAGE);
                    } else if(player1.getPositionOnDiceRolled() > player1.getFinishingPoint()){
                        p1Indicator.setText("You got bitten by a snake!!");
                        p1Indicator.setForeground(Color.BLUE);
                        JOptionPane.showMessageDialog(
                                frame,
                                "You got bitten by a snake!! Down to " + player1.getFinishingPoint(),
                                "No! It's a snake!",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
                p1Tag[player1.getFinishRowNo()][player1.getFinishColNo()].setText("Player 1");
                p1Moved = true;
                System.out.println("get start before if block, row: "+ player1.getStartRowNo()+ " column: "+player1.getStartColNo());
                System.out.println("get finish before if block, row: "+ player1.getFinishRowNo()+ " column: "+player1.getFinishColNo());
                Boolean playerOnBoard = player1.getStartRowNo() >= 0 && player1.getStartColNo() >= 0;
                Boolean playerStandStill = player1.getStartRowNo()== player1.getFinishRowNo() && player1.getStartColNo() == player1.getFinishColNo();
                System.out.println("Player stand still: "+playerStandStill);
                if(playerOnBoard){
                    if(!playerStandStill){
                        p1Tag[player1.getStartRowNo()][player1.getStartColNo()].setText("");
                    }
                }
                player1.setStartRowNo(player1.getFinishRowNo());
                player1.setStartColNo(player1.getFinishColNo());
                P1Button.setBackground(Color.WHITE);
                p1Messager.setIcon(new ImageIcon(getClass().getResource("./img/icon.png")));
                playerNo(2);
                p1Messager.setText("");
            } else{
                p1Messager.setText("You tap the wrong piece!");
                p1Messager.setForeground(Color.RED);
                p1Messager.setBackground(Color.ORANGE);
            }
        }
    }
    private int moveOn(int start, int dist) {
        int next;
        next = start;
        if (start + dist <= 50) {
            next = next + dist;
            if (gameBoard.getBoardNo(next) != 0) {
                String message = gameBoard.getMessage(next);
                System.out.println(message);
                secondaryLabel.setText(message);
                next = gameBoard.getBoardNo(next);
            }
        } else {
            extraInfo = "Must roll exact number to finish";
        }
        return next;
    }
    private void p1TurnFlowControl(){
        Timer timer = new Timer();
        p1Indicator.setText(extraInfo);
        p1Messager.setText("P1 rolling");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                P1Button.setBackground(Color.WHITE);
                playerNo(2);
            }
        };
        timer.schedule(timerTask, 2000);
    }
    private void p2TurnFlowControl(){
        Timer timer = new Timer();
        P2Button.setBackground(Color.GREEN);
        p2Indicator.setText("Player2 Turn");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                P2Button.setBackground(Color.white);
                p2Indicator.setText("");
                p2Messager.setText("");
                p2Messager.setIcon(new ImageIcon(getClass().getResource("./img/icon.png")));
                p1Messager.setIcon(null);
                diceFace.setEnabled(true);
                P1Button.setBackground(Color.GREEN);
                diceFace.setText("Your turn! Tap here to roll");
                mainGameLabel.setIcon(new ImageIcon(getClass().getResource("./img/DiceFace.png")));
            }
        };
        timer.schedule(timerTask, 2000);
    }
    private void displayP2Tag(){
        String buttonNoStr = locationNoToButton(player2.getFinishingPoint());
        String rowStr = String.valueOf(buttonNoStr.charAt(0));
        String colStr = String.valueOf(buttonNoStr.charAt(2));
        int rowNo = Integer.parseInt(rowStr);
        int colNo = Integer.parseInt(colStr);
        player2.setFinishRowNo(rowNo);
        player2.setFinishColNo(colNo);
        Boolean playerOnBoard = player2.getStartRowNo() >= 0 && player2.getStartColNo() >= 0;
        Boolean playerStandStill = player2.getStartRowNo()== player2.getFinishRowNo() && player2.getStartColNo() == player2.getFinishColNo();
        p2Tag[player2.getFinishRowNo()][player2.getFinishColNo()].setText("Player 2");
        if(playerOnBoard){
            if(!playerStandStill){
                p2Tag[player2.getStartRowNo()][player2.getStartColNo()].setText("");
            }
        }
        player2.setStartRowNo(player2.getFinishRowNo());
        player2.setStartColNo(player2.getFinishColNo());

    }
    private void quit()
    {
        System.exit(0);
    }
    private void showAbout(){
        System.out.println("\nAuthor: Yufeng Lin; Stu ID: 101795019\n");
        JOptionPane.showMessageDialog(
                frame,
                "Author: Yufeng Lin, stu id: 101795019",
                "About Author",
                JOptionPane.PLAIN_MESSAGE);
    }
    private void swapPlayer() {
        if (playerNum == 1) {
            playerNum = 2;
        } else {
            playerNum = 1;
        }
    }
    private void gameWrapUp(){
        diceFace.setBackground(new Color(230, 255, 238));
        diceFace.setText("Match has ended");
        diceFace.setEnabled(false);
        P1Button.setBackground(Color.WHITE);
        p1Indicator.setIcon(null);
        p2Indicator.setIcon(null);
        gameStart = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                p1Tag[i][j].setText("");
                p2Tag[i][j].setText("");
            }
        }
        if(winner != null){
            int choice = JOptionPane.showConfirmDialog(frame,
                    winner + " has won the game, do you want to start a new game?",
                    "Game Finished",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION){
                startGame();
            } else if (choice == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        } else{
            int choice = JOptionPane.showConfirmDialog(frame,
                    "All the game status will be lost, do you want to start a new game?",
                    "Restart",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION){
                startGame();
            }
        }
    }
    private int buttonToLocationNo(String criteria){
        for(int i = 0; i<buttonNo.length; i++){
            if(buttonNo[i].equals(criteria)){
                int result = LocationNo[i];
                return result;
            }
        }
        return 0;
    }
    private String locationNoToButton(int location){
        for(int i = 0; i < LocationNo.length; i++){
            if(LocationNo[i] == location){
                String result = buttonNo[i];
                return result;
            }
        }
        return null;
    }
}
