package adventure;

/**
 * This class is used to define an entrance within the game
 * 
 * @author Sophie Michaud
 * @version 1.0
 */
public class Entrance implements java.io.Serializable{

    private Long entranceID;
    private String direction;
    private static final long serialVersionUID = -7928849122309613787L;

    /**
     * Constructor
     * 
     * @param id
     * @param dir
     */
    public Entrance(Long id, String dir){

        entranceID = id;
        direction = dir;
    }

    /**
     * Overriden toString
     * 
     * @return a formatted string with the entrance ID and direction
     */
    public String toString(){

        return "Entrance ID: " +entranceID + "\n Entrance Direction: " +direction;
    }

    /**
     * gets the room ID of the entrance
     * @return entranceID
     */
    public Long getRoomID(){

        return entranceID;
    }

    /**
     * Gets the direction of the entrance
     * @return direction
     */
    public String getDirection(){

        return direction;
    }
}
