package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.game.board.pathfinding.IPathFinder;
import hexaround.game.board.pathfinding.pathvalidator.IPathCondition;
import hexaround.game.board.pathfinding.pointvalidator.IPointCondition;
import hexaround.game.player.PlayerName;

import java.util.*;

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
    public Optional<ICreature> makeCreature(CreatureName creatureName, PlayerName ownerName) {
        if (!creatureDefinitions.containsKey(creatureName)) {
            return Optional.empty();
        }
        CreatureDefinition creatureDefinition = creatureDefinitions.get(creatureName);

        return Optional.of(new Creature(creatureDefinition.name(),
                ownerName,
                creatureDefinition.maxDistance(),
                creatureDefinition.properties()));
    }

    private IPathFinder makePathFinder(Collection<CreatureProperty> creatureProperties) {
        List<IPointCondition> pointConditions = new ArrayList<>();
        List<IPathCondition> pathConditions = new ArrayList<>();

        boolean walking = creatureProperties.contains(CreatureProperty.WALKING);
        boolean running = creatureProperties.contains(CreatureProperty.RUNNING);
        boolean flying = creatureProperties.contains(CreatureProperty.FLYING);
        boolean jumping = creatureProperties.contains(CreatureProperty.JUMPING);
        boolean intruding = creatureProperties.contains(CreatureProperty.INTRUDING);
        boolean trapping = creatureProperties.contains(CreatureProperty.TRAPPING);
        boolean

        if (creatureProperties.contains(CreatureProperty.KAMIKAZE) || creatureProperties.contains(CreatureProperty.SWAPPING) || creatureProperties.contains(CreatureProperty.TRAPPING)) {

        }
    }
}
