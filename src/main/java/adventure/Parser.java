package adventure;

import java.util.ArrayList;

public class Parser implements java.io.Serializable{

   /* private member variables */
   private Command myCommand;
   private ArrayList<Item> currentRoomItems;
   private ArrayList<Item> currentInventory;
   private static final long serialVersionUID = -5999794247611845230L;

   /* default constructor */
   public Parser(){

      this.currentInventory = new ArrayList<Item>();
      this.currentRoomItems = new ArrayList<Item>();
   }

   /**
    * Overriden toString
    *
    * @return a formatted string
    */
   public String toString(){

      return "This is the parser class.";
   }

   /**
    * Sets the list of all the current room items
    * @param newList
    */
   public void setCurrentRoomItems(ArrayList<Item> newList){

      if(newList.size() != 0){

         this.currentRoomItems = new ArrayList<Item>();
         this.currentRoomItems = newList;
      }
   }

   /**
    * Sets the list of the player's current inventory
    * @param newList
    */
   public void setCurrentInventory(ArrayList<Item> newList){

      if(newList.size() != 0){

         this.currentInventory = new ArrayList<Item>();
         this.currentInventory = newList;
      }
   }

   /**
    * Parses the user command
    *
    * @param userCommand
    * @return an object of type Command
    * @throws InvalidCommandException
    */
   public Command parseUserCommand(String userCommand) throws InvalidCommandException{

      String[] splitCommand = splitUserCommand(userCommand);
      Command theCommand = new Command();
      
      if(splitCommand.length == 1){

         theCommand = createOneWordCommand(userCommand);

      }else if(splitCommand.length == 2){

         theCommand = createTwoWordCommand(splitCommand);

      }else{

         theCommand = createTwoPlusCommand(splitCommand);
      }
      return theCommand;
   }

   /**
    * Splits the user's command into an array of Strings 
    *
    * @param userCommand
    * @return a command split into an array of Strings
    */
    public String[] splitUserCommand(String userCommand){

      String[] splitCommand = userCommand.split(" ");

      return splitCommand;
      
   }

   /**
    * Creates a Command object from a verb
    *
    * @param userCommand
    * @return an object of type Command
    * @throws InvalidCommandException
    * @throws NoDir
    */
   public Command createOneWordCommand(String userCommand) throws InvalidCommandException{

      String verb = userCommand;
      Command oneCommand = new Command(verb);
      return oneCommand;
   }

   /**
    * Creates a Command object from a noun and a multiple word verb
    *
    * @param splitCommand
    * @return an object of type Command
    * @throws InvalidCommandException
    * @throws InvalidDir
    * @throws InvalidItem
    */
   public Command createTwoPlusCommand(String[] splitCommand) throws InvalidCommandException{

      String noun = "";
      String verb = splitCommand[0];
      Command twoCommand;
      constructTwoWordNoun(splitCommand, noun);
      if(isInInventory(noun) || isInRoom(noun)){
         twoCommand = new Command(verb, noun);
      }else{

         throw new InvalidCommandException("You do not have access to that item!");
      }
      return twoCommand;
   }

   private String constructTwoWordNoun(String[] splitCommand, String noun){

      for(int i = 1; i < splitCommand.length; i++){

         noun = makeNoun(noun, splitCommand, i);
      }
      return noun;
   }

   /**
    * Makes a noun string for two+ word items
    * @param noun
    * @param splitCommand
    * @param i
    * @return the correct noun string
    */
   public String makeNoun(String noun, String[] splitCommand, int i){

      if(i == splitCommand.length -1){

         noun = noun.concat(splitCommand[i]);
      }else{
         noun = noun.concat(splitCommand[i] + " ");
      }
      return noun;
   }

   /**
    * Creates a Command object from a noun and a verb
    *
    * @param splitCommand
    * @return an object of type Command
    */
   public Command createTwoWordCommand(String[] splitCommand) throws InvalidCommandException{

      String verb = splitCommand[0];
      String noun = splitCommand[1];
      Command twoCommand = null;
      if(verb.equals("go")){
         twoCommand = new Command(verb, noun);
      }else if(!verb.equals("go")){

         twoCommand = notGoInstructions(twoCommand, verb, noun);
      }
      return twoCommand;
   }

   private Command notGoInstructions(Command twoCommand, String verb, String noun) throws InvalidCommandException{

      if(isInInventory(noun) && !verb.equals("take")){

         twoCommand = new Command(verb,noun);
      
      }else if(verb.equals("take") && isInRoom(noun)){

         twoCommand = new Command(verb,noun);
      }else{

         throw new InvalidCommandException("That item is not in your inventory or room!");
      }
      return twoCommand;
   }

   /**
    * Returns a string representation of all the commands in a printable form
    *
    * @return formmated string of all available commands
    */
   public String allCommands(){

      String theList = "";
      try{
         myCommand = new Command();
         theList = myCommand.getAllCommands();
      }catch(InvalidCommandException ex){
      }
      return theList;
   }

   /**
    * Sets the myCommand instance variable
    *
    * @param newCommand
    */
   public void setMyCommand(Command newCommand){
       
      this.myCommand = newCommand;
   }

   /**
     * Follows the user commands and completes the right action
     * 
     * @param toDo
     * @param myAdventure
     * @return the output string from following any of the commands
     */
    public String followCommands(Command toDo, Adventure myAdventure) throws InvalidCommandException{

      String theOutput = "";
      theOutput = followCommandsA1(toDo, myAdventure, theOutput);
      theOutput = followCommandsA2(toDo, myAdventure, theOutput);
      theOutput = followCommandsA3(toDo, myAdventure, theOutput);
      return theOutput;
   }

   /**
    * Follows commands from the A1 assignment
    * @param toDo
    * @param myAdventure
    * @param theOutput
    * @return the output string from following any of the commands from A2
    */
   public String followCommandsA1(Command toDo, Adventure myAdventure, String theOutput) throws InvalidCommandException{

      if(toDo.getActionWord().equals("go")){

         theOutput = myAdventure.moveHero(toDo);

      }else if(toDo.getActionWord().equals("look")){

         if(toDo.hasSecondWord()){

            theOutput = myAdventure.lookAtSomething(toDo);

         }else{

            theOutput = myAdventure.lookAtSomething(toDo);
         }
      }
      return theOutput;
   }

   /**
    * Follows commands from the A2 assignment
    * @param toDo
    * @param myAdventure
    * @param theOutput
    * @return the output string from following any of the commands from A2
    */
   public String followCommandsA2(Command toDo, Adventure myAdventure, String theOutput) throws InvalidCommandException{

      if(toDo.getActionWord().equals("inventory")){

         theOutput = myAdventure.playerInventory();
      
      }else if(toDo.getActionWord().equals("take")){

         myAdventure.takeItem(toDo);
         theOutput = (toDo.getNoun() + " has been placed in your inventory!");

      }
      return theOutput;
   }

   /**
    * Follows commands from the A3 assignment
    * @param toDo
    * @param myAdventure
    * @param theOutput
    * @return the output string from following any of the commands from A3
    */
   public String followCommandsA3(Command toDo, Adventure myAdventure, String theOutput) throws InvalidCommandException{

      if(toDo.getActionWord().equals("eat")){

         theOutput = myAdventure.eatSomething(toDo);

      }else if(toDo.getActionWord().equals("wear")){

         theOutput = myAdventure.wearSomething(toDo);

      }else if(toDo.getActionWord().equals("toss")){

         theOutput = myAdventure.tossSomething(toDo);

      }else if(toDo.getActionWord().equals("read")){

         theOutput = myAdventure.readSomething(toDo);
      }
      return theOutput;
   }

   /**
    * Determines if the item is actually in the player's inventory
    * @param newCommand
    * @return true/false if item is in inventory
    * @throws InvalidCommandException
    */
   public boolean isInInventory(String newCommand){

      boolean isValid = false;
      if(this.currentInventory != null){
         for(int i = 0; i < this.currentInventory.size(); i++){

            if(this.currentInventory.get(i).getName().equals(newCommand)){

               isValid = true;
            }
         }
      }
      return isValid;
  }

  /**
    * Determines if the item is actually in the current room
    * @param newCommand
    * @return true/false if item is in the current room
    * @throws InvalidCommandException
    */
   public boolean isInRoom(String newCommand){

      boolean isValid = false;
      if(this.currentRoomItems.size() != 0){

         for(int i = 0; i < this.currentRoomItems.size(); i++){

            if(this.currentRoomItems.get(i).getName().equals(newCommand)){

               isValid = true;
            }
         }
      }
      return isValid;
   }

}
