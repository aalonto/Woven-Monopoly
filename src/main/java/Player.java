import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int position;
    private List<String> properties;
    private int money;
    private int numTurns;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.money = 16;
        this.properties = new ArrayList<String>();
        this.numTurns = 0;
    }

    public void receiveAmount(int money) {
        this.money += money;
    }

    public void payAmount(int money) {
        this.money -= money;
    }

    public boolean isPlayerBankrupt() {
        return this.money <= 0;
    }

    public void addNumTurns () {
        this.numTurns += 1;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public int getMoney() {
        return this.money;
    }

    public int getNumTurns() {
        return this.numTurns;
    }

}