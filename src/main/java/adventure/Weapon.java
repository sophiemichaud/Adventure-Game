package adventure;

import org.json.simple.JSONObject;

public class Weapon extends Item implements Tossable{

    /**
     * Returns a string for tossing
     * @return tossing string
     */
    @Override
    public String toss(){

        return ("Good throw!");
    }

    public Weapon(JSONObject itemObj){

        super(itemObj);
    }
}
