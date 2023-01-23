import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Gameplay {
    Board board;
    List<Player> players;

    public Gameplay() {
        board = new Board();
        players = new LinkedList<Player>();
    }

    private void startGame() {
        String[] playerNames = {"Peter","Billy","Charlotte", "Sweedal"};
        loadPlayers(playerNames);

    }

    public void loadPlayers(String[] playerNames) {
        for (String name : playerNames) {
            players.add(new Player(name));
        }

    }
}
