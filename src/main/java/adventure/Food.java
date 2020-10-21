package adventure;

import org.json.simple.JSONObject;

public class Food extends Item implements Edible{

    /**
     * Returns a string for eating
     * @return eating string
     */
    @Override
    public String eat(){

        return ("Yum, that was delicious!");
    }

    public Food(JSONObject itemObj){

        super(itemObj);
    }
}
