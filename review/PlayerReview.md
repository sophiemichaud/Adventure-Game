## Class Player

| Method Signature                                  | Responsibility                                    | Instance Variables Used | Other Class Methods Called | Objects Used with Method Calls | Lines of Code |
|---------------------------------------------------|---------------------------------------------------|-------------------------|----------------------------|--------------------------------|---------------|
| public Player                                     | Initializes private members                       | All                     | None                       | None                           | 5             |
| public String toString()                          | Creates a formatted String with class information | None                    | None                       | None                           | 1             |
| public String getName()                           | Gets the player's name                            | playerName              | None                       | None                           | 1             |
| public void setPlayerName(String newPlayerName)   | Sets the player's name                            | playerName              | None                       | None                           | 1             |
| public ArrayList getInventory()                   | Gets the list of the player's inventory           | playerInventory         | None                       | None                           | 1             |
| public void setPlayerInventory(ArrayList newList) | Sets the list of the player's inventory           | playerInventory         | None                       | None                           | 1             |
| public void addToInventory(Item newItem)          | Adds a new item to the player's inventory         | playerInventory         | None                       | None                           | 1             |
| public void removeFromInventory(Item newItem)     | Removes an item from the player's inventory       | playerInventory         | None                       | None                           | 1             |
| public void setCurrentRoom(Room theRoom)          | Sets the player's current room                    | currentRoom             | None                       | None                           | 1             |
| public void findCurrentRoom()                     | Finds the current room in a list of rooms         | roomList                | setCurrentRoom             | None                           | 5             |
| public String getSaveGameName()                   | Gets the player's save game name                  | saveGameName            | None                       | None                           | 1             |
| public void setSaveGameName(String newSaveGame)   | Sets the player's save game name                  | saveGameName            | None                       | None                           | 1             |
| public void setRoomList(ArrayList newList)        | Sets the room list                                | roomList                | None                       | None                           | 1             |