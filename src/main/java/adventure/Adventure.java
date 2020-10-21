package adventure;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;

/**
 * Adventure Class
 * 
 * @author Sophie Michaud
 * @version 2.0
 */
public class Adventure implements java.io.Serializable {
    
    /* private member variables */
    private ArrayList<Room> roomList;
    private ArrayList<Item> itemList;
    private Room currentRoom;
    private String currentRoomDescription;
    private Player newPlayer;
    private static final long serialVersionUID = -1878151202853886542L;

    /* default constructor */
    public Adventure(){

        this.roomList = new ArrayList<Room>();
        this.itemList = new ArrayList<Item>();
        this.currentRoomDescription = "No room description";
        this.newPlayer = new Player();
    }

    public Adventure(JSONObject adventureJSON){

        this.roomList = new ArrayList<Room>();
        this.itemList = new ArrayList<Item>();

        setListAllRooms((JSONArray) adventureJSON.get("room"));
        setListAllItems((JSONArray) adventureJSON.get("item"));

        setCurrentRoom();
        setCurrentRoomDescription();
        sendAndSetExits();
        this.newPlayer = new Player();
    }

    /**
     * Sends the list of rooms to each room object to set its exits
     */
    public void sendAndSetExits(){

        for(int i = 0; i < roomList.size(); i++){

            roomList.get(i).setListOfRooms(roomList);
            roomList.get(i).setExit();
        }
    }

    /**
     * Overriden toString() method, provides: All room names, all item names
     * and the current room description
     *
     * @return the complete string
     */
    public String toString(){

        String roomNames = roomsToString();
        String itemNames = itemsToString();
        String currDesc = "\n\nCurrent Room Description:\n\n" + this.currentRoomDescription;
        String completeString = roomNames.concat(itemNames);
        completeString = completeString.concat(currDesc);
        return completeString;
    }

    /**
     * Creates a string representation of the rooms in the adventure
     * 
     * @return a formatted string that includes all of the room names
     */
    public String roomsToString(){

        String roomNames = "All Room Names:\n";
        for(int i = 0; i < this.roomList.size(); i++){

            String temp = "\nRoom " +i +": " +roomList.get(i).getName();
            roomNames = roomNames.concat(temp);
        }

        return roomNames;
    }

    /**
     * Creates a string representation of the items in the adventure
     * 
     * @return a formatted string that includes all of the item names
     */
    public String itemsToString(){

        String itemNames = "\n\nAll Item Names:\n";
        for(int j = 0; j < this.itemList.size(); j++){

            String temp = "\nItem " +j +": " +itemList.get(j).getName();
            itemNames = itemNames.concat(temp);
        }
        
        return itemNames;
    }
    
    /**
     * Sets the list of rooms in the adventure
     * 
     * @param newList
     */
    public void setRoomList(ArrayList<Room> newList){
        
        this.roomList = newList;
    }
    
    /**
     * Sets the list of items in the adventure
     * 
     * @param newList
     */
    public void setItemList(ArrayList<Item> newList){
        
        this.itemList = newList;
    }

    /**
     * Gets a list of all the rooms in the adventure
     * 
     * @return list of all rooms
     */
    public ArrayList<Room> listAllRooms(){

        return roomList;
    }

    /**
     * Sets the list of all the rooms in the adventure
     * 
     * @param newRoomList
     */
    private void setListAllRooms(JSONArray newRoomList){

        for(Object currRoom : newRoomList){

            JSONObject room = (JSONObject) currRoom;
            Room nextRoom = new Room(room);
            this.roomList.add(nextRoom);
        }
    }

    /**
     * Gets a list of all the items in the adventure
     * 
     * @return list of all items
     */
    public ArrayList<Item> listAllItems(){

        return itemList;
    }

    /**
     * Sets the list of all the items in the adventure
     * 
     * @param newItemList
     */
    private void setListAllItems(JSONArray newItemList){

        for(Object currentItem : newItemList){

            JSONObject item = (JSONObject) currentItem;
            Item nextItem = determineItemType(item);
            this.itemList.add(nextItem);
        }
    }

    /**
     * Determines the type of a given item
     * @param item
     * @return a new item object
     */
    public Item determineItemType(JSONObject item){

        Item nextItem = null;
        nextItem = setItemIfEdibleOrTossable(item, nextItem);
        nextItem = setItemIfReadableOrWearable(item, nextItem);
        if(nextItem == null){

            nextItem = new Item(item);
        }
        return nextItem;
    }

    /**
     * Creates new Food or SmallFood object if valid
     * @param item
     * @param nextItem
     * @return an object that is Edible or Tossable
     */
    public Item setItemIfEdibleOrTossable(JSONObject item, Item nextItem){

        if(item.get("edible") != null && item.get("tossable") != null){
            
            if(item.get("edible").equals(true) && item.get("tossable").equals(true)){

                nextItem = new SmallFood(item);
            }
        }else if(item.get("edible") != null){

            nextItem = ifEdible(item, nextItem);
        }else if(item.get("tossable") != null){

            nextItem = ifTossable(item, nextItem);
        }
        return nextItem;
    }

    /**
     * Follows instructions for an item that only has edible in JSON
     * @param item
     * @param nextItem
     * @return a Food object
     */
    public Item ifEdible(JSONObject item, Item nextItem){

        if(item.get("edible").equals(true)){

            nextItem = new Food(item);
        }
        return nextItem;
    }

    /**
     * Follows instructions for an item that only has throwable in JSON
     * @param item
     * @param nextItem
     * @return a Weapon object
     */
    public Item ifTossable(JSONObject item, Item nextItem){

        if(item.get("tossable").equals(true)){

            nextItem = new Weapon(item);
        }
        return nextItem;
    }

    /**
     * Creates new Clothing or BrandedClothing object if valid
     * @param item
     * @param nextItem
     * @return an object that is Readable or Wearable
     */
    public Item setItemIfReadableOrWearable(JSONObject item, Item nextItem){

        if(item.get("wearable") != null && item.get("readable") != null){
            
            if(item.get("wearable").equals(true) && item.get("readable").equals(true)){

                nextItem = new BrandedClothing(item);
            }
        }else if(item.get("wearable") != null){ 

            nextItem = ifWearable(item, nextItem);
        }else if(item.get("readable") != null){

            nextItem = ifReadable(item, nextItem);
        }
        return nextItem;
    }

    /**
     * Follows instructions for an item that only has wearable in JSON
     * @param item
     * @param nextItem
     * @return a Clothing object
     */
    public Item ifWearable(JSONObject item, Item nextItem){

        if(item.get("wearable").equals(true)){

            nextItem = new Clothing(item);
        }
        return nextItem;
    }

    /**
     * Follows instructions for an item that only has readable in JSON
     * @param item
     * @param nextItem
     * @return a Spell object
     */
    public Item ifReadable(JSONObject item, Item nextItem){

        if(item.get("readable").equals(true)){

            nextItem = new Spell(item);
        }
        return nextItem;
    }
    
    /**
     * Sets the room to be the current room
     * 
     */
    public void setCurrentRoom(){

        for(int i = 0; i < this.roomList.size(); i++){

            if((roomList.get(i).isCurrentRoom())){

                this.currentRoom = roomList.get(i);
            }
        }
    }

    /**
     * Gets the description of the current room
     * 
     * @return description of the current room
     */
    public String getCurrentRoomDescription(){   //REQUIRED

        return currentRoomDescription;
    }

    /**
     * Sets the description of the current room
     * 
     */
    public void setCurrentRoomDescription(){

        String newDesc = this.currentRoom.getLongDescription();
        this.currentRoomDescription = newDesc;
    }

    /**
     * Moves the hero to another room given a direction
     * 
     * @param newCommand
     * @return A string with the list of items in the new room
     */
    public String moveHero(Command newCommand) throws InvalidCommandException{

        getCurrentInfo();
        setConnection(newCommand);
        transferConnection();

        String myItemList = this.currentRoom.sendItemList();
        String formatted = null;
        formatted = formatOuput(myItemList, formatted);
        return formatted;
    }

    /**
     * Extracts information from the current room
     */
    public void getCurrentInfo(){

        this.currentRoom.resetCurrentRoom();
        this.currentRoom.setListOfRooms(roomList);
        this.currentRoom.setExit();
    }

    /**
     * Sets a connection between the current room and the next room
     * 
     * @param newCommand
     * @throws InvalidRoom
     */
    public void setConnection(Command newCommand) throws InvalidCommandException{

        String direction = newCommand.getNoun();
        if(this.currentRoom.getConnectedRoom(direction) != null){

            this.currentRoom = this.currentRoom.getConnectedRoom(direction);
            this.currentRoom.setItemList(itemList);
        }else{

            throw new InvalidCommandException("There is no room in that direction. Please try again!");
        }
    }

    /**
     * Transfers current room to be the next room
     */
    public void transferConnection(){

        if(this.currentRoom != null){

            this.currentRoom.setCurrentRoom();
        }
    }

    /**
     * Formats the output for moveHero method
     * 
     * @param myItemList
     * @param formatted
     * @return formatted string
     */
    public String formatOuput(String myItemList, String formatted){

        if(myItemList.length() > 1){
            formatted = "You have entered: " +this.currentRoom.getName() + "\n  Here is the list of items in the room:";
            formatted = formatted.concat(myItemList);
        }else{

            formatted = "You have entered: " +this.currentRoom.getName();
            formatted = formatted.concat("\n  There are no items in this room!");
        }

        return formatted;
    }

    /**
     * Allows the player to look at a longer description of an item or room
     * 
     * @param newCommand
     * @return a description of an item or a room
     */
    public String lookAtSomething(Command newCommand) throws InvalidCommandException{

        String theDescription;
        if(newCommand.getNoun() != null){

            theDescription = findMatchingItemDescription(newCommand);
        }else{

            theDescription = this.currentRoom.getLongDescription();
        }
        return theDescription;
    }

    /**
     * Finds a matching item description based on an item name
     * 
     * @param newCommand
     * @return the matching item's description
     */
    public String findMatchingItemDescription(Command newCommand){

        String theItemName = newCommand.getNoun();
        String theDescription = "";
        for(int i = 0; i < itemList.size(); i++){

            if((itemList.get(i).getName()).equals(theItemName)){

                theDescription = itemList.get(i).getLongDescription();
            }
        }
        return theDescription;
    }

    /**
     * Allows the player to take an item from a room to keep in their inventory
     * 
     * @param newCommand
     */
    public void takeItem(Command newCommand) throws InvalidCommandException{

        String theItemName = newCommand.getNoun();
        findMatchingItem(theItemName);
    }

    /**
     * Finds an item object based on an item name
     * 
     * @param theItemName
     * @throws InvalidItem
     */
    public void findMatchingItem(String theItemName) throws InvalidCommandException{

        Item theItem;
        for(int i = 0; i < itemList.size(); i++){

            if((itemList.get(i).getName().equals(theItemName))){

                theItem = itemList.get(i);
                setItemProperties(theItem);
            }
        }
    }

    /**
     * Sets an item's properties
     * 
     * @param theItem
     * @throws InvalidItem
     */
    public void setItemProperties(Item theItem) throws InvalidCommandException{

        theItem.setListOfRooms(roomList);
        theItem.setContainingRoom();
        roomToInventory(theItem);
    }

    /**
     * Takes an item from room and puts it in player's inventory
     * 
     * @param theItem
     * @throws InvalidItem
     */
    public void roomToInventory(Item theItem) throws InvalidCommandException{

        if((theItem.getContainingRoom()).equals(this.currentRoom)){

            this.newPlayer.addToInventory(theItem);
            this.currentRoom.removeItem(theItem.getItemID());
        }else{

            throw new InvalidCommandException("That item doesn't exist here. Please try again!");
        }
    }

    /**
     * Allows the player to look at the items in their inventory
     * 
     * @return a string with the items in player's inventory
     */
    public String playerInventory(){

        String playerInv = "";
        for(int i = 0; i < this.newPlayer.getInventory().size(); i++){

            playerInv = playerInv.concat(newPlayer.getInventory().get(i).getName() + "\n");
        }

        return playerInv;
    }

    /**
     * Allows the player to eat an item in their inventory
     * @param newCommand
     * @return the eat string
     */
    public String eatSomething(Command newCommand) throws InvalidCommandException{

        String output = "";
        String itemName = newCommand.getNoun();
        for(int i = 0; i < this.newPlayer.getInventory().size(); i++){

            Item theItem = this.newPlayer.getInventory().get(i);
            output = ifFoodFound(theItem, output, itemName);
        }
        return output;
    }

    /**
     * Gets the output string if an item is eaten
     * @param theItem
     * @param output
     * @param itemName
     * @return the output string if item is eaten
     */
    public String ifFoodFound(Item theItem, String output, String itemName) throws InvalidCommandException{

        if(theItem.getName().equals(itemName) && theItem instanceof Edible){

            this.newPlayer.removeFromInventory(theItem);
            Food thefood = (Food) theItem;
            output = thefood.eat();
        }else{
            throw new InvalidCommandException("I can't eat that!");
        }
        return output;
    }

    /**
     * Allows the player to wear an item in their inventory
     * @param newCommand
     * @return the wear string
     */
    public String wearSomething(Command newCommand) throws InvalidCommandException{

        String output = "";
        String itemName = newCommand.getNoun();
        for(int i = 0; i < this.newPlayer.getInventory().size(); i++){

            Item theItem = this.newPlayer.getInventory().get(i);
            output = ifClothingFound(theItem, output, itemName);
        }
        return output;
    }

    /**
     * Follows instructions if correct clothing item is found in inventory
     * @param theItem
     * @param output
     * @param itemName
     * @return the output string if item is worn
     */
    public String ifClothingFound(Item theItem, String output, String itemName) throws InvalidCommandException{

        if(theItem.getName().equals(itemName) && theItem instanceof Wearable){

            String newName = theItem.getName() + " (Wearing)";
            theItem.setItemName(newName);
            Clothing theClothes = (Clothing) theItem;
            output = theClothes.wear();
        }else{
            throw new InvalidCommandException("I can't wear that!");
        }
        return output;
    }

    /**
     * Allows the player to toss something from their inventory into the current room
     * @param newCommand
     * @return the output of tossing
     */
    public String tossSomething(Command newCommand) throws InvalidCommandException{

        String output = "";
        String itemName = newCommand.getNoun();
        for(int i = 0; i < this.newPlayer.getInventory().size(); i++){

            Item theItem = this.newPlayer.getInventory().get(i);
            output = ifTossableFound(theItem, output, itemName);
        }
        return output;
    }

    /**
     * Gets the output string if an item is tossed
     * @param theItem
     * @param output
     * @param itemName
     * @return the ouput if an item is tossed
     */
    public String ifTossableFound(Item theItem, String output, String itemName) throws InvalidCommandException{

        if(theItem.getName().equals(itemName) && theItem instanceof Weapon){

            output = weaponInstructions(theItem, output);

        }else if(theItem.getName().equals(itemName) && theItem instanceof SmallFood){

            output = smallFoodInstructions(theItem, output);
        }else{
            throw new InvalidCommandException("I can't toss that!");
        }
        return output;
    }

    /**
     * Computes a set of instructions if the item is a weapon
     * @param theItem
     * @param output
     * @return the output of computing the instructions for a weapon
     */
    public String weaponInstructions(Item theItem, String output){

        Weapon theWeapon = (Weapon) theItem;
        this.newPlayer.removeFromInventory(theWeapon);
        this.currentRoom.addToItemList(theWeapon);
        output = theWeapon.toss();
        return output;
    }

    /**
     * Computes a set of instructions if the item is a small food
     * @param theItem
     * @param output
     * @return the output of computing the instructions for a small food
     */
    public String smallFoodInstructions(Item theItem, String output){

        SmallFood theFood = (SmallFood) theItem;
        this.newPlayer.removeFromInventory(theFood);
        this.currentRoom.addToItemList(theFood);
        output = theFood.toss();
        return output;
    }

    /**
     * Allows the user to read an item in their inventory
     * @param newCommand
     * @return the output if an item is read
     */
    public String readSomething(Command newCommand) throws InvalidCommandException{

        String output = "";
        String itemName = newCommand.getNoun();
        for(int i = 0; i < this.newPlayer.getInventory().size(); i++){

            Item theItem = this.newPlayer.getInventory().get(i);
            output = ifReadableFound(theItem, output, itemName);
        }
        return output;

    }

    /**
     * Gets the output if the item is read
     * @param theItem
     * @param output
     * @param itemName
     * @return the output if the item is read
     */
    public String ifReadableFound(Item theItem, String output, String itemName) throws InvalidCommandException{

        if(theItem.getName().equals(itemName) && theItem instanceof BrandedClothing){

            BrandedClothing theClothes = (BrandedClothing) theItem;
            output = theClothes.read();

        }else if(theItem.getName().equals(itemName) && theItem instanceof Spell){

            Spell theSpell = (Spell) theItem;
            output = theSpell.read();
        }else{
            throw new InvalidCommandException("I can't read that!");
        }
        return output;
    }

    /**
     * Sets the player's name
     * 
     * @param newName
     */
    public void setPlayerName(String newName){

        this.newPlayer.setPlayerName(newName);
    }

    /**
     * Sets the name of the player's saved game
     * 
     * @param newName
     */
    public void setSaveGameName(String newName){

        this.newPlayer.setSaveGameName(newName);
    }
    
    /**
     * Sets the new player instance variable
     * 
     * @param myPlayer
     */
    public void setNewPlayer(Player myPlayer){
        
        this.newPlayer = myPlayer;
    }

    /**
     * Gets the player's name
     * 
     * @return the player's name
     */
    public String getPlayerName(){

        return this.newPlayer.getName();
    }

    /**
     * Sends a list of all the items currently in inventory to Game
     * 
     * @return a list of the player's current inventory
     */
    public ArrayList<Item> getInventory(){

        return(this.newPlayer.getInventory());
    }

    /**
     * Gets a list of all the items in the current room
     * @return the current inventory
     */
    public ArrayList<Item> getItems(){

        return this.currentRoom.getItemList();
    }
}
