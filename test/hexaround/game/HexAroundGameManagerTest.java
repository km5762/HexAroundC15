package hexaround.game;

import hexaround.game.creature.CreatureName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HexAroundGameManagerTest {
    static HexAroundGameBuilder hexAroundGameBuilder = new HexAroundGameBuilder();
    static IHexAroundGameManager gameManager;

    static MoveResponse legalResponse = new MoveResponse(MoveResult.OK, "Legal move");
    static MoveResponse disconnectedResponse = new MoveResponse(MoveResult.MOVE_ERROR, "Colony is not connected, try again");

    @BeforeEach
    void setUpGameManager() throws IOException {
        gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");
    }

    @Test
    void legalResponseOnPlaceCreature() {
        MoveResponse actualResponse = gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertEquals(legalResponse, actualResponse);
    }

    @Test
    void legalResponseOnMoveCreatureOnEmptyBoard() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        MoveResponse actualResponse = gameManager.moveCreature(CreatureName.BUTTERFLY, 3, 5, 4, 5);

        assertEquals(legalResponse, actualResponse);
    }

    @Test
    void legalResponseOnMoveCreatureInConnectedManner() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 2);
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 0, 1));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 1, 0, 2));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 2, 0, 3));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 3, 1, 3));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 2));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 2));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 1));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 0));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, -1));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 1, -1));
    }

    @Test
    void legalResponseOnMoveCreatureCreatingOneStack() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);

        MoveResponse actualResponse = gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 0, 1, 0);

        assertEquals(legalResponse, actualResponse);
    }

    @Test
    void errorResponseOnDisconnectingMove() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 2);
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 0, 1));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 1, 0, 2));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 2, 0, 3));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 3, 1, 3));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 2));
        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 0, 4));
        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 0, 5));
        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 3, 0));
        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, -1, -1));
    }

    @Test
    void disconnectingMoveDoesNotChangeBoardState() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 1, 0));
        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 0, 2, 5));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 0, 2, -1));
    }

    @Test
    void errorResponseOnSplittingDisconnectingMove() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, -1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, -1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, -1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);

        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 2, -1));
        assertEquals(disconnectedResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, -2, 1));
        assertEquals(legalResponse, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 0, 1));
    }
}
