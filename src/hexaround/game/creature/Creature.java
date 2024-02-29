package hexaround.game.creature;

import hexaround.game.rules.MovementRules;
import hexaround.game.player.PlayerName;

import java.util.Collection;

public class Creature implements ICreature {
    protected CreatureName name;
    PlayerName ownerName;
    protected int maxDistance;
    protected MovementRules movementRules;
    protected Collection<CreatureProperty> properties;

    /**
     * Constructs an instance of Creature
     * @param name the Creature's name. Must be of type CreatureName
     * @param maxDistance the maximum range of the creature's movement.
     * @param movementRules an instance of MovementRules which describes the creatures movement constraints
     * @param properties the properties the Creature possesses. Must be of type CreatureProperty.
     */
    public Creature(CreatureName name, PlayerName ownerName, int maxDistance, MovementRules movementRules, Collection<CreatureProperty> properties) {
        this.name = name;
        this.ownerName = ownerName;
        this.maxDistance = maxDistance;
        this.movementRules = movementRules;
        this.properties = properties;
    }

    public CreatureName getName() {
        return name;
    }

    public PlayerName getOwnerName() {
        return ownerName;
    }

    public int getMaxDistance() {
        return maxDistance;
    }


    /**
     * Used to determine if this creature has a specified property
     * @param property the property to check for
     * @return true if the property is in the creatures properties list, false otherwise
     */
    public boolean hasProperty(CreatureProperty property) {
        return properties.contains(property);
    }

    public MovementRules getMovementRules() {
        return movementRules;
    }
}
