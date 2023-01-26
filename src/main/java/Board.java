import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Board {
    private List<BoardItem> items;

    public Board() {
        this.items = new LinkedList<BoardItem>();
        initialiseBoardItems();
    }

    /**Add board items from JSON file to board class as LinkedList variable**/
    private void initialiseBoardItems() {
        //Add GO square to board as first item
        BoardItem go = new BoardItem("GO", "N/A", "go", 0);
        items.add(go);
        JSONArray itemsList = Utils.readJSONFile("data/board.json");
        if (itemsList != null){
            itemsList.forEach( item -> parseItemObject( (JSONObject) item ));
        }

    }
    //Parse item object by getting key-value pairs in board.json file
    private void parseItemObject(JSONObject item)
    {
        //add properties if board item is not GO
        if(!Objects.equals((String) item.get("name"), "GO")) {
            String name = (String) item.get("name");
            String colour = (String) item.get("colour");
            String type = (String) item.get("type");
            String price =  String.valueOf(item.get("price"));
            Integer.parseInt(price);
            BoardItem i = new BoardItem(name, colour, type, Integer.parseInt(price));
            items.add(i);

        }
    }

    public int getBoardSize() {
        return items.size();
    }

    //get board item using index given
    public BoardItem getBoardItem(int index) {
        return items.get(index);
    }

    public List<BoardItem> getItems() {
        return items;
    }

    //Checks to see if colour property set is owned by a single owner
    public boolean isPropertySetOwnedByOneOwner(BoardItem boardItem) {
        boolean propertySetOwned = true;

        for(BoardItem item : items) {
            //if colour of board item is equal to colour of board item given
            if(item.getColour().equals(boardItem.getColour())) {
                if(item.getOwner() != null) {
                    //if board item owner is not equal to owner of board item given,
                    // property set is not owned by one owner
                    if(!item.getOwner().equals(boardItem.getOwner())) {
                        propertySetOwned = false;
                    }
                } else {
                    //if item is currently not owned
                    propertySetOwned = false;
                }
            }
        }
        return propertySetOwned;
    }
}
