=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: aboakye
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Recursion (This is implemented in the click function and is used to find the tiles next to 
  the blank tiles and revealing them until it is only number tiles left being shown.

  2. File IO - this is used to load and save a game.

  3. Inheritance and Subtyping - I used subtyping to make different types of tiles and also refer
  to a general tile in an array or other structures and was able to make sure all tiles had certain
  functions and elements.

  4. 2D Array - this was used throughout the program, specifically in creating the game board and 
  solution board, and this helped me to iterate through the boards, change their characters with 
  ease and map them how I wanted.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  BlankTile.java - Creates a tile that has no bombs around it and is on the board 
  and gives it functionality. Extends the Tile Class.
  
  Board.java - sets up the gameBoard, and elements later needed to set up a minesweeper board. It 
  contains the checking if a user won, or lost the game, and also holds the recursive function for 
  the blank tiles.
  
  BombTile.java - creates a bomb tile which once clicked, the user loses the game and gives it
  functionality. Extends the Tile Class.
  
  CoverTile.java - creates the initial tile before a user clicks on a tile and gives it 
  functionality. Extends the Tile Class.
  
  FileLineIterator.java - allows the user to read and create files. Has a hasNext function (bool) 
  and a Next function which reveals which string was just iterated over.
  
  FlagTile.java - creates the tile that allows a user to mark where a bomb might be and gives
  it functionality. Extends the Tile Class.
  
  MineSweeper.java - creates the JPanel for where the user will play the MineSweeper game. Creates
  and implements status, board functionality, and more.
  
  NumberTile.java - creates the tiles that are next to a bomb, and shows how many bombs are next to 
  it and adds its functionality. Extends the Tile Class.
  
  RunMineSweeper.java - Creates the full interface that the user will interact with. Contains
  JPanels, Messages for instructions, Buttons, and many more, and ultimately makes the program run.
  
  Tile.java - The abstract class for all the tiles, holds information like x and y position, as 
  well as abstract functions like the getTile() function or the drawTile() function.
  
- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
 
I definitely had trouble implementing the recursive function and also had trouble implementing the
creation of number tiles. This may have had to do with the overuse of for loops.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I think there could have been more separation of functionality, and I could've been more concise if 
I created more functions but it still was okay. I would definitely make more functions and I would
have also tried to find a way to condense my code and use less for loops.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
