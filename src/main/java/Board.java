import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Board {
    List<BoardItem> items;

    public Board() {
        this.items = new LinkedList<BoardItem>();
        initialiseBoardItems();
    }

    private void initialiseBoardItems() {
        BoardItem go = new BoardItem("GO", null, "go", 0);
        items.add(go);
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("data/board.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray itemsList = (JSONArray) obj;

            //Iterate over employee array
            itemsList.forEach( item -> parseItemObject( (JSONObject) item ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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
}
