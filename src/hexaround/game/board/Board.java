package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.IPathFinder;
import hexaround.game.board.pathfinding.MovementRules;
import hexaround.game.board.pathfinding.PathFinder;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import hexaround.game.player.PlayerName;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.List;

public class Board implements IBoard {
    protected Map<IPoint, CreatureStack> board;
    protected IPathFinder pathFinder;

    public Board(Map<IPoint, CreatureStack> board) {
        this.board = board;
        this.pathFinder = new PathFinder();
    }

    /**
     * Places an instance of ICreature at the specified IPoint
     *
     * @param creature an instance of ICreature
     * @param point    the specified IPoint
     */
    public void placeCreature(ICreature creature, IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);
        creaturesAtPoint.addCreature(creature);

        if (!pointIsOccupied(point)) {
            board.put(point, creaturesAtPoint);
        }
    }

    /**
     * Removes creature with CreatureName from the specified IPoint
     *
     * @param creatureName the CreatureName of the creature to remove
     * @param point        the specified IPoint
     */
    public void removeCreature(ICreature creature, IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);
        creaturesAtPoint.removeCreature(creature);

        if (creaturesAtPoint.isEmpty()) {
            board.remove(point);
        }
    }

    public void removeAllCreatures(IPoint point) {
        board.remove(point);
    }

    /**
     * Moves creature with CreatureName from one IPoint to another, carrying out any side effects of such movement (KAMIKAZE, SWAPPING)
     *
     * @param creatureName the CreatureName of the creature to move
     * @param fromPoint    the IPoint the creature is currently at
     * @param fromPoint    the IPoint to move the creature to
     */
    @Override
    public void moveCreature(ICreature creature, IPoint fromPoint, IPoint toPoint) {
        removeCreature(creature, fromPoint);
        placeCreature(creature, toPoint);

//            if (creatureWithName.get().hasProperty(CreatureProperty.KAMIKAZE)) {
//                removeAllCreatures(toPoint);
//            } else {
//                if (creatureWithName.get().hasProperty(CreatureProperty.SWAPPING)) {
//                    Optional<ICreature> swappedCreature = getTopCreature(toPoint);
//
//                    if (swappedCreature.isPresent()) {
//                        removeCreature(swappedCreature.get().getName(), toPoint);
//                        placeCreature(swappedCreature.get(), fromPoint);
//                    }
//                }
//                placeCreature(creatureWithName.get(), toPoint);
//            }
    }

    /**
     * Returns the creature on top of the stack of creatures at the specified IPoint
     *
     * @param point the specified IPoint
     * @return the top ICreature at point
     */
    public Optional<ICreature> getTopCreature(IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);
        return creaturesAtPoint.getTopCreature();
    }

    /**
     * Returns all creatures at the specified IPoint
     *
     * @param point the specified IPoint
     * @return all creatures at point or an empty creature stack if none exist
     */
    public CreatureStack getAllCreatures(IPoint point) {
        return board.getOrDefault(point, new CreatureStack());
    }

    /**
     * Return a ICreature instance with CreatureName at IPoint
     *
     * @param creatureName the specified CreatureName
     * @param point        the specified IPoint
     * @return the ICreature with CreatureName at IPoint, or an empty Optional if none exist
     */
    public Optional<ICreature> getCreatureWithNameAndOwner(CreatureName creatureName, PlayerName ownerName, IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);

        if (!creaturesAtPoint.isEmpty()) {
            return creaturesAtPoint.getCreatureWithNameAndOwner(creatureName, ownerName);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns true if the board is disconnected
     *
     * @return true if the board is disconnected, false otherwise
     */
    public boolean isConnected() {
        if (board.isEmpty()) {
            return false;
        } else if (board.keySet().size() == 1) {
            return true;
        }

        Set<IPoint> occupiedPoints = board.keySet();
        Set<IPoint> visitedPoints = new HashSet<>();
        Queue<IPoint> pointQueue = new LinkedList<>();
        IPoint firstPoint = board.keySet().iterator().next();
        pointQueue.add(firstPoint);
        visitedPoints.add(firstPoint);

        while (!pointQueue.isEmpty()) {
            IPoint currentPoint = pointQueue.poll();

            for (IPoint occupiedNeighboringPoint : getOccupiedNeighboringPoints(currentPoint)) {
                if (!visitedPoints.contains(occupiedNeighboringPoint)) {
                    visitedPoints.add(occupiedNeighboringPoint);

                    if (visitedPoints.size() == occupiedPoints.size()) {
                        return true;
                    }

                    pointQueue.add(occupiedNeighboringPoint);
                }
            }
        }

        return false;
    }

    public boolean isSurrounded(IPoint point) {
        return getOccupiedNeighboringPoints(point).size() == 6;
    }

    @Override
    public Board clone() {
        Map<IPoint, CreatureStack> copiedBoard = new HashMap<>();

        for (Map.Entry<IPoint, CreatureStack> entry : board.entrySet()) {
            IPoint copiedPoint = entry.getKey().clone();
            CreatureStack copiedCreatures = entry.getValue().clone();

            copiedBoard.put(copiedPoint, copiedCreatures);
        }

        return new Board(copiedBoard);
    }

    private List<IPoint> getOccupiedNeighboringPoints(IPoint point) {
        List<IPoint> occupiedNeighboringPoints = new ArrayList<>();

        for (IPoint neighboringPoint : point.getNeighboringPoints()) {
            if (pointIsOccupied(neighboringPoint)) {
                occupiedNeighboringPoints.add(neighboringPoint);
            }
        }

        return occupiedNeighboringPoints;
    }

    public boolean pointIsOccupied(IPoint point) {
        return board.containsKey(point);
    }

    /**
     * Returns true if the specified placement disconnects the colony, and false otherwise
     *
     * @param creature the creature instance being placed
     * @param point    the specified IPoint
     * @return true if the specified move is disconnecting, and false otherwise
     */
    public boolean placementIsDisconnecting(ICreature creature, IPoint point) {
        if (board.isEmpty()) {
            return false;
        }

        Board boardSimulation = this.clone();
        boardSimulation.placeCreature(creature, point);

        return !boardSimulation.isConnected();
    }

    /**
     * Returns true if a creature could slide unobstructed from the specified IPoint to the target IPoint
     *
     * @param fromPoint the IPoint the creature is currently at
     * @param fromPoint the target IPoint
     * @return true if the creature's slide is unobstructed to the target, false otherwise
     */
    public boolean creatureCanSlide(IPoint fromPoint, IPoint toPoint) {
        List<IPoint> fromOccupiedNeighboringPoints = getOccupiedNeighboringPoints(fromPoint);
        List<IPoint> toOccupiedNeighboringPoints = getOccupiedNeighboringPoints(toPoint);
        int commonNeighborsCount = 0;

        for (IPoint fromOccupiedNeighboringPoint : fromOccupiedNeighboringPoints) {
            if (toOccupiedNeighboringPoints.contains(fromOccupiedNeighboringPoint)) {
                commonNeighborsCount++;

                if (commonNeighborsCount == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean existsPath(ICreature creature, IPoint fromPoint, IPoint toPoint) {
        MovementRules movementRules = creature.getMovementRules();
        return pathFinder.findPath(this, creature, fromPoint, toPoint, movementRules).isPresent();
    }

    public boolean existsPath(ICreature creature, IPoint fromPoint) {
        MovementRules movementRules = creature.getMovementRules();
        return pathFinder.findPath(this, creature, fromPoint, movementRules).isPresent();
    }
}
