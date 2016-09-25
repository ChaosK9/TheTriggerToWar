/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    Graphics for The Trigger Games by: Jonathan Lee, Matthew Wong, and Ethan Liong
//    Version: 3.0
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Import stuff to make program work
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class JGraphics extends JPanel{
  //Properties
  public String strSetsLine[];    //Variable for each row of the strMapSets/strMapChar array
  public String strCharLine[];    //
  public String strPName;       //Variable for the user's name
  
  public String strMapSets[][];   //Array variable for the map
  public String strMapChar[][];   //Array variable for the characters and items
  public String strItem[][];      //Array variable for the item csv file
  public String strItemLine[];    //Array variable for each line of the item csv file
  public String strHeal[];        //Array variable for the heal csv file
  public String[] strPlaces;      //Array variable for the player rankings
  
  public int intWalls[][];    //Array variable for all possible wall locations
  public int intSmoke[];      //Array variable for the acid fog characteristics
  
  public int intCount;  //Counter variables
  public int intCount2; //
  public int intCount3; //
  
  public int intTimer = 120;    //Timer variable for the countdown
  
  public int intPlayerN;        //Int variable for the user's number
  public int intTotalPlayers;   //Int variable for the total amount of players
  
  public int intSX = 1080;    //The server's X and Y position
  public int intSY = 1080;    //
  
  public int intDirection;      //Variable for the user's directiona and current direction
  public int intCDirection;     //
  
  public double dblFistCooldown = 0;     //Variables for the cooldown of every ability
  public double dblKnifeCooldown = 0;    //
  public double dblSpearCooldown = 0;    //
  public double dblAxeCooldown = 0;      //
  public double dblAbsorbCooldown = 0;   //
  public double dblHealCooldown = 0;     //
  
  public double[][][] dblPictureTime;  //3D Double Array for the picture time popup of every ability for each player
  
  public boolean blnUp = false;      //Directional booleans
  public boolean blnDown = false;    //
  public boolean blnLeft = false;    //
  public boolean blnRight = false;   //
  public boolean blnMove;              //Boolean for if the player moves
  
  public boolean blnLoad = true;             //Boolean to load the map or not
  public boolean blnStart = false;           //Boolean to start the game
  public boolean blnClient;                  //Boolean for if you are a client
  public boolean blnSend = false;            //Boolean for sending a message if a player moves 
  public boolean blnCheckName = false;       //Boolean to check a name or not
  public boolean blnGameSet = false;         //Boolean to state if the game has ended
  public boolean blnShowScores = false;      //Boolean to show the scores of the game or not
  public boolean blnMainMenu = true;         //Boolean that shows the main menu
  
  public boolean blnCountDown;   //Boolean to countdown
  
  public Warrior[] warriors;     //Array of the Warrior Class Object
  
  BufferedImage theCanvas = new BufferedImage(2160, 2160,  BufferedImage.TYPE_INT_RGB);    //Canvas variables are made
  BufferedImage theCanvas2 = new BufferedImage(2160, 2160,  BufferedImage.TYPE_INT_RGB);   //
  Graphics2D graphics = theCanvas.createGraphics();                                        //
  Graphics2D graphics2 = theCanvas2.createGraphics();                                      //
  
  BufferedReader inMapSets = null;  //File input variable for the map and items
  BufferedReader inMapChar = null;  //
  BufferedReader inItem = null;     //
  BufferedReader inHeal = null;     //
  
  Font font1 = new Font("Calibri",Font.BOLD,24);     //Font variables
  Font font2 = new Font("Calibri",Font.BOLD,60);     //
  Font font3 = new Font("Calibri",Font.BOLD,40);     //
  Font font4 = new Font("Calibri",Font.BOLD,100);    //
  
  //Image Variables
  BufferedImage grass = null;       //
  BufferedImage wall = null;        //
  BufferedImage knife = null;       //
  BufferedImage spear = null;       //
  BufferedImage axe = null;         //
  BufferedImage absorb = null;      //
  BufferedImage heal = null;        //
  BufferedImage heal2 = null;       //
  BufferedImage FistUp = null;      //
  BufferedImage FistDown = null;    //
  BufferedImage FistLeft = null;    //
  BufferedImage FistRight = null;   //
  BufferedImage KnifeUp = null;     //
  BufferedImage KnifeDown = null;   //
  BufferedImage KnifeLeft = null;   //
  BufferedImage KnifeRight = null;  //
  BufferedImage SpearUp = null;     //
  BufferedImage SpearDown = null;   //
  BufferedImage SpearLeft = null;   //
  BufferedImage SpearRight = null;  //
  BufferedImage AxeUp = null;       //
  BufferedImage AxeDown = null;     //
  BufferedImage AxeLeft = null;     //
  BufferedImage AxeRight = null;    //
  BufferedImage dummy = null;       //
  BufferedImage menu = null;        //
  BufferedImage menu2 = null;        //  
  
  //Methods
  public void gamestart(){        //Method for when the game started
    warriors = new Warrior[intTotalPlayers];     //Initialize the warrior array
    for(intCount=0;intCount<intTotalPlayers;intCount++){     //Count through each player
      warriors[intCount] = new Warrior("Player " + intCount,"asdf",0,0,0,0,intCount,100,0,0,0,0,0,0,Color.BLACK,false,true,false,false,false,false,false);  //Warriors are created for each player
    }
    blnCheckName = true;                         //Name checking is true
    strPlaces = new String[intTotalPlayers];             //Ranking variable is initialized
    for(intCount=0;intCount<intTotalPlayers;intCount++){     //Count through all the players
      strPlaces[intCount] = "";                                //And set the rankings to nothing
    }
    dblPictureTime = new double[intTotalPlayers][5][2];      //Picture time variable is initialized
    for(intCount=0;intCount<intTotalPlayers;intCount++){     //Count through all the players
      dblPictureTime[intCount][0][0] = 0;                      //All picture time values are 0
      dblPictureTime[intCount][1][0] = 0;                      //
      dblPictureTime[intCount][2][0] = 0;                      //
      dblPictureTime[intCount][3][0] = 0;                      //
      dblPictureTime[intCount][4][0] = 0;                      //
      dblPictureTime[intCount][0][1] = 0;                      //
      dblPictureTime[intCount][1][1] = 0;                      //
      dblPictureTime[intCount][2][1] = 0;                      //
      dblPictureTime[intCount][3][1] = 0;                      //
      dblPictureTime[intCount][4][1] = 0;                      //
    }
  }
  
  public void loadmap(){        //Method to load the map
    strMapSets = new String[60][60];    //These arrays will have 60 rows and columns
    strMapChar = new String[60][60];    //
    strItem = new String[5][4];       //Variables are initialized for the items and abilities
    strHeal = new String[4];          //
    intWalls = new int[100][3];       //
    intSmoke = new int[4];            //
    inMapSets  = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("MapSets.csv")));  //File variables are assosiated with their respectful .csv files
    inMapChar  = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("MapChar.csv")));  //
    inItem  = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("items.csv")));       //
    inHeal = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("healing.csv")));      //
    
    for(intCount = 0; intCount < 60; intCount++){      //For loop to read every line of both the map and item text files
      try{
        this.strSetsLine = (inMapSets.readLine()).split(",");   //Split the lines into a 1D array
        this.strCharLine = (inMapChar.readLine()).split(",");   //
      }catch(IOException e){
      }
      for(intCount2 = 0; intCount2 < 60; intCount2++){
        strMapSets[intCount2][intCount] = this.strSetsLine[intCount2];  //Insert characters into the 2D array with accordance to the map files
        strMapChar[intCount2][intCount] = this.strCharLine[intCount2];  //
      }
    }
    for(intCount = 0; intCount < 5; intCount++){  //Count through all the lines in the item.csv file
      try{    //This try and catch statement takes care of the IOException
        strItemLine = (inItem.readLine()).split(",");    //Split the line of the .csv file into an array
      }catch(IOException e){
      }
      for(intCount2 = 0; intCount2 < 4; intCount2++){           //Count through the array variable with the split .csv line
        strItem[intCount][intCount2] = strItemLine[intCount2];    //Load the item.csv values into the 2D array variable
      }
    }
    try{    //This try and catch statement takes care of the IOException 
      strHeal = (inHeal.readLine()).split(",");    //Split the line of the .csv file into an array
    }catch(IOException e){
    }
    for(intCount=0;intCount<100;intCount++){      //Count through all the walls
      for(intCount2=0;intCount2<3;intCount2++){   ////
        intWalls[intCount][intCount2] = 0;            //Set each wall's cooldown to 0
      }
    }
    for(intCount=0;intCount<4;intCount++){  //Count through all the smoke characteristics
      intSmoke[intCount] = 0;                 //Set the smoke characteristics to 0
    }
    try{    //This try and catch statement takes care of the IOExceptions
      inMapSets.close();    //Files are closed
      inMapChar.close();    //
    }catch(IOException e){
    }
    for(intCount = 0; intCount < 60; intCount++){      //For loop to read every line of both the map and item text files
      for(intCount2 = 0; intCount2 < 60; intCount2++){
        if(strMapSets[intCount][intCount2].equals("g")){                  //If "g" then print grass to the Canvases
          graphics.drawImage(grass, intCount*36, intCount2*36, null);
          graphics2.drawImage(grass, intCount*36, intCount2*36, null);
        }else if(strMapSets[intCount][intCount2].equals("t")){            //If "t" then print wall to the Canvases
          graphics.drawImage(wall, intCount*36, intCount2*36, null);
          graphics2.drawImage(wall, intCount*36, intCount2*36, null);
        }
      }
    }
  }
  
  public void paintComponent(Graphics g){    //Method used to paint stuff on the screen
    if(blnMainMenu == true){
      g.drawImage(menu,0,0,null);
    }else if(blnCountDown){                          //If countdown is true, print the countdown
      g.setColor(Color.WHITE);
      g.fillRect(720,360,560,360);
      g.setFont(font4);
      g.setColor(Color.BLACK);
      g.fillRect(0,0,720,720);
      g.setColor(Color.YELLOW);
      g.drawString(""+(int)(intTimer/20),340,350);
    }else{                                     //Else...
      if(blnLoad){                               //Load the map if needed
        loadmap();
        blnLoad = false;   //This if statement can't be accessed again
      }
      g.setColor(Color.WHITE);
      g.fillRect(0,0,720,720);
      graphics2.drawImage(theCanvas,0,0,null);
      graphics2.setColor(Color.BLACK);
      if(blnStart){           //If the game has started
        for(intCount=0;intCount<intTotalPlayers;intCount++){    //Count through all the players
          if(warriors[intCount].dblHP > 0){                       //If player is alive, draw them onto the canvas
            graphics2.setColor(warriors[intCount].UserColor);
            graphics2.fillOval(-18+warriors[intCount].intPosX,-18+warriors[intCount].intPosY,36,36);
          }
        }
        for(intCount=0;intCount<100;intCount++){     //Count through all the walls
          if(intWalls[intCount][2] > 0){               //If the wall's timer is greater than 0, draw it onto the map
            graphics2.setColor(Color.BLACK);
            graphics2.fillRect(intWalls[intCount][0]*36,intWalls[intCount][1]*36,36,36);
          }
        }
        if(intSmoke[2] > 0){      //If the smoke timer is greater than 0, draw it on the map
          graphics2.setColor(Color.GRAY);
          graphics2.fillOval(intSmoke[0]*36-intSmoke[3],intSmoke[1]*36-intSmoke[3],intSmoke[3]*2,intSmoke[3]*2);
          for(intCount=0;intCount<intTotalPlayers;intCount++){  //Count through all the players
            if(Math.pow(warriors[intCount].intPosX-intSmoke[0]*36,2) + Math.pow(warriors[intCount].intPosY-intSmoke[1]*36,2) < 2*Math.pow(intSmoke[3]/(1.4),2) && warriors[intCount].dblHP > 0){  //If the player is within smoke radius
              warriors[intCount].dblHP -= 0.18;               //Decrease health if player is in smoke radius
            }
          }
        }
        for(intCount=0;intCount<intTotalPlayers;intCount++){    //COunt through all the players
          if(warriors[intCount].dblHP > 0){                       //If the player is alive, draw player's health bar and username
            graphics2.setColor(Color.RED);
            graphics2.fillRect(warriors[intCount].intPosX-49,warriors[intCount].intPosY+30,100,6);
            graphics2.setColor(Color.GREEN);
            graphics2.fillRect(warriors[intCount].intPosX-49,warriors[intCount].intPosY+30,(int)(warriors[intCount].dblHP),6);
            graphics2.setColor(Color.BLACK);
            graphics2.drawString(warriors[intCount].strName, warriors[intCount].intPosX-18, warriors[intCount].intPosY-40);
          }
          if(warriors[intCount].dblHP > 0){              //If the player is alive
            if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("a") && !warriors[intCount].blnItem1){        //If the player runs into an item the player doesn't have, the player picks it up and this action is update on other player's maps
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].blnItem1 = true;
            }else if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("b") && !warriors[intCount].blnItem2){  //
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].blnItem2 = true;
            }else if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("c") && !warriors[intCount].blnItem3){  //
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].blnItem3 = true;
            }else if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("d") && !warriors[intCount].blnItem4){  //
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].blnItem4 = true;
            }else if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("h") && !warriors[intCount].blnItem5){  //
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].blnItem5 = true;
            }else if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("h2")){                                 //
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].dblHP += 20.0;
            }else if(strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)].equals("x")){                                  //
              strMapChar[(int)(warriors[intCount].intPosX/36.0)][(int)(warriors[intCount].intPosY/36.0)] = "0";
              warriors[intCount].dblHP -= 20.0;
            }
          }
        }
        for(intCount=0;intCount<intTotalPlayers;intCount++){    //Count through all the players
          for(intCount2=0;intCount2<5;intCount2++){               //Count through all the weapons
            if(dblPictureTime[intCount][intCount2][0] > 0){         //If this certain weapon's cooldown is greater than 0
              if(dblPictureTime[intCount][intCount2][1] == 1){        //Depending on which weapon has a cooldown that is still above 0, draw it
                if(intCount2 == 0){                                     //Weapon is drawn according to the player's direction
                  graphics2.drawImage(FistUp, warriors[intCount].intPosX-36, warriors[intCount].intPosY-72, null);
                }else if(intCount2 == 1){
                  graphics2.drawImage(KnifeUp, warriors[intCount].intPosX-20, warriors[intCount].intPosY-90, null);
                }else if(intCount2 == 2){
                  graphics2.drawImage(SpearUp, warriors[intCount].intPosX-18, warriors[intCount].intPosY-144, null);
                }else if(intCount2 == 3){
                  graphics2.drawImage(AxeUp, warriors[intCount].intPosX-40, warriors[intCount].intPosY-80, null);
                }else if(intCount2 == 4){
                  graphics2.setColor(Color.YELLOW);
                  graphics2.fillRect(warriors[intCount].intPosX-50, warriors[intCount].intPosY-50, 100, 100);
                }
              }else if(dblPictureTime[intCount][intCount2][1] == 2){  //
                if(intCount2 == 0){                                     //
                  graphics2.drawImage(FistDown, warriors[intCount].intPosX-36, warriors[intCount].intPosY, null);
                }else if(intCount2 == 1){
                  graphics2.drawImage(KnifeDown, warriors[intCount].intPosX-20, warriors[intCount].intPosY, null);
                }else if(intCount2 == 2){
                  graphics2.drawImage(SpearDown, warriors[intCount].intPosX-18, warriors[intCount].intPosY, null);
                }else if(intCount2 == 3){
                  graphics2.drawImage(AxeDown, warriors[intCount].intPosX-40, warriors[intCount].intPosY, null);
                }else if(intCount2 == 4){
                  graphics2.setColor(Color.YELLOW);
                  graphics2.fillRect(warriors[intCount].intPosX-50, warriors[intCount].intPosY-50, 100, 100);
                }
              }else if(dblPictureTime[intCount][intCount2][1] == 3){  //
                if(intCount2 == 0){                                     //
                  graphics2.drawImage(FistLeft, warriors[intCount].intPosX-72, warriors[intCount].intPosY-36, null);
                }else if(intCount2 == 1){
                  graphics2.drawImage(KnifeLeft, warriors[intCount].intPosX-90, warriors[intCount].intPosY-20, null);
                }else if(intCount2 == 2){
                  graphics2.drawImage(SpearLeft, warriors[intCount].intPosX-144, warriors[intCount].intPosY-18, null);
                }else if(intCount2 == 3){
                  graphics2.drawImage(AxeLeft, warriors[intCount].intPosX-80, warriors[intCount].intPosY-40, null);
                }else if(intCount2 == 4){
                  graphics2.setColor(Color.YELLOW);
                  graphics2.fillRect(warriors[intCount].intPosX-50, warriors[intCount].intPosY-50, 100, 100);
                }
              }else if(dblPictureTime[intCount][intCount2][1] == 4){  //
                if(intCount2 == 0){                                     //
                  graphics2.drawImage(FistRight, warriors[intCount].intPosX, warriors[intCount].intPosY-36, null);
                }else if(intCount2 == 1){
                  graphics2.drawImage(KnifeRight, warriors[intCount].intPosX, warriors[intCount].intPosY-20, null);
                }else if(intCount2 == 2){
                  graphics2.drawImage(SpearRight, warriors[intCount].intPosX, warriors[intCount].intPosY-18, null);
                }else if(intCount2 == 3){
                  graphics2.drawImage(AxeRight, warriors[intCount].intPosX, warriors[intCount].intPosY-40, null);
                }else if(intCount2 == 4){
                  graphics2.setColor(Color.YELLOW);
                  graphics2.fillRect(warriors[intCount].intPosX-50, warriors[intCount].intPosY-50, 100, 100);
                }
              }
            }
          }
        }
        if(intCDirection == 1){    //Depending on the player's current direction
          if(dblSpearCooldown >= Integer.parseInt(strItem[2][3])-1.5){    //If the Cooldown for the picture is greater than a certain number, draw the picture of the weapon
            graphics2.drawImage(SpearUp, warriors[intPlayerN].intPosX-18, warriors[intPlayerN].intPosY-144, null);
          }
          if(dblAbsorbCooldown >= Integer.parseInt(strItem[4][3])-1.5){   //
            graphics2.setColor(Color.YELLOW);
            graphics2.fillRect(warriors[intPlayerN].intPosX-50, warriors[intPlayerN].intPosY-50, 100, 100);
          }
          if(dblFistCooldown >= Integer.parseInt(strItem[0][3])-1.5){     //
            graphics2.drawImage(FistUp, warriors[intPlayerN].intPosX-36, warriors[intPlayerN].intPosY-72, null);
          }
          if(dblKnifeCooldown >= Integer.parseInt(strItem[1][3])-1.5){    //
            graphics2.drawImage(KnifeUp, warriors[intPlayerN].intPosX-20, warriors[intPlayerN].intPosY-90, null);
          }
          if(dblAxeCooldown >= Integer.parseInt(strItem[3][3])-1.5){      //
            graphics2.drawImage(AxeUp, warriors[intPlayerN].intPosX-40, warriors[intPlayerN].intPosY-80, null);
          }
        }
        if(intCDirection == 2){    //Depending on the player's current direction
          if(dblSpearCooldown >= Integer.parseInt(strItem[2][3])-1.5){    //If the Cooldown for the picture is greater than a certain number, draw the picture of the weapon
            graphics2.drawImage(SpearDown, warriors[intPlayerN].intPosX-18, warriors[intPlayerN].intPosY, null);
          }
          if(dblAbsorbCooldown >= Integer.parseInt(strItem[4][3])-1.5){   //
            graphics2.setColor(Color.YELLOW);
            graphics2.fillRect(warriors[intPlayerN].intPosX-50, warriors[intPlayerN].intPosY-50, 100, 100);
          }
          if(dblFistCooldown >= Integer.parseInt(strItem[0][3])-1.5){     //
            graphics2.drawImage(FistDown, warriors[intPlayerN].intPosX-36, warriors[intPlayerN].intPosY, null);
          }
          if(dblKnifeCooldown >= Integer.parseInt(strItem[1][3])-1.5){    //
            graphics2.drawImage(KnifeDown, warriors[intPlayerN].intPosX-20, warriors[intPlayerN].intPosY, null);
          }
          if(dblAxeCooldown >= Integer.parseInt(strItem[3][3])-1.5){      //
            graphics2.drawImage(AxeDown, warriors[intPlayerN].intPosX-40, warriors[intPlayerN].intPosY, null);
          }
        }
        if(intCDirection == 3){    //Depending on the player's current direction
          if(dblSpearCooldown >= Integer.parseInt(strItem[2][3])-1.5){    //If the Cooldown for the picture is greater than a certain number, draw the picture of the weapon
            graphics2.drawImage(SpearLeft, warriors[intPlayerN].intPosX-144, warriors[intPlayerN].intPosY-18, null);
          }
          if(dblAbsorbCooldown >= Integer.parseInt(strItem[4][3])-1.5){   //
            graphics2.setColor(Color.YELLOW);
            graphics2.fillRect(warriors[intPlayerN].intPosX-50, warriors[intPlayerN].intPosY-50, 100, 100);
          }
          if(dblFistCooldown >= Integer.parseInt(strItem[0][3])-1.5){     //
            graphics2.drawImage(FistLeft, warriors[intPlayerN].intPosX-72, warriors[intPlayerN].intPosY-36, null);
          }
          if(dblKnifeCooldown >= Integer.parseInt(strItem[1][3])-1.5){    //
            graphics2.drawImage(KnifeLeft, warriors[intPlayerN].intPosX-90, warriors[intPlayerN].intPosY-20, null);
          }
          if(dblAxeCooldown >= Integer.parseInt(strItem[3][3])-1.5){      //
            graphics2.drawImage(AxeLeft, warriors[intPlayerN].intPosX-80, warriors[intPlayerN].intPosY-40, null);
          }
        }
        if(intCDirection == 4){    //Depending on the player's current direction
          if(dblSpearCooldown >= Integer.parseInt(strItem[2][3])-1.5){    //If the Cooldown for the picture is greater than a certain number, draw the picture of the weapon
            graphics2.drawImage(SpearRight, warriors[intPlayerN].intPosX, warriors[intPlayerN].intPosY-18, null);
          }
          if(dblAbsorbCooldown >= Integer.parseInt(strItem[4][3])-1.5){   //
            graphics2.setColor(Color.YELLOW);
            graphics2.fillRect(warriors[intPlayerN].intPosX-50, warriors[intPlayerN].intPosY-50, 100, 100);
          }
          if(dblFistCooldown >= Integer.parseInt(strItem[0][3])-1.5){     //
            graphics2.drawImage(FistRight, warriors[intPlayerN].intPosX, warriors[intPlayerN].intPosY-36, null);
          }
          if(dblKnifeCooldown >= Integer.parseInt(strItem[1][3])-1.5){    //
            graphics2.drawImage(KnifeRight, warriors[intPlayerN].intPosX, warriors[intPlayerN].intPosY-20, null);
          }
          if(dblAxeCooldown >= Integer.parseInt(strItem[3][3])-1.5){      //
            graphics2.drawImage(AxeRight, warriors[intPlayerN].intPosX, warriors[intPlayerN].intPosY-40, null);
          }
        }
        
        for(intCount = 0; intCount < 60; intCount++){       //Count through all the tiles of the map
          for(intCount2 = 0; intCount2 < 60; intCount2++){  ////
            if(strMapChar[intCount][intCount2].equals("a")){        //If there is an item on this tile, draw it
              graphics2.drawImage(knife, intCount*36, intCount2*36, null);
            }else if(strMapChar[intCount][intCount2].equals("b")){  //
              graphics2.drawImage(spear, intCount*36, intCount2*36, null);
            }else if(strMapChar[intCount][intCount2].equals("c")){  //
              graphics2.drawImage(axe, intCount*36, intCount2*36, null);
            }else if(strMapChar[intCount][intCount2].equals("d")){  //
              graphics2.drawImage(absorb, intCount*36, intCount2*36, null);
            }else if(strMapChar[intCount][intCount2].equals("h")){  //
              graphics2.drawImage(heal, intCount*36, intCount2*36, null);
            }else if(strMapChar[intCount][intCount2].equals("h2")){ //
              graphics2.drawImage(heal2, intCount*36, intCount2*36, null);
            }else if(strMapChar[intCount][intCount2].equals("x")){  //
              graphics2.drawImage(dummy, intCount*36, intCount2*36, null);
            }
          }
        }
        if(blnClient){     //If you are a client, draw the map according to your position
          g.drawImage(theCanvas2,360-warriors[intPlayerN].intPosX,360-warriors[intPlayerN].intPosY,null);
        }else{             //Else you are the server, draw the map according to your position
          g.drawImage(theCanvas2,360-intSX,360-intSY,null);
        }
        g.setColor(Color.WHITE);
        g.fillRect(0,0,120,120);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,121,121);
        for(intCount=0;intCount<intTotalPlayers;intCount++){   //Count through all the players
          g.setColor(warriors[intCount].UserColor);
          if(warriors[intCount].dblHP > 0.0){                    //If the player is alive, draw the player on the mini map
            g.fillRect((int)(warriors[intCount].intPosX/18.0)-1,(int)(warriors[intCount].intPosY/18.0)-1,4,4);
          }
        }
        if(!blnClient){    //If you are the server, draw your view area on the mini map
          g.setColor(Color.BLACK);
          g.drawRect((int)((intSX-360)/18.0),(int)((intSY-360)/18.0),42,42);
        }
        if(blnUp && blnStart && !blnClient){      //If the user presses up and the game has started and is the server
          intSY = intSY-18;
        //Else if the player presses up and the game has started and (is walking on grass or is dead) and is walking within the map boundary
        }else if(blnUp && blnStart && (strMapSets[(int)((warriors[intPlayerN].intPosX)/36)][(int)((warriors[intPlayerN].intPosY-20)/36)].equals("g") || warriors[intPlayerN].dblHP <= 0) && warriors[intPlayerN].intPosY-20 > 36){
          blnMove = true;
          for(intCount=0;intCount<100;intCount++){  //Count through all the walls, and if the player isn't walking into a makeshift wall, he is allowed to move
            if((int)((warriors[intPlayerN].intPosX)/36) == intWalls[intCount][0] && (int)((warriors[intPlayerN].intPosY-20)/36) == intWalls[intCount][1] && intWalls[intCount][2] >0){
              blnMove = false;
            }
          }
          if(warriors[intPlayerN].dblHP <= 0){      //If the player is dead, the player is allowed to move freely
            warriors[intPlayerN].intPosY = warriors[intPlayerN].intPosY-18;
          }else if(blnMove){                        //Else if the player is able to move, then move the player
            warriors[intPlayerN].intPosY = warriors[intPlayerN].intPosY-9;
          }
          blnSend = true;    //Sending other players your current location is true
        }
        if(blnDown && blnStart && !blnClient){    //If the user presses down and the game has started and is the server
          intSY = intSY+18;
        //Else if the player presses down and the game has started and (is walking on grass or is dead) and is walking within the map boundary
        }else if(blnDown && blnStart && (strMapSets[(int)((warriors[intPlayerN].intPosX)/36)][(int)((warriors[intPlayerN].intPosY+20)/36)].equals("g") || warriors[intPlayerN].dblHP <= 0) && warriors[intPlayerN].intPosY+20 < 2124){
          blnMove = true;
          for(intCount=0;intCount<100;intCount++){  //Count through all the walls, and if the player isn't walking into a makeshift wall, he is allowed to move
            if((int)((warriors[intPlayerN].intPosX)/36) == intWalls[intCount][0] && (int)((warriors[intPlayerN].intPosY+20)/36) == intWalls[intCount][1] && intWalls[intCount][2] >0){
              blnMove = false;
            }
          }
          if(warriors[intPlayerN].dblHP <= 0){      //If the player is dead, the player is allowed to move freely
            warriors[intPlayerN].intPosY = warriors[intPlayerN].intPosY+18;
          }else if(blnMove){                        //Else if the player is able to move, then move the player
            warriors[intPlayerN].intPosY = warriors[intPlayerN].intPosY+9;
          }
          blnSend = true;    //Sending other players your current location is true
        }
        if(blnLeft && blnStart && !blnClient){    //If the user presses left and the game has started and is the server
          intSX = intSX-18;
        //Else if the player presses left and the game has started and (is walking on grass or is dead) and is walking within the map boundary
        }else if(blnLeft && blnStart && (strMapSets[(int)((warriors[intPlayerN].intPosX-20)/36)][(int)((warriors[intPlayerN].intPosY)/36)].equals("g") || warriors[intPlayerN].dblHP <= 0) && warriors[intPlayerN].intPosX-20 > 36){
          blnMove = true;
          for(intCount=0;intCount<100;intCount++){  //Count through all the walls, and if the player isn't walking into a makeshift wall, he is allowed to move
            if((int)((warriors[intPlayerN].intPosX-20)/36) == intWalls[intCount][0] && (int)((warriors[intPlayerN].intPosY)/36) == intWalls[intCount][1] && intWalls[intCount][2] >0){
              blnMove = false;
            }
          }
          if(warriors[intPlayerN].dblHP <= 0){      //If the player is dead, the player is allowed to move freely
            warriors[intPlayerN].intPosX = warriors[intPlayerN].intPosX-18;
          }else if(blnMove){                        //Else if the player is able to move, then move the player
            warriors[intPlayerN].intPosX = warriors[intPlayerN].intPosX-9;
          }
          blnSend = true;    //Sending other players your current location is true
        }
        if(blnRight && blnStart && !blnClient){   //If the user presses right and the game has started and is the server
          intSX = intSX+18;
        //Else if the player presses right and the game has started and (is walking on grass or is dead) and is walking within the map boundary
        }else if(blnRight && blnStart && (strMapSets[(int)((warriors[intPlayerN].intPosX+20)/36)][(int)((warriors[intPlayerN].intPosY)/36)].equals("g") || warriors[intPlayerN].dblHP <= 0) && warriors[intPlayerN].intPosX+20 < 2124){
          blnMove = true;
          for(intCount=0;intCount<100;intCount++){  //Count through all the walls, and if the player isn't walking into a makeshift wall, he is allowed to move
            if((int)((warriors[intPlayerN].intPosX+20)/36) == intWalls[intCount][0] && (int)((warriors[intPlayerN].intPosY)/36) == intWalls[intCount][1] && intWalls[intCount][2] >0){
              blnMove = false;
            }
          }
          if(warriors[intPlayerN].dblHP <= 0){      //If the player is dead, the player is allowed to move freely
            warriors[intPlayerN].intPosX = warriors[intPlayerN].intPosX+18;
          }else if(blnMove){                        //Else if the player is able to move, then move the player
            warriors[intPlayerN].intPosX = warriors[intPlayerN].intPosX+9;
          }
          blnSend = true;    //Sending other players your current location is true
        }
      }
      if(blnGameSet && blnShowScores){    //If the game has ended and scores can be shown
        g.setColor(Color.BLACK);
        g.fillRect(0,0,720,720);
        g.setColor(Color.YELLOW);
        g.setFont(font2);
        g.drawString("The Trigger Rankings",25,80);
        for(intCount=0;intCount<intTotalPlayers;intCount++){    //Count through all the rankings and print them out
          g.setFont(font3);
          g.drawString((intCount+1)+": "+strPlaces[intCount],40,intCount*40+150);
        }
        if(blnClient){    //If you are a client, it is printed out that you have to wait for the server/Game Master
          g.setColor(Color.WHITE);
          g.drawString("Waiting for Game Master...",25,705);
        }
      }
      g.setColor(Color.GRAY);
      g.fillRect(720,360,560,360);
      if(blnStart){     //If the game has started
        g.setColor(Color.BLACK);
        g.setFont(font1);
        if(blnClient){    //If you are a client, draw some part of the statistics UI
          g.drawString(warriors[intPlayerN].strName+"'s Statistics and Abilities",730,390);
        }else{            //Else you are the server, draw some part of the statistics UI
          g.drawString("Game Master Abilities",730,390);
        }
        if(blnClient){    //If you are a client, draw your Statistics UI, including their health
          g.setColor(Color.WHITE);
          g.fillRect(720,665,560,55);
          g.setColor(Color.BLACK);
          g.drawRect(720,665,560,55);
          g.setFont(font3);
          g.setColor(Color.YELLOW);
          g.drawString("HP: "+((int)(warriors[intPlayerN].dblHP*100.0))/100.0,743,705);
          g.setColor(Color.RED);
          g.drawString("HP: "+((int)(warriors[intPlayerN].dblHP*100.0))/100.0,740,705);
          if(warriors[intPlayerN].dblHP <= 0){  //If the player is dead, show the user is dead with text
            g.setFont(font3);
            g.setColor(Color.BLACK);
            g.drawString("YOU DIED",1070,705);
          }
        }
      }
      if(!blnStart){  //If the game didn't start yet, draw the menu
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1280,720);
        g.drawImage(menu2,0,0,null);
      }
    }
    if(intTimer<=0){    //If the countdown timer is less than or equal to zero
      blnCountDown = false;  //Count down is now false
    }
  }
  //Constructor
  public JGraphics(){
    super();  //panel stuff is supered
    try{      //This try and catch statement takes care of the IOExceptions
      grass = ImageIO.read(getClass().getResource("grass.png"));  //Picture variables are attached to picture files
      wall = ImageIO.read(getClass().getResource("wall.png"));   
      knife = ImageIO.read(getClass().getResource("knife.png")); 
      spear = ImageIO.read(getClass().getResource("spear.png"));   
      axe = ImageIO.read(getClass().getResource("axe.png")); 
      absorb = ImageIO.read(getClass().getResource("absorb.png"));  
      heal = ImageIO.read(getClass().getResource("heal.png"));  
      heal2 = ImageIO.read(getClass().getResource("heal2.png"));    
      FistUp = ImageIO.read(getClass().getResource("FistUp.png"));    
      FistDown = ImageIO.read(getClass().getResource("FistDown.png")); 
      FistLeft = ImageIO.read(getClass().getResource("FistLeft.png"));   
      FistRight = ImageIO.read(getClass().getResource("FistRight.png"));    
      KnifeUp = ImageIO.read(getClass().getResource("KnifeUp.png"));  
      KnifeDown = ImageIO.read(getClass().getResource("KnifeDown.png")); 
      KnifeLeft = ImageIO.read(getClass().getResource("KnifeLeft.png"));   
      KnifeRight = ImageIO.read(getClass().getResource("KnifeRight.png"));  
      SpearUp = ImageIO.read(getClass().getResource("SpearUp.png"));   
      SpearDown = ImageIO.read(getClass().getResource("SpearDown.png"));  
      SpearLeft = ImageIO.read(getClass().getResource("SpearLeft.png"));  
      SpearRight = ImageIO.read(getClass().getResource("SpearRight.png"));  
      AxeUp = ImageIO.read(getClass().getResource("AxeUp.png"));   
      AxeDown = ImageIO.read(getClass().getResource("AxeDown.png")); 
      AxeLeft = ImageIO.read(getClass().getResource("AxeLeft.png"));  
      AxeRight = ImageIO.read(getClass().getResource("AxeRight.png")); 
      dummy = ImageIO.read(getClass().getResource("dummy.png"));
      menu = ImageIO.read(getClass().getResource("menu.png"));
      menu2 = ImageIO.read(getClass().getResource("menu2.png"));
    }catch(IOException e){    //Catch an exception if needed
    }
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    The End of the Trigger Games JGraphics Program
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////