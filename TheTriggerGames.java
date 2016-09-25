/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    The Trigger Games Program by: Jonathan Lee, Matthew Wong, and Ethan Liong
//    Version: 3.0
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Import stuff to make program work
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;

public class TheTriggerGames implements ActionListener, MouseListener, MouseMotionListener, KeyListener{    //Stuff is implemented to make the program work
  //Properties
  public JFrame theframe;            //A frame is created for the program
  public JGraphics thepanel;         //A panel is created for the GUI
  public Timer thetimer;             //A timer for the time
  public SuperSocketMaster ssm;      //Super Socket Master used for networking
  
  public JTextField texttosend;      //TextField for sending messages
  public JTextArea textreceived;     //TextArea for receiving messages
  public JScrollPane thescroll;      //This allows scrolling in the TextArea
  public JTextField theIP;           //TextField for the IP Address
  public JTextField theUserName;     //TextField for the user's username
  public JButton startserver;        //Button to start server
  public JButton startclient;        //Button to start client
  public JButton wait;               //Wait button for waiting for clients to connect
  public JButton restart;            //Restart button to restart the game
  public JButton exit;               //Exit button to exit the game
  public JButton instructions;       //Instructions button to show instructions
  public JButton play;               //Main menu button that starts the game
  public JButton quit;               //Main menu button that exits the game
  
  public JLabel theLabel1;        //Labels for the text in the main menu
  public JLabel theLabel2;        //
  public JLabel theLabel3;        //
  
  public JRadioButton wallbutton;   //Radio buttons for the game master's abilities
  public JRadioButton acidbutton;   //
  public JRadioButton itembutton;   //
  public ButtonGroup servergroup;     //These buttons are in this group
  
  public JRadioButton fistbutton;    //Radio buttons for each client's abilities
  public JRadioButton knifebutton;   //
  public JRadioButton spearbutton;   //
  public JRadioButton axebutton;     //
  public JRadioButton absorbbutton;  //
  public JRadioButton healbutton;    //
  public ButtonGroup clientgroup;      //These buttons are in this group
  
  public JRadioButton knifeitem;   //Radio buttons for the items that the game master can drop
  public JRadioButton spearitem;   //
  public JRadioButton axeitem;     //
  public JRadioButton absorbitem;  //
  public JRadioButton healitem;    //
  public JRadioButton healthitem;  //
  public JRadioButton dummyitem;   //
  public ButtonGroup itemgroup;      //These buttons are in this group
  
  public String strMessage = "abcdefghijklmnopqrstuvwxyz";    //String variable for all messages recieved via ssm
  
  public String[] strPositionMessage;  //Array String variables used for splitting the strMessage that is recieved via ssm for different purposes
  public String[] strWallMessage;      //
  public String[] strSmokeMessage;     //
  public String[] strNameMessage;      //
  public String[] strMessageMessage;   //
  public String[] strAttackMessage;    //
  public String[] strHPMessage;        //
  public String[] strPlaceMessage;     //
  public String[] strItemMessage;      //
  
  public int intCount;               //Counter variables
  public int intCount2;              //
  public int intTimer = 0;           //Time counting variables
  public int intTimer2 = 0;          //
  public int intRandom = 0;          //Random number variables
  public int intRandom2 = 0;         //
  public int intRandom3 = 0;         //
  
  public int intPlayerCount=0;         //Variables for counting the number of players
  public int intPlayerCountPrev=-1;    //
  public int intPlayerNumber=0;        //The user's player number
  public int intDead = 0;              //Variables for the number of dead players
  public int intDead2 = 0;             //
  
  public int intDamage = 0;          //Variable that contains how much damage a weapon will do
  
  public int[][] intpositions;             //Array for all the default spawn positions for each player
  
  public boolean blnConnected;             //Boolean for the player connects to the server
  public boolean blnClient;                //Boolean for if this user is a client or not
  public boolean blnGameStart = false;     //Boolean for if the game started or not
  public boolean blnServerStart = false;   //Boolean for if the server started the game or not
  
  public boolean blnBuildWall;                //Boolean for if the game master wants to build a wall
  public boolean blnCLICKED = false;          //Boolean for if the game master is currently building a wall
  public boolean blnFailWhisper = false;      //Boolean for if a user fails a whisper
  public boolean blnNameTake = false;         //Boolean for if a user's name is taken
  
  public boolean blnItem1 = false;       //Boolean for if the user already has a certain item
  public boolean blnItem2 = false;       //
  public boolean blnItem3 = false;       //
  public boolean blnItem4 = false;       //
  public boolean blnItem5 = false;       //
  public boolean blnWait = false;          //Boolean for if the server is waiting for clients or not
  public boolean blnItemDrop = false;        //Boolean for when the user will drop his/her items if he/she is dead
  
  public boolean blnSendPlaces = false;    //Boolean for if the server needs to send all the clients everyone's ranking
  public boolean blnSendWinner = false;    //Boolean for sending the message of who the winner is to all players
  
  Image imgPlay;          //Picture for the play button
  Image imgQuit;          //Picture for the quit button
  
  public void actionPerformed(ActionEvent evt){  //Method for when an action is performed
    if(evt.getSource() == thetimer){               //If the timer triggers an event
      if(thepanel.blnCountDown){                     //If the countdown has started
        thepanel.intTimer -= 1.0;                      //Start a certain countdown timer
      }
      intTimer2++;                                   //A timer is counting up
      if(intPlayerCount == 11){                        //If intPlayerCount counts 11
        intPlayerCount++;                                //There are 12 clients
        ssm.sendText("Clientsss"+(intPlayerCount));      //Send certain strings via ssm to trigger events on other user's programs
        ssm.sendText("GameSettings");                    //
        this.wait.setEnabled(false);                     //No need to wait anymore
        thepanel.remove(this.wait);                      //Wait button is gone
        thepanel.remove(this.instructions);              //Instructions button is gone
        GameSettings();                                //Game settings method is triggered
      }
      if(intPlayerCount < 1 && !blnClient && blnWait){          //If there aren't enough players connected and you are the server, you cannot press the wait button
        this.wait.setEnabled(false);
      }else if(!blnClient && intPlayerCount >= 1 && blnWait){   //Else if there are enough players connected and you are the server, you can now press the wait button
        this.wait.setEnabled(true);
      }
      if(blnNameTake){                     //If your name is taken, tell the user that via textreceived TextArea
        this.textreceived.append("Sorry, your name was taken\n");
        this.textreceived.append("Your new name is: " + thepanel.warriors[intPlayerNumber].strName + "\n");
        blnNameTake = false;        //This for loop cannot be accessed again
      }
      if(thepanel.blnCheckName){              //If player's name needs to be checked
        if(!thepanel.strPName.equals("")){      //If the player entered a username
          blnNameTake = false;                    //Name taken is currently false
          for(intCount=0;intCount<intPlayerCount;intCount++){ //For loop to count through all the players
            if(thepanel.strPName.equals(thepanel.warriors[intCount].strName)){  //If the user's name is the same as another user's name, then Name taken is true
              blnNameTake = true;
            }
          }
          if(!blnNameTake){       //If the Name taken is false
            ssm.sendText("Name,"+intPlayerNumber+","+thepanel.strPName);      //Tell everyone your name via ssm
            thepanel.warriors[intPlayerNumber].strName = thepanel.strPName;   //Your name is now official
          }
          thepanel.blnCheckName = false;    //No need to check name again since this variable is now false
        }
      }
      intDead = 0;  //Set number of dead to 0
      for(intCount=0;intCount<intPlayerCount;intCount++){  //For loop to check through every player, then check if any player is dead, and add 1 to intDead for every dead person
        if(blnGameStart){
          if(thepanel.warriors[intCount].dblHP <= 0){
            intDead++;
          }
        }
      }
      if(intDead >= intPlayerCount-1){  //If there is 1 person alive
        thepanel.blnGameSet = true;       //Game is set
      }
      if(thepanel.blnGameSet && thepanel.blnShowScores){    //If the game is done and the scores can be shown, change certain button positions
        this.restart.setSize(360,50);
        this.restart.setLocation(0,670);
        this.exit.setSize(360,50);
        this.exit.setLocation(360,670);
      }
      if(!blnGameStart){        //If the game didn't start yet
        if(!blnClient){           //If you are the server
          this.wait.setText("Click to Start (Number of players connected: " + (intPlayerCount+1) + ") (At least 2 players (clients) are needed to play)");  //Change the wait button text appropriately
        }
        if(intPlayerCountPrev != intPlayerCount && blnServerStart){                               //If the playercount changes, inform the server
          this.textreceived.append("A player has connected\n");
          this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
          intPlayerCountPrev = intPlayerCount;  //Current playercount is recorded
        }
      }
      if(blnGameStart){           //If the game has started
        for(intCount=0;intCount<intPlayerCount;intCount++){  //Count through all the players
          if(thepanel.warriors[intCount].dblHP > 100){         //If the player has more than 100 HP, and that player is you, then your health is just 100
            if(intCount == intPlayerNumber){
              this.textreceived.append("You are at max health!\n");
              this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
            }
            thepanel.warriors[intCount].dblHP = 100;
          }else if(thepanel.warriors[intCount].dblHP < 0){     //Else if the player has less than 0 HP, and that player is you, then your health is just 0
            if(intCount == intPlayerNumber){
              this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
              thepanel.warriors[intCount].dblHP = 0;
            }
          }
        }
        if(!blnClient && intTimer2%100==0){         //If you are the server and if a certain timer is divisable by 100
          intRandom = (int)(Math.random()*7+1);       //3 random numbers are generated
          intRandom2 = (int)(Math.random()*58+1);     //
          intRandom3 = (int)(Math.random()*58+1);     //
          if(intRandom==1){                                          //If a certain random number equals a certain number
            ssm.sendText("IPU,a,"+(intRandom2)+","+(intRandom3));      //Send a message via ssm to randomly drop an item on the map
            thepanel.strMapChar[intRandom2][intRandom3] = "a";         //
          }
          if(intRandom==2){                                          //
            ssm.sendText("IPU,b,"+(intRandom2)+","+(intRandom3));      //
            thepanel.strMapChar[intRandom2][intRandom3] = "b";         //
          }
          if(intRandom==3){                                          //
            ssm.sendText("IPU,c,"+(intRandom2)+","+(intRandom3));      //
            thepanel.strMapChar[intRandom2][intRandom3] = "c";         //
          }
          if(intRandom==4){                                          //
            ssm.sendText("IPU,d,"+(intRandom2)+","+(intRandom3));      //
            thepanel.strMapChar[intRandom2][intRandom3] = "d";         //
          }
          if(intRandom==5){                                          //
            ssm.sendText("IPU,h,"+(intRandom2)+","+(intRandom3));      //
            thepanel.strMapChar[intRandom2][intRandom3] = "h";
          }
          if(intRandom==6){                                          //
            ssm.sendText("IPU,x,"+(intRandom2)+","+(intRandom3));      //
            thepanel.strMapChar[intRandom2][intRandom3] = "x";         //
          }
          if(intRandom==7){                                          //
            ssm.sendText("IPU,h2,"+(intRandom2)+","+(intRandom3));     //
            thepanel.strMapChar[intRandom2][intRandom3] = "h2";        //
          }
          if(!blnClient){
            for(intCount=0;intCount<intPlayerCount;intCount++){
              ssm.sendText("UserHP," + intCount + "," + thepanel.warriors[intCount].dblHP);  //Every player's health is updated to all the players via ssm
            }
          }
        }
        if(thepanel.intSmoke[2]>0){                                   //If a certain ability hasn't cooled down yet, inform the user
          acidbutton.setText("(Cooldown: "+thepanel.intSmoke[2]+")"+ " Acid Fog Spawner (Click on map to spawn on map)");
        }else{                                                        //Otherwise, inform the user that the ability has cooled down
          acidbutton.setText("Acid Fog Spawner (Click on map to spawn on map)");
        }
        if(thepanel.dblFistCooldown > 0){                             //
          fistbutton.setText("(Cooldown: "+(((int)(thepanel.dblFistCooldown*1000))/1000.0)+")"+ " Fists");
        }else{                                                        //
          fistbutton.setText("Fists");
        }
        if(thepanel.dblKnifeCooldown > 0){                            //
          knifebutton.setText("(Cooldown: "+(((int)(thepanel.dblKnifeCooldown*1000))/1000.0)+")"+ " Knife");
        }else{                                                        //
          knifebutton.setText("Knife");
        }
        if(thepanel.dblSpearCooldown > 0){                            //
          spearbutton.setText("(Cooldown: "+(((int)(thepanel.dblSpearCooldown*1000))/1000.0)+")"+ " Spear");
        }else{                                                        //
          spearbutton.setText("Spear");
        }
        if(thepanel.dblAxeCooldown > 0){                              //
          axebutton.setText("(Cooldown: "+(((int)(thepanel.dblAxeCooldown*1000))/1000.0)+")"+ " Axe");
        }else{                                                        //
          axebutton.setText("Axe");
        }
        if(thepanel.dblAbsorbCooldown > 0){                           //
          absorbbutton.setText("(Cooldown: "+(((int)(thepanel.dblAbsorbCooldown*1000))/1000.0)+")"+ " Absorb");
        }else{                                                        //
          absorbbutton.setText("Absorb");
        }
        if(thepanel.dblHealCooldown > 0){                             //
          healbutton.setText("(Cooldown: "+(((int)(thepanel.dblHealCooldown*1000))/1000.0)+")"+ " Heal (ITEM)");
        }else{                                                        //
          healbutton.setText("Heal (ITEM)");
        }
        if(!blnSendPlaces && intPlayerCount-intDead2==1 && !blnClient && intTimer2%25 == 0){         //If places weren't sent yet and 1 player is alive and if this user is the server
          for(intCount=0;intCount<intPlayerCount;intCount++){
            ssm.sendText("Place,"+intCount+","+thepanel.strPlaces[intCount]);     //Send all players the game rankings
          }
          blnSendPlaces = true;  //Places have been sent
        }
        if(thepanel.warriors[intPlayerNumber].blnWin && thepanel.warriors[intPlayerNumber].dblHP <= 0 && blnItemDrop == false){  //If this player has no more health left
          if(thepanel.warriors[intPlayerNumber].blnItem1){                                                 //If this player has a certain item, drop the item, and inform other players via ssm
            ssm.sendText("IPU,a,"+((int)(thepanel.warriors[intPlayerNumber].intPosX/36.0)-1)+","+((int)(thepanel.warriors[intPlayerNumber].intPosY/36.0)-1));
            thepanel.strMapChar[(int)((thepanel.warriors[intPlayerNumber].intPosX/36.0)-1)][(int)((thepanel.warriors[intPlayerNumber].intPosY/36.0)-1)] = "a";
          }
          if(thepanel.warriors[intPlayerNumber].blnItem2){                                                 //
            ssm.sendText("IPU,b,"+((int)(thepanel.warriors[intPlayerNumber].intPosX/36.0)+1)+","+((int)(thepanel.warriors[intPlayerNumber].intPosY/36.0)-1));
            thepanel.strMapChar[(int)((thepanel.warriors[intPlayerNumber].intPosX/36.0)+1)][(int)((thepanel.warriors[intPlayerNumber].intPosY/36.0)-1)] = "b";
          }
          if(thepanel.warriors[intPlayerNumber].blnItem3){                                                 //
            ssm.sendText("IPU,c,"+((int)(thepanel.warriors[intPlayerNumber].intPosX/36.0)-1)+","+((int)(thepanel.warriors[intPlayerNumber].intPosY/36.0)+1));
            thepanel.strMapChar[(int)((thepanel.warriors[intPlayerNumber].intPosX/36.0)-1)][(int)((thepanel.warriors[intPlayerNumber].intPosY/36.0)+1)] = "c";
          }
          if(thepanel.warriors[intPlayerNumber].blnItem4){                                                 //
            ssm.sendText("IPU,d,"+((int)(thepanel.warriors[intPlayerNumber].intPosX/36.0)+1)+","+((int)(thepanel.warriors[intPlayerNumber].intPosY/36.0)+1));
            thepanel.strMapChar[(int)((thepanel.warriors[intPlayerNumber].intPosX/36.0)+1)][(int)((thepanel.warriors[intPlayerNumber].intPosY/36.0)+1)] = "d";
          }
          if(thepanel.warriors[intPlayerNumber].blnItem5){                                                 //
            ssm.sendText("IPU,h,"+((int)(thepanel.warriors[intPlayerNumber].intPosX/36.0))+","+((int)(thepanel.warriors[intPlayerNumber].intPosY/36.0)));
            thepanel.strMapChar[(int)((thepanel.warriors[intPlayerNumber].intPosX/36.0))][(int)((thepanel.warriors[intPlayerNumber].intPosY/36.0))] = "h";
          }
          blnItemDrop = true;
        }
        if(!blnClient){        //If you are the server
          for(intCount=0;intCount<intPlayerCount;intCount++){  //Count through all the players
            if(thepanel.warriors[intCount].blnWin && thepanel.warriors[intCount].dblHP <= 0){    //If this player has no more health
              intDead2++;                                                                          //Number of dead people increase
              thepanel.strPlaces[intPlayerCount-intDead2] = thepanel.warriors[intCount].strName;   //Note this player's rank
              thepanel.warriors[intCount].blnWin=false;                                            //This player didn't win
              ssm.sendText("msg"+thepanel.warriors[intCount].strName+" is dead"+",S,P,L,I,T,-1");                 //Inform players of this player's death
              this.textreceived.append("Game Master (YOU): "+thepanel.warriors[intCount].strName+" is dead\n");   //
              this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
            }
          }
          for(intCount=0;intCount<intPlayerCount;intCount++){  //Count through all the players
            if(thepanel.warriors[intCount].blnWin && intPlayerCount-intDead2 == 1){   //If this player is the last player alive
              thepanel.strPlaces[0] = thepanel.warriors[intCount].strName;              //This player is first place
              thepanel.blnShowScores = true;                                            //Scores will now be shown
              if(!blnSendWinner){                 //If the winner message isn't sent yet
                ssm.sendText("msg"+thepanel.warriors[intCount].strName+" has won!"+",S,P,L,I,T,-1");                 //Inform players of this player's victory
                this.textreceived.append("Game Master (YOU): "+thepanel.warriors[intCount].strName+" has won!\n");   //
                this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
                blnSendWinner = true;               //The winner message is sent
              }
            }
          }
        }
        intTimer += ((3.0+1.0/3.0)/120.0);                //A timer is counting up
        for(intCount=0;intCount<100;intCount++){          //Count through all the walls
          thepanel.intWalls[intCount][2] -= ((1)/120.0);    //Cooldowns for the walls are decreasing
        }
        thepanel.intSmoke[3] += (3.25);                 //Radius of the smoke gets bigger
        thepanel.intSmoke[2] -= ((1)/200.0);            //Decrease the cooldowns for abilities
        thepanel.dblFistCooldown -= ((90.0)/200.0);     //
        thepanel.dblKnifeCooldown -= ((90.0)/200.0);    //
        thepanel.dblSpearCooldown -= ((90.0)/200.0);    //
        thepanel.dblAxeCooldown -= ((90.0)/200.0);      //
        thepanel.dblAbsorbCooldown -= ((90.0)/200.0);   //
        thepanel.dblHealCooldown -= ((90.0)/200.0);     //
        if(thepanel.warriors[intPlayerNumber].blnItem1 && !blnItem1 && blnClient){          //If a player picks up an item he doesn't have, he gets that weapon
          thepanel.add(knifebutton);
          blnItem1 = true;
        }else if(thepanel.warriors[intPlayerNumber].blnItem2 && !blnItem2 && blnClient){    //
          thepanel.add(spearbutton);
          blnItem2 = true;
        }else if(thepanel.warriors[intPlayerNumber].blnItem3 && !blnItem3 && blnClient){    //
          thepanel.add(axebutton);
          blnItem3 = true;
        }else if(thepanel.warriors[intPlayerNumber].blnItem4 && !blnItem4 && blnClient){    //
          thepanel.add(absorbbutton);
          blnItem4 = true;
        }else if(thepanel.warriors[intPlayerNumber].blnItem5 && !blnItem5 && blnClient){    //
          thepanel.add(healbutton);
          blnItem5 = true;
        }
        for(intCount=0;intCount<intPlayerCount;intCount++){     //Count through all the players
          for(intCount2=0;intCount2<5;intCount2++){               //Count through all the items
            thepanel.dblPictureTime[intCount][intCount2][0] -= ((90.0)/200.0); //The time for a picture is decreased
          }
        }
      }
      
      thepanel.blnStart = blnGameStart;  //Tell the JGraphics that the game has started if it did start
      thepanel.repaint();                //Repaint the panel
      if(blnGameStart && intPlayerCount>0 && thepanel.blnSend){    //If the game has started and there are multiple players and Sending is made possible
        ssm.sendText("mov,"+intPlayerNumber+","+thepanel.warriors[intPlayerNumber].intPosX+","+thepanel.warriors[intPlayerNumber].intPosY);  //Inform other players of your position via ssm
        this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
        thepanel.blnSend = false;    //Sending is made impossible for now (so data isn't being sent every second)
      }
      if(!blnClient && blnGameStart){        //If you are the server and the game has started
        for(intCount=0;intCount<100;intCount++){    //Count through all the walls
          if(thepanel.intWalls[intCount][2] > 320 || (thepanel.intWalls[intCount][2] > 0 && thepanel.intWalls[intCount][2] < 5)){  //If the wall timer variable in the array is at a certain time
            ssm.sendText("WALL,"+thepanel.intWalls[intCount][0] + "," + thepanel.intWalls[intCount][1] + "," + thepanel.intWalls[intCount][2] + "," + intCount);  //Tell all players if the wall is up or not based on its characteristics
          }
        }
        if((thepanel.intSmoke[2]>0 && thepanel.intSmoke[2] < 5) || thepanel.intSmoke[2] > 245){    //Tell all players the characteristics of the smoke at certain intervals of time recorded by a smoke array variable
          ssm.sendText("SMOKE,"+thepanel.intSmoke[0]+","+thepanel.intSmoke[1]+","+thepanel.intSmoke[2] + "," + thepanel.intSmoke[3]);
        }
      }
    }
    if(evt.getSource() == play){
      thepanel.add(this.theIP);                       //Everything is added to the panel
      thepanel.add(this.theUserName);                 //
      thepanel.add(this.startserver);                 //                   
      thepanel.add(this.startclient);                 //                  
      thepanel.add(theLabel1);                        //                                                              
      thepanel.add(theLabel2);                        //  
      thepanel.add(theLabel3);                        //
      thepanel.blnMainMenu = false;                   //Tells the graphics to take off the main menu background
      
      thepanel.remove(play);                          //Removes the main menu buttons from the panel
      thepanel.remove(quit);                          //
    }
    if(evt.getSource() == quit){
      System.exit(0);                      //Game exits
    }
    if(evt.getSource() == wallbutton){          //If a radiobutton is pressed, let the frame be focused again
      theframe.requestFocus();
      blnCLICKED = false;  //Wall making is off since blnClicked is false
    }else if(evt.getSource() == acidbutton){    //
      theframe.requestFocus();
    }else if(evt.getSource() == itembutton){    //
      theframe.requestFocus();
    }else if(evt.getSource() == fistbutton){    //
      theframe.requestFocus();
    }else if(evt.getSource() == knifebutton){   //
      theframe.requestFocus();
    }else if(evt.getSource() == spearbutton){   //
      theframe.requestFocus();
    }else if(evt.getSource() == axebutton){     //
      theframe.requestFocus();
    }else if(evt.getSource() == absorbbutton){  //
      theframe.requestFocus();
    }else if(evt.getSource() == healbutton){    //
      theframe.requestFocus();
    }else if(evt.getSource() == knifeitem){     //
      theframe.requestFocus();
    }else if(evt.getSource() == spearitem){     //
      theframe.requestFocus();
    }else if(evt.getSource() == axeitem){       //
      theframe.requestFocus();
    }else if(evt.getSource() == absorbitem){    //
      theframe.requestFocus();
    }else if(evt.getSource() == healitem){      //
      theframe.requestFocus();
    }else if(evt.getSource() == healthitem){    //
      theframe.requestFocus();
    }else if(evt.getSource() == dummyitem){     //
      theframe.requestFocus();
    }
    if(evt.getSource() == this.restart){    //If the restart button is pressed
      ssm.sendText("GameSettings");           //Restart the game by resetting certain variables while telling all clients to restart as well via ssm
      thepanel.intSX = 1080;
      thepanel.intSY = 1080;
      ssm.sendText("Restart");
      thepanel.blnLoad = true;
      wallbutton.setSelected(false);
      acidbutton.setSelected(false);
      itembutton.setSelected(false);
      GameSettings();
      intDead2 = 0;
      thepanel.blnGameSet = false;
      blnSendPlaces = false;
      this.restart.setSize(280,130);
      this.restart.setLocation(720,590);      
      this.exit.setSize(280,130);
      this.exit.setLocation(1000,590);
      thepanel.blnCountDown = false;
      blnSendWinner = false;
    }
    if(evt.getSource() == this.exit){    //If the exit button is pressed
      ssm.sendText("exit");                //Tell all clients to exit via ssm
      System.exit(0);                      //Game exits
    }
    if(evt.getSource() == this.startserver){    //If the start server button is pressed
      thepanel.remove(theLabel1);                                         //Edit the GUI accordingly
      thepanel.remove(theLabel2);                                         //
      thepanel.remove(theLabel3);                                         //
      blnWait = true;                                                     //You will now wait
      thepanel.strPName = this.theUserName.getText();                     //Your user name is noted, not that it matters since you are the server
      blnClient = false;                                                  //You are not a client
      this.textreceived.append("Starting server\n");
      this.textreceived.append("Waiting for clients to connect...\n");
      ssm = new SuperSocketMaster(1773,this);                             //ssm is created
      this.textreceived.append(ssm.getMyAddress()+"\n");                  //Your IP address is printed oiut
      blnConnected = ssm.connect();                                       //Find out if you are connected or not
      if(blnConnected){                                                   //If you are connected
        this.startclient.setEnabled(false);                                 //Some GUI stuff is defaulted
        this.startserver.setEnabled(false);
        thepanel.add(this.thescroll);
        thepanel.add(this.texttosend);
        thepanel.remove(this.startclient);
        thepanel.remove(this.startserver);
        thepanel.remove(this.theIP);
        thepanel.remove(this.theUserName);
        theframe.requestFocus();                //the frame now has focus
      }
      intPlayerCount--;           //PlayerCount variable is adjusted since you are the server
      blnServerStart = true;        //Server has now started
      this.wait.setEnabled(true);     //You will now wait
      thepanel.add(this.wait);        //Waiting button is added
      this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
    }else if(evt.getSource() == this.startclient){             //Else if the client button is pressed
      if(!this.theIP.getText().equals("")){                           //If the IP TextField has text in it
        ssm = new SuperSocketMaster(this.theIP.getText(),1773,this);    //ssm is created to connect to server
        blnConnected = ssm.connect();                                   //Check if you are connected
        if(blnConnected){                                               //If you are connected
          this.startclient.setEnabled(false);                             //Some GUI stuff is defaulted
          this.startserver.setEnabled(false);
          thepanel.add(this.thescroll);
          thepanel.add(this.texttosend);
          thepanel.remove(this.startclient);
          thepanel.remove(this.startserver);
          thepanel.remove(this.theIP);
          thepanel.remove(this.theUserName);
          thepanel.remove(theLabel1);
          thepanel.remove(theLabel2);
          thepanel.remove(theLabel3);
          blnClient = true;                                           //You are a client
          this.textreceived.append("Starting client\n");
          this.textreceived.append("You have connected to the server\n");
          thepanel.strPName = this.theUserName.getText();             //Your username is noted
          this.wait.setEnabled(false);
          thepanel.add(this.wait);
          this.wait.setText("Waiting...");
          intPlayerNumber=intPlayerCount;
          ssm.sendText("Increase");
          this.wait.setEnabled(false);                                //You cannot press the wiat button
          this.textreceived.append("You are currently player: " +intPlayerNumber+"\n");
          this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
        }
      }
    }else if(evt.getSource() == this.instructions){
      JOptionPane.showMessageDialog(thepanel, "HOW TO PLAY THE TRIGGER GAMES: \n\n As the Game Master: \n You are to play as a neutral player in the game, possibly helping or sabotaging players \n You can build walls, spawn acid fog, or spawn items of your choice on the map \n\n As a Warrior: \n Your objective is to fight all the other warriors to the death \n Win by eliminating all the other players using your special abilities \n Gain more special abilities by collecting items that are on the map \n\n Controls:\n Use W/S/A/D to move Up/Down/Left/Right on tha map \n Choose your special ability in the bottom right box \n As a warrior, use your special ability by pressing the space bar in the direction you are facing \n As the Game Master, use your special abilities by clicking on the map \n"); //A window popup is popped up with How-To-Play information
    }else if(evt.getSource() == this.texttosend){                       //Else if the event is from sending a text
      if(blnClient){                                                      //If you are a client, send a certain string to all other players
        ssm.sendText("msg"+this.texttosend.getText()+",S,P,L,I,T,"+intPlayerNumber);
      }else{                                                              //Else you are the server, send a certain string to all other players
        ssm.sendText("msg"+this.texttosend.getText()+",S,P,L,I,T,-1");
      }
      if(!this.texttosend.getText().substring(0,1).equals("/") || blnClient){    //If you are not sending a whisper or you are a client
        if(blnClient){                                                             //Update the texreceived TextArea appropriately
          this.textreceived.append(thepanel.warriors[intPlayerNumber].strName + " (YOU): " + this.texttosend.getText() + "\n");
        }else{
          this.textreceived.append("Game Master (YOU): " + this.texttosend.getText() + "\n");
        }
        blnFailWhisper = true;             //Fail whisper is currently true
        this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
        for(intCount=0;intCount<intPlayerCount;intCount++){      //Count through all the players, checking if they sent a valid whisper to a real player
          if(this.texttosend.getText().substring(0,1).equals("/") && this.texttosend.getText().substring(1,this.texttosend.getText().length()).length() >= thepanel.warriors[intCount].strName.length()){
            if(this.texttosend.getText().substring(1,thepanel.warriors[intCount].strName.length()+1).equals(thepanel.warriors[intCount].strName)){
              blnFailWhisper = false;     //If the user didn't send a proper whisper, then they failed a whisper
            }
          }
        }
        if(blnFailWhisper && this.texttosend.getText().substring(0,1).equals("/")){     //If the user failed to whisper, tell the user that that
          this.textreceived.append("UNABLE TO FIND PLAYER TO WHISPER TO\n");
        }
      }else{  //Else statement for if you try to send a whisper as a server... tell the user that he cannot send whispers
        this.textreceived.append("YOU CANNOT SEND WHISPERS\n");
      }
      this.texttosend.setText("");    //Set the texttosend TextField to being empty
      this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
    }else if(evt.getSource() == ssm){    //Else if the ssm triggered the event
      strMessage = ssm.readText();         //The message is recorded
      if(strMessage.equals("exit")){         //If the message tells the user to exit, then exit the game
        System.exit(0);
      }
      if(strMessage.length() > 3){           //If the message is longer than 3 characters
        if(strMessage.equals("Increase")){     //If the message or part of the message equals a certain phrase
          intPlayerCount++;
          intPlayerNumber=intPlayerCount;
          if(blnClient){
            this.textreceived.append("You are currently player: " +intPlayerNumber+"\n");
          }
        }else if(strMessage.equals("GameSettings")){     //If the message or part of the message equals a certain phrase
          GameSettings();                      //Trigger the game settings method
        }
        if(strMessage.length() >= 9){        //If the message is longer than or equal to 9 characters
          if(strMessage.substring(0,9).equals("Clientsss")){     //If the message or part of the message equals a certain phrase
            intPlayerCount = Integer.parseInt(strMessage.substring(9,(strMessage.length())));
          }
        }
        if(strMessage.length() >= 3){
          if(strMessage.substring(0,3).equals("mov")){           //If the message or part of the message equals a certain phrase
            strPositionMessage = strMessage.split(",");            //Split the message into a certain array
            thepanel.warriors[Integer.parseInt(strPositionMessage[1])].intPosX = Integer.parseInt(strPositionMessage[2]);
            thepanel.warriors[Integer.parseInt(strPositionMessage[1])].intPosY = Integer.parseInt(strPositionMessage[3]);
          }else if(strMessage.substring(0,3).equals("atk")){     //Else If the message or part of the message equals a certain phrase
            strAttackMessage = strMessage.split(",");              //Split the message into a certain array
            if(Integer.parseInt(strAttackMessage[1]) == 0){          //Depending on what was received, a certain damage is recorded
              intDamage = Integer.parseInt(thepanel.strItem[Integer.parseInt(strAttackMessage[1])][2]);
            }else if(Integer.parseInt(strAttackMessage[1]) == 1){    //
              intDamage = Integer.parseInt(thepanel.strItem[Integer.parseInt(strAttackMessage[1])][2]);
            }else if(Integer.parseInt(strAttackMessage[1]) == 2){    //
              intDamage = Integer.parseInt(thepanel.strItem[Integer.parseInt(strAttackMessage[1])][2]);
            }else if(Integer.parseInt(strAttackMessage[1]) == 3){    //
              intDamage = Integer.parseInt(thepanel.strItem[Integer.parseInt(strAttackMessage[1])][2]);
            }else if(Integer.parseInt(strAttackMessage[1]) == 4){    //
              intDamage = Integer.parseInt(thepanel.strItem[Integer.parseInt(strAttackMessage[1])][2]);
            }
            //A picture time variable is made for a certain attack animation
            thepanel.dblPictureTime[Integer.parseInt(strAttackMessage[6])][Integer.parseInt(strAttackMessage[1])][0] = 1.5;
            thepanel.dblPictureTime[Integer.parseInt(strAttackMessage[6])][Integer.parseInt(strAttackMessage[1])][1] = Integer.parseInt(strAttackMessage[7]);
            //If the player is within a certain range
            if(thepanel.warriors[intPlayerNumber].intPosX > Integer.parseInt(strAttackMessage[2]) && thepanel.warriors[intPlayerNumber].intPosX < Integer.parseInt(strAttackMessage[3]) && thepanel.warriors[intPlayerNumber].intPosY > Integer.parseInt(strAttackMessage[4]) && thepanel.warriors[intPlayerNumber].intPosY < Integer.parseInt(strAttackMessage[5]) && Integer.parseInt(strAttackMessage[6]) != intPlayerNumber){
              thepanel.warriors[intPlayerNumber].dblHP -= intDamage;  //Player is damaged
              if(Integer.parseInt(strAttackMessage[1]) == 4){           //If the attack is from the healing ability
                thepanel.warriors[Integer.parseInt(strAttackMessage[6])].dblHP += 15;  //The player that is attacking is healed by 15 HP
                ssm.sendText("UserHP," + Integer.parseInt(strAttackMessage[6]) + "," + thepanel.warriors[Integer.parseInt(strAttackMessage[6])].dblHP);  //This player's health is updated to all players via ssm
              }
              ssm.sendText("UserHP," + intPlayerNumber + "," + thepanel.warriors[intPlayerNumber].dblHP);  //Your health is updated to all the players via ssm
            }
          }
        }
        if(strMessage.equals("Restart")){    //If the message received is "Restart"
          thepanel.blnLoad = true;             //Game settings are reset for a new game
          thepanel.remove(knifebutton);
          thepanel.remove(spearbutton);
          thepanel.remove(axebutton);
          thepanel.remove(absorbbutton);
          thepanel.remove(healbutton);
          blnSendPlaces = false;
          thepanel.blnShowScores = false;
          fistbutton.setSelected(true);
          intDead2 = 0;
          thepanel.blnGameSet = false;
          thepanel.blnCountDown = false;
          blnItem1 = false;
          blnItem2 = false;
          blnItem3 = false;
          blnItem4 = false;
          blnItem5 = false;
          blnItemDrop = false;
        }
        if(strMessage.substring(0,3).equals("IPU")){     //If the message or part of the message equals a certain phrase
          strItemMessage = strMessage.split(",");          //Split the message into a certain array
          thepanel.strMapChar[Integer.parseInt(strItemMessage[2])][Integer.parseInt(strItemMessage[3])] = strItemMessage[1];
        }
        if(strMessage.substring(0,5).equals("Place")){   //If the message or part of the message equals a certain phrase
          strPlaceMessage = strMessage.split(",");         //Split the message into a certain array
          thepanel.strPlaces[Integer.parseInt(strPlaceMessage[1])] = strPlaceMessage[2];
          thepanel.blnShowScores = true;
        }
        if(strMessage.substring(0,6).equals("UserHP")){  //If the message or part of the message equals a certain phrase
          strHPMessage = strMessage.split(",");            //Split the message into a certain array
          thepanel.warriors[Integer.parseInt(strHPMessage[1])].dblHP = Double.parseDouble(strHPMessage[2]);
        }
        if(strMessage.substring(0,4).equals("WALL")){    //If the message or part of the message equals a certain phrase
          strWallMessage = strMessage.split(",");          //Split the message into a certain array
          thepanel.intWalls[Integer.parseInt(strWallMessage[4])][0] = Integer.parseInt(strWallMessage[1]);    //A certain wall's properties are updated
          thepanel.intWalls[Integer.parseInt(strWallMessage[4])][1] = Integer.parseInt(strWallMessage[2]);    //
          thepanel.intWalls[Integer.parseInt(strWallMessage[4])][2] = Integer.parseInt(strWallMessage[3]);    //
        }else if(strMessage.substring(0,5).equals("SMOKE")){     //If the message or part of the message equals a certain phrase
          strSmokeMessage = strMessage.split(",");                 //Split the message into a certain array
          thepanel.intSmoke[0] = Integer.parseInt(strSmokeMessage[1]);    //The smoke's properties are updated
          thepanel.intSmoke[1] = Integer.parseInt(strSmokeMessage[2]);    //
          thepanel.intSmoke[2] = Integer.parseInt(strSmokeMessage[3]);    //
          thepanel.intSmoke[3] = Integer.parseInt(strSmokeMessage[4]);    //
        }
        if(strMessage.substring(0,3).equals("msg")){             //If the message or part of the message equals a certain phrase
          strMessageMessage = strMessage.split(",S,P,L,I,T,");
          if(strMessageMessage[0].length() != 3){                  //If the message is not just the 3 letter mnemonic
            if(strMessageMessage[0].substring(3,4).equals("/")){     //If the message is a whisper
              if(strMessageMessage[0].substring(4,strMessageMessage[0].length()).length() > thepanel.warriors[intPlayerNumber].strName.length()){   //If part of the message is longer than your name length
                if(strMessageMessage[0].substring(4,thepanel.warriors[intPlayerNumber].strName.length()+4).equals(thepanel.warriors[intPlayerNumber].strName) && blnClient){ //If you are the player being whispered to
                  this.textreceived.append(thepanel.warriors[Integer.parseInt(strMessageMessage[1])].strName + " (WHISPER): "+strMessageMessage[0].substring(thepanel.warriors[intPlayerNumber].strName.length()+5,strMessageMessage[0].length()) + "\n");  //You will receive the whisper
                  this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
                }
              }
              if(!blnClient){    //If you are the server, then you will receive all messages
                this.textreceived.append(thepanel.warriors[Integer.parseInt(strMessageMessage[1])].strName + ": "+strMessageMessage[0].substring(3,strMessageMessage[0].length()) + "\n");
                this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
              }
            }else if(!strMessageMessage[0].substring(3,4).equals("/") || !blnClient){     //Else if the message isn't a whisper
              if(!strMessageMessage[1].equals("-1")){                                       //If the message is from a client, update the chat box appropriately
                this.textreceived.append(thepanel.warriors[Integer.parseInt(strMessageMessage[1])].strName + ": "+strMessageMessage[0].substring(3,strMessageMessage[0].length()) + "\n");
              }else{                                                                        //Else the message is from the server, update the chat box appropriately
                this.textreceived.append("Game Master: "+strMessageMessage[0].substring(3,strMessageMessage[0].length()) + "\n");
              }
              this.textreceived.setCaretPosition(this.textreceived.getDocument().getLength());
            }
            strMessage = "abcdefghijklmnopqrstuvwxyz";  //Message variable is set to be gibberish
          }
        }
        if(strMessage.substring(0,4).equals("Name")){   //If part of the message contains "Name"
          strNameMessage = strMessage.split(",");         //Split the message into a certain array
          thepanel.warriors[Integer.parseInt(strNameMessage[1])].strName = strNameMessage[2];  //A certain warrior's name is updated
        }
      }
    }
    
    if(evt.getSource() == this.wait){              //If the wait button is pressed
      intPlayerCount++;                              //Player Count is adjusted since you are the server
      ssm.sendText("Clientsss"+(intPlayerCount));    //Tell all the clients the number of players of the game
      ssm.sendText("GameSettings");                  //Tell all the clients to trigger the game settings method
      this.wait.setEnabled(false);                   //Default GUI stuff for the game is set
      wallbutton.setSelected(false);
      acidbutton.setSelected(false);
      itembutton.setSelected(false);
      knifeitem.setSelected(true);
      thepanel.remove(this.wait);
      thepanel.remove(this.instructions);
      GameSettings();                //Trigger the game settings method
    }
  }
  
  public void GameSettings(){     //Game settings method for setting the game up
    if(blnClient){                  //If you are the client, record your player number
      thepanel.intPlayerN = intPlayerNumber;
    }else{                          //Else you are the server, your player number is -1 so it is irrelevant to the game
      thepanel.intPlayerN = -1;
    }
    thepanel.intTotalPlayers = intPlayerCount;  //JGraphics is told the amount of players in the game
    thepanel.blnClient = blnClient;             //JGraphics is told if you are a client or not
    thepanel.gamestart();                       //JGraphics game start method is triggered
    intpositions = new int[12][2];       //Position values are created for all potential 12 players
    intpositions[0][0] = 64;             //
    intpositions[0][1] = 64;             //
    intpositions[1][0] = 2096;           //
    intpositions[1][1] = 2096;           //
    intpositions[2][0] = 64;             //
    intpositions[2][1] = 2096;           //
    intpositions[3][0] = 2096;           //
    intpositions[3][1] = 64;             //
    intpositions[4][0] = 64;             //
    intpositions[4][1] = 720;            //
    intpositions[5][0] = 64;             //
    intpositions[5][1] = 1440;           //
    intpositions[6][0] = 2096;           //
    intpositions[6][1] = 720;            //
    intpositions[7][0] = 2096;           //
    intpositions[7][1] = 1440;           //
    intpositions[8][0] = 1440;           //
    intpositions[8][1] = 64;             //
    intpositions[9][0] = 720;            //
    intpositions[9][1] = 64;             //
    intpositions[10][0] = 1440;          //
    intpositions[10][1] = 2096;          //
    intpositions[11][0] = 720;           //
    intpositions[11][1] = 2096;          //
    if(intPlayerCount>0){          //Depending on how many players there are, set certain player's/warrior's user color to a certain color
      thepanel.warriors[0].UserColor = Color.BLACK;
      if(intPlayerCount>1){          //
        thepanel.warriors[1].UserColor = Color.RED;
        if(intPlayerCount>2){          //
          thepanel.warriors[2].UserColor = Color.GREEN;
          if(intPlayerCount>3){          //
            thepanel.warriors[3].UserColor = Color.BLUE;
            if(intPlayerCount>4){          //
              thepanel.warriors[4].UserColor = Color.MAGENTA;
              if(intPlayerCount>5){          //
                thepanel.warriors[5].UserColor = Color.ORANGE;
                if(intPlayerCount>6){          //
                  thepanel.warriors[6].UserColor = Color.GRAY;
                  if(intPlayerCount>7){          //
                    thepanel.warriors[7].UserColor = Color.YELLOW;
                    if(intPlayerCount>8){          //
                      thepanel.warriors[8].UserColor = new Color(139,69,19);
                      if(intPlayerCount>9){          //
                        thepanel.warriors[9].UserColor = new Color(218,165,32);
                        if(intPlayerCount>10){          //
                          thepanel.warriors[10].UserColor = new Color(210,180,140);
                          if(intPlayerCount>11){          //
                            thepanel.warriors[11].UserColor = new Color(210,105,30);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    strPositionMessage = new String[4];    //Position array is set up
    this.textreceived.append("There are: " + intPlayerCount + " clients\n");    //Server and Client are made aware of certain characteristics of the game
    if(blnClient){
      this.textreceived.append("You are player: " + intPlayerNumber+"\n");
    }else{
      this.textreceived.append("You are the Server\n");
    }
    for(intCount=0;intCount<intPlayerCount;intCount++){            //Count through all the players
      try{
        thepanel.warriors[intCount].intPosX = intpositions[intCount][0];  //Set the player's default X and Y position
        thepanel.warriors[intCount].intPosY = intpositions[intCount][1];  //
      }catch(NullPointerException e){
      }
    }
    thepanel.remove(this.wait);          //Wait button is removed
    thepanel.remove(this.instructions);  //Instructions button is removed
    this.textreceived.setText("");  //Chat box is emptied
    blnGameStart=true;              //It is true that the game has started
    if(!blnClient){                 //If you are the server
      thepanel.add(wallbutton);       //Certain GUI elements are added
      thepanel.add(acidbutton);
      thepanel.add(itembutton);
      thepanel.add(knifeitem);
      thepanel.add(spearitem);
      thepanel.add(axeitem);
      thepanel.add(absorbitem);
      thepanel.add(healitem);
      thepanel.add(healthitem);
      thepanel.add(dummyitem);
      thepanel.add(this.restart);
      thepanel.add(this.exit);
    }else{                          //Else you are a client
      thepanel.add(fistbutton);       //Certain GUI elements are added
      fistbutton.setSelected(true);
    }
    thepanel.dblHealCooldown = 0;     //Heal cooldown is 0
    this.textreceived.append("The Game Master has joined the game\n");      //Starting game messages are sent
    for(intCount = 0;intCount < intPlayerCount; intCount++){
      this.textreceived.append(thepanel.warriors[intCount].strName + " has joined the game\n");
    }
    thepanel.blnGameSet = false;  //Game hasn't ended
    blnCLICKED = false;           //Walls are set not to be created by the game master
    knifeitem.setSelected(true);  //The knife is the default item that can be dropped
    fistbutton.setSelected(true); //The fist if the default ability that a client can use
    thepanel.blnCountDown = true; //Countdown can be done now
    theframe.requestFocus();      //The frame now has focus
  }
  
  public void mouseMoved(MouseEvent e){  //Method for when the mouse moves
    //If you are the server, and you want to make a wall, and the mouse is within certain boundaries
    if(!blnClient && blnCLICKED && blnGameStart && wallbutton.isSelected() && !thepanel.blnCountDown && e.getX() + thepanel.intSX - 360 < 2160 && e.getX() + thepanel.intSX - 360 > 0 && e.getY() + thepanel.intSY - 360 < 2160 && e.getY() + thepanel.intSY - 360 > 0){
      for(intCount=0;intCount<100;intCount++){         //Count through all the walls
        if(thepanel.intWalls[intCount][2] <= 0){         //If the wall counter is less than or equal to 0
          thepanel.intWalls[intCount][0] = (int)((e.getX() + thepanel.intSX - 360)/36.0);    //Record the Wall's XY position
          thepanel.intWalls[intCount][1] = (int)((e.getY() + thepanel.intSY - 360)/36.0);    //
          blnBuildWall = true;                           //Build a wall is currently true
          for(intCount2=0;intCount2<100;intCount2++){    //Count through all the walls
            //If this wall is attempted to be built on another built wall
            if(thepanel.intWalls[intCount][0] == thepanel.intWalls[intCount2][0] && thepanel.intWalls[intCount][1] == thepanel.intWalls[intCount2][1] && intCount != intCount2){
              blnBuildWall = false;  //The wall cannot be built
            }
          }
          if(blnBuildWall){          //If the wall can be built
            thepanel.intWalls[intCount][2] = 325;  //The wall's timer is set at 325 units of time
          }
        }
      }
    }
  }
  public void mouseDragged(MouseEvent e){ //Needed method
  }
  public void mouseExited(MouseEvent e){  //Needed Method for when the mouse exits the screen
  }
  public void mouseEntered(MouseEvent e){ //Needed Method for when the mouse enters the screen
  }
  public void mouseReleased(MouseEvent e){    //Needed method
  }
  public void mousePressed(MouseEvent e){     //If the mouse is pressed
    if(blnGameStart && e.getButton() == 1){     //If the game has started
      blnCLICKED = !blnCLICKED;                   //A wall building is toggled ON/OFF
    }
    if(blnGameStart && e.getButton() == 1 && acidbutton.isSelected() && !thepanel.blnCountDown){  //If you press the mouse on the map while acid fog is selected
      if(!blnClient && blnGameStart && thepanel.intSmoke[2] <= 0){                //If you are the server and the smoke cooldown is less than or equal to 0
        thepanel.intSmoke[0] = (int)((e.getX() + thepanel.intSX - 360)/36.0);       //Smoke is spawned with a certain X and Y position and with a countdown and radius
        thepanel.intSmoke[1] = (int)((e.getY() + thepanel.intSY - 360)/36.0);       //
        thepanel.intSmoke[2] = 250;                                                 //
        thepanel.intSmoke[3] = 0;                                                   //
      }
    }else if(blnGameStart && e.getButton() == 1 && itembutton.isSelected()){                      //Else if you press the mouse on the map while Item Spawner is selected
      if(!blnClient && blnGameStart && !thepanel.blnCountDown && e.getX() + thepanel.intSX - 360 < 2160 && e.getX() + thepanel.intSX - 360 > 0 && e.getY() + thepanel.intSY - 360 < 2160 && e.getY() + thepanel.intSY - 360 > 0){
        if(knifeitem.isSelected()){                         //If a certain item is selected, drop the item on the map, and tell update the dropped item to all the other clients via ssm
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "a";
          ssm.sendText("IPU,a,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }else if(spearitem.isSelected()){                   //
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "b";
          ssm.sendText("IPU,b,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }else if(axeitem.isSelected()){                     //
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "c";
          ssm.sendText("IPU,c,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }else if(absorbitem.isSelected()){                  //
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "d";
          ssm.sendText("IPU,d,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }else if(healitem.isSelected()){                    //
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "h";
          ssm.sendText("IPU,h,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }else if(healthitem.isSelected()){                  //
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "h2";
          ssm.sendText("IPU,h2,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }else if(dummyitem.isSelected()){                   //
          thepanel.strMapChar[(int)((e.getX() + thepanel.intSX - 360)/36.0)][(int)((e.getY() + thepanel.intSY - 360)/36.0)] = "x";
          ssm.sendText("IPU,x,"+(int)((e.getX() + thepanel.intSX - 360)/36.0)+","+(int)((e.getY() + thepanel.intSY - 360)/36.0));
        }
      }
    }
  }
  public void mouseClicked(MouseEvent e){     //Needed method for mouse clicking
    if(e.getX() > 0 && e.getX() < 720 && e.getY() > 0 && e.getY() < 720){  //If you click within the map area
      theframe.requestFocus();                                               //The frame requests focus
    }
  }
  public void keyPressed(KeyEvent evt){       //Method for when a key is pressed
    if(blnGameStart && blnClient){              //If you are a client and the game has started
      //Depending on the direction key you press (W/A/S/D), your direction is assigned an integer value, while your direction is recorded via booleans
      //You can only move if you are moving onto grass
      if(evt.getKeyChar() == 'w' && (thepanel.strMapSets[(int)((thepanel.warriors[intPlayerNumber].intPosX)/36)][(int)((thepanel.warriors[intPlayerNumber].intPosY-20)/36)].equals("g") || thepanel.warriors[intPlayerNumber].dblHP <= 0) && thepanel.warriors[intPlayerNumber].intPosY-20 > 36){
        thepanel.blnUp = true;
        thepanel.intDirection = 1;
      }
      if(evt.getKeyChar() == 's' && (thepanel.strMapSets[(int)((thepanel.warriors[intPlayerNumber].intPosX)/36)][(int)((thepanel.warriors[intPlayerNumber].intPosY+20)/36)].equals("g") || thepanel.warriors[intPlayerNumber].dblHP <= 0) && thepanel.warriors[intPlayerNumber].intPosY+20 < 2124){
        thepanel.blnDown = true;
        thepanel.intDirection = 2;
      }
      if(evt.getKeyChar() == 'a' && (thepanel.strMapSets[(int)((thepanel.warriors[intPlayerNumber].intPosX-20)/36)][(int)((thepanel.warriors[intPlayerNumber].intPosY)/36)].equals("g") || thepanel.warriors[intPlayerNumber].dblHP <= 0) && thepanel.warriors[intPlayerNumber].intPosX-20 > 36){
        thepanel.blnLeft = true;
        thepanel.intDirection = 3;
      }
      if(evt.getKeyChar() == 'd' && (thepanel.strMapSets[(int)((thepanel.warriors[intPlayerNumber].intPosX+20)/36)][(int)((thepanel.warriors[intPlayerNumber].intPosY)/36)].equals("g") || thepanel.warriors[intPlayerNumber].dblHP <= 0) && thepanel.warriors[intPlayerNumber].intPosX+20 < 2124){
        thepanel.blnRight = true;
        thepanel.intDirection = 4;
      }
    }else if(blnGameStart){          //Else if the game has started, but you are not client (so you are server)
      //Depending on the direction key you press (W/A/S/D), your direction is recorded via booleans
      if(evt.getKeyChar() == 'w'){
        thepanel.blnUp = true;
      }
      if(evt.getKeyChar() == 's'){
        thepanel.blnDown = true;
      }
      if(evt.getKeyChar() == 'a'){
        thepanel.blnLeft = true;
      }
      if(evt.getKeyChar() == 'd'){
        thepanel.blnRight = true;
      }
    }
    //Depending on what ability button is selected, and if the ability's cooldown is less than or equal to 0, and if you are still alive, and if you press the space bar which is the attack button
    if(evt.getKeyChar() == ' ' && fistbutton.isSelected() && thepanel.dblFistCooldown <= 0 && thepanel.warriors[intPlayerNumber].dblHP > 0){
      thepanel.dblFistCooldown = Integer.parseInt(thepanel.strItem[0][3]);     //The cooldown of this item is recorded
      thepanel.intCDirection = thepanel.intDirection;                          //The direction of the player is recorded
      //Depending on the direction of the player, send a certain text to all clients via ssm that contains attack info of where players will be damaged within a given range that is noted in the message sent. The message also contains who sent the attack and which attack was sent
      if(thepanel.intDirection==1){
        ssm.sendText("atk,0,"+(thepanel.warriors[intPlayerNumber].intPosX-36)+","+(thepanel.warriors[intPlayerNumber].intPosX+36)+","+(thepanel.warriors[intPlayerNumber].intPosY-72)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+intPlayerNumber+","+1);
      }else if(thepanel.intDirection==2){
        ssm.sendText("atk,0,"+(thepanel.warriors[intPlayerNumber].intPosX-36)+","+(thepanel.warriors[intPlayerNumber].intPosX+36)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+(thepanel.warriors[intPlayerNumber].intPosY+72)+","+intPlayerNumber+","+2);
      }else if(thepanel.intDirection==3){
        ssm.sendText("atk,0,"+(thepanel.warriors[intPlayerNumber].intPosX-72)+","+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosY-36)+","+(thepanel.warriors[intPlayerNumber].intPosY+36)+","+intPlayerNumber+","+3);
      }else if(thepanel.intDirection==4){
        ssm.sendText("atk,0,"+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosX+72)+","+(thepanel.warriors[intPlayerNumber].intPosY-36)+","+(thepanel.warriors[intPlayerNumber].intPosY+36)+","+intPlayerNumber+","+4);
      }
    //
    }else if(evt.getKeyChar() == ' ' && knifebutton.isSelected() && thepanel.dblKnifeCooldown <= 0 && thepanel.warriors[intPlayerNumber].dblHP > 0){
      thepanel.dblKnifeCooldown = Integer.parseInt(thepanel.strItem[1][3]);    //
      thepanel.intCDirection = thepanel.intDirection;                          //
      //Depending on the direction of the player, send a certain text to all clients via ssm that contains attack info of where players will be damaged within a given range that is noted in the message sent. The message also contains who sent the attack and which attack was sent
      if(thepanel.intDirection==1){
        ssm.sendText("atk,1,"+(thepanel.warriors[intPlayerNumber].intPosX-20)+","+(thepanel.warriors[intPlayerNumber].intPosX+20)+","+(thepanel.warriors[intPlayerNumber].intPosY-90)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+intPlayerNumber+","+1);
      }else if(thepanel.intDirection==2){
        ssm.sendText("atk,1,"+(thepanel.warriors[intPlayerNumber].intPosX-20)+","+(thepanel.warriors[intPlayerNumber].intPosX+20)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+(thepanel.warriors[intPlayerNumber].intPosY+90)+","+intPlayerNumber+","+2);
      }else if(thepanel.intDirection==3){
        ssm.sendText("atk,1,"+(thepanel.warriors[intPlayerNumber].intPosX-90)+","+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosY-20)+","+(thepanel.warriors[intPlayerNumber].intPosY+20)+","+intPlayerNumber+","+3);
      }else if(thepanel.intDirection==4){
        ssm.sendText("atk,1,"+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosX+90)+","+(thepanel.warriors[intPlayerNumber].intPosY-20)+","+(thepanel.warriors[intPlayerNumber].intPosY+20)+","+intPlayerNumber+","+4);
      }
    //
    }else if(evt.getKeyChar() == ' ' && spearbutton.isSelected() && thepanel.dblSpearCooldown <= 0 && thepanel.warriors[intPlayerNumber].dblHP > 0){
      thepanel.dblSpearCooldown = Integer.parseInt(thepanel.strItem[2][3]);    //
      thepanel.intCDirection = thepanel.intDirection;                          //
      //Depending on the direction of the player, send a certain text to all clients via ssm that contains attack info of where players will be damaged within a given range that is noted in the message sent. The message also contains who sent the attack and which attack was sent
      if(thepanel.intDirection==1){
        ssm.sendText("atk,2,"+(thepanel.warriors[intPlayerNumber].intPosX-18)+","+(thepanel.warriors[intPlayerNumber].intPosX+18)+","+(thepanel.warriors[intPlayerNumber].intPosY-144)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+intPlayerNumber+","+1);
      }else if(thepanel.intDirection==2){
        ssm.sendText("atk,2,"+(thepanel.warriors[intPlayerNumber].intPosX-18)+","+(thepanel.warriors[intPlayerNumber].intPosX+18)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+(thepanel.warriors[intPlayerNumber].intPosY+144)+","+intPlayerNumber+","+2);
      }else if(thepanel.intDirection==3){
        ssm.sendText("atk,2,"+(thepanel.warriors[intPlayerNumber].intPosX-144)+","+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosY-18)+","+(thepanel.warriors[intPlayerNumber].intPosY+18)+","+intPlayerNumber+","+3);
      }else if(thepanel.intDirection==4){
        ssm.sendText("atk,2,"+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosX+144)+","+(thepanel.warriors[intPlayerNumber].intPosY-18)+","+(thepanel.warriors[intPlayerNumber].intPosY+18)+","+intPlayerNumber+","+4);
      }
    //
    }else if(evt.getKeyChar() == ' ' && axebutton.isSelected() && thepanel.dblAxeCooldown <= 0 && thepanel.warriors[intPlayerNumber].dblHP > 0){
      thepanel.dblAxeCooldown = Integer.parseInt(thepanel.strItem[3][3]);      //
      thepanel.intCDirection = thepanel.intDirection;                          //
      //Depending on the direction of the player, send a certain text to all clients via ssm that contains attack info of where players will be damaged within a given range that is noted in the message sent. The message also contains who sent the attack and which attack was sent
      if(thepanel.intDirection==1){
        ssm.sendText("atk,3,"+(thepanel.warriors[intPlayerNumber].intPosX-40)+","+(thepanel.warriors[intPlayerNumber].intPosX+40)+","+(thepanel.warriors[intPlayerNumber].intPosY-80)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+intPlayerNumber+","+1);
      }else if(thepanel.intDirection==2){
        ssm.sendText("atk,3,"+(thepanel.warriors[intPlayerNumber].intPosX-40)+","+(thepanel.warriors[intPlayerNumber].intPosX+40)+","+(thepanel.warriors[intPlayerNumber].intPosY)+","+(thepanel.warriors[intPlayerNumber].intPosY+80)+","+intPlayerNumber+","+2);
      }else if(thepanel.intDirection==3){
        ssm.sendText("atk,3,"+(thepanel.warriors[intPlayerNumber].intPosX-80)+","+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosY-40)+","+(thepanel.warriors[intPlayerNumber].intPosY+40)+","+intPlayerNumber+","+3);
      }else if(thepanel.intDirection==4){
        ssm.sendText("atk,3,"+(thepanel.warriors[intPlayerNumber].intPosX)+","+(thepanel.warriors[intPlayerNumber].intPosX+80)+","+(thepanel.warriors[intPlayerNumber].intPosY-40)+","+(thepanel.warriors[intPlayerNumber].intPosY+40)+","+intPlayerNumber+","+4);
      }
    //
    }else if(evt.getKeyChar() == ' ' && absorbbutton.isSelected() && thepanel.dblAbsorbCooldown <= 0 && thepanel.warriors[intPlayerNumber].dblHP > 0){
      thepanel.dblAbsorbCooldown = Integer.parseInt(thepanel.strItem[4][3]);   //
      thepanel.intCDirection = thepanel.intDirection;                          //
      //A certain text is sent to all clients via ssm that contains attack info of where players will be damaged within a given range that is noted in the message sent. The message also contains who sent the attack and which attack was sent
      ssm.sendText("atk,4,"+(thepanel.warriors[intPlayerNumber].intPosX-50)+","+(thepanel.warriors[intPlayerNumber].intPosX+50)+","+(thepanel.warriors[intPlayerNumber].intPosY-50)+","+(thepanel.warriors[intPlayerNumber].intPosY+50)+","+intPlayerNumber+","+1);
      //
    }else if(evt.getKeyChar() == ' ' && healbutton.isSelected() && thepanel.dblHealCooldown <= 0 && thepanel.warriors[intPlayerNumber].dblHP > 0){
      thepanel.dblHealCooldown = Integer.parseInt(thepanel.strHeal[3]);        //The cooldown of this ability is reset
      thepanel.warriors[intPlayerNumber].dblHP -= Integer.parseInt(thepanel.strHeal[2]);       //The user gains HP depending on the value in the csv healing file
      ssm.sendText("UserHP," + intPlayerNumber + "," + thepanel.warriors[intPlayerNumber].dblHP);    //All other players are updated on the health of this player via ssm
    }
  }
  public void keyReleased(KeyEvent evt){    //Method for when a key is released
    if(blnGameStart){                         //If the game started already
      if(evt.getKeyChar() == 'w'){              //If a certain key is released
        thepanel.blnUp = false;                   //The boolean assosiated with this key is set to false
        if(thepanel.intDirection == 1){             //A series of IF STATEMENTS are used to determine the player's new main direction if a button that is still being pressed since one certain key was released.
          if(thepanel.blnDown){                       //
            thepanel.intDirection = 2;
          }else if(thepanel.blnLeft){                 //
            thepanel.intDirection = 3;
          }else if(thepanel.blnRight){                //
            thepanel.intDirection = 4;
          }
        }
      }
      if(evt.getKeyChar() == 's'){              //
        thepanel.blnDown = false;                 //
        if(thepanel.intDirection == 2){             //
          if(thepanel.blnUp){                         //
            thepanel.intDirection = 1;
          }else if(thepanel.blnLeft){                 //
            thepanel.intDirection = 3;
          }else if(thepanel.blnRight){                //
            thepanel.intDirection = 4;
          }
        }
      }
      if(evt.getKeyChar() == 'a'){              //
        thepanel.blnLeft = false;                 //
        if(thepanel.intDirection == 3){             //
          if(thepanel.blnUp){                         //
            thepanel.intDirection = 1;
          }else if(thepanel.blnDown){                 //
            thepanel.intDirection = 2;
          }else if(thepanel.blnRight){                //
            thepanel.intDirection = 4;
          }
        }
      }
      if(evt.getKeyChar() == 'd'){              //
        thepanel.blnRight = false;                //
        if(thepanel.intDirection == 4){             //
          if(thepanel.blnUp){                         //
            thepanel.intDirection = 1;
          }else if(thepanel.blnDown){                 //
            thepanel.intDirection = 2;
          }else if(thepanel.blnLeft){                 //
            thepanel.intDirection = 3;
          }
        }
      }
    }
  }
  public void keyTyped(KeyEvent evt){  //Needed method
  }
  
  //Constructor
  public TheTriggerGames(){
    theframe = new JFrame("The Trigger Games");  //The frame is created with a name
    try{  //Sets the images for the buttons
      imgPlay = ImageIO.read(getClass().getResource("play.png")); 
      imgQuit = ImageIO.read(getClass().getResource("quit.png")); 
    }catch(IOException e){}
    theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Windows exits properly
    thepanel = new JGraphics();                                //A panel is created
    thepanel.setLayout(null);                                  //
    thepanel.setPreferredSize(new Dimension(1280,720));    //The panel controls the size of the window
    thepanel.addMouseMotionListener(this);         //MouseMotionLister is added to the panel
    thepanel.addMouseListener(this);               //MouseListener is added to the panel
    theframe.addKeyListener(this);           //The frame is listening to any potential keypresses
    
    this.play = new JButton();                 //JButtons are set up with a size and location. They are being listened to and are added to the panel
    this.play.setSize(85,46);                  //
    this.play.setLocation(185,320);            //
    this.play.setIcon(new ImageIcon(imgPlay)); //
    this.play.addActionListener(this);         //
    thepanel.add(this.play);                   //
    this.quit = new JButton();                 //
    this.quit.setSize(91,38);                  //
    this.quit.setLocation(180,510);            //
    this.quit.setIcon(new ImageIcon(imgQuit)); //
    this.quit.addActionListener(this);         //
    thepanel.add(this.quit);                   //
    
    
    this.texttosend = new JTextField();            //TextField for text sending is made
    this.texttosend.setSize(560, 50);              //Its size is set
    this.texttosend.setLocation(720, 310);         //Its location is set
    this.texttosend.addActionListener(this);       //It is being listened to
    
    this.textreceived = new JTextArea();                  //TextArea for text receiving is made
    this.thescroll = new JScrollPane(this.textreceived);  //A scroll is added to the text receiving TextArea
    this.thescroll.setSize(560, 310);                     //Its size is set
    this.thescroll.setLocation(720, 0);                   //Its Location is set
    this.textreceived.setEditable(false);                 //It cannot be edited
    
    this.theIP = new JTextField();                  //TextField for the IP address is made
    this.theIP.setSize(720,50);                     //Its size is set
    this.theIP.setLocation(280,350);                //Its location is set
    this.theIP.addActionListener(this);             //It is being listened to
    this.theUserName = new JTextField();                  //TextField for the user's username is made
    this.theUserName.setSize(720,50);                     //Its size is set
    this.theUserName.setLocation(280,300);                //Its location is set
    this.theUserName.addActionListener(this);             //It is being listened to
    
    this.startserver = new JButton("Start Server");   //JButtons are set up with a size and location. They are being listened to and are added to the panel
    this.startserver.setSize(720,50);                 //
    this.startserver.setLocation(280,450);            //
    this.startserver.addActionListener(this);         //
    this.startclient = new JButton("Start Client");   //
    this.startclient.setSize(720,50);                 //
    this.startclient.setLocation(280,500);            //
    this.startclient.addActionListener(this);         //
    this.instructions = new JButton("How To Play");   //
    this.instructions.setSize(560,130);               //
    this.instructions.setLocation(720,590);           //
    this.instructions.addActionListener(this);        //
    thepanel.add(this.instructions);                  //
    this.wait = new JButton("Ready?");                //
    this.wait.setSize(720,50);                        //
    this.wait.setLocation(0,0);                       //
    this.wait.addActionListener(this);                //
    this.restart = new JButton("Restart Game");       //
    this.restart.setSize(280,130);                    //
    this.restart.setLocation(720,590);                //
    this.restart.addActionListener(this);             //
    this.exit = new JButton("Exit Game");             //
    this.exit.setSize(280,130);                       //
    this.exit.setLocation(1000,590);                  //
    this.exit.addActionListener(this);                //
    
    this.theLabel1 = new JLabel("WELCOME TO", SwingConstants.CENTER);   //JLabels are set up with a size and location. They each have a font and are added to the panel
    this.theLabel1.setFont(new Font("Arial", Font.BOLD, 20));                             //
    this.theLabel1.setSize(1280, 50);                                                     //
    this.theLabel1.setLocation(0, 0);                                                     //
    this.theLabel2 = new JLabel("USERNAME:");                                             //
    this.theLabel2.setFont(new Font("Arial", Font.BOLD, 20));                             //
    this.theLabel2.setSize(1280, 50);                                                     //
    this.theLabel2.setLocation(150, 300);                                                 //
    this.theLabel3 = new JLabel("IP ADDRESS:");                                           //
    this.theLabel3.setFont(new Font("Arial", Font.BOLD, 20));                             //
    this.theLabel3.setSize(1280, 50);                                                     //
    this.theLabel3.setLocation(143, 350);                                                 //
    theLabel1.setForeground(Color.white);             //The foreground color of each label is set
    theLabel2.setForeground(Color.white);             //
    theLabel3.setForeground(Color.white);             //
    
    wallbutton = new JRadioButton("Toggle Wall Spawner (Click on map to Toggle ON/OFF the wall on map)");   //JRadioButtons are set up with a size and locatiom, and are being listened to
    wallbutton.setSize(560, 50);                                                                            //These JRadioButtons are for the abilities that the server and clients can use if they select them
    wallbutton.setLocation(720, 410);                                                                       //
    wallbutton.addActionListener(this);                                                                     //
    acidbutton = new JRadioButton("Acid Fog Spawner (Click on map to spawn on map)");                       //
    acidbutton.setSize(560, 50);                                                                            //
    acidbutton.setLocation(720, 460);                                                                       //
    acidbutton.addActionListener(this);                                                                     //
    itembutton = new JRadioButton("Item Spawner (Click on map to spawn on map)");                           //
    itembutton.setSize(560, 50);                                                                            //
    itembutton.setLocation(720, 510);                                                                       //
    itembutton.addActionListener(this);                                                                     //
    fistbutton = new JRadioButton("Fists");                                                                 //
    fistbutton.setSize(560, 30);                                                                            //
    fistbutton.setLocation(720, 400);                                                                       //
    fistbutton.addActionListener(this);                                                                     //
    knifebutton = new JRadioButton("Knife");                                                                //
    knifebutton.setSize(560, 30);                                                                           //
    knifebutton.setLocation(720, 430);                                                                      //
    knifebutton.addActionListener(this);                                                                    //
    spearbutton = new JRadioButton("Spear");                                                                //
    spearbutton.setSize(560, 30);                                                                           //
    spearbutton.setLocation(720, 460);                                                                      //
    spearbutton.addActionListener(this);                                                                    //
    axebutton = new JRadioButton("Axe");                                                                    //
    axebutton.setSize(560, 30);                                                                             //
    axebutton.setLocation(720, 490);                                                                        //
    axebutton.addActionListener(this);                                                                      //
    absorbbutton = new JRadioButton("Absorb");                                                              //
    absorbbutton.setSize(560, 30);                                                                          //
    absorbbutton.setLocation(720, 520);                                                                     //
    absorbbutton.addActionListener(this);                                                                   //
    healbutton = new JRadioButton("Heal (ITEM)");                                                           //
    healbutton.setSize(560, 30);                                                                            //
    healbutton.setLocation(720, 550);                                                                       //
    healbutton.addActionListener(this);                                                                     //
    knifeitem = new JRadioButton("Knife");                                                                  //
    knifeitem.setSize(60, 30);                                                                              //
    knifeitem.setLocation(720, 560);                                                                        //
    knifeitem.addActionListener(this);                                                                      //
    spearitem = new JRadioButton("Spear");                                                                  //
    spearitem.setSize(60, 30);                                                                              //
    spearitem.setLocation(780, 560);                                                                        //
    spearitem.addActionListener(this);                                                                      //
    axeitem = new JRadioButton("Axe");                                                                      //
    axeitem.setSize(80, 30);                                                                                //
    axeitem.setLocation(840, 560);                                                                          //
    axeitem.addActionListener(this);                                                                        //
    absorbitem = new JRadioButton("Absorb");                                                                //
    absorbitem.setSize(80, 30);                                                                             //
    absorbitem.setLocation(920, 560);                                                                       //
    absorbitem.addActionListener(this);                                                                     //
    healitem = new JRadioButton("Heal (Item) ");                                                            //
    healitem.setSize(100, 30);                                                                              //
    healitem.setLocation(1000, 560);                                                                        //
    healitem.addActionListener(this);                                                                       //
    healthitem = new JRadioButton("Health Up");                                                             //
    healthitem.setSize(100, 30);                                                                            //
    healthitem.setLocation(1100, 560);                                                                      //
    healthitem.addActionListener(this);                                                                     //
    dummyitem = new JRadioButton("Dummy");                                                                  //
    dummyitem.setSize(80, 30);                                                                              //
    dummyitem.setLocation(1200, 560);                                                                       //
    dummyitem.addActionListener(this);                                                                      //
    
    servergroup = new ButtonGroup();    //Radio buttons are added to a certain group
    servergroup.add(wallbutton);        //
    servergroup.add(acidbutton);        //
    servergroup.add(itembutton);        //
    
    clientgroup = new ButtonGroup();    //Radio buttons are added to a certain group
    clientgroup.add(fistbutton);        //
    clientgroup.add(knifebutton);       //
    clientgroup.add(spearbutton);       //
    clientgroup.add(axebutton);         //
    clientgroup.add(absorbbutton);      //
    clientgroup.add(healbutton);        //
    
    itemgroup = new ButtonGroup();    //Radio buttons are added to a certain group
    clientgroup.add(knifeitem);       //
    clientgroup.add(spearitem);       //
    clientgroup.add(axeitem);         //
    clientgroup.add(absorbitem);      //
    clientgroup.add(healitem);        //
    clientgroup.add(healthitem);      //
    clientgroup.add(dummyitem);       //
    knifeitem.setFont(knifeitem.getFont().deriveFont(10.0f));     //Font size of the item radio buttons are set
    spearitem.setFont(spearitem.getFont().deriveFont(10.0f));     //
    axeitem.setFont(axeitem.getFont().deriveFont(10.0f));         //
    absorbitem.setFont(absorbitem.getFont().deriveFont(10.0f));   //
    healitem.setFont(healitem.getFont().deriveFont(10.0f));       //
    healthitem.setFont(healthitem.getFont().deriveFont(10.0f));   //
    dummyitem.setFont(dummyitem.getFont().deriveFont(10.0f));     //
    fistbutton.setSelected(true);       //These JRadioButtons are defaulted to be selected
    knifeitem.setSelected(true);        //These JRadioButtons are defaulted to be selected
    theframe.setContentPane(thepanel);
    this.theframe.setResizable(false);
    //This command tells the frame to set itself up based on the size of the contents inside
    theframe.pack();
    theframe.setVisible(true);
    thetimer = new Timer(1000/120, this); //120 frames per second
    thetimer.start();
  }
  
  //Main Method
  public static void main (String[] args){
    //The Trigger Games object is created
    TheTriggerGames Game = new TheTriggerGames();
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    The End of the Trigger Games Program
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////