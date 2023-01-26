# Implementation

* Code language used: Java
* Unit Tests: JUnit

## Classes:
* `Main.java` - Main class of application
* `Player.java` - Player object
* `Board.java` - Contains list of board items (GO and properties)
* `BoardItem.java` - Board item object with given attributes from JSON file + owner of board (either null or Player object)
* `Gameplay.java` - Game engine of application

## Own Assumptions for Implementation:
* `rolls_1.json` and `rolls_2.json` represent two dice rolls just like in an actual monopoly game.
Hence, these are added to determine number of steps for the player for each turn.
* As the rules mention that the player **MUST** buy the unowned property they land on. 
Hence, the amount of money the player currently have is not checked regardless if the player does not have sufficient money to buy the property.
* In `BoardItem.java`, the GO square is stored with its `colour` as "N/A" and `price` as 0. This is important to note if Jail or other stations
are introduced for this game in the future.

## How to Run:
* Run main method in `Main.java` class.