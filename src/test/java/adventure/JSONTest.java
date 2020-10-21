package adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import adventure.InvalidJSONException;
import jdk.jfr.Timestamp;
import org.junit.Before;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class JSONTest{

    private Game theGame;
    private String fileName;

    @Before
    public void setup(){

        theGame = new Game();
        String fileName = "";
    }

    @Test
    public void testNoRoomConnections(){

        fileName = "src/test/java/adventure/example_adventure_noexits.json";
        System.out.println("Testing new JSON file with a room that has no exits");
        try{

            JSONObject advObj = theGame.loadAdventureJson(fileName);
            theGame.testGivenJson((JSONObject) advObj.get("adventure"));

        }catch(InvalidJSONException ex){

            assertTrue(ex.getMessage().equals("A room in your JSON has no exits. Please fix the error, and try loading again!"));
        }
    }

    @Test
    public void testBadRoomConnection(){

        fileName = "src/test/java/adventure/example_adventure_badConnection.json";
        System.out.println("Testing new JSON file with an exit going the wrong dircection");
        try{

            JSONObject advObj = theGame.loadAdventureJson(fileName);
            theGame.testGivenJson((JSONObject) advObj.get("adventure"));

        }catch(InvalidJSONException ex){

            assertTrue(ex.getMessage().equals("A room in your JSON file has an incorrect exit direction. Try again!"));
        }
    }

    @Test
    public void testNoItem(){

        fileName = "src/test/java/adventure/example_adventure_badItem.json";
        System.out.println("Testing new JSON file with an item that does not exist in any room");
        try{

            JSONObject advObj = theGame.loadAdventureJson(fileName);
            theGame.testGivenJson((JSONObject) advObj.get("adventure"));

        }catch(InvalidJSONException ex){

            assertTrue(ex.getMessage().equals("One of the rooms in your JSON has an item that does not exist in the game!"));
        }
    }

    @Test
    public void testNoRoomWithThatID(){

        fileName = "src/test/java/adventure/example_adventure_badID.json";
        System.out.println("Testing new JSON file with a room that has an exit to a room that does not exist");
        try{

            JSONObject advObj = theGame.loadAdventureJson(fileName);
            theGame.testGivenJson((JSONObject) advObj.get("adventure"));

        }catch(InvalidJSONException ex){

            assertTrue(ex.getMessage().equals("A room in your JSON file exits to a room that does not exist!"));
        }
    }

}
