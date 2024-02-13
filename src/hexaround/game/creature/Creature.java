package hexaround.game.creature;

import java.util.Collection;

public class Creature implements ICreature {
    protected CreatureName name;
    protected int maxDistance;
    protected Collection<CreatureProperty> properties;

    /**
     * Constructs an instance of Creature
     * @param name the Creature's name. Must be of type CreatureName
     * @param maxDistance the maximum range of the creature's movement.
     * @param properties the properties the Creature possesses. Must be of type CreatureProperty.
     */
    public Creature(CreatureName name, int maxDistance, Collection<CreatureProperty> properties) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.properties = properties;
    }

    /**
     * Gets the CreatureName enum for this Creature instance
     * @return the CreatureName of the Creature
     */
    public CreatureName getName() {
        return name;
    }

    /**
     * Gets the maxDistance for this Creature instance's movement
     * @return the maxDistance of the Creature
     */
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
}
