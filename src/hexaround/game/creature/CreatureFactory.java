package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.game.player.PlayerName;

import java.util.Map;

public class CreatureFactory {
    protected Map<CreatureName, CreatureDefinition> creatureDefinitions;

    /**
     * Constructs an instance of CreatureFactory
     *
     * @param creatureDefinitions a map of each Creature's name to their respective CreatureDefinition
     */
    public CreatureFactory(Map<CreatureName, CreatureDefinition> creatureDefinitions) {
        this.creatureDefinitions = creatureDefinitions;
    }

    /**
     * Makes an instance of Creature with name belonging to player with ownerName and fields as specified in that Creature's CreatureDefinition
     *
     * @param name the CreatureName of the creature
     * @param ownerName the PlayerName of the owner
     * @return an instance of Creature with fields configured according to CreatureName belonging to ownerName
     */
    public ICreature makeCreature(CreatureName name, PlayerName ownerName) {
        if (!creatureDefinitions.containsKey(name)) {
            return null;
        }
        CreatureDefinition creatureDefinition = creatureDefinitions.get(name);

        return new Creature(creatureDefinition.name(),
                ownerName,
                creatureDefinition.maxDistance(),
                creatureDefinition.properties());
    }
}
