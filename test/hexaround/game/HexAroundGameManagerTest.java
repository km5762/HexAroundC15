package hexaround.game;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HexAroundGameManagerTest {
    HexAroundGameBuilder hexAroundGameBuilder = new HexAroundGameBuilder();

    @Test
    void locationIsntOccupied() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertFalse(gameManager.isOccupied(4, 5));
    }

    @Test
    void locationIsOccupied() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertTrue(gameManager.isOccupied(3, 5));
    }

    @Test
    void getCreatureAtNonOccupied() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        CreatureName creatureName = gameManager.getCreatureAt(3, 5);

        assertNull(creatureName);
    }

    @Test
    void getCreatureAt() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        CreatureName creatureName = gameManager.getCreatureAt(3, 5);

        assertEquals(CreatureName.BUTTERFLY, creatureName);
    }

    @Test
    void creatureDoesntHaveProperty() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertFalse(gameManager.hasProperty(3, 5, CreatureProperty.INTRUDING));
    }

    @Test
    void creatureHasProperty() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertTrue(gameManager.hasProperty(3, 5, CreatureProperty.WALKING));
    }

    @Test
    void creatureCannotReach() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertFalse(gameManager.canReach(3, 5, 4, 6));
    }

    @Test
    void creatureCanReach() throws IOException {
        IHexAround1 gameManager = hexAroundGameBuilder.buildGameManager("testConfigurations/FirstConfiguration.hgc");

        gameManager.placeCreature(CreatureName.BUTTERFLY, 3, 5);

        assertTrue(gameManager.canReach(3, 5, 4, 5));
    }
}
