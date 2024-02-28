package hexaround.game.player;

import hexaround.config.CreatureDefinition;
import hexaround.config.GameConfiguration;
import hexaround.config.HexAroundConfigurationMaker;
import hexaround.game.creature.CreatureName;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    TestPlayer player;

    @BeforeEach
    void setUpPlayer() {
        Map<CreatureName, Integer> creatureCounts = new HashMap<>();

        creatureCounts.put(CreatureName.BUTTERFLY, 1);
        creatureCounts.put(CreatureName.GRASSHOPPER, 2);
        creatureCounts.put(CreatureName.CRAB, 2);

        player = new TestPlayer(PlayerName.RED, creatureCounts);
    }

    @Test
    void incrementCreature() {
        Map<CreatureName, Integer> creatureCounts = player.getCreatureCounts();

        player.incrementCreature(CreatureName.BUTTERFLY);
        assertEquals(2, creatureCounts.get(CreatureName.BUTTERFLY));
        player.incrementCreature(CreatureName.BUTTERFLY);
        assertEquals(3, creatureCounts.get(CreatureName.BUTTERFLY));
    }


    @Test
    void decrementCreature() {
        Map<CreatureName, Integer> creatureCounts = player.getCreatureCounts();

        player.decrementCreature(CreatureName.CRAB);
        assertEquals(1, creatureCounts.get(CreatureName.CRAB));
        player.decrementCreature(CreatureName.CRAB);
        assertEquals(0, creatureCounts.get(CreatureName.CRAB));
    }

    @Test
    void outOfCreatures() {
        player.decrementCreature(CreatureName.BUTTERFLY);
        player.decrementCreature(CreatureName.GRASSHOPPER);
        player.decrementCreature(CreatureName.GRASSHOPPER);
        player.decrementCreature(CreatureName.CRAB);
        player.decrementCreature(CreatureName.CRAB);
        assertTrue(player.outOfCreatures());
    }

    @Test
    void notOutOfCreatures() {
        player.decrementCreature(CreatureName.BUTTERFLY);
        player.decrementCreature(CreatureName.GRASSHOPPER);
        player.decrementCreature(CreatureName.GRASSHOPPER);
        player.decrementCreature(CreatureName.CRAB);
        assertFalse(player.outOfCreatures());
    }
}
