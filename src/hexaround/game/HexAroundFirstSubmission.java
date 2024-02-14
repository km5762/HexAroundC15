package hexaround.game;

import hexaround.game.board.MoveResponse;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;

import java.util.Collection;
import java.awt.Point;
import java.util.Map;

public class HexAroundFirstSubmission implements IHexAround1 {
    protected Collection<Player> players;
    protected Map<Point, ICreature> board;
    protected CreatureFactory creatureFactory;

    /**
     * This is the default constructor, and the only constructor
     * that you can use. The builder creates an instance using
     * this connector. You should add getters and setters as
     * necessary for any instance variables that you create and
     * will be filled in by the builder.
     */
    public HexAroundFirstSubmission() {
        // Nothing to do.
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    public void setBoard(Map<Point, ICreature> board) {
        this.board = board;
    }

    public void setCreatureFactory(CreatureFactory creatureFactory) {
        this.creatureFactory = creatureFactory;
    }

    /**
     * Given the x and y-coordinates for a hex, return the name
     * of the creature on that coordinate.
     *
     * @param x
     * @param y
     * @return the name of the creature on (x, y), or null if there
     * is no creature.
     */
    @Override
    public CreatureName getCreatureAt(int x, int y) {
        if (!isOccupied(x, y)) {
            return null;
        }
        return board.get(new Point(x, y)).getName();
    }

    /**
     * Determine if the creature at the x and y-coordinates has the specified
     * property. You can assume that there will be a creature at the specified
     * location.
     *
     * @param x
     * @param y
     * @param property the property to look for.
     * @return true if the creature at (x, y) has the specified property,
     * false otherwise.
     */
    @Override
    public boolean hasProperty(int x, int y, CreatureProperty property) {
        return board.get(new Point(x, y)).hasProperty(property);
    }

    /**
     * Given the x and y-coordinate of a hex, determine if there is a
     * piece on that hex on the board.
     *
     * @param x
     * @param y
     * @return true if there is a piece on the hex, false otherwise.
     */
    @Override
    public boolean isOccupied(int x, int y) {
        return board.containsKey(new Point(x, y));
    }

    /**
     * Given the coordinates for two hexes, (x1, y1) and (x2, y2),
     * return whether the piece at (x1, y1) could reach the other
     * hex.
     * You can assume that there will be a piece at (x1, y1).
     * The distance is just the distance between the two hexes. You
     * do not have to do any other checking.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return itrue if the distance between the two hexes is less
     * than or equal to the maximum distance property for the piece
     * at (x1, y1). Return false otherwise.
     */
    @Override
    public boolean canReach(int x1, int y1, int x2, int y2) {
        int maxDistance = board.get(new Point(x1, y1)).getMaxDistance();
        double distance = calculateDistance(x1, y1, x2, y2);

        return distance <= maxDistance;
    }

    /**
     * Source: https://www.redblobgames.com/grids/hexagons/#distances
     */
    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(y1 - y2)
                + Math.abs(y1 + x1 - y2 - x2)
                + Math.abs(x1 - x2)) / 2;
    }

    /**
     * For this submission, just put the piece on the board. You
     * can assume that the hex (x, y) is empty. You do not have to do
     * any checking.
     *
     * @param creature
     * @param x
     * @param y
     * @return a response, or null. It is not going to be checked.
     */
    @Override
    public MoveResponse placeCreature(CreatureName creature, int x, int y) {
        ICreature creatureInstance = creatureFactory.makeCreature(creature);

        board.put(new Point(x, y), creatureInstance);

        return null;
    }

    /**
     * This is never used in this submission. You do not have to do anything.
     *
     * @param creature
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    @Override
    public MoveResponse moveCreature(CreatureName creature, int fromX, int fromY, int toX, int toY) {
        return null;
    }
}
