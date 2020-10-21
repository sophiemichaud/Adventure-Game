package adventure;
import java.util.ArrayList;

public class Player implements java.io.Serializable{

   /* private member variables */
   private String playerName;
   private ArrayList<Item> playerInventory;
   private ArrayList<Room> roomList;
   private String saveGameName;
   private Room currentRoom;
   private static final long serialVersionUID = -746807690929574412L;

   /* default constructor */
   public Player(){

      playerName = "No player name";
      playerInventory = new ArrayList<Item>();
      roomList = new ArrayList<Room>();
      saveGameName = "No save game";
      currentRoom = null;
   }

   /**
    * Outputs a formatted string with some player information 
    *
    * @return a formatted string
    */
   public String toString(){

      return "Player Name: " +playerName + "\nPlayer Save Game Name: " +saveGameName;
   }

   /**
    * Method gets the player's name
    *
    * @return player name
    */
   public String getName(){

      return this.playerName;
   }

   /**
    * Method sets the player's name
    *
    *@param newPlayerName
    */
   public void setPlayerName(String newPlayerName){

      this.playerName = newPlayerName;
   }

   /**
    * Gets a list of the player's inventory
    *
    *@return player inventory
    */
   public ArrayList<Item> getInventory(){

      return this.playerInventory;
   }
   
   /**
    * Sets the list of the player's inventory
    *
    * @param newList
    */
   public void setPlayerInventory(ArrayList<Item> newList){
       
      this.playerInventory = newList;
   }

   /**
    * Adds an item to a player's inventory
    *
    * @param newItem
    */
   public void addToInventory(Item newItem){

      this.playerInventory.add(newItem);
   }

   /**
    * Removes an item from the player's inventory
    * @param newItem
    */
   public void removeFromInventory(Item newItem){

      this.playerInventory.remove(newItem);
   }

   /**
    * Gets the reference to the current room
    *
    * @return current room
    */
   public Room getCurrentRoom(){

      return currentRoom;
   }

   /**
    * Sets the reference to the room that the player is currently in 
    *
    * @param theRoom
    */
   public void setCurrentRoom(Room theRoom){

      this.currentRoom = theRoom;
   }

   /**
    * Finds the current room in the list of rooms
    *
    */
   public void findCurrentRoom(){

      for(int i = 0; i < this.roomList.size(); i++){

         if((roomList.get(i).isCurrentRoom())){

            setCurrentRoom(roomList.get(i));
         }
     }
   }

   /**
    * Gets the name of the saved game
    *
    *@return name of saved game
    */
   public String getSaveGameName(){

      return saveGameName;
   }

   /**
    * Sets the name of the saved game
    *
    *@param newSaveGame
    */
   public void setSaveGameName(String newSaveGame){

      this.saveGameName = newSaveGame;
   }

   /**
    * Sets the room list from the adventure 
    *
    * @param newList
    */
   public void setRoomList(ArrayList<Room> newList){

      this.roomList = newList;
   }
}
