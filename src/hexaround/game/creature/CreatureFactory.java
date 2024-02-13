package hexaround.game.creature;

import hexaround.config.CreatureDefinition;

import java.util.Map;

public class CreatureFactory {
    protected Map<CreatureName, CreatureDefinition> creatureDefinitions;

    /**
     * Constructs an instance of CreatureFactory
     * @param creatureDefinitions a map of each Creature's name to their respective CreatureDefinition
     */
    public CreatureFactory(Map<CreatureName, CreatureDefinition> creatureDefinitions) {
        this.creatureDefinitions = creatureDefinitions;
    }

    /**
     * Makes an instance of Creature with name with fields as specified in that Creature's CreatureDefinition
     * @param name the CreatureName of the creature
     * @return an instance of Creature with fields configured according to CreatureName
     */
    public ICreature makeCreature(CreatureName name) {
        CreatureDefinition creatureDefinition = creatureDefinitions.get(name);

        return new Creature(creatureDefinition.name(),
                creatureDefinition.maxDistance(),
                creatureDefinition.properties());
    }
}
