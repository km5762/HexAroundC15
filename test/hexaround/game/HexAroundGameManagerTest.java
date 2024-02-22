package hexaround.game;

import hexaround.game.creature.CreatureName;
import hexaround.game.MoveResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HexAroundGameManagerTest {
    static HexAroundGameBuilder hexAroundGameBuilder = new HexAroundGameBuilder();
    static IHexAroundGameManager gameManager;

    @BeforeEach
    void setUpGameManager() throws IOException {
        gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/HexAroundGameManagerConfiguration.hgc");
    }

    @Test
    void legalResponseOnPlaceCreature() {
        MoveResponse actualResponse = gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertEquals(MoveResponses.LEGAL_MOVE, actualResponse);
    }

    @Test
    void legalResponseOnMoveCreatureOnEmptyBoard() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        MoveResponse actualResponse = gameManager.moveCreature(CreatureName.BUTTERFLY, 3, 5, 4, 5);

        assertEquals(MoveResponses.LEGAL_MOVE, actualResponse);
    }

    @Test
    void legalResponseOnMoveCreatureInConnectedManner() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 2);
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 0, 1));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 1, 0, 2));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 2, 0, 3));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 3, 1, 3));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 2));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 2, 1));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 1, 2, 0));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 0, 2, -1));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, -1, 1, -1));
    }

    @Test
    void legalResponseOnMoveCreatureCreatingOneStack() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);

        MoveResponse actualResponse = gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 0, 1, 0);

        assertEquals(MoveResponses.LEGAL_MOVE, actualResponse);
    }

    @Test
    void errorResponseOnDisconnectingMove() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 2);
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 0, 1));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 1, 0, 2));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 2, 0, 3));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 3, 1, 3));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 3, 2, 2));
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 0, 4));
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 0, 5));
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, 3, 0));
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 2, 2, -1, -1));
    }

    @Test
    void disconnectingMoveDoesNotChangeBoardState() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 1, 0));
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 0, 2, 5));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 1, 0, 2, -1));
    }

    @Test
    void errorResponseOnSplittingDisconnectingMove() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, -1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, -1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, -1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 2, -1));
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, -2, 1));
        assertEquals(MoveResponses.LEGAL_MOVE, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 0, 1));
    }

    @Test
    void creatureDoesNotExistAtPoint() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, -1, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, -1, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, -1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 1, 0);
        assertEquals(MoveResponses.CREATURE_DOES_NOT_EXIST, gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 0, 2, -1));
        assertEquals(MoveResponses.CREATURE_DOES_NOT_EXIST, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 2, -2, 1));
    }

    @Test
    void placeNonExistentCreature() {
        assertEquals(MoveResponses.CREATURE_NOT_DEFINED, gameManager.placeCreature(CreatureName.TURTLE, 0, 0));
    }

    @Test
    void creatureKamikazesPoint() {
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 0);
        gameManager.placeCreature(CreatureName.SPIDER, -1, 1);
        gameManager.moveCreature(CreatureName.SPIDER, -1, 1, 0, 0);
        assertEquals(MoveResponses.CREATURE_DOES_NOT_EXIST, gameManager.moveCreature(CreatureName.GRASSHOPPER, 0, 0, 1, 1));
    }

    @Test
    void creatureKamikazesPointDisconnectingColony() {
        gameManager.placeCreature(CreatureName.SPIDER, 0, 0);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 1);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 2);
        gameManager.placeCreature(CreatureName.GRASSHOPPER, 0, 3);
        assertEquals(MoveResponses.DISCONNECTING_MOVE, gameManager.moveCreature(CreatureName.SPIDER, 0, 0, 0, 2));
    }
}
