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
                int move = rolls_1.get(rollsIndices[0]) + rolls_2.get(rollsIndices[1]);
                updateRollsIndex();
                movePlayer(player);

                if (player.getNumTurns() != 0 && player.getPosition() == 0) {
                    player.addMoney(1);
                }

                player.addNumTurns();
                if (player.isPlayerBankrupt()) loserExists = true;
            }
        }
    }

    private void movePlayer(Player player) {
        

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
          System.out.println(rollsIndices[i]);
      }

    }
}
