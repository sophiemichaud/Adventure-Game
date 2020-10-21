package adventure;

import org.json.simple.JSONObject;

public class Clothing extends Item implements Wearable{

    /**
    * Returns a string for wearing
    * @return wearing string
    */
    @Override
    public String wear(){

        return ("This looks great!");
    }

    public Clothing(JSONObject itemObj){

        super(itemObj);
    }
}
