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
                if (player.isPlayerBankrupt()) loserExists = true;
            }
        }
    }

    public int rollDice() {
        int steps = rolls_1.get(rollsIndices[0]) + rolls_2.get(rollsIndices[1]);
        updateRollsIndex();
        return steps;

    }

    public void movePlayer(Player player, int steps) {
        int  currentPosition = player.getPosition() + steps;
        player.setPosition(currentPosition % board.getBoardSize());
        checkBoardItem(player);

    }

    private void checkBoardItem(Player player) {
        BoardItem boardItem = board.getBoardItem(player.getPosition());
        String itemType = boardItem.getType();
        if(itemType == "property") {

        } else if (itemType == "go") {
            if(player.getPosition() == 0) {
                player.addMoney(1);
            }

        }
        System.out.println("Player " + player.getName() + " moved to " + boardItem.getName());
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
