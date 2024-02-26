package hexaround.game.creature;

import hexaround.game.player.PlayerName;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureFactoryTest {

    static CreatureFactory creatureFactory1;
    static CreatureFactory creatureFactory2;

    @BeforeAll
    static void loadCreatureFactory() throws IOException {
        creatureFactory1 = CreatureFactoryTestingUtils.loadCreatureFactory("testConfigurations/FirstConfiguration.hgc");
        creatureFactory2 = new CreatureFactory(new HashMap<>());
    }

    @Test
    void makeCreatureInstance() {
        Optional<ICreature> creature = creatureFactory1.makeCreature(CreatureName.BUTTERFLY, PlayerName.RED);
        assertTrue(creature.get() instanceof ICreature);
    }

    @Test
    void makeSpecificCreatureInstance() {
        Optional<ICreature> creature = creatureFactory1.makeCreature(CreatureName.BUTTERFLY, PlayerName.RED);
        assertEquals(CreatureName.BUTTERFLY, creature.get().getName());
    }

    @Test
    void makeCreatureInstanceWithMaxDistance() {
        Optional<ICreature> creature = creatureFactory1.makeCreature(CreatureName.GRASSHOPPER, PlayerName.RED);
        assertEquals(5, creature.get().getMaxDistance());
    }

    @Test
    void makeCreatureWithProperties() {
        Optional<ICreature> creature = creatureFactory1.makeCreature(CreatureName.CRAB, PlayerName.RED);
        assertTrue(creature.get().hasProperty(CreatureProperty.INTRUDING));
        assertTrue(creature.get().hasProperty(CreatureProperty.WALKING));
    }

    @Test
    void makeCreatureThatDoesntExist() {
        Optional<ICreature> creature = creatureFactory2.makeCreature(CreatureName.TURTLE, PlayerName.RED);
        assertTrue(creature.isEmpty());
    }
}
