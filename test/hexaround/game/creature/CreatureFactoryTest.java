package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.config.GameConfiguration;
import hexaround.config.HexAroundConfigurationMaker;
import hexaround.game.creature.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureFactoryTest {
    @Test
    void makeCreatureInstance() throws IOException {
        CreatureFactory creatureFactory = loadCreatureFactory("testConfigurations/FirstConfiguration.hgc");

        ICreature creature = creatureFactory.makeCreature(CreatureName.BUTTERFLY);
        assertTrue(creature instanceof ICreature);
    }

    @Test
    void makeSpecificCreatureInstance() throws IOException {
        CreatureFactory creatureFactory = loadCreatureFactory("testConfigurations/FirstConfiguration.hgc");

        ICreature creature = creatureFactory.makeCreature(CreatureName.BUTTERFLY);
        assertEquals(CreatureName.BUTTERFLY, creature.getName());
    }

    @Test
    void makeCreatureInstanceWithMaxDistance() throws IOException {
        CreatureFactory creatureFactory = loadCreatureFactory("testConfigurations/FirstConfiguration.hgc");

        ICreature creature = creatureFactory.makeCreature(CreatureName.GRASSHOPPER);
        assertEquals(3, creature.getMaxDistance());
    }

    @Test
    void makeCreatureWithProperties() throws IOException {
        CreatureFactory creatureFactory = loadCreatureFactory("testConfigurations/FirstConfiguration.hgc");

        ICreature creature = creatureFactory.makeCreature(CreatureName.GRASSHOPPER);
        assertTrue(creature.hasProperty(CreatureProperty.INTRUDING));
        assertTrue(creature.hasProperty(CreatureProperty.JUMPING));
    }

    private CreatureFactory loadCreatureFactory(String configurationFile) throws IOException {
        HexAroundConfigurationMaker configurationMaker =
                new HexAroundConfigurationMaker(configurationFile);
        GameConfiguration configuration = configurationMaker.makeConfiguration();

        Map<CreatureName, CreatureDefinition> creatureDefinitions = new HashMap<>();

        for (CreatureDefinition creatureDefinition : configuration.creatures()) {
            creatureDefinitions.put(creatureDefinition.name(), creatureDefinition);
        }

        return new CreatureFactory(creatureDefinitions);
    }
}
