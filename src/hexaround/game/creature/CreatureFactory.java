package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.game.board.pathfinding.IPathFinder;
import hexaround.game.board.pathfinding.PathFinder;
import hexaround.game.board.pathfinding.pathvalidator.*;
import hexaround.game.board.pathfinding.pointvalidator.*;
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
        IPathFinder pathFinder = makePathFinder(creatureDefinition.properties());

        return Optional.of(new Creature(creatureName,
                ownerName,
                creatureDefinition.maxDistance(),
                pathFinder,
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
        boolean swapping = creatureProperties.contains(CreatureProperty.SWAPPING);
        boolean kamikaze = creatureProperties.contains(CreatureProperty.KAMIKAZE);
        boolean hatching = creatureProperties.contains(CreatureProperty.HATCHING);

        boolean hasMovementEffect = trapping || swapping || kamikaze;

        if (flying || jumping) {
            pathConditions.add(new PathConnected());
        } else if (hasMovementEffect) {
            if (kamikaze) {
                pathConditions.add(new PathDestinationRemovable());
            }
            pathConditions.add(new PathEmpty());
        } else {
            pointConditions.add(new PointEmpty());
        }

        if (running) {
            pathConditions.add(new PathAtRange());
        } else {
            pathConditions.add(new PathInRange());
        }

        else if (walking || running) {
            if (!intruding) {
                pointConditions.add(new PointSlideable());
                pointConditions.add(new PointEmpty());
            }
            pointConditions.add(new PointConnected());
        } else {
            if (jumping) {
                pointConditions.add(new PointInline());
            } else {
                pathConditions.add(new PathConnected());
            }
        }

        IPointValidator pointValidator = new PointValidator(pointConditions);
        IPathValidator pathValidator = new PathValidator(pathConditions);

        return new PathFinder(pointValidator, pathValidator);
    }
}
