public class BoardItem {
    private String name;
    private String colour;
    private String type;
    private int price;
    private Player owner;

    public BoardItem(String name, String colour, String type, int price) {
        this.name = name;
        this.colour = colour;
        this.type = type;
        this.price = price;
        this.owner = null;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }



}
