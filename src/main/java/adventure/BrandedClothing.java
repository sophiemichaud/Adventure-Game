package adventure;

import org.json.simple.JSONObject;

public class BrandedClothing extends Clothing implements Readable{

    /**
     * Returns a string for reading
     * @return reading string
     */
    @Override
    public String read(){

        return ("Wow, that was a great read!");
    }

    public BrandedClothing(JSONObject itemObj){

        super(itemObj);
    }
}
