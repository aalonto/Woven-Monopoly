public class Player {
    private String name;
    private String position;
    private String[] properties;
    private int money;

    public Player(String name) {
        this.name = name;
        this.position = "GO";
        this.money = 16;
        this.properties = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }




}