package adventure;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream; 
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * This is the Game class
 * 
 * @version 3.0
 * @author Sophie Michaud
 */
public class Game implements java.io.Serializable{

    /* private member variables */
    private Parser parser;
    private static Adventure myAdventure;
    private static final long serialVersionUID = -7431294770559489881L;
    private Scanner gameScanner;
    
    /* default constructor */
    public Game(){

        this.parser = new Parser();
    }

    /**
     * Overriden toString
     * 
     * @return a formatted string
     */
    public String toString(){

        return "This is the Game class";
    }
    public static void main(String[] args){

        Game theGame = new Game();
        theGame.setGameScanner();
        theGame.setParser();

        try{
            theGame.parseCommandLineArguments(args);
            theGame.gameLoop();
            theGame.saveGameProcess();
        }catch(InvalidJSONException ex){

            theGame.outputToUser(ex.getMessage());
        }  
    }


    /* THE FOLLOWING SUB METHODS CORRESPOND TO: generic, used by all sections */

    /**
     * Initializes the game's scanner
     */
    public void setGameScanner(){

        this.gameScanner = new Scanner(System.in);
    }

    /**
     * Gets the game's scanner
     * 
     * @return a game scanner object
     */
    public Scanner getGameScanner(){

        return this.gameScanner;
    }
    
    /**
     * Prints a string to a user
     * 
     * @param toPrint
     */
    private void outputToUser(String toPrint){

        System.out.println( "> " +toPrint + "\n");
        System.out.print("> ");
    }

    private String outputToUserGUI(String toPrint){

        return( "> " +toPrint + "\n");
    }

    /**
     * Sets the parser instance variable
     */
    public void setParser(){

        this.parser = new Parser();
    }


    /* THE FOLLOWING METHODS CORRESPOND TO: gameLoop() */


    /**
     * Passes the user input to the parser loop
     */
    public void gameLoop(){

        String userIn = "";
        while(!(userIn.equals("quit"))){

            userIn = getGameLoopInput();

            if(userIn.equals("quit")){

                String doNothing = "";

            }else{
                sendToParser(userIn);
            }
        }
    }

    /**
     * Passes the user input to the parser loop for the GUI
     * 
     * @param userIn
     * @return the output from the parser
     * @throws InvalidCommandException
     */
    public String gameLoopGUI(String userIn) throws InvalidCommandException{

        String message = "";
        if(!userIn.equals("quit")){

            message = sendToParserGUI(userIn);
        }
        return message;
    }

    /**
     * Gets the input for the game loop
     * 
     * @return the user's command
     */
    public String getGameLoopInput(){

        Scanner scnr = getGameScanner();
        String userIn = scnr.nextLine();
        return userIn;
    }

    /**
     * Sends the input to the parser loop
     * 
     * @param userIn
     */
    public void sendToParser(String userIn){

        Command nextCommand = null;
        userInputLoop(nextCommand, userIn);
    }

    /**
     * Sends the input to the parser loop
     * 
     * @param userIn
     * @return the output from the input loop in string format
     */
    public String sendToParserGUI(String userIn) throws InvalidCommandException{

        Command nextCommand = null;
        return(userInputLoopGUI(nextCommand, userIn));
    }

    /* THE FOLLOWING SUB METHODS CORRESPOND TO: parseCommandLineArguments() */

    /**
     * Parses the command line arguments
     * 
     * @param args
     */
    public void parseCommandLineArguments(String [] args) throws InvalidJSONException{

        if(args.length == 2){
            if((args[0]).equals("-a")){

                newJsonAdventureSetUp(args[1]);

            }else if((args[0]).equals("-l")){

                saveGameSetUp(args[1]);
            }
        }else if(args.length == 0){
            
            defaultAdventureSetUp();
        }
    }

    /**
     * Completes instructions for a new JSON adventure set-up
     * @param fileName
     */
    public void newJsonAdventureSetUp(String fileName) throws InvalidJSONException{

        JSONObject advObj = loadAdventureJson(fileName);
        testGivenJson((JSONObject) advObj.get("adventure"));
        myAdventure = generateAdventure(advObj);
        firstTimeSetup();
    }

    /**
     * Completes instructions for a new JSON adventure set-up for the GUI
     * @param fileName
     * @return the output from the new JSON set-up in string format
     * @throws InvalidJSONException
     */
    public String newJsonAdventureSetUpGUI(String fileName) throws InvalidJSONException{

        JSONObject advObj = loadAdventureJson(fileName);
        testGivenJson((JSONObject) advObj.get("adventure"));
        myAdventure = generateAdventure(advObj);
        return(firstTimeSetUpGUI());
    }

    /**
     * Completes instructions for a save game set-up
     * @param fileName
     */
    public void saveGameSetUp(String fileName){

        System.out.println(deSerializeAdventure(fileName));
        welcomeBackMessage();
    }

    /**
     * Completes instructions for a save game set-up for the GUI
     * @param fileName
     * @return the output from the save game set-up in string format
     */
    public String saveGameSetUpGUI(String fileName){

        return (deSerializeAdventure(fileName).concat(welcomeBackMessageGUI()));
    }

    /**
     * Completes instructions for a default adventure set-up
     */
    public void defaultAdventureSetUp(){

        InputStream inputStream = Game.class.getClassLoader().getResourceAsStream("defaultAdventure.json");
        JSONObject advObj = loadAdventureJson(inputStream);
        myAdventure = generateAdventure(advObj);
        firstTimeSetup();
    }

    /**
     * Completes the instructions for a default adventure set-up for the GUI
     * @return the first time set up string output
     */
    public String defaultAdventureSetUpGUI(){

        InputStream inputStream = Game.class.getClassLoader().getResourceAsStream("defaultAdventure.json");
        JSONObject advObj = loadAdventureJson(inputStream);
        myAdventure = generateAdventure(advObj);
        return firstTimeSetUpGUI();
    }

    /**
    * Loads the users adventure file
    * 
    * @param filename is the name of the users adventure file
    * @return JSONObject
    */
    public JSONObject loadAdventureJson(String filename){

        JSONObject adventureJSON;

        JSONParser myParser = new JSONParser();

        try (Reader reader = new FileReader(filename)){

            adventureJSON = (JSONObject) myParser.parse(reader);

        }catch (Exception e){

            System.out.println("File not found.");
            adventureJSON = null;
        }
        return adventureJSON;
    }

    /**
    * Loads the users adventure file
    * 
    * @param inputStream is input stream of the default adventure
    * @return JSONObject 
    */
    public JSONObject loadAdventureJson(InputStream inputStream){

        JSONObject adventureJSON;

        JSONParser myParser = new JSONParser();

        try(InputStreamReader reader = new InputStreamReader(inputStream)){

            adventureJSON = (JSONObject) myParser.parse(reader);
        }catch (Exception e){

            System.out.println("File not found!");
            adventureJSON = null;
        }
        return adventureJSON;
    }

     /**
     * Generates a new adventure
     * 
     * @param adventureJSON
     * @return a new adventure
     */
    public Adventure generateAdventure(JSONObject adventureJSON){

        Adventure newAdventure = new Adventure((JSONObject) adventureJSON.get("adventure"));

        return newAdventure;
    }

    /**
     * Follows instructions for when a brand new game is created
     */
    public void firstTimeSetup(){

        gameInstructions();
        setPlayerName();
    }

    /**
     * 
     * @return the string format of instructions for first time set up
     */
    public String firstTimeSetUpGUI(){

        return(gameInstructionsGUI());
    }

    /**
     * Prints a welcome back message with the player's name
     */
    public void welcomeBackMessage(){

        String playerName = myAdventure.getPlayerName();
        System.out.println("Welcome Back to your Adventure " +playerName+ "!!!");
        System.out.print("\n> ");
    }

    /**
     * Gets the output string for the welcome message
     * @return the welcome message in string format
     */
    public String welcomeBackMessageGUI(){

        String playerName = myAdventure.getPlayerName();
        return("Welcome Back to your Adventure " +playerName+ "!!!");
    }

    /**
     * Sets the Adventure Object's player name
     */
    public void setPlayerName(){

        String playerName = getPlayerName();
        myAdventure.setPlayerName(playerName);
        System.out.print("\n> ");
    }

    /**
     * Sets the players name
     * @param newName
     */
    public void setPlayerNameGUI(String newName){

        myAdventure.setPlayerName(newName);
    }

    /**
     * Gets the player's current inventory list from the adventure
     * @return the player's inventory
     */
    public String getInventoryList(){

        return(myAdventure.playerInventory());
    }

    /**
     * Gets the player's name that they will use in the Adventure
     * 
     * @return the player's name
     */
    public String getPlayerName(){

        System.out.print("> Please enter your player name: ");
        Scanner scnr = getGameScanner();
        String playerName = scnr.nextLine();
        return playerName;
    }

    /**
     * Gets the player's name for the adventure
     * @return the player's name
     */
    public String getPlayerNameGUI(){

        return (myAdventure.getPlayerName());
    }

    /**
     * Loops through and scans the user input, catches and prints out error messages for any exceptions
     * 
     * @param userIn
     * @param nextCommand
     */
    public void userInputLoop(Command nextCommand, String userIn){

        try{

            nextCommand = parse(userIn);
            String returnString = parser.followCommands(nextCommand, myAdventure);
            outputToUser(returnString);

        }catch(InvalidCommandException ex){
                
            outputToUser(ex.getMessage());
        }
    }

    /**
     * Loops through and scans the user input, catches and prints out error messages for any exceptions
     * 
     * @param userIn
     * @param nextCommand
     * @return the output string that corresponds to the entered command
     */
    public String userInputLoopGUI(Command nextCommand, String userIn) throws InvalidCommandException{

        nextCommand = parse(userIn);
        String returnString = parser.followCommands(nextCommand, myAdventure);
        return (outputToUserGUI(returnString));
    }

    /**
     * DeSerializes the adventure
     * 
     * @param fileName
     * @return a message
     */
    public String deSerializeAdventure(String fileName){  

        Adventure gotItBack = null;
        String out = "";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));){ 

            out = valFile(gotItBack, out, in);

        }catch(IOException ex){

            out = ("Your file could not be opened");
            
        }catch(ClassNotFoundException ex){

            out = ("ClassNotFoundException is caught " + ex); 
        }
        return out;
    }

    /**
     * Goes through instructions to open a valid save game
     * @param gotIt
     * @param out
     * @param in
     * @return the message from loading a valid file
     */
    public String valFile(Adventure gotIt, String out, ObjectInputStream in) throws IOException,ClassNotFoundException{

        gotIt = (Adventure)in.readObject();
        myAdventure = gotIt;
        out = "Your Adventure has been loaded!\n";
        return out;
    }

    /**
     * Provides the player with all the game instructions
     */
    public void gameInstructions(){

        printStartBanner();
        printA1Stories();
        printA2Stories();
        printA3Stories();
    }

    /**
     * Provides the player with all the game instructions
     * @return the game instructions in string format
     */
    public String gameInstructionsGUI(){

        String theMessage = printStartBannerGUI();
        theMessage = theMessage.concat(printA1StoriesGUI());
        theMessage = theMessage.concat(printA2StoriesGUI());
        theMessage = theMessage.concat(printA3StoriesGUI());
        return theMessage;
    }

    /**
     * Prints a welcome banner
     */
    public void printStartBanner(){

        System.out.println("Welcome to your Adventure!!!");
        System.out.printf("\n");
        System.out.println("Game instructions: ");
        System.out.print("**********************\n");
    }

    /**
     * Gets the start banner string for the GUI
     * @return the start banner in string format
     */
    public String printStartBannerGUI(){

        String output = ("Welcome to your Adventure!!!");
        output = output.concat("\nGame instructions: \n");
        output = output.concat("**********************\n");
        return output;
    }

    /**
     * Prints the instructions for all A1 user stories
     */
    public void printA1Stories(){

        System.out.print("1. Use the \"go\" command with a direction \"N, S, E, W, up, down\" ");
        System.out.println("to move from room to room.");
        System.out.println("2. Use the \"look\" command to see a longer description of a room.");
        System.out.print("3. Use the \"look\" command followed");
        System.out.println(" by the item name to get a description of it.");
    }

    /**
     * Gets the instructions for all A1 user stories for the GUI
     * @return the instructions for A1 user stories in string format
     */
    public String printA1StoriesGUI(){

        String output = ("1. Use the \"go\" command with a direction \"N, S, E, W, up, down\" ");
        output = output.concat("to move from room to room.\n");
        output = output.concat("2. Use the \"look\" command to see a longer description of a room.\n");
        output = output.concat("3. Use the \"look\" command followed");
        output = output.concat(" by the item name to get a description of it.\n");
        return output;
    }

    /**
     * Prints the instructions for all A2 user stories
     */
    public void printA2Stories(){

        System.out.print("3. Use the \"take\" command followed");
        System.out.println(" by the item name to add the item to your inventory.");
        System.out.println("2. Use the \"inventory\" command to see the items in your inventory.");
    }

    /**
     * Gets the instructions for all A2 user stories for the GUI
     * @return the instructions for A2 user stories in string format
     */
    public String printA2StoriesGUI(){

        String output = ("3. Use the \"take\" command followed");
        output = output.concat(" by the item name to add the item to your inventory.\n");
        output = output.concat("2. Use the \"inventory\" command to see the items in your inventory.\n");
        return output;
    }

    /**
     * Prints the instructions for all A3 user stories
     */
    public void printA3Stories(){

        System.out.println("5. Use the \"eat\" command followed by the name of an item to eat it.");
        System.out.println("5. Use the \"wear\" command followed by the name of an item to wear it.");
        System.out.println("5. Use the \"toss\" command followed by the name of an item to toss it");
        System.out.println("5. Use the \"read\" command followed by the name of an item to read it.");
        System.out.println("4. Use the \"quit\" command to end the game.");
        System.out.printf("\n");
    }

    /**
     * Gets the instructions for all A3 user stories for the GUI
     * @return the instructions for A3 user stories in string format
     */
    public String printA3StoriesGUI(){

        String output = ("5. Use the \"eat\" command followed by the name of an item to eat it.");
        output = output.concat("6. Use the \"wear\" command followed by the name of an item to wear it.\n");
        output = output.concat("7. Use the \"toss\" command followed by the name of an item to toss it\n");
        output = output.concat("8. Use the \"read\" command followed by the name of an item to read it.\n");
        output = output.concat("9. Use the \"quit\" command to end the game.\n");
        output = output.concat("\n");
        return output;
    }

    /**
     * Getter for the parser
     * 
     * @param userInput
     * @return a validated command
     */
    public Command parse(String userInput) throws InvalidCommandException{

        sendListsToParser();
        return parser.parseUserCommand(userInput);
    }

    /**
     * Sends the items in the current room and the inventory to parser
     */
    public void sendListsToParser(){

        if(myAdventure.getItems().size() != 0){

            this.parser.setCurrentRoomItems(myAdventure.getItems());
        }
        if(myAdventure.getInventory().size() != 0){

            this.parser.setCurrentInventory(myAdventure.getInventory());
        }
    }

    /* THE FOLLOWING SUB METHODS CORRESPOND TO: saveGameProcess() */

    /**
     * Executes the save game instructions
     * 
     */
    public void saveGameProcess(){

        String userAnswer = getUserAnswer();
        if(userAnswer.equals("yes")){

            answerIsYesProcess();

        }else{

            System.out.println("\n> Thanks for playing!");
        }
    }

    /**
     * Gets the user's answer to wether or not they would like to save the game
     * @return the user's answer
     */
    public String getUserAnswer(){

        System.out.print("\n> Would you like to save your game progress? (yes/no): ");
        Scanner userIn = getGameScanner();
        String userAnswer = userIn.nextLine();
        return userAnswer;
    }

    /**
     * Completes a series of instructions if the user would like to save a game
     */
    public void answerIsYesProcess(){

        String userFile = getSaveFileName();
        try{ 
            
            createTheObject(userFile);
            
        }catch(IOException ex){
            System.out.println("Error creating the file.");
        }
        System.out.println("\n> Your progress has been saved. Thanks for playing!");
    }

    /**
     * Goes through the process of creating a save game file
     * @param fileName
     */
    public void saveGameProcessGUI(String fileName){

        try{

            createTheObject(fileName);
            
        }catch(IOException ex){

            System.out.println("Error creating the file.");
        }
    }

    /**
     * Creates the object to be serialized
     * 
     * @param userFile
     * @throws IOException
     */
    public void createTheObject(String userFile) throws IOException{

        FileOutputStream outPutStream = new FileOutputStream(userFile); 
        ObjectOutputStream outPutDest = new ObjectOutputStream(outPutStream); 
        outPutDest.writeObject(myAdventure);
        outPutDest.close(); 
        outPutStream.close(); 
    }

    /**
     * Gets the file name for the saved game from the user
     * 
     * @return the file name
     */
    public String getSaveFileName(){

        System.out.print("\n> Enter the name for your save game file: ");
        Scanner userIn = getGameScanner();
        String userFile = userIn.nextLine();
        myAdventure.setSaveGameName(userFile);
        return userFile;
    }

    /**
     * Goes through the process of testing a new JSON file
     * @param adventureJSON
     * @throws InvalidJSONException
     */
    public void testGivenJson(JSONObject adventureJSON) throws InvalidJSONException{

        Adventure testAdventure = new Adventure(adventureJSON);
        validateExits(testAdventure.listAllRooms());
        validateItems(testAdventure.listAllRooms(), createItemIDList(testAdventure.listAllItems()));
        validateIDS(testAdventure.listAllRooms(), createRoomIDList(testAdventure.listAllRooms()));
        validateDirections(testAdventure.listAllRooms());
    }

    private ArrayList<Long> createItemIDList(ArrayList<Item> itemList){

        ArrayList<Long> itemIDS = new ArrayList<Long>();
        for(Item nextItem: itemList){

            itemIDS.add(nextItem.getItemID());
        }
        return itemIDS;
    }

    private ArrayList<Long> createRoomIDList(ArrayList<Room> roomList){

        ArrayList<Long> roomIDS = new ArrayList<Long>();
        for(Room nextRoom: roomList){

            roomIDS.add(nextRoom.getRoomID());
        }
        return roomIDS;
    }

    private void validateExits(ArrayList<Room> theList) throws InvalidJSONException{

        for(int i = 0; i < theList.size(); i++){

            if(theList.get(i).getExitsMap().size() == 0){

                String message = ("A room in your JSON has no exits. Please fix the error, and try loading again!");
                throw new InvalidJSONException(message);
            }
        }
    }

    private void validateItems(ArrayList<Room> roomList, ArrayList<Long> itemList) throws InvalidJSONException{
        
        for(Room nextRoom: roomList){

            ArrayList<Long> nextRoomItemList = nextRoom.getItemListIDS();

            for(Long nextItemID: nextRoomItemList){

                if(!itemList.contains(nextItemID)){

                    String message = "One of the rooms in your JSON has an item that does not exist in the game!";
                    throw new InvalidJSONException(message);
                }
            }
        } 
    }

    private void validateIDS(ArrayList<Room> roomList, ArrayList<Long> idList) throws InvalidJSONException{

        for(Room nextRoom: roomList){

            ArrayList<Long> exitIDS = nextRoom.getExitIDS();

            for(Long nextRoomID: exitIDS){

                if(idList.contains(nextRoomID)){

                    boolean isValid = true;
                
                }else{

                    throw new InvalidJSONException("A room in your JSON file exits to a room that does not exist!");
                }
            }
        }
    }

    private void validateDirections(ArrayList<Room> roomList) throws InvalidJSONException{

        for(Room nextRoom: roomList){

            ArrayList<String> exitDirections = nextRoom.getExitDirections();

            for(String exitDirection: exitDirections){

                checkConnections(nextRoom, roomList, exitDirection);
            }
        }
    }

    private void checkConnections(Room nextRoom, ArrayList<Room> roomList, String exitDir) throws InvalidJSONException{

        Room connectedRoom = nextRoom.getConnectedRoom(exitDir);
        String expectedDirection = findCorrespondingDirection(exitDir);
        if(roomList.contains(connectedRoom)){

            if(connectedRoom.getConnectedRoom(expectedDirection) == nextRoom){

                boolean isValid = true;
            }else{

                String message = "A room in your JSON file has an incorrect exit direction. Try again!";
                throw new InvalidJSONException(message);
            }
        }
    }

    /**
     * Finds what the correct corresponding direction should be to a given direction
     * @param direction
     * @return the correct corresponding direction
     */
    public String findCorrespondingDirection(String direction){

        String correspondingDir = "";
        correspondingDir = northSouth(direction, correspondingDir);
        correspondingDir = eastWest(direction, correspondingDir);
        correspondingDir = upDown(direction, correspondingDir);
        return correspondingDir;
    }

    /**
     * Returns the corresponding direction for north and south
     * @param direction
     * @param correspondingDir
     * @return corresponding direction for up and down
     */
    public String northSouth(String direction, String correspondingDir){

        if(direction.equals("N")){
            correspondingDir = "S";
        }else if(direction.equals("S")){
            correspondingDir = "N";
        }
        return correspondingDir;
    }

    /**
     * Returns the corresponding direction for east and west
     * @param direction
     * @param correspondingDir
     * @return corresponding direction for east and west
     */
    public String eastWest(String direction, String correspondingDir){

        if(direction.equals("E")){
            correspondingDir = "W";
        }else if(direction.equals("W")){
            correspondingDir = "E";
        }
        return correspondingDir;
    }

    /**
     * Returns the corresponding direction for up and down
     * @param direction
     * @param correspondingDir
     * @return corresponding direction for up and down
     */
    public String upDown(String direction, String correspondingDir){

        if(direction.equals("up")){
            correspondingDir = "down";
        }else if(direction.equals("down")){
            correspondingDir = "up";
        }
        return correspondingDir;
    }
}

