import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Gameplay {
    Board board;
    List<Player> players;
    List<Integer> rolls_1;
    List<Integer> rolls_2;

    public Gameplay() {
        board = new Board();
        players = new LinkedList<Player>();
        rolls_1 = new ArrayList<Integer>();
        rolls_2 = new ArrayList<Integer>();

    }

    protected void startGame() {
        loadRolls();
        String[] playerNames = {"Peter","Billy","Charlotte", "Sweedal"};
        loadPlayers(playerNames);
        playGame();

    }

    private void loadRolls() {
        JSONArray rollsList;
        for(int i=1; i<3; i++) {
            rollsList = Utils.readJSONFile("data/rolls_" + i + ".json");

            if(rollsList != null) {
                for(int j=0; j < rollsList.size(); j++) {
                    if(i == 1) {
                        rolls_1.add(Integer.parseInt(String.valueOf(rollsList.get(j))));
                    } else {
                        rolls_2.add(Integer.parseInt(String.valueOf(rollsList.get(j))));
                    }
                }
            }
        }


    }

    private void playGame() {
        for(Player player : players) {

        }
    }

    private void loadPlayers(String[] playerNames) {
        for (String name : playerNames) {
            players.add(new Player(name));
        }

    }
}
