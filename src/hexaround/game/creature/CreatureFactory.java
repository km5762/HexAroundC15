package hexaround.game.creature;

import hexaround.config.CreatureDefinition;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.MovementRules;
import hexaround.game.rules.Validator;
import hexaround.game.rules.movement.*;
import hexaround.game.rules.path.*;
import hexaround.game.rules.pre_movement.*;
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
     * Makes an instance of ICreature belonging to owner with PlayerName and with properties according to their corresponding CreatureDefinition
     *
     * @param creatureName the CreatureName of the creature to make
     * @param ownerName    the PlayerName of the creature's owner
     * @return an Optional containing either the constructed ICreature, or empty if the ICreature has not been defined
     */
    public Optional<ICreature> makeCreature(CreatureName creatureName, PlayerName ownerName) {
        if (!creatureDefinitions.containsKey(creatureName)) {
            return Optional.empty();
        }

        CreatureDefinition creatureDefinition = creatureDefinitions.get(creatureName);
        MovementRules movementRules = makeMovementRules(creatureDefinition.properties());

        return Optional.of(new Creature(creatureName, ownerName, creatureDefinition.maxDistance(), movementRules, creatureDefinition.properties()));
    }

    private MovementRules makeMovementRules(Collection<CreatureProperty> creatureProperties) {
        List<ICondition<PreMoveContext>> preMoveConditions = new ArrayList<>();
        List<ICondition<MoveContext>> moveConditions = new ArrayList<>();
        List<ICondition<PathContext>> pathConditions = new ArrayList<>();

        addInvariantConditions(creatureProperties, preMoveConditions, pathConditions);

        if (isGroundCreature(creatureProperties)) {
            addGroundCreatureConditions(creatureProperties, preMoveConditions, moveConditions, pathConditions);
        } else {
            addFlyingCreatureConditions(creatureProperties, preMoveConditions, moveConditions, pathConditions);
        }

        Validator<PreMoveContext> preMoveValidator = new Validator<>(preMoveConditions);
        Validator<MoveContext> moveValidator = new Validator<>(moveConditions);
        Validator<PathContext> pathValidator = new Validator<>(pathConditions);

        return new MovementRules(preMoveValidator, moveValidator, pathValidator);
    }

    private void addInvariantConditions(Collection<CreatureProperty> creatureProperties,
                                        List<ICondition<PreMoveContext>> preMoveConditions,
                                        List<ICondition<PathContext>> pathConditions) {
        boolean kamikaze = creatureProperties.contains(CreatureProperty.KAMIKAZE);
        boolean running = creatureProperties.contains(CreatureProperty.RUNNING);
        boolean swapping = creatureProperties.contains(CreatureProperty.SWAPPING);
        boolean intruding = creatureProperties.contains(CreatureProperty.INTRUDING);

        preMoveConditions.add(new PreMoveNotPinned());
        preMoveConditions.add(new PreMoveInRange());
        preMoveConditions.add(new PreMoveDestinationConnected());

        if (kamikaze) {
            pathConditions.add(new PathDestinationRemovable());
            preMoveConditions.add(new PreMoveDestinationRemovable());
        }

        if (running) {
            pathConditions.add(new PathAtRange());
        }

        if (intruding && !kamikaze) {
            pathConditions.add(new PathDestinationNotStack());
        }

        if (swapping) {
            preMoveConditions.add(new PreMoveDestinationNotButterfly());
            pathConditions.add(new PathDestinationNotButterfly());
        }
    }

    private void addGroundCreatureConditions(Collection<CreatureProperty> creatureProperties,
                                             List<ICondition<PreMoveContext>> preMoveConditions,
                                             List<ICondition<MoveContext>> moveConditions,
                                             List<ICondition<PathContext>> pathConditions) {
        boolean intruding = creatureProperties.contains(CreatureProperty.INTRUDING);

        moveConditions.add(new MoveConnected());

        if (!intruding) {
            moveConditions.add(new MoveSlideable());
            if (hasMovementEffect(creatureProperties)) {
                pathConditions.add(new PathUpToDestinationEmpty());
            } else {
                preMoveConditions.add(new PreMoveDestinationEmpty());
                moveConditions.add(new MoveEmpty());
            }
        }
    }

    private void addFlyingCreatureConditions(Collection<CreatureProperty> creatureProperties,
                                             List<ICondition<PreMoveContext>> preMoveConditions,
                                             List<ICondition<MoveContext>> moveConditions,
                                             List<ICondition<PathContext>> pathConditions) {
        boolean jumping = creatureProperties.contains(CreatureProperty.JUMPING);
        boolean intruding = creatureProperties.contains(CreatureProperty.INTRUDING);

        pathConditions.add(new PathDestinationConnected());

        if (jumping) {
            moveConditions.add(new MoveInline());
            preMoveConditions.add(new PreMoveDestinationInline());
        } else {
            preMoveConditions.add(new PreMoveNotSurrounded());
        }

        if (!(intruding || hasMovementEffect(creatureProperties))) {
            preMoveConditions.add(new PreMoveDestinationEmpty());
            pathConditions.add(new PathDestinationEmpty());
        }
    }

    private boolean hasMovementEffect(Collection<CreatureProperty> creatureProperties) {
        return creatureProperties.contains(CreatureProperty.KAMIKAZE)
                || creatureProperties.contains(CreatureProperty.SWAPPING)
                || creatureProperties.contains(CreatureProperty.TRAPPING);
    }

    private boolean isGroundCreature(Collection<CreatureProperty> creatureProperties) {
        return creatureProperties.contains(CreatureProperty.WALKING)
                || creatureProperties.contains(CreatureProperty.RUNNING);
    }
}
