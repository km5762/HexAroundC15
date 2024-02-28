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
    static IHexAroundGameManager sparseGameManager;
    MoveResponse response;
    CreatureName walkingCreatureName = CreatureName.BUTTERFLY;
    CreatureName walkingIntrudingCreatureName = CreatureName.CRAB;
    CreatureName walkingEffectCreatureName = CreatureName.DOVE;
    CreatureName runningCreatureName = CreatureName.DUCK;
    CreatureName runningIntrudingCreatureName = CreatureName.GRASSHOPPER;
    CreatureName runningEffectCreatureName = CreatureName.HORSE;
    CreatureName flyingCreatureName = CreatureName.HUMMINGBIRD;
    CreatureName flyingIntrudingCreatureName = CreatureName.RABBIT;
    CreatureName flyingEffectCreatureName = CreatureName.SPIDER;
    CreatureName jumpingCreatureName = CreatureName.TURTLE;
    CreatureName jumpingIntrudingCreatureName = CreatureName.CRAB;
    CreatureName jumpingEffectCreatureName = CreatureName.DOVE;
    CreatureName kamikazeCreatureName = CreatureName.GRASSHOPPER;

    @BeforeEach
    void setUpGameManager() throws IOException {
        gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/CreatureTestConfiguration1.hgc");
        sparseGameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/CreatureTestConfiguration2.hgc");
    }

    @Test
    void placeNonExistentCreature() {
        response = sparseGameManager.placeCreature(CreatureName.TURTLE, 0, 0);
        assertEquals(MoveResponses.CREATURE_NOT_DEFINED, response);
    }

    @Test
    void placeExistingCreature() {
        response = sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        assertEquals(MoveResponses.LEGAL_MOVE, response);
    }

    @Test
    void openingPlayerDoesntHaveCreature() {
        response = sparseGameManager.placeCreature(CreatureName.DUCK, 0, 0);
        assertEquals(MoveResponses.PLACEMENT_MISSING_CREATURE, response);
    }

    @Test
    void openingNotConnected() {
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        response = sparseGameManager.placeCreature(CreatureName.CRAB, 0, 2);
        assertEquals(MoveResponses.PLACEMENT_NOT_CONNECTED, response);
    }

    @Test
    void openingConnected() {
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        response = sparseGameManager.placeCreature(CreatureName.CRAB, 0, 1);
        assertEquals(MoveResponses.LEGAL_MOVE, response);
    }

    @Test
    void openingOnTopOfCreature() {
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        response = sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        assertEquals(MoveResponses.PLACEMENT_NOT_EMPTY, response);
    }

    @Test
    void playerDepletesCreature() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        response = gameManager.placeCreature(CreatureName.BUTTERFLY, 0, -1);
        assertEquals(MoveResponses.PLACEMENT_MISSING_CREATURE, response);
    }

    @Test
    void placingPlayerMustPlaceButterfly() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1); // 1
        gameManager.placeCreature(CreatureName.CRAB, 0, -1);
        gameManager.placeCreature(CreatureName.CRAB, 0, 2); // 2
        gameManager.placeCreature(CreatureName.CRAB, 0, -2);
        gameManager.placeCreature(CreatureName.CRAB, 0, 3); // 3
        gameManager.placeCreature(CreatureName.CRAB, 0, -3);
        gameManager.placeCreature(CreatureName.CRAB, 0, 4); // 4
        response = gameManager.placeCreature(CreatureName.CRAB, 0, -4);
        assertEquals(MoveResponses.MUST_PLACE_BUTTERFLY, response);
    }

    @Test
    void placementDisconnected() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        response = gameManager.placeCreature(CreatureName.CRAB, 0, 3);
        assertEquals(MoveResponses.PLACEMENT_NOT_NEXT_TO_ALLY, response);
    }

    @Test
    void placementNextToEnemy() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        response = gameManager.placeCreature(CreatureName.CRAB, 1, 0);
        assertEquals(MoveResponses.PLACEMENT_NEXT_TO_ENEMY, response);
    }

    @Test
    void movedCreatureNotAtPoint() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        response = gameManager.moveCreature(CreatureName.TURTLE, 0, 0, 0, 2);
        assertEquals(MoveResponses.CREATURE_DOES_NOT_EXIST, response);
    }

    @Test
    void mustPlaceCreatureInOpening() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        response = gameManager.moveCreature(CreatureName.CRAB, 0, 0, 0, 1);
        assertEquals(MoveResponses.MUST_PLACE_CREATURE_IN_OPENING, response);
    }

    @Test
    void kamikazeCreature() {
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0); // blue
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 1); // red
        sparseGameManager.placeCreature(kamikazeCreatureName, 0, -1); // blue
        sparseGameManager.placeCreature(CreatureName.HORSE, 1, 1); // red
        sparseGameManager.placeCreature(CreatureName.CRAB, -1, 0); // blue
        response = sparseGameManager.placeCreature(CreatureName.HORSE, 2, 0); // red
        assertEquals(MoveResponses.PLACEMENT_MISSING_CREATURE, response);
        response = sparseGameManager.placeCreature(CreatureName.CRAB, -1, 2); // red
        sparseGameManager.moveCreature(kamikazeCreatureName, 0, -1, 1, 1);
        response = sparseGameManager.moveCreature(CreatureName.HORSE, 1, 1, 0, 2);
        assertEquals(MoveResponses.CREATURE_DOES_NOT_EXIST, response);
        response = sparseGameManager.placeCreature(CreatureName.HORSE, 1, 1);
        assertEquals(MoveResponses.LEGAL_MOVE, response);
    }

    @Test
    void kamikazeNothing() {
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, 1);
        sparseGameManager.placeCreature(kamikazeCreatureName, 0, -1);
        sparseGameManager.placeCreature(CreatureName.HORSE, 1, 1);
        sparseGameManager.moveCreature(kamikazeCreatureName, 0, -1, 1, -1);
        sparseGameManager.moveCreature(CreatureName.HORSE, 1, 1, -1, 1);
        response = sparseGameManager.moveCreature(kamikazeCreatureName, 1, -1, 0, -1);
        assertEquals(MoveResponses.LEGAL_MOVE, response);
    }

    @Test
    void mustPlaceButterflyAfterKamikaze() {
        sparseGameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        sparseGameManager.placeCreature(kamikazeCreatureName, 0, 1);
        sparseGameManager.placeCreature(CreatureName.CRAB, 0, -1);
        sparseGameManager.placeCreature(CreatureName.CRAB, 1, 1);
        sparseGameManager.moveCreature(CreatureName.CRAB, 0, -1, 1, -1);
        sparseGameManager.moveCreature(CreatureName.CRAB, 1, 1, 1, 0);
        sparseGameManager.moveCreature(CreatureName.CRAB, 1, -1, 2, -1);
        sparseGameManager.moveCreature(kamikazeCreatureName, 0, 1, 0, 0);
        response = sparseGameManager.moveCreature(CreatureName.CRAB, 0, -1, 0, 0);
        assertEquals(MoveResponses.MUST_PLACE_BUTTERFLY, response);
        response = sparseGameManager.placeCreature(CreatureName.CRAB, 0, 0);
        assertEquals(MoveResponses.MUST_PLACE_BUTTERFLY, response);
    }

    @Test
    void swapCreatures() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.CRAB, 0, 1);
        gameManager.placeCreature(walkingEffectCreatureName, 0, -1);
        gameManager.placeCreature(CreatureName.CRAB, 1, 1);
        gameManager.moveCreature(walkingEffectCreatureName, 0, -1, 1, -1);
        gameManager.moveCreature(CreatureName.CRAB, 1, 1, 0, 2);
        gameManager.moveCreature(walkingEffectCreatureName, 1, -1, 0, 2);

        response = gameManager.moveCreature(CreatureName.CRAB, 0, 2, 1, 1);
        assertEquals(MoveResponses.CREATURE_DOES_NOT_EXIST, response);
    }

    @Test
    void movingPlayerMustPlaceButterfly() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1); // 1
        gameManager.placeCreature(CreatureName.CRAB, 0, -1);
        gameManager.placeCreature(CreatureName.CRAB, 0, 2); // 2
        gameManager.placeCreature(CreatureName.CRAB, 0, -2);
        gameManager.placeCreature(CreatureName.CRAB, 0, 3); // 3
        gameManager.placeCreature(CreatureName.CRAB, 0, -3);
        gameManager.placeCreature(CreatureName.CRAB, 0, 4); // 4
        response = gameManager.moveCreature(CreatureName.CRAB, 0, -4, -1, -3);
        assertEquals(MoveResponses.MUST_PLACE_BUTTERFLY, response);
    }

    @Test
    void movingPlayerHasNoPath() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        response = gameManager.moveCreature(CreatureName.CRAB, 0, 0, -1, -3);
        assertEquals(MoveResponses.MOVE_OUT_OF_RANGE, response);
    }

    @Test
    void movingPlayerWinsBySurrounding() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        gameManager.placeCreature(CreatureName.CRAB, -1, 0);
        gameManager.placeCreature(CreatureName.CRAB, -1, 2);
        gameManager.placeCreature(CreatureName.CRAB, 1, -1);
        gameManager.placeCreature(CreatureName.CRAB, 1, 1);
        gameManager.moveCreature(CreatureName.CRAB, 1, -1, 0, -1);
        gameManager.moveCreature(CreatureName.CRAB, -1, 2, -1, 1);
        gameManager.placeCreature(CreatureName.CRAB, 1, -1);
        response = gameManager.moveCreature(CreatureName.CRAB, 1, 1, 1, 0);
        assertEquals(MoveResponses.RED_WON, response);
    }

    @Test
    void bothButterfliesSurrounded() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        gameManager.placeCreature(CreatureName.CRAB, -1, 0);
        gameManager.placeCreature(CreatureName.CRAB, -1, 2);
        gameManager.placeCreature(CreatureName.CRAB, 1, -1);
        gameManager.placeCreature(CreatureName.CRAB, 1, 1);
        gameManager.moveCreature(CreatureName.CRAB, 1, -1, 0, -1);
        gameManager.moveCreature(CreatureName.CRAB, -1, 2, -1, 1);
        gameManager.placeCreature(CreatureName.CRAB, 1, -1);
        gameManager.placeCreature(CreatureName.CRAB, -1, 2);
        gameManager.placeCreature(CreatureName.CRAB, 0, -2);
        gameManager.placeCreature(CreatureName.CRAB, 0, 2);
        response = gameManager.moveCreature(CreatureName.CRAB, 0, -2, 1, 0);
        assertEquals(MoveResponses.DRAW, response);
    }

    @Test
    void noMoves() {
        sparseGameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        sparseGameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        sparseGameManager.placeCreature(kamikazeCreatureName, -1, 0);
        sparseGameManager.moveCreature(CreatureName.BUTTERFLY, 0, 1, -1, 1);
        response = sparseGameManager.moveCreature(kamikazeCreatureName, -1, 0, -1, 1);
        assertEquals(MoveResponses.LEGAL_MOVE, response);
        response = sparseGameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        assertEquals(MoveResponses.BLUE_WON, response);
    }

    @Test
    void playerGetsThemselvesStuck() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(flyingCreatureName, 0, 1);
        gameManager.placeCreature(CreatureName.CRAB, 0, -1);
        gameManager.moveCreature(flyingCreatureName, 0, 1, 1, 0);
        gameManager.placeCreature(CreatureName.CRAB, -1, 0);
        gameManager.moveCreature(flyingCreatureName, 1, 0, 0, 1);
        gameManager.placeCreature(CreatureName.CRAB, 1, -1);
        gameManager.moveCreature(flyingCreatureName, 0, 1, 0, -2);
        gameManager.placeCreature(CreatureName.CRAB, 1, 0);
        gameManager.moveCreature(flyingCreatureName, 0, -2, 1, -2);
        gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 0, 0, 1);
        gameManager.moveCreature(flyingCreatureName, 1, -2, 1, 1);
        gameManager.placeCreature(CreatureName.CRAB, -1, 1);
        response = gameManager.moveCreature(flyingCreatureName, 1, 1, 0, 0);
    }

    @Test
    void moveDestinationDisconnected() {
        gameManager.placeCreature(CreatureName.CRAB, 0, 0);
        gameManager.placeCreature(CreatureName.CRAB, 0, 1);
        response = gameManager.moveCreature(CreatureName.CRAB, 0, 0, 0, -1);
        assertEquals(MoveResponses.MOVE_DISCONNECTED, response);
    }

    @Test
    void moveDestinationOccupied() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 0);
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        response = gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 0, 0, 1);
        assertEquals(MoveResponses.MOVE_OCCUPIED, response);
    }

    @Test
    void moveDestinationNotInline() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        gameManager.placeCreature(jumpingCreatureName, 0, 0);
        gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 1, -1, 1);
        response = gameManager.moveCreature(jumpingCreatureName, 0, 0, -1, 2);
        assertEquals(MoveResponses.MOVE_NOT_INLINE, response);
    }

    @Test
    void swapDestinationButterfly() {
        gameManager.placeCreature(CreatureName.BUTTERFLY, 0, 1);
        gameManager.placeCreature(walkingEffectCreatureName, 0, 0);
        gameManager.moveCreature(CreatureName.BUTTERFLY, 0, 1, -1, 1);
        response = gameManager.moveCreature(walkingEffectCreatureName, 0, 0, -1, 1);
        assertEquals(MoveResponses.MOVE_BUTTERFLY, response);
    }
}
