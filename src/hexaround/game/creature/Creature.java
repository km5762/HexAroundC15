package hexaround.game.creature;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.IPathFinder;
import hexaround.game.player.PlayerName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Creature implements ICreature {
    protected CreatureName name;
    PlayerName ownerName;
    protected int maxDistance;
    protected IPathFinder pathFinder;
    protected Collection<CreatureProperty> properties;

    /**
     * Constructs an instance of Creature
     * @param name the Creature's name. Must be of type CreatureName
     * @param maxDistance the maximum range of the creature's movement.
     * @param properties the properties the Creature possesses. Must be of type CreatureProperty.
     */
    public Creature(CreatureName name, PlayerName ownerName, int maxDistance, IPathFinder pathFinder, Collection<CreatureProperty> properties) {
        this.name = name;
        this.ownerName = ownerName;
        this.maxDistance = maxDistance;
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

    public boolean canMove(IBoard board, IPoint fromPoint, IPoint toPoint) {
        return pathFinder.findPath(board, this, fromPoint, toPoint).isPresent();
    }

    public boolean canMove(IBoard board, IPoint fromPoint) {
        return pathFinder.findPath(board, this, fromPoint).isPresent();
    }
}
