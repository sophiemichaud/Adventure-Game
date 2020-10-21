package adventure;

import org.json.simple.JSONObject;

public class Spell extends Item implements Readable{

    /**
     * Returns a string for reading
     * @return reading string
     */
    @Override
    public String read(){

        return ("Wow, that was a great read!");
    }

    public Spell(JSONObject itemObj){

        super(itemObj);
    }
}
