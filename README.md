## Author Information

* Name: Sophie Michaud
* Email: sophiem@uoguelph.ca
* Student ID: 0999546

## Running from the Command Line

1. From the project folder, use mvn assembly:assembly to create the jar file.
2. To load the default adventure use: java -cp target/2430_A3-1.0-jar-with-dependencies.jar adventure.Game
3. To load a new adventure use: java -cp target/2430_A3-1.0-jar-with-dependencies.jar adventure.Game -a "json file name" (JSON file can be added to the project folder after creating the jar file)
4. To load a save game use: java -cp target/2430_A3-1.0-jar-with-dependencies.jar adventure.Game -l "save game file name"

## Running the GUI

1. From the project folder, use mvn assembly:assembly to create the jar file.
2. To load the GUI from the command line, use java -jar target/2430_A3-1.0-jar-with-dependencies.jar
3. If you want to load a new JSON file use the menu item "Load JSON File"
4. If you want to load a save game use the menu item "Load Saved Game"
5. Use the "Start Adventure" button to start the game. If you have not used the load JSON or Saved Game options it will remind you and ask you if you would like a default adventure."
6. Use the menu item "Change Name" to set your player name only once you have pressed the "Start Adventure" button.
7. Use the text field beside "Enter Command:" to enter your commands.
8. The menu item "Save Game" will prompt you to enter a save game file name.
9. Using the exit button on the JFrame will close the window.
10. Typing quit in the text field will ask the user if they are sure they want to quit.

## Instructions for Using the Program from the Command Line

1. The user will be given instructions when the game starts
2. Typing **"look"** by itself will give a longer description of the current room
3. Typing **"look"** with an item name will give a longer description of the item
4. Typing **"go"** by itself will tell the user to re-enter the command with a direction.
5. Typing **"go"** with a non-existant direction will tell the user to re-enter the command with a valid direction.
6. Typing **"go"** with a valid direction (N,S,E,W,up,down) will bring the user into the connected room in that direction.
7. Typing **"go"** with a valid direction but no connected room will tell the user that there is no room in that direction.
8. Typing **"take"** with a valid item will add that item to the player's inventory.
9. Typing **"inventory"** will show the user what is in their inventory.
10. Typing **"quit"** will end the game.

If the user enters any invalid command they will continue to be prompted for another command until they do so.

In addition, the player will be asked for their name at the beginning of the program. Once the player enters "quit", they will be asked if they want to save their progress.
If the user chooses "yes", they will then be asked to enter a file name for their saved gamed. If they choose "no" the program will end.

## Instructions for Using the Program from the GUI

1. The Menu allows the user to:
  * **Change the Player's Name** (The label "Set your Player name in the Menu!" at the top of the GUI will not change until you do so).
  * **Load a JSON file** into the Adventure
  * **Load a Save Game** into the Adventure
  * **Save an Adventure** in progress (Choosing this option will trigger a pop-up requesting a save game file name).
2. The text field allows the user to **enter any of the valid commands** in the above section, otherwise a pop-up will tell the user that they have entered an invalid command.
3. The main text area **displays the game output** given a valid command.
4. The smaller text area on the LHS of the GUI displays all of the valid commands for user reference.
5. The smaller text area on the RHS of the GUI displays the **Player's Current Inventory**

## Statement of Individual Work

By the action of submitting this work for grading, I certify that this assignment is my own work, based on my personal study.  I also certify that no parts of this assignment have previously been submitted for assessment in any other course, except where specific permission has been granted.  Finally, I certify that I have not copied in part or whole  or otherwise plagiarised the work of other persons.

