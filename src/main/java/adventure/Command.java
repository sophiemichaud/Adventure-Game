package adventure;

import java.util.ArrayList;

public class Command implements java.io.Serializable{

    private String action;
    private String noun;
    public static final String[] COMMANDS = {"go", "look", "take", "inventory", "quit", "eat", "wear", "toss", "read"};
    public static final String[] DIRECTIONS = {"N", "S", "E", "W", "up", "down"};
    private static final long serialVersionUID = 320758431752926915L;

    /**
     * Create a command object with default values.  
     * both instance variables are set to null
     * 
     */
    public Command() throws InvalidCommandException{
        
        this.action = null;
        this.noun = null;
    }

    /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command. 
     * 
     */
    public Command(String command) throws InvalidCommandException{

        if(isValidCommand(command)){

            validCommandDecision(command);

        }else{

            throw new InvalidCommandException("I'm sorry I don't know what you mean. Try another command!");
        }
    }

    /**
     * Determines what to do if given a valid command
     * 
     * @param command
     * @throws InvalidCommandException
     */
    public void validCommandDecision(String command) throws InvalidCommandException{

        if(command.equals("go")){

            throw new InvalidCommandException("Go where?");

        }else if(command.equals("take")){

            throw new InvalidCommandException("Take what?");

        }else if(command.equals("eat") || command.equals("wear") || command.equals("read") || command.equals("toss")){

            commandsThatRequireItems(command);
        
        }else{

            this.action = command;
            this.noun = null;
        }
    }

    /**
     * Throws expections for one word commands that require items
     * 
     * @param command
     * @throws InvalidCommandException
     */
    public void commandsThatRequireItems(String command) throws InvalidCommandException{

        if(command.equals("eat")){
            
            throw new InvalidCommandException("Eat what?");

        }else if(command.equals("wear")){

            throw new InvalidCommandException("Wear what?");

        }else if(command.equals("toss")){

            throw new InvalidCommandException("Toss what?");
        
        }else if(command.equals("read")){

            throw new InvalidCommandException("Read what?");

        }
    }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command. 
     * @param what The second word of the command.
     */
    public Command(String command, String what) throws InvalidCommandException{
        
        boolean isValid = isValidCommand(command);
        if(isValid){
            
            tryInstructions(isValid, command, what);

        }else{

            throw new InvalidCommandException("I'm sorry I don't know what you mean. Please try again!");
        }
    }

    /**
     * Finds the instructions a specific valid command
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void tryInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        goInstructions(isValid, command, what);
        takeInstructions(isValid, command, what);
        lookInstructions(isValid, command, what);
        eatInstructions(isValid, command, what);
        tossInstructions(isValid, command, what);
        readInstructions(isValid, command, what);
        wearInstructions(isValid, command, what);
    }

    /**
     * Overriden toString
     * 
     * @return a formatted string with the command's action and noun
     */
    public String toString(){

        return "Command Action: " +action +"\nCommand noun: " +noun;
    }

    /**
     * Computes set of instructions if user enters "go"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void goInstructions(boolean isValid, String command, String what) throws InvalidCommandException{ 

        if(isValid && command.equals("go")){

            this.action = command;

            if(isValidDirection(what)){

                this.noun = what;

            }else{

                throw new InvalidCommandException("The direction you have entered does not exist. Please try again!");
            }
        }
    }

    /**
     * Computes set of instructions if user enters "take"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void takeInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        if(isValid && command.equals("take")){

            this.action = command;
            this.noun = what;
        }
    }

    /**
     * Computes set of instructions if user enters "look"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void lookInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        if(isValid && command.equals("look")){

            this.action = command;
            this.noun = what;
        }
    }

    /**
     * Computes set of instructions if user enters "eat"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void eatInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        if(isValid && command.equals("eat")){

            this.action = command;
            this.noun = what;

        }
    }

    /**
     * Computes set of instructions if user enters "wear"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void wearInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        if(isValid && command.equals("wear")){

            this.action = command;
            this.noun = what;
        }  
    }

    /**
     * Computes set of instructions if user enters "toss"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void tossInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        if(isValid && command.equals("toss")){

            this.action = command;
            this.noun = what;

        }
    }

    /**
     * Computes set of instructions if user enters "readr"
     * 
     * @param isValid
     * @param command
     * @param what
     */
    public void readInstructions(boolean isValid, String command, String what) throws InvalidCommandException{

        if(isValid && command.equals("read")){

            this.action = command;
            this.noun = what;

        }
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {

        return this.action;
    }

    /**
     * Sets the action word
     * 
     * @param newAction
     */
    public void setAction(String newAction){

        this.action = newAction;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {

        return this.noun;
    }

    /**
     * Sets the noun
     * 
     * @param newNoun
     */
    public void setNoun(String newNoun){

        this.noun = newNoun;
    }

    /**
     * Gets the list of all valid commands
     * 
     * @return the list of valid commands
     */
    public String getAllCommands(){

        String theCommands = "Hello";
        for(int i = 0; i < COMMANDS.length; i++){

            theCommands = theCommands.concat(COMMANDS[i] + " ");
        }
        return theCommands;
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {

        boolean isTrue = false;
        if(this.noun != null){

            isTrue = true;
        }else{

            isTrue = false;
        }

        return isTrue;
    }

    /**
     * Validates the given command
     * 
     * @param newCommand
     * @return true if commands is in COMMANDS, false otherwise
     */
    public static boolean isValidCommand(String newCommand){

        boolean isValid = false;
        for(int i = 0; i < COMMANDS.length; i++){

            if(COMMANDS[i].equals(newCommand)){
                isValid = true;                                

            }
        }
        return isValid;
    }

    public static boolean isValidDirection(String newDirection){

        boolean isValid = false;

        for(int i = 0; i < DIRECTIONS.length; i++){

            if(DIRECTIONS[i].equals(newDirection)){
                isValid = true;
            }
        }
        return isValid;
    }

    public static boolean isValidItem(String itemName){

        return true;
    }

}
