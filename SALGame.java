import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
/**
 * Description
 * ===========
 * 1) This class allows to generate random number using Random generator function
 * 2) This class allows to play the snake and ladder game
 * Human player (P1) - Manually rolls the dice and move the postion in the board (1 - 30)
 * Computer player (P2) - Manually rolls the dice and automatically moves the position in the board (1 - 30) 
 *                          based upon the value of the dice.
 * 
 * Concepts Involved
 * =================
 * 1) Swing, AWT
 * 2) Action Listener
 * 3) Interface
 * 4) Inner class
 * 5) Random generator
 * 
 * @author (Shreesh Karthikeyan)
 * @version (27/05/2022)
 */
public class SALGame implements ActionListener
{
     public JFrame frame, frame1;
     int humanValue = 0; // Dice value of the human player(P1)
     int computerValue = 0; // Dice value of the human player(P2)
     JTextField textField1, textField2, textField3;
     Container contentPane, contentPane1, contentPane2, contentPane3;
     JPanel jPanel1, jPanel2;
     
     /**
     * Constructor for objects of class SALGame
     */
    public SALGame()
    {
        contentPane = new Container();
        frame = new JFrame("Snake And Ladder Game");
        frame.setSize(500,500);
        
        
        contentPane.setLayout(new FlowLayout());
        
        JButton play = new JButton("Play");
        contentPane.add(play);
        play.addActionListener(this);
        JButton quit = new JButton("Quit");
        contentPane.add(quit);
        quit.addActionListener(this);
        frame.add(contentPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    /**
     * ActionPerformed method for SALGame class
     */
    String currentPlyr = "Human"; // An instance variable to decide whose turn in rolling the dice
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        
        if(command.equals("Quit"))
            System.exit(0);
        
        if(command.equals("Play")) // Game sets to begin by finding out who rolls the dice first
        {
            contentPane.removeAll();
            contentPane.setLayout(new GridLayout(2,1));
            JLabel label = new JLabel("Lets play!!",JLabel.CENTER);
            contentPane.add(label);
            
            JLabel label1 = new JLabel("Whose turn first??",JLabel.CENTER);
            contentPane.add(label1);
            
            jPanel1 = new JPanel();
            jPanel1.setLayout(new GridLayout(2,2));
            JButton userLbl = new JButton("Human(P1)"); // Denotes the human player
            userLbl.setBackground(Color.BLUE);
            userLbl.setForeground(Color.WHITE);
            jPanel1.add(userLbl);
            highlight(userLbl);
            
            JTextField textField1 = new JTextField(""); // Displays the number the human player rolled
            jPanel1.add(textField1);
            
            JButton cmpLbl = new JButton("Comp(P2)"); // Denotes the computer player
            cmpLbl.setBackground(Color.RED);
            cmpLbl.setForeground(Color.WHITE);
            jPanel1.add(cmpLbl);
            
            JTextField textField2 = new JTextField(""); // Displays the number the computer player rolled
            jPanel1.add(textField2);
            
            jPanel2 = new JPanel( );
            jPanel2.setLayout(new GridLayout(3,1));
            textField3 = new JTextField("Pls roll the dice for P1");
            jPanel2.add(textField3);
            
            JButton jbutton1 = new JButton("Roll Dice"); // Rolls the dice for both the players
            jPanel2.add(jbutton1);
            
            jbutton1.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    
                    if(currentPlyr.equals("Human")) // Human players gets the chance, rolls the dice
                    {
                        humanValue = rollDice();
                        textField3.setText("");
                        textField1.setText(String.valueOf(humanValue));
                        unhighlight(userLbl);
                        
                        highlight(cmpLbl);
                        currentPlyr = "Computer"; // Assigns the current player to Computer to gets its chance to roll the dice
                        textField3.setText("Roll the dice for P2");
                    }
                    else
                    {
                        computerValue = rollDice();
                        textField2.setText(String.valueOf(computerValue));
                        unhighlight(cmpLbl);
                        currentPlyr = "Human"; // Assigns the current player to Human to gets its chance to roll the dice
                        if(humanValue > computerValue) // Decides whose turn first based upon the dice numbers from both the player
                            textField3.setText("Human player will roll dice first");
                        else if(humanValue < computerValue)
                            textField3.setText("Computer will roll dice first");
                        else
                            textField3.setText("Human player will roll dice first");
                            
                        
                    }
                }     
            });
            
            JButton jbutton2 = new JButton("Start the Game!!");
            jPanel2.add(jbutton2);
            jbutton2.addActionListener(new innerListenerClass()); // Invokes the Inner listener class
            
            //Adds all the conatiners and jPanel into the frame
            frame.add(contentPane, BorderLayout.NORTH);
            frame.add(jPanel1, BorderLayout.CENTER);
            frame.add(jPanel2, BorderLayout.SOUTH);
            frame.setVisible(true);
        }
    }
    
    /**
     * This methods allows to roll the dice and returns the value of the dice using random generator function
     *
     * @return  int   value of the dice
     */
    public int rollDice()
    {
        int result;
        Random generator = new Random();
        result = generator.nextInt(6)+1;
        return result;
    }
    
    /**
     * This methods sets the border for the current player to roll the dice
     * 
     * param buttonName - JButton - Sets the border for the buttonName
     */
    public void highlight(JButton buttonName)
    {
        buttonName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    }
    
    /**
     * This methods erases the border for the current player
     * 
     *  param buttonName - JButton - Erases the border for the buttonName
     */
    public void unhighlight(JButton buttonName)
    {
       buttonName.setBorder(BorderFactory.createEmptyBorder());
    }
    
    /**
     * Starts the execution
     */
    public static void main(String[] args)
    {
        clearScreen();
        SALGame slGame = new SALGame();
    }
    
    /**
     * Clears the console before the exection
     */
    public static void clearScreen() 
    {
        System.out.print("\u000C");
    }

    /**
     * Inner listener class
     */
    class innerListenerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new Board();
        }
    }
    
    /**
     * Inner class which have the board functionalities
     */
    public class Board implements ActionListener
    {
        JButton[] btn = new JButton[30]; // button in the board
        JButton userLbl,cmpLbl; // Human player, Computer player
        JTextField userTxt,cmpTxt,cmntsTxt;
        JButton commonBtn; //Roll dice button
        String currentPlayer; // Current player(Human, Computer)
        int playerPostion = 0; // Position of the human player(P1)
        int computerPostion = 0; // Position of the computer player(P2)
        int lastNumber = 30; // Maximum number in the board
        boolean isLadder = false;
        boolean isSnake = false;
        private int[] ladderStarts = { 4, 12}; //Ladder starting position
        private int[] ladderEnds =   {14, 22}; //Ladder ending position
        
        private int[] snakeStarts = {20, 16}; // Snake hear position
        private int[] snakeEnds =   {7, 5}; //Snake bottom position

     /**
     * Constructor for objects of class Board
     */ 
    public Board()
    {
        frame.dispose(); // Closes the previous frame
        
        frame1 = new JFrame("Snake And Ladder Game"); //Sets a new frame
        frame1.setSize(500,500);
        
        JMenuBar menuBar = new JMenuBar(); // Adds menu bar content
        frame1.setJMenuBar(menuBar);
        JMenu optionMenu = new JMenu("File");
        menuBar.add(optionMenu);
        JMenuItem playItem = new JMenuItem("Restart");
        optionMenu.add(playItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        optionMenu.add(quitItem);
        playItem.addActionListener(new ActionListener(){  //Goes back to previous frame to restart game
                public void actionPerformed(ActionEvent e){
                    frame1.dispose();
                    frame.setVisible(true);
                }     
            });
        quitItem.addActionListener(new ActionListener(){  //Closes the frame and stops the execution
                public void actionPerformed(ActionEvent e){
                    System.exit(0);
                }     
            });
        
        contentPane1 =  new Container();
        contentPane2 = new Container();
        contentPane3 = new Container();
        //Adds the button from 1 to 30
        btn[29] = new JButton("30"); contentPane1.add(btn[29]);
        btn[28] = new JButton("29"); contentPane1.add(btn[28]);
        btn[27] = new JButton("28"); contentPane1.add(btn[27]);
        btn[26] = new JButton("27"); contentPane1.add(btn[26]);
        btn[25] = new JButton("26"); contentPane1.add(btn[25]);
        
        btn[20] = new JButton("21"); contentPane1.add(btn[20]);
        btn[21] = new JButton("22"); contentPane1.add(btn[21]);
        btn[22] = new JButton("23"); contentPane1.add(btn[22]);
        btn[23] = new JButton("24"); contentPane1.add(btn[23]);
        btn[24] = new JButton("25"); contentPane1.add(btn[24]);
        
        btn[19] = new JButton("20 Snake Goto7"); contentPane1.add(btn[19]);
        btn[18] = new JButton("19"); contentPane1.add(btn[18]);
        btn[17] = new JButton("18"); contentPane1.add(btn[17]);
        btn[16] = new JButton("17"); contentPane1.add(btn[16]);
        btn[15] = new JButton("16 Snake Goto5"); contentPane1.add(btn[15]);
        
        btn[10] = new JButton("11"); contentPane1.add(btn[10]);
        btn[11] = new JButton("12 Ladder Goto22"); contentPane1.add(btn[11]);
        btn[12] = new JButton("13"); contentPane1.add(btn[12]);
        btn[13] = new JButton("14"); contentPane1.add(btn[13]);
        btn[14] = new JButton("15"); contentPane1.add(btn[14]);
        
        btn[9] = new JButton("10"); contentPane1.add(btn[9]);
        btn[8] = new JButton("9"); contentPane1.add(btn[8]);
        btn[7] = new JButton("8"); contentPane1.add(btn[7]);
        btn[6] = new JButton("7"); contentPane1.add(btn[6]);
        btn[5] = new JButton("6"); contentPane1.add(btn[5]);
        
        btn[0] = new JButton("1"); contentPane1.add(btn[0]);
        btn[1] = new JButton("2"); contentPane1.add(btn[1]);
        btn[2] = new JButton("3"); contentPane1.add(btn[2]);
        btn[3] = new JButton("4 Ladder Goto14"); contentPane1.add(btn[3]);
        btn[4] = new JButton("5"); contentPane1.add(btn[4]);
        
        contentPane1.setLayout(new GridLayout(6,5,3,3));
        
        contentPane2.setLayout(new GridLayout(1,5));
        userLbl = new JButton("Human(P1)"); // Human player
        userLbl.setBackground(Color.BLUE);
        userLbl.setForeground(Color.WHITE);
        contentPane2.add(userLbl);
        
        userTxt = new JTextField("Dice value : "); // Displays the dice number the human player(P1) rolls
        contentPane2.add(userTxt);
        
        commonBtn = new JButton("Roll Dice"); //Rolls the dice
        contentPane2.add(commonBtn);
        
        cmpLbl = new JButton("Comp(P2)"); // Computer player
        cmpLbl.setBackground(Color.RED);
        cmpLbl.setForeground(Color.WHITE);
        contentPane2.add(cmpLbl);
        
        cmpTxt = new JTextField("Dice value : "); // Displays the dice number the computer player(P2) rolls
        contentPane2.add(cmpTxt);
        
        contentPane3.setLayout(new GridLayout(1,1)); // Displays the appropriate message for each of the player 
        cmntsTxt = new JTextField("Comments Label",JLabel.CENTER);
        cmntsTxt.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        contentPane3.add(cmntsTxt);
        
        frame1.add(contentPane1,BorderLayout.NORTH);// Adds all the container to the frame
        frame1.add(contentPane2,BorderLayout.SOUTH);
        frame1.add(contentPane3,BorderLayout.CENTER);
   
        if(humanValue > computerValue || humanValue == computerValue) //Decides the current player to roll dice and highlights them
        {
           highlight(userLbl);
           currentPlayer = "Human";
        }  
        else
        {
           highlight(cmpLbl);
           currentPlayer = "Computer";
        }
        
        startGame(); //Game begins
        frame1.setVisible(true);
    }
    
    /**
     * This method starts the game
     */ 
    public void startGame()
    {
        
        if(currentPlayer.equals("Computer"))
        {
            highlight(cmpLbl);
            cmntsTxt.setText("");
            cmntsTxt.setText("P2 : Roll the dice");
        }
        
        else if(currentPlayer.equals("Human"))
        {
            highlight(userLbl);
            cmntsTxt.setText("");
            cmntsTxt.setText("P1 : Roll the dice");
        }
        commonBtn.addActionListener(this);
        for (int i = 0; i < btn.length;i++)
            btn[i].addActionListener(this);
    }
    
    /**
     * Action performed method for Board class
     */ 
    int hValue = 0;
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Entered Action Performed");
        String command = e.getActionCommand();
        if(command.equals("Roll Dice") && currentPlayer.equals ("Human")) // Rolls the dice for human player
        {
            hValue = humanRollDice(); // Returns the value of the dice
            int total = 0;
            total = playerPostion + hValue; // Adds player position and value of the dice
            if(total <= lastNumber) // Allows player(P1) to move the dice only when total is less than the last number(30)
            {
                cmntsTxt.setText("");
                cmntsTxt.setText("P1 : Move the dice");
            }
            else // Swaps the player if total exceeds the last number(30)
            {
                cmntsTxt.setText("");
                setBackGround(btn[playerPostion-1],currentPlayer);
                cmntsTxt.setText("P1: Roll '"+(lastNumber - playerPostion)+"' to finish the game..P2 : Roll the dice");
                
                swapPlayer();
                unhighlight(userLbl);
                highlight(cmpLbl);
            }
        }
        else if(command.equals("Roll Dice") && currentPlayer.equals("Computer"))// Rolls the dice for human player
        {
            int value = computerMove(); //Returns the value of the dice, updates and sets the indication for computerPostion in the board
            swapPlayer();  // Swaps the player (CurrentPlayer moves to "Human")
            if((value == 6 && computerPostion < lastNumber && !isSnake)) //Gets an another chance if P2 rolled the dice with value of 6
            {
                if(isLadder)
                        {
                            int initialStep = ladderFind(computerPostion);
                            cmntsTxt.setText("P2 : Got a ladder("+initialStep+"-"+computerPostion+")..P2: Again "+
                                                "U roll the dice");
                        }
                        else
                            cmntsTxt.setText("P2: Again U roll the dice");
                swapPlayer(); // Swaps the player (CurrentPlayer moves to "Computer")
            }
            else //Displays the current turn in the message box
            {
                unhighlight(cmpLbl);
                highlight(userLbl);
                if(value == 0)
                    cmntsTxt.setText("P2: Roll '"+(lastNumber - computerPostion)+"' to finish the game.."+
                                                 "P1: Roll the dice");
                else if(!isLadder && !isSnake)
                    cmntsTxt.setText("P1: Roll the dice");
            }
        }
        else //This action gets performed for P1 player for manual moving the dice from (1 - 30)
        {
            if(currentPlayer.equals("Human"))
                humanManualMove(command);
            else
                cmntsTxt.setText("P2: Roll the dice");
        }
    }
    
    /**
     * This methods allow the P1 player to manual move the postion of the human player in the board ( 1- 30)
     * Add gets corresponding message in the cmntsTxt Textfield.
     *
     * @param  String  a string variable which holds the position of the P1 player(Manual clicking)
     */
    public void humanManualMove(String position)
    {
            int total = 0;
            total = playerPostion + hValue; // Adds the player postion with dice number
            System.out.println(total);
            System.out.println(position);
            if(position.contains("20 Snake Goto7")) position = "20";
            else if(position.contains("16 Snake Goto5")) position = "16";
            else if(position.contains("4 Ladder Goto14")) position = "4";
            else if(position.contains("12 Ladder Goto22")) position = "12";
                
            if(total < lastNumber) //This clause gets executed only when total value is less than last number(30)
            {
                if(total == Integer.parseInt(position)) //This clause gets executed when the manual move number and total number matches
                {
                    if(playerPostion > 0)
                        unSetBackGround(btn[playerPostion-1],currentPlayer); // Erases the background of the P1 player postion in the board
                        
                    playerPostion = total; //Assigns the total value to playerPosition
                    System.out.println(currentPlayer);
                    setBackGround(btn[playerPostion-1],currentPlayer);
                    
                    isLadder = checkLadder(playerPostion); //Checks for the ladder
                    if(isLadder) //Updates the player position
                    {
                        unSetBackGround(btn[playerPostion-1],currentPlayer);
                        playerPostion = afterLadderUpPosition(playerPostion);
                        setBackGround(btn[playerPostion-1],currentPlayer);
                        
                        int initialStep = ladderFind(playerPostion);
                        if(hValue != 6)
                        {
                            cmntsTxt.setText("P1 : Got a ladder("+initialStep+"-"+playerPostion+")..P2: Now "+
                                                "U roll the dice");
                        }
                    }
                    
                    isSnake = checkSnake(playerPostion); //Checks for the snake
                    if(isSnake) //Updates the player position
                    {
                        unSetBackGround(btn[playerPostion-1],currentPlayer);
                        playerPostion = afterSnakeBitePosition(playerPostion);
                        setBackGround(btn[playerPostion-1],currentPlayer);
                        
                        int initialStep = snakeFind(playerPostion);
                        cmntsTxt.setText("P1 : Bit by snake("+initialStep+"-"+playerPostion+")..P2: Now "+
                                                "U roll the dice");
                    }
                    
                    swapPlayer();// Swaps the player (CurrentPlayer moves to "Computer")
                    if((hValue == 6 && playerPostion < lastNumber && !isSnake)) //P1 player gets a chance when he rolls dice with the value of 6
                    {
                        if(isLadder)
                        {
                            int initialStep = ladderFind(playerPostion);
                            cmntsTxt.setText("P1 : Got a ladder("+initialStep+"-"+playerPostion+")..P1: Again "+
                                                "U roll the dice");
                        }
                        else
                            cmntsTxt.setText("P1: Again U roll the dice");
                        swapPlayer(); // Swaps the player (CurrentPlayer moves to "Human")
                    }
                    else
                    {
                        unhighlight(userLbl);
                        highlight(cmpLbl);
                        if(!isLadder && !isSnake)
                            cmntsTxt.setText("P2: Roll the dice");
                    }
                }
                else //This clause gets executed when the manual move click number is different from total value in the board
                {
                    cmntsTxt.setText("");
                    cmntsTxt.setText("P1 : Move the dice to '"+total+"' box");
                }
            }
            else if(total == lastNumber) //This clause gets executed only when total is same as the last number
            {
                if(total == Integer.parseInt(position)) //This clause gets executed when the manual move number and total number matches
                {
                    JOptionPane.showMessageDialog(frame1,"Human won the game!!");// Option pane arises and stop the execution
                    System.exit(0);
                }
                else //This clause gets executed when the manual move click number is different from total value in the board
                {
                    cmntsTxt.setText("");
                    cmntsTxt.setText("P1 : Move the dice to '"+total+"' box");
                }
            }
    }
    
    /**
     * This method allows the P2 player to automatically move  the computer postion based upon the value of the dice.
     *
     * @return  int  Returns the value of the dice 
     */
    public int computerMove()
    {
        
        int value = 0;
        System.out.println("Inside the Computer main Loop");
        
        if(computerPostion < lastNumber) //This clause gets executed only when total value is less than last number(30)
        {
            value = rollDice();
            cmpTxt.setText("");
            if(computerPostion > 0)
                unSetBackGround(btn[computerPostion-1],currentPlayer);    
            
            cmpTxt.setText("Dice value : "+String.valueOf(value));
            System.out.println("Dice value : "+String.valueOf(value));
            
            int var = computerPostion + value; //Adds the computer position with the value of the dice
            if(var < lastNumber) //This clause gets executed only when var value is less than last number(30)
            {
                computerPostion = var; // Assigns the value of the var to computer position
                setBackGround(btn[computerPostion-1],currentPlayer);
                
                System.out.println("cmp"+computerPostion);
                isLadder = checkLadder(computerPostion); // Checks for ladder
                if(isLadder) // Updates the position of the computer player
                {
                    unSetBackGround(btn[computerPostion-1],currentPlayer);
                    computerPostion = afterLadderUpPosition(computerPostion);
                    setBackGround(btn[computerPostion-1],currentPlayer);
                    
                    int initialStep = ladderFind(computerPostion);
                    cmntsTxt.setText("");
                    if(value != 6)
                            cmntsTxt.setText("P2 : Got a ladder("+initialStep+"-"+computerPostion+")..P1: Now "+
                                                "U roll the dice");
                    
                }
                isSnake = checkSnake(computerPostion); // Checks for snake
                if(isSnake) // Updates the position of the computer player
                {
                    unSetBackGround(btn[computerPostion-1],currentPlayer);
                    computerPostion = afterSnakeBitePosition(computerPostion);
                    setBackGround(btn[computerPostion-1],currentPlayer);
                    
                    int initialStep = snakeFind(computerPostion);
                    cmntsTxt.setText("");
                    cmntsTxt.setText("P2 : Bit by snake("+initialStep+"-"+computerPostion+")..P1: Now "+
                                                "U roll the dice");
                }
            }
            else if(var == lastNumber) // This clause gets executed only when var value is same as the last number
            {
                JOptionPane.showMessageDialog(frame1,"Computer won the game!!"); // Option pane arises and stop the execution
                System.exit(0);
            }
            else //This clause gets executed when the var value exceeds the last number(30)
            {
                value = 0;
                setBackGround(btn[computerPostion-1],currentPlayer);
            }
        }
        return value;
    }
    
    /**
     * This method allows the P1 player to roll the dice and returns the value from the dice
     *
     * @return  int  returns the value of the dice
     */
    public int humanRollDice()
    {        
        int value = 0;
        
        value = rollDice();
        userTxt.setText("");   
        userTxt.setText("Dice value : "+String.valueOf(value));
        System.out.println("Dice value : "+String.valueOf(value));
        
        return value;
    }
    
    /**
     * This methods allow to set the background of the button in the board (1 - 30)
     *
     * @param1  Jbutton  button to set the back ground
     * @param2  String   Player like Human, Computer
     */
    public void setBackGround(JButton btn,String player)
    {
        if(computerPostion == playerPostion)
            btn.setBackground(new Color(211, 0, 185));
        else
        {
            if(player.equals("Computer"))
                btn.setBackground(Color.RED);
            else if(player.equals("Human"))
                btn.setBackground(Color.BLUE);
        }
    }
    
    /**
     * This methods allow to erase the background of the button in the board (1-30)
     *
     * @param1  Jbutton  button to erase the back ground
     * @param2  String   Player like Human, Computer
     */
    public void unSetBackGround(JButton btn,String player)
    {
        if(computerPostion == playerPostion)
        {
            if(player.equals("Computer"))
                btn.setBackground(Color.BLUE);
            else if(player.equals("Human"))
                btn.setBackground(Color.RED);
        }
        else
        {
            if(player.equals("Computer"))
                btn.setBackground(null);
            else if(player.equals("Human"))
                btn.setBackground(null);
        }
    }
    
    /**
     * This methods allow to swap the the current player
     *
     */
    public void swapPlayer()
    {
        if(currentPlayer.equals("Human"))
            currentPlayer = "Computer";
        else
            currentPlayer = "Human";
    }
    
    /**
     * This methods allow to verify the ladder bottom available in the position
     *
     * return  boolean  To check whether ladder bottom in the landed position
     * @param1  int     Position of the player
     */
    public boolean checkLadder(int position)
    {
        boolean isFound = false;
        for(int i = 0;i<ladderStarts.length;i++)
        {
            if(ladderStarts[i] == position)
                isFound = true;
        }
        return isFound;
    }

    /**
     * This methods allow to verify the snake head available in the position
     *
     * return  boolean  To check whether snake head in the landed position
     * @param1  int     Position of the player
     */
    public boolean checkSnake(int position)
    {
        boolean isFound = false;
        for(int i = 0;i<snakeStarts.length;i++)
        {
            if(snakeStarts[i] == position)
                isFound = true;
        }
        return isFound;
    }
    
    /**
     * This methods allow to update the position of the current player after the snake bite
     *
     * return  int      Returns the updated position of the current player after the snake bite
     * @param1  int     Position of the player
     */
    public int afterSnakeBitePosition(int position)
    {
        int updatedPostion , i;
        for(i = 0;i<snakeStarts.length;i++)
        {
            if(snakeStarts[i] == position)
                break;
        }
        updatedPostion = snakeEnds[i];
        return updatedPostion;
    }
    
    /**
     * This methods allow to update the position of the current player after climbing the ladder
     *
     * return  int      Returns the updated position of the current player after climbing the ladder
     * @param1  int     Position of the player
     */
    public int afterLadderUpPosition(int position)
    {
        int updatedPostion , i;
        for(i = 0;i<ladderStarts.length;i++)
        {
            if(ladderStarts[i] == position)
                break;
        }
        
        System.out.println(i);
        updatedPostion = ladderEnds[i];
        return updatedPostion;
    }
    
    /**
     * This methods allow to retrieve the original position of the current player before climbing the ladder
     *
     * return  int      Returns the original position of the current player before climbing the ladder
     * @param1  int     Position of the player
     */
    public int ladderFind(int position)
    {
        int updatedPostion , i;
        for(i = 0;i<ladderEnds.length;i++)
        {
            if(ladderEnds[i] == position)
                break;
        }
        
        System.out.println(i);
        updatedPostion = ladderStarts[i];
        return updatedPostion;
    }
    
    /**
     * This methods allow to retrieve the original position of the current player before getting bite from the snake
     *
     * return  int      Returns the original position of the current player before getting bite from the snake
     * @param1  int     Position of the player
     */
    public int snakeFind(int position)
    {
        int updatedPostion , i;
        for(i = 0;i<snakeEnds.length;i++)
        {
            if(snakeEnds[i] == position)
                break;
        }
        
        System.out.println(i);
        updatedPostion = snakeStarts[i];
        return updatedPostion;
    }
}
}