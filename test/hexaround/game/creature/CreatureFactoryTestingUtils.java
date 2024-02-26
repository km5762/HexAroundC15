package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.config.GameConfiguration;
import hexaround.config.HexAroundConfigurationMaker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreatureFactoryTestingUtils {
    public static CreatureFactory loadCreatureFactory(String configuration) throws IOException {
        HexAroundConfigurationMaker configurationMaker1 =
                new HexAroundConfigurationMaker(configuration);
        GameConfiguration configuration1 = configurationMaker1.makeConfiguration();

        Map<CreatureName, CreatureDefinition> creatureDefinitions = new HashMap<>();

        for (CreatureDefinition creatureDefinition : configuration1.creatures()) {
            creatureDefinitions.put(creatureDefinition.name(), creatureDefinition);
        }

        return new CreatureFactory(creatureDefinitions);
    }
}
