package adventure;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Room implements java.io.Serializable{

    /* you will need to add some private member variables */
    private String roomName;
    private String longDescription;
    private String shortDescription;
    private Long roomID;
    private HashMap<String, Room> exitsMap;
    private String start;
    private boolean currentRoom;
    private ArrayList<Long> roomItems;
    private ArrayList<Long> exitIDS;
    private ArrayList<Room> listOfRooms;
    private ArrayList<String> exitDirections;
    private ArrayList<Entrance> entranceList;
    private ArrayList<Item> itemList;
    private static final long serialVersionUID = -2092476811237608763L;
    public static final String[] DIRECTIONS = {"N", "S", "E", "W", "up", "down"};
    private String myException;

    /* default constructor */
    public Room(){

        this.roomName = "No room name";
        this.longDescription = "No long description";
        this.shortDescription = "No short description";
        this.start = "Not starting room";
        this.roomID = 0L;
        this.currentRoom = false;
        this.exitsMap = new HashMap<String, Room>();
        this.roomItems = new ArrayList<Long>();
        this.itemList = new ArrayList<Item>();
    }

    public Room(JSONObject roomObj){

        setRoomProperties(roomObj);
        createObjects();
        isStartingRoom();
        setJsonExits(roomObj);
        setJsonLoot(roomObj);
    }

    /**
     * Sets a Room object's basic properties
     * 
     * @param roomObj
     */
    public void setRoomProperties(JSONObject roomObj){

        this.roomName = (String) roomObj.get("name");
        this.shortDescription = (String) roomObj.get("short_description");
        this.longDescription = (String) roomObj.get("long_description");
        this.roomID = (Long) roomObj.get("id");
        this.start = (String) roomObj.get("start");
    }

    /**
     * Creates memory for all the lists and map for a Room object
     */
    public void createObjects(){

        this.listOfRooms = new ArrayList<Room>();
        this.entranceList = new ArrayList<Entrance>();
        this.exitsMap = new HashMap<String, Room>();
        this.roomItems = new ArrayList<Long>();
        this.itemList = new ArrayList<Item>();
        this.exitIDS = new ArrayList<Long>();
        this.exitDirections = new ArrayList<String>();
    }

    /**
     * Determines the starting room in the adventure
     */
    public void isStartingRoom(){

        if(this.start != null){

            this.currentRoom = true;
        }else{

            this.start = "False";
            resetCurrentRoom();
        }
    }

    /**
     * Sets the list of exit objects from the JSON
     * 
     * @param roomObj
     */
    public void setJsonExits(JSONObject roomObj){

        JSONArray exitList = (JSONArray) roomObj.get("entrance");
        if(exitList != null){

            for(Object currExit : exitList){

                JSONObject exit = (JSONObject) currExit;
                Long connectingRoomID = (Long) exit.get("id");
                String direction = (String) exit.get("dir");
                Entrance newEnt = new Entrance(connectingRoomID, direction);
                addToLists(connectingRoomID, direction, newEnt);
            }
        }
    }

    private void addToLists(Long connectingRoomID, String direction, Entrance newEnt){

        exitIDS.add(connectingRoomID);
        exitDirections.add(direction);
        entranceList.add(newEnt);
    }
    
    /**
     * Sets the list of loot in a room from the JSON
     * 
     * @param roomObj
     */
    public void setJsonLoot(JSONObject roomObj){

        JSONArray lootList = (JSONArray) roomObj.get("loot");
        if(lootList != null){

            for(Object currLoot : lootList){

                JSONObject loot = (JSONObject) currLoot;
                this.roomItems.add((Long) loot.get("id"));
            }
        }
    }

    /**
     * Overriden toString() method
     * 
     * @return A formatted print statement with the room's name, long description, and ID.
     */
    public String toString(){

        return "Room name: " +roomName + "\nDescription: " +longDescription +"\nRoom ID: " +roomID;
    }

    /**
     * Method gets the list of all the items in the room
     * 
     * @return the list of items in the room
     */
    public ArrayList<Long> listItems(){
        
        return roomItems;
    }

    /**
     * Method sets the list of all the items in the room
     * 
     * @param newItemList
     */
    public void setListItems(ArrayList<Long> newItemList){

        if(this.roomItems != null){

            this.roomItems = newItemList;

        }else{

            roomItems = new ArrayList<Long>();
            this.roomItems = newItemList;
        }
    }

    /**
     * Removes an item object from a room
     * 
     * @param itemID
     */
    public void removeItem(Long itemID){

        for(int i = 0; i < roomItems.size(); i++){

            if(roomItems.get(i).equals(itemID)){

                roomItems.remove(i);
            }
        }
    }

    /**
     * Method gets the room's name.
     * 
     * @return the room name
     */
    public String getName(){   //REQUIRED

        return roomName;
    }

    /**
     * Method sets the room's name.
     * 
     * @param newName
     */
    public void setRoomName(String newName){

        this.roomName = newName;
    }

    /**
     * Method gets the room's long description
     * 
     * @return the room's long description
     */
    public String getLongDescription(){   //REQUIRED

        return longDescription;
    }

    /**
     * Method sets the room's long description
     * 
     * @param newLongDescription
     */
    public void setLongDescription(String newLongDescription){

        this.longDescription = newLongDescription;
    }

     /**
     * Method gets the room's short description
     * 
     * @return the room's short description
     */
    public String getShortDescription(){   //REQUIRED

        return shortDescription;
    }

    /**
     * Method sets the room's short description
     * 
     * @param newShortDescription
     */
    public void setShortDescription(String newShortDescription){

        this.shortDescription = newShortDescription;
    }

    /**
     * Method gets the room's ID
     * 
     * @return the room ID
     */
    public Long getRoomID(){

        return roomID;
    }

    /**
     * Method sets the room's ID
     * 
     * @param newRoomID
     */
    public void setRoomID(Long newRoomID){

        this.roomID = newRoomID;
    }

    /**
     * Method gets the connected room given a direction
     * 
     * @param direction
     * @return the reference to a connected room given a direction
     */
    public Room getConnectedRoom(String direction) {   //REQUIRED 

        Room theRoom = null;
        try{
            tester(direction);
            theRoom = exitsMap.get(direction);
        }catch(InvalidCommandException ex){
            this.myException = "Invalid Connection";
            theRoom = null;
        }
        return theRoom;
    }

    /**
     * Tester for try statement in getConnectedRoom
     * 
     * @param direction
     * @throws InvalidCommandException
     */
    public void tester(String direction) throws InvalidCommandException{

        if(isValidDirection(direction)){

            validOperations(direction);

        }else if(!isValidDirection(direction)){

            invalidOperations(direction);
        }
    }

    /**
     * Determines if there is a connection in a valid direction
     * 
     * @param direction
     * @throws InvalidCommandException
     */
    public void validOperations(String direction) throws InvalidCommandException{

        if(exitsMap.get(direction) == null){

            throw new InvalidCommandException("There is no room in that direction. Please try again!");
        }else{
            this.myException = "No Exception";
        }
    }

    /**
     * Determines if there is no direction or an invalid direction
     * 
     * @param direction
     * @throws InvalidCommandException
     */
    public void invalidOperations(String direction) throws InvalidCommandException{

        if(direction == null){
            throw new InvalidCommandException("A direction must follow the go command. Please try again!");
        }else{
            throw new InvalidCommandException("The direction you have entered does not exist. Please try again!");
        }
    }

    /**
     * Sets the myException member
     * 
     * @param message
     */
    public void setMyException(String message){

        this.myException = message;
    }

    /**
     * Gets the exception string
     * 
     * @return the exception
     */
    public String getMyException(){

        return this.myException;
    }

    /**
     * Method sets an exit for the room
     * 
     */
    public void setExit(){

        for(int i = 0; i < entranceList.size(); i++){

            Room newRoom = roomFinder(entranceList.get(i).getRoomID());
            String direction = entranceList.get(i).getDirection();
            exitsMap.put(direction, newRoom);
        }
    }

    /**
     * Method to set a tester exit
     * 
     * @param direction
     * @param newRoom
     */
    public void testSetExit(String direction, Room newRoom){

        exitsMap.put(direction, newRoom);
    }

    /**
     * Sets the list of rooms from the adventure
     * 
     * @param newRoomList
     */
    public void setListOfRooms(ArrayList<Room> newRoomList){

        this.listOfRooms = newRoomList;
    }

    /**
     * Finds a room in the list of rooms given a room ID
     * 
     * @param theRoomID
     * @return the room associated with the room ID
     */
    public Room roomFinder(Long theRoomID){

        Room theRoom = null;
        for(int i = 0; i < this.listOfRooms.size(); i++){

            if((listOfRooms.get(i).getRoomID()).equals(theRoomID)){

                theRoom = listOfRooms.get(i);
            }
        }
        return theRoom;
    }

    /**
     * Determines if a room is current or not
     * 
     * @return true or false depending on the status of the current room
     */
    public boolean isCurrentRoom(){

        return this.currentRoom;
    }

    /**
     * Method sets the room to be the current room or to not the current room.
     * 
     */
    public void setCurrentRoom(){

        this.currentRoom = true;
    }

    /**
     * Sets the currentRoom value to false
     * 
     */
    public void resetCurrentRoom(){

        this.currentRoom = false;
    }

    /**
     * Sets the item list passed by adventure to the current room object
     * 
     * @param newItemList
     */
    public void setItemList(ArrayList<Item> newItemList){

        this.itemList = newItemList;
    }

    /**
     * Finds an item in the list of items given an item ID
     * 
     * @param theItemID
     * @return the item associated with the item ID
     */
    public Item itemFinder(Long theItemID){

        Item theItem = null;
        for(int i = 0; i < this.itemList.size(); i++){

            if((itemList.get(i).getItemID()).equals(theItemID)){

                theItem = itemList.get(i);
            }
        }
        return theItem;
    }

    /**
     * Sends a formatted list of items in a room
     * 
     * @return the formatted list of items in the current room
     */
    public String sendItemList(){

        String theList = "\n";
        if(roomItems.size() > 0){
            for(int i = 0; i < roomItems.size(); i++){

                Item nextItem = itemFinder(roomItems.get(i));
                theList = theList.concat("  " +(i+1) + "." +nextItem.getName());
            }
        }
        return theList;
    }

    /**
     * Determines if a direction is valid
     * 
     * @param newDirection
     * @return if the direction is valid
     */
    public static boolean isValidDirection(String newDirection){

        boolean isValid = false;

        for(int i = 0; i < DIRECTIONS.length; i++){

            if(DIRECTIONS[i].equals(newDirection)){
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * Sets a Room object to be the starting room in the adventure
     * 
     * @param newStart
     */
    public void setStart(String newStart){
        
        this.start = newStart;
    }
    
    /**
     * Sets the list of items inside a Room
     * 
     * @param newItems
     */
    public void setRoomItems(ArrayList<Long> newItems){
        
        this.roomItems = newItems;
    }
    
    /**
     * Sets the entrance list for a room
     * 
     * @param newList
     */
    public void setEntranceList(ArrayList<Entrance> newList){
        
        this.entranceList = newList;
    }
    
    /**
     * Sets the exits map with a new map
     * 
     * @param newMap
     */
    public void setExitsMap(HashMap<String,Room> newMap){
        
        this.exitsMap = newMap;
    }

    /**
     * Gets a room's exits map
     * @return the room's exits map
     */
    public HashMap<String,Room> getExitsMap(){

        return this.exitsMap;
    }

    /**
     * Adds a new item to the list of current items in the room
     * @param newItem
     */
    public void addToItemList(Item newItem){

        this.itemList.add(newItem);
    }

    /**
     * Returns the list of the room's current items
     * @return itemList
     */
    public ArrayList<Item> getItemList(){

        return this.itemList;
    }

    /**
     * Gets a list of all the item IDs in the room
     * @return a list of item IDs in the room
     */
    public ArrayList<Long> getItemListIDS(){

        return this.roomItems;
    }

    /**
     * Sets a list of room exits ID's
     * @param newList
     */
    public void setExitIDS(ArrayList<Long> newList){

        this.exitIDS = newList;
    }
    
    /**
     * Gets a list of room exit ID's
     * @return a list of room exit ID's
     */
    public ArrayList<Long> getExitIDS(){

        return this.exitIDS;
    }

    /**
     * Sets a list of exit directions
     * @param exitList
     */
    public void setExitDirections(ArrayList<String> exitList){

        this.exitDirections = exitList;
    }

    /**
     * Gets a list of all the exit directions
     * @return a list of exit directions
     */
    public ArrayList<String> getExitDirections(){

        return this.exitDirections;
    }
}
