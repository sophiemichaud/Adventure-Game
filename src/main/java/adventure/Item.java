package adventure;

import org.json.simple.JSONObject;
import java.util.ArrayList;

/**
 * This is the item class
 * 
 * @author Sophie Michaud
 * @version 2.0
 */
public class Item implements java.io.Serializable{

    /* private member variables */
    private String itemName;
    private String longDescription;
    private Long itemID;
    private Room containingRoom;
    private ArrayList<Room> listOfRooms;
    private static final long serialVersionUID = 882592969324047491L;

    /* default constructor */
    public Item(){

        this.itemName = "No Name";
        this.longDescription = "No long description";
        this.itemID = 0L;
        this.containingRoom = null;
        this.listOfRooms = new ArrayList<Room>();
    }

    public Item(JSONObject itemObj){

        this.itemName = (String) itemObj.get("name");
        this.longDescription = (String) itemObj.get("desc");
        this.itemID = (Long) itemObj.get("id");
        listOfRooms = new ArrayList<Room>();
    }

    /**
     * Overriden toString method
     * 
     * @return A formatted print statement with the item's name, long description, and ID.
     */
    public String toString(){

        return "Item Name: " +this.itemName + " Item Description: " +this.longDescription + " Item ID: " +this.itemID;
    }

    /**
     * This method gets the name of an item.
     * 
     * @return the item's name
     */
    public String getName(){  //REQUIRED

        return itemName;
    }

    /**
     * This method sets the name of an item.
     * 
     * @param newItemName
     */
    public void setItemName(String newItemName){

        this.itemName = newItemName;
    }

    /**
     * This method gets the item's long description.
     * 
     * @return the item's long description
     */
    public String getLongDescription(){  //REQUIRED
    
        return longDescription;
    }

    /**
     * This method sets the item's long description.
     * 
     * @param newLongDescription
     */
    public void setLongDescription(String newLongDescription){

        this.longDescription = newLongDescription;
    }

    /**
     * This method gets the item's ID.
     * 
     * @return the item ID.
     */
    public Long getItemID(){

        return itemID;
    }

    /**
     * This method sets the item's ID.
     * 
     * @param newItemID
     */
    public void setItemID(Long newItemID){

        this.itemID = newItemID;
    }

    /**
     * Gets the list of rooms from the adventure and sets the list in the room object
     * 
     * @param newRoomList
     */
    public void setListOfRooms(ArrayList<Room> newRoomList){

        this.listOfRooms = newRoomList;
    }

    /**
     * This methods gets the reference to the room that contains the item.
     * 
     * @return the room containing the item
     */
    public Room getContainingRoom(){  //REQUIRED
        
        return containingRoom;
    }

    /**
     * This method sets the reference to the room that contains the item.
     * 
     */
    public void setContainingRoom(){

        this.containingRoom = findContainingRoom();
    }

    /**
     * Finds an item's containing room
     * 
     * @return the containing room
     */
    public Room findContainingRoom(){

        Room theRoom = null;
        for(int i = 0; i < listOfRooms.size(); i++){

            ArrayList<Long> itemIDs = listOfRooms.get(i).listItems();
            for(int j = 0; j < itemIDs.size(); j++){

                if((itemIDs.get(j)).equals(this.itemID)){

                    theRoom = listOfRooms.get(i);
                }
            }
        }
        return theRoom;
    }
}
