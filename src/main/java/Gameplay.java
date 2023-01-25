import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Gameplay {
    private Board board;
    private List<Player> players;
    private List<Integer> rolls_1;
    private List<Integer> rolls_2;
    private List<Integer>[] rollsList;
    private int[] rollsIndices = {0,0};
    private final String[] PLAYER_NAMES = {"Peter","Billy","Charlotte", "Sweedal"};
    private boolean loserExists;


    public Gameplay() {
        this.board = new Board();
        this.players = new LinkedList<Player>();
        this.loserExists = false;
        this.rolls_1 = new ArrayList<Integer>();
        this.rolls_2 = new ArrayList<Integer>();
        this.rollsList = new List[]{rolls_1, rolls_2};

    }

    protected void startGame() {
        loadRolls();
        loadPlayers(PLAYER_NAMES);
        playGame();
    }

    private void playGame() {
        while(!loserExists) {
            for(Player player : players) {
                int steps = rollDice();
                movePlayer(player, steps);
                player.addNumTurns();
                if (player.isPlayerBankrupt()) {
                    System.out.println(player.getName() + " lost the game.");
                    loserExists = true;
                    break;
                }
            }
        }

        displayResults();
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

    public int rollDice() {
        int steps = rolls_1.get(rollsIndices[0]) + rolls_2.get(rollsIndices[1]);
        updateRollsIndex();
        return steps;

    }

    public void movePlayer(Player player, int steps) {
        System.out.println(player.getName() + " is moving " + steps + " steps.");
        int tempPosition = player.getPosition() + steps;
        final int boardSize = board.getBoardSize();
        for(int i = 0, j = player.getPosition();  i <= steps; i++, j++) {
            if(j >= boardSize) {
                j = 0;
                System.out.println(player.getName() + " passed GO and received $1.");
            }
            if (i == steps) {
                player.setPosition(j);
            }
        }
        checkBoardItem(player);

    }

    private void checkBoardItem(Player player) {
        BoardItem boardItem = board.getBoardItem(player.getPosition());
        System.out.println(player.getName() + " landed in " + boardItem.getName() + ".");
        String itemType = boardItem.getType();
        if(itemType.equals("property")) {
            if(boardItem.getOwner() == null){
                if(player.getMoney() >= boardItem.getPrice()) {
                    player.payAmount(boardItem.getPrice());
                    boardItem.setOwner(player);
                    player.addProperty(boardItem);
                    System.out.println(player.getName() + " bought " + boardItem.getName() + ".");
                }
            } else if (boardItem.getOwner().equals(player)) {
                return;
            } else {
                int amountToPay = boardItem.getPrice();
                if(board.isPropertySetOwnedByOneOwner(boardItem)) {
                    amountToPay *= 2;
                }
                player.payAmount(amountToPay);
                boardItem.getOwner().receiveAmount(amountToPay);
                System.out.println(player.getName() + " paid $" + amountToPay + " to " + boardItem.getOwner().getName() + ".");
            }
        }
    }

    private void loadRolls() {
        JSONArray rollsList;
        for(int i=1; i<3; i++) {
            rollsList = Utils.readJSONFile("data/rolls_" + i + ".json");

            if(rollsList != null) {
                for (int j = 0; j < rollsList.size(); j++) {
                    if (i == 1) {
                        rolls_1.add(Integer.parseInt(String.valueOf(rollsList.get(j))));
                    } else {
                        rolls_2.add(Integer.parseInt(String.valueOf(rollsList.get(j))));
                    }
                }
            }
        }
    }

    private void loadPlayers(String[] playerNames) {
        for (String name : playerNames) {
            players.add(new Player(name));
            System.out.println(name + " has joined the game.");
        }

    }

    private void updateRollsIndex() {
      for (int i = 0; i < rollsIndices.length; i++) {
          rollsIndices[i]++;
          if(rollsIndices[i] >= rollsList[i].size()) {
              rollsIndices[i] = 0;
          }
      }
    }
}
