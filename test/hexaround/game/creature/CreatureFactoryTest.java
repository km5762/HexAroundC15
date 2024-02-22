package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.config.GameConfiguration;
import hexaround.config.HexAroundConfigurationMaker;
import hexaround.game.player.PlayerName;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureFactoryTest {

    private static CreatureFactory creatureFactory;

    @BeforeAll
    static void loadCreatureFactory() throws IOException {
        HexAroundConfigurationMaker configurationMaker =
                new HexAroundConfigurationMaker("testConfigurations/FirstConfiguration.hgc");
        GameConfiguration configuration = configurationMaker.makeConfiguration();

        Map<CreatureName, CreatureDefinition> creatureDefinitions = new HashMap<>();

        for (CreatureDefinition creatureDefinition : configuration.creatures()) {
            creatureDefinitions.put(creatureDefinition.name(), creatureDefinition);
        }

        creatureFactory = new CreatureFactory(creatureDefinitions);
    }

    @Test
    void makeCreatureInstance() throws IOException {
        ICreature creature = creatureFactory.makeCreature(CreatureName.BUTTERFLY, PlayerName.RED);
        assertTrue(creature instanceof ICreature);
    }

    @Test
    void makeSpecificCreatureInstance() throws IOException {
        ICreature creature = creatureFactory.makeCreature(CreatureName.BUTTERFLY, PlayerName.RED);
        assertEquals(CreatureName.BUTTERFLY, creature.getName());
    }

    @Test
    void makeCreatureInstanceWithMaxDistance() throws IOException {
        ICreature creature = creatureFactory.makeCreature(CreatureName.GRASSHOPPER, PlayerName.RED);
        assertEquals(3, creature.getMaxDistance());
    }

    @Test
    void makeCreatureWithProperties() throws IOException {
        ICreature creature = creatureFactory.makeCreature(CreatureName.GRASSHOPPER, PlayerName.RED);
        assertTrue(creature.hasProperty(CreatureProperty.INTRUDING));
        assertTrue(creature.hasProperty(CreatureProperty.JUMPING));
    }
}
