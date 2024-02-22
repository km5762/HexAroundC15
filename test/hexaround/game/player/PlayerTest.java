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
        creatureCounts.put(CreatureName.GRASSHOPPER, 5);
        creatureCounts.put(CreatureName.CRAB, 6);

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
        assertEquals(5, creatureCounts.get(CreatureName.CRAB));
        player.decrementCreature(CreatureName.CRAB);
        assertEquals(4, creatureCounts.get(CreatureName.CRAB));    }
}
