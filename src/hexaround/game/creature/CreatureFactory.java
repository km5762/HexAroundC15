package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.game.board.pathfinding.*;
import hexaround.game.board.pathfinding.pathvalidator.*;
import hexaround.game.board.pathfinding.movevalidator.*;
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
        MovementRules movementRules = makeMovementRules(creatureDefinition.properties());

        return Optional.of(new Creature(creatureName,
                ownerName,
                creatureDefinition.maxDistance(),
                movementRules,
                creatureDefinition.properties()));
    }

    private MovementRules makeMovementRules(Collection<CreatureProperty> creatureProperties) {
        List<ICondition<MoveContext>> moveConditions = new ArrayList<>();
        List<ICondition<PathContext>> pathConditions = new ArrayList<>();

        boolean walking = creatureProperties.contains(CreatureProperty.WALKING);
        boolean running = creatureProperties.contains(CreatureProperty.RUNNING);
        boolean jumping = creatureProperties.contains(CreatureProperty.JUMPING);
        boolean intruding = creatureProperties.contains(CreatureProperty.INTRUDING);
        boolean trapping = creatureProperties.contains(CreatureProperty.TRAPPING);
        boolean swapping = creatureProperties.contains(CreatureProperty.SWAPPING);
        boolean kamikaze = creatureProperties.contains(CreatureProperty.KAMIKAZE);

        boolean hasMovementEffect = trapping || swapping || kamikaze;
        boolean isGroundCreature = walking || running;

        if (kamikaze) {
            pathConditions.add(new PathDestinationRemovable());
        }

        if (running) {
            pathConditions.add(new PathAtRange());
        }

        if (isGroundCreature) {
            moveConditions.add(new MoveConnected());

            if (hasMovementEffect) {
                pathConditions.add(new PathUpToDestinationEmpty());
            } else if (!intruding) {
                moveConditions.add(new MoveSlideable());
                moveConditions.add(new MoveEmpty());
            }
        } else {
            pathConditions.add(new PathDestinationConnected());

            if (jumping) {
                moveConditions.add(new MoveInline());
            }

            if (!(intruding || hasMovementEffect)) {
                pathConditions.add(new PathDestinationEmpty());
            }
        }

        Validator<MoveContext> moveValidator = new Validator<>(moveConditions);
        Validator<PathContext> pathValidator = new Validator<>(pathConditions);

        return new MovementRules(moveValidator, pathValidator);
    }
}
