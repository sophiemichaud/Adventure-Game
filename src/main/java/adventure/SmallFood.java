package adventure;

import org.json.simple.JSONObject;

public class SmallFood extends Food implements Tossable{

    /**
     * Returns a string for tossing
     * @return tossing string
     */
    @Override
    public String toss(){

        return ("Good throw!");
    }

    public SmallFood(JSONObject itemObj){

        super(itemObj);
    }
}
