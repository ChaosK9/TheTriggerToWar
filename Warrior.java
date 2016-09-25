//The Trigger Games Class Definition Document for the Players
import java.awt.*;
/**
 * This class will create Warrior objects that each player can control in the game
 * These "Warriors" will be used by each player to fight to the death
 */
public class Warrior{
  //Properties
  /**
   * This variable will contain the name of this warrior
   */
  public String strName;
  /**
   * This variable will contain what this warrior typed into the message box
   */
  public String strMessage;
  /**
   * This variable will contains the warrior's Current X position on the map
   */
  public int intPosX;
  /**
   * This variable will contains the warrior's Current Y position on the map
   */
  public int intPosY;
  /**
   * This variable will contains the warrior's NEXT potential X position on the map
   */
  public int intNextPosX;
  /**
   * This variable will contains the warrior's NEXT potential T position on the map
   */
  public int intNextPosY;
  /**
   * A unique number is assigned to each different warrior in this variable
   */
  public int intWarriorNumber;
  
  /**
   * This variable will contain how many health points this warrior has
   */
  public double dblHP;
  /**
   * This determines which weapon the warrior currently has out
   */
  public int intWeaponNumber;
  /**
   * This determines which potion the warrior currently has out
   */
  public int intPotionNumber;
  /**
   * This cooldown states how many more seconds until the warrior can attack again
   */
  public int intCooldown;
  /**
   * This cooldown states how many more seconds until the warrior can use a potion again
   */
  public int intCooldown2;
  /**
   * This determines the final amount of damage and defence that the warrior has
   */
  public int intStrength;
  /**
   * This determines the range within enemies will be affected by the warrior's attack
   */
  public int intRange;
  
  /**
   * This determines the color of the player
   */
  public Color UserColor;
  
  /**
   * This variable will state whether or not this warrior has the HP potion or not
   */
  public boolean blnHPPotion;
  /**
   * This variable will state whether or not this warrior has won the game
   */
  public boolean blnWin;
  /**
   * This variable will state whether or not this warrior has connected
   */
  public boolean blnWarriorConnect;
  
  public boolean blnItem1;
  
  public boolean blnItem2;
  
  public boolean blnItem3;
  
  public boolean blnItem4;
  
  public boolean blnItem5;
  
  //Methods
  /**
   * This method will move the warrior depending on the warrior's direction
   */
  public static void Move(String strDirection){
  }
  /**
   * This method will switch the warrior's current item depending on what item is given in the parameters
   */
  public static void ItemSwitch(int intItemNumber){
  }
  /**
   * This method will deal damage within a range depending on the warrior's position and attack cooldown
   */
  public static void Attack(int intCooldown, int intRange){
  }
  /**
   * The warrrior will be dealt damage depending on the enemy's weapon and user's strength
   */
  public static void Attacked(int intWeapon, int intUserStrength){
  }
  /**
   * The warrior will drop a weapon, depending on which weapon he drops
   */
  public static void ItemDrop(int intDropped){
  }
  /**
   * The warrior will heal himself depending on the potion cooldown, and whether or not the warrior has a health potion
   */
  public static void Heal(int intCooldown2, boolean blnHPPotion){
  }
  /**
   * The warrior will pick up an item depending on what item is detected near him
   */
  public static void ItemPickUp(int intItem){
  }
  /**
   * The warrior will be dealt damage if he walks onto anything that can hurt him
   */
  public static void EnterTrap(){
  }
  
  //Constructor
  public Warrior(String strName, String strMessage, int intPosX, int intPosY, int intNextPosX, int intNextPosY, int intWarriorNumber, double dblHP, int intWeaponNumber, int intPotionNumber, int intCooldown, int intCooldown2, int intStrength, int intRange, Color UserColor, boolean blnHPPotion, boolean blnWin, boolean blnItem1, boolean blnItem2, boolean blnItem3, boolean blnItem4, boolean blnItem5){
    this.strName = strName;
    this.strMessage = strMessage;
    this.intPosX = intPosX;
    this.intPosY = intPosY;
    this.intNextPosX = intNextPosX;
    this.intNextPosY = intNextPosY;
    this.intWarriorNumber = intWarriorNumber;
    this.dblHP = dblHP;
    this.intWeaponNumber = intWeaponNumber;
    this.intCooldown = intCooldown;
    this.intCooldown2 = intCooldown2;
    this.intStrength = intStrength;
    this.intRange = intRange;
    this.UserColor = UserColor;
    this.blnHPPotion = blnHPPotion;
    this.blnWin = blnWin;
    this.blnItem1 = blnItem1;
    this.blnItem2 = blnItem2;
    this.blnItem3 = blnItem3;
    this.blnItem4 = blnItem4;
  }
}