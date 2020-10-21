package adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import jdk.jfr.Timestamp;
import org.junit.Before;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class RoomTest{

    private Room testRoom;
    private Room testRoom2;

    @Before
    public void setup(){

        testRoom = new Room();
        testRoom2 = new Room();

        testRoom.testSetExit("S", testRoom2);
    }

    @Test
    public void testGetConnectedRoomWithValidDirection(){

        System.out.println("Testing getConnectedRoom with a valid direction");
        String direction = "S";
        testRoom.getConnectedRoom(direction);
        assertTrue(testRoom.getMyException().equals("No Exception"));
        
    }

    @Test
    public void testGetConnectedRoomWithInvalidDirection(){

        System.out.println("Testing getConnectedRoom with an invalid direction");
        String direction = "wrong";
        testRoom.getConnectedRoom(direction);
        assertTrue(testRoom.getMyException().equals("Invalid Connection"));
    }

    @Test
    public void testGetConnectedRoomWithNoDirection(){

        System.out.println("Testing getConnectedRoom with no direction");
        String direction = null;
        testRoom.getConnectedRoom(direction);
        assertTrue(testRoom.getMyException().equals("Invalid Connection"));
    }

    @Test
    public void testGetConnectedRoomWithNoConnectedRoom(){

        System.out.println("Testing getConnectedRoom with no connected room");
        String direction = "N";
        testRoom.getConnectedRoom(direction);
        assertTrue(testRoom.getMyException().equals("Invalid Connection"));
    }
}