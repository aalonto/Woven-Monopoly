import org.json.simple.JSONArray;
import utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Gameplay {
    private Board board;
    private List<Player> players;
    //List of rolls from rolls_1.json
    private List<Integer> rolls_1;
    //List of rolls from rolls_2.json
    private List<Integer> rolls_2;
    //contains the two dice rolls ArrayLists
    private List<Integer>[] rollsList;
    //Indices of current roll used in both rolls
    private int[] rollsIndices = {0,0};
    private final String[] PLAYER_NAMES = {"Peter","Billy","Charlotte", "Sweedal"};
    private boolean loserExists;
    private final int DELAY_SECONDS = 1000;


    public Gameplay() {
        this.board = new Board();
        this.players = new LinkedList<Player>();
        this.loserExists = false;
        this.rolls_1 = new ArrayList<Integer>();
        this.rolls_2 = new ArrayList<Integer>();
        this.rollsList = new List[]{rolls_1, rolls_2};

    }

    public void startGame() {
        displayIntro();
        loadRolls();
        loadPlayers();
        playGame();
        displayResults();
    }

    private void playGame() {
        //while no one is bankrupt yet, continue player turns
        while(!loserExists) {
            for(Player player : players) {
                int steps = rollDice();
                movePlayer(player, steps);
                if (player.isPlayerBankrupt()) {
                    System.out.println(player.getName() + " lost the game.");
                    addDelay();
                    loserExists = true;
                    break;
                }
            }
        }
    }

    public int rollDice() {
        //add first and second dice rolls based on dice rolls array
        int steps = rolls_1.get(rollsIndices[0]) + rolls_2.get(rollsIndices[1]);
        updateRollsIndex();
        return steps;
    }

    private void updateRollsIndex() {
        //for both rolls array, add one to each
        for (int i = 0; i < rollsIndices.length; i++) {
            rollsIndices[i]++;
            //go back to first index of dice rolls if last index is reached
            if(rollsIndices[i] >= rollsList[i].size()) {
                rollsIndices[i] = 0;
            }
        }
    }

    public void movePlayer(Player player, int steps) {
        System.out.println(player.getName() + " is moving " + steps + " steps.");
        addDelay();
        final int boardSize = board.getBoardSize();
        for(int i = 0, j = player.getPosition();  i <= steps; i++, j++) {
            //if index is greater or equal to board size, return to GO (first board item)
            if(j >= boardSize) {
                j = 0;
                //player receives $1 if GO is passed
                player.receiveAmount(1);
                System.out.println(player.getName() + " passed GO and received $1.");
            addDelay();
            }
            //once loop reaches last step, set player's position to index of board item
            if (i == steps) {
                player.setPosition(j);
            }
        }
        checkBoardItem(player);
    }

    private void checkBoardItem(Player player) {
        //get board item player is currently in
        BoardItem boardItem = board.getBoardItem(player.getPosition());
        System.out.println(player.getName() + " landed in " + boardItem.getName() + ".");
        addDelay();
        String itemType = boardItem.getType();
        if(itemType.equals("property")) {
            //if board item is null, player must buy property
            if(boardItem.getOwner() == null){
                    player.payAmount(boardItem.getPrice());
                    boardItem.setOwner(player);
                    //add board item to player's list of owned properties
                    player.addProperty(boardItem);
                    System.out.println(player.getName() + " bought " + boardItem.getName() + ".");
                    addDelay();
            } else if (boardItem.getOwner().equals(player)) {
                //do nothing if player landed on own property
                return;
            } else {
                //if board is not null and not owned by player, property is owned by another player
                int amountToPay = boardItem.getPrice();
                //rent doubled if property set owned by same player
                if(board.isPropertySetOwnedByOneOwner(boardItem)) {
                    amountToPay *= 2;
                }
                //player pays amount to property owner
                player.payAmount(amountToPay);
                boardItem.getOwner().receiveAmount(amountToPay);
                System.out.println(player.getName() + " paid $" + amountToPay + " to " + boardItem.getOwner().getName() + ".");
                addDelay();
            }
        }
    }

    private void loadRolls() {
        JSONArray rollsList;
        //for loop for 2 dice rolls array
        for(int i=1; i<3; i++) {
            rollsList = Utils.readJSONFile("data/rolls_" + i + ".json");

            if(rollsList != null) {
                for (int j = 0; j < rollsList.size(); j++) {
                    if (i == 1) {
                        //convert roll item to integer and add to list of rolls
                        rolls_1.add(Integer.parseInt(String.valueOf(rollsList.get(j))));
                    } else {
                        rolls_2.add(Integer.parseInt(String.valueOf(rollsList.get(j))));
                    }
                }
            }
        }
    }

    public void loadPlayers() {
        for (String name : PLAYER_NAMES) {
            //create player
            players.add(new Player(name));
            System.out.println(name + " has joined the game.");
            addDelay();
        }
    }

    private void displayResults() {
        Player winner = players.get(0);

        System.out.println("________________________________________________________________");
        System.out.println("****************************Game Results************************");
        System.out.println("________________________________________________________________");

        for (Player player : players) {
            if(player.getMoney() > winner.getMoney()) {
                winner = player;
            }

            System.out.printf("%-16s%-20s\n", "Player:", player.getName() );
            System.out.printf("%-16s%-20s\n", "End Position:", board.getBoardItem(player.getPosition()).getName());
            System.out.printf("%-16s%-20s\n", "Cash Amount:", "$" + player.getMoney());
            System.out.printf("%-16s%-20s\n", "Total Properties Owned: ", player.getProperties().size());

            for (BoardItem item : player.getProperties()) {
                System.out.printf("%16s%10s\n", "Property: ",  item.getName());
                System.out.printf("%16s%-20s\n", "Colour: ", item.getColour());
            }
            System.out.println("_________________________________");

        }
        System.out.printf("\n%-16s%-20s\n", "WINNER: ", winner.getName());
    }

    private void displayIntro() {
        System.out.println("________________________________________________________________");
        System.out.println("********************WELCOME TO WOVEN MONOPOLY*******************");
        System.out.println("________________________________________________________________");
        addDelay();
    }

    private void addDelay() {
        try{
            Thread.sleep(DELAY_SECONDS);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
