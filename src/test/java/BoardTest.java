import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private final int BOARD_SIZE = 9;
    private Board board;


    @BeforeEach
    public void initialiseEmptyBoardItemsList () {
        board = new Board();
    }

    @Test
    public void testBoardSizeAfterInitialising() {
        assertEquals(BOARD_SIZE, board.getBoardSize());
    }

    @Test
    @DisplayName("Return true when player owns whole property set ")
    public void SuccessfullyCheckIfPlayerOwnsWholePropertySet() {
        int index = 0;
        Player testPlayer = new Player("testPlayer");
        for (BoardItem item : board.getItems()) {
            if(item.getType().equals("property")) {
                if(item.getColour().equals("Brown")) {
                    index = board.getItems().indexOf(item);
                    item.setOwner(testPlayer);
                }
            }
        }

        boolean result = board.isPropertySetOwnedByOneOwner(board.getBoardItem(index));
        assertTrue(result);


    }


}
