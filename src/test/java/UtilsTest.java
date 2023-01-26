import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    private final int BOARD_SIZE = 9;

    @Test
    public void SuccessfullyReadBoardJSONFile() {
        JSONArray array = Utils.readJSONFile("data/board.json");
        assertEquals(BOARD_SIZE, array.size());
    }

}
