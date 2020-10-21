package adventure;

public class InvalidCommandException extends Exception{

    private String exceptionMessage;
 
    /**
     * Constructor that creates a new exception based on a new message
     * 
     * @param theMessage
     */
    public InvalidCommandException(String theMessage){

        setMessage(theMessage);
    }

    /**
     * Sets the exception message
     * @param theMessage
     */
    public void setMessage(String theMessage){

        this.exceptionMessage = theMessage;
    }

    /**
     * Gets the exceptions message
     * 
     * @return the exception message
     */
    public String getMessage(){

        return this.exceptionMessage;
    }

    /**
     * Returns a formatted string
     */
    @Override
    public String toString(){

        return("Error: " +this.exceptionMessage);
    }
}
