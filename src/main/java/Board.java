import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Board {
    private List<BoardItem> items;

    public Board() {
        this.items = new LinkedList<BoardItem>();
        initialiseBoardItems();
    }

    private void initialiseBoardItems() {
        BoardItem go = new BoardItem("GO", "N/A", "go", 0);
        items.add(go);
        JSONArray itemsList = Utils.readJSONFile("data/board.json");
        if (itemsList != null){
            itemsList.forEach( item -> parseItemObject( (JSONObject) item ));
        }

    }

    private void parseItemObject(JSONObject item)
    {
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

    public BoardItem getBoardItem(int index) {
        return items.get(index);
    }

    public List<BoardItem> getItems() {
        return items;
    }

    public boolean isPropertySetOwnedByOneOwner(BoardItem boardItem) {
        boolean propertySetOwned = true;

        for(BoardItem item : items) {
            if(item.getColour().equals(boardItem.getColour())) {
                if(item.getOwner() != null) {
                    if(!item.getOwner().equals(boardItem.getOwner())) {
                        propertySetOwned = false;
                    }
                } else {
                    propertySetOwned = false;
                }
            }
        }
        return propertySetOwned;
    }
}
