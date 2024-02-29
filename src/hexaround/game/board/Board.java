package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.IPathFinder;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.MovementRules;
import hexaround.game.board.pathfinding.PathFinder;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import hexaround.game.player.PlayerName;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.Validator;
import hexaround.game.rules.pre_movement.PreMoveContext;

import java.util.*;
import java.util.List;

public class Board implements IBoard {
    protected Map<IPoint, CreatureStack> board;
    protected IPathFinder pathFinder;

    /**
     * Used to construct an instance of a board
     *
     * @param board a map of IPoint to CreatureStacks representing the initial state of the board
     */
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
     * @param creature the Creature instance to remove
     * @param point    the specified IPoint
     */
    public void removeCreature(ICreature creature, IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);
        creaturesAtPoint.removeCreature(creature);

        if (creaturesAtPoint.isEmpty()) {
            board.remove(point);
        }
    }

    /**
     * Removes all creatures from the specified IPoint
     *
     * @param point the specified IPoint
     */
    public void removeAllCreatures(IPoint point) {
        board.remove(point);
    }

    /**
     * Moves a creature from one IPoint to another
     *
     * @param creature  the Creature instance of the creature to move
     * @param fromPoint the IPoint the creature is currently at
     * @param fromPoint the IPoint to move the creature to
     */
    @Override
    public void moveCreature(ICreature creature, IPoint fromPoint, IPoint toPoint) {
        removeCreature(creature, fromPoint);
        placeCreature(creature, toPoint);
    }

    /**
     * Returns the creature on top of the stack of creatures at the specified IPoint
     *
     * @param point the specified IPoint
     * @return an Optional containing the top creature, or empty if none exist
     */
    public Optional<ICreature> getTopCreature(IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);
        return creaturesAtPoint.getTopCreature();
    }

    /**
     * Returns all creatures at the specified IPoint
     *
     * @param point the specified IPoint
     * @return the CreatureStack containing all the creatures at the point, or an empty CreatureStack if none exist
     */
    public CreatureStack getAllCreatures(IPoint point) {
        return board.getOrDefault(point, new CreatureStack());
    }

    /**
     * Returns an ICreature instance with CreatureName and PlayerName as their owner at IPoint
     *
     * @param creatureName the specified CreatureName
     * @param ownerName    the specified PlayerName of the owner
     * @param point        the specified IPoint
     * @return an Optional containing the specified ICreature, or empty if no match was found
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
     * Returns true if the board is connected
     *
     * @return true if the board is connected, false otherwise
     */
    public boolean isConnected() {
        if (board.isEmpty()) {
            return true;
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

    /**
     * Returns true if the IPoint is surrounded
     *
     * @param point
     * @return true if the IPoint is surrounded (every neighboring point is occupied), false otherwise
     */
    public boolean isSurrounded(IPoint point) {
        return getOccupiedNeighboringPoints(point).size() == 6;
    }

    /**
     * Creates a deep copy of this Board instance
     *
     * @return a deep copy of the current instance
     */
    @Override
    public IBoard clone() {
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

    /**
     * Returns true if the IPoint is occupied, false otherwise
     *
     * @param point the specified IPoint
     * @return true if the IPoint is occupied (there is a creature at the point), false otherwise
     */
    public boolean pointIsOccupied(IPoint point) {
        return board.containsKey(point);
    }

    /**
     * Determines if there exists a path between two IPoints according to the specified ICreature's movement rules.
     *
     * @param creature  the specified ICreature
     * @param fromPoint the IPoint the movement starts from
     * @param toPoint   the IPoint the movement is going to
     * @return a ValidationResult describing the outcome of the pathfinding attempt. The "valid" field of the result
     * will be set to true if a valid path was found, otherwise it will be set to false and the message will be set
     * describing the issue.
     */
    public ValidationResult existsPath(ICreature creature, IPoint fromPoint, IPoint toPoint) {
        MovementRules movementRules = creature.getMovementRules();
        return pathFinder.findPath(this, creature, fromPoint, toPoint, movementRules);
    }

    /**
     * Determines if there exists any path from an IPoint according to the specified ICreature's movement rules
     *
     * @param creature  the specified ICreature
     * @param fromPoint the IPoint the movement starts from
     * @return a ValidationResult describing the outcome of the pathfinding attempt. The "valid" field of the result
     * will be set to true if a valid path was found, otherwise it will be set to false and the message will be set
     * describing the issue.
     */
    public ValidationResult existsPath(ICreature creature, IPoint fromPoint) {
        MovementRules movementRules = creature.getMovementRules();
        return pathFinder.findPath(this, creature, fromPoint, movementRules);
    }

    /**
     * Finds a creature matching the specified CreatureName and owner PlayerName on the board
     *
     * @param creatureName the CreatureName of the creature
     * @param ownerName    the PlayerName of the owner of the creature
     * @return an Optional either containing the location of the found creature, or empty if no match was found.
     */
    public Optional<IPoint> findCreature(CreatureName creatureName, PlayerName ownerName) {
        for (Map.Entry<IPoint, CreatureStack> entry : board.entrySet()) {
            IPoint point = entry.getKey();
            CreatureStack creatureStack = entry.getValue();
            Optional<ICreature> specifiedCreature = creatureStack.getCreatureWithNameAndOwner(creatureName, ownerName);

            if (specifiedCreature.isPresent()) {
                return Optional.of(point);
            }
        }

        return Optional.empty();
    }

    /**
     * Finds all creatures belonging to a player with PlayerName on board
     *
     * @param ownerName the PlayerName of the owner
     * @return a list of CreatureLocation records containing IPoints and the corresponding ICreature at that point
     */
    public List<CreatureLocation> getOwnersCreaturesAndLocations(PlayerName ownerName) {
        List<CreatureLocation> ownersCreatures = new ArrayList<>();

        for (Map.Entry<IPoint, CreatureStack> entry : board.entrySet()) {
            IPoint point = entry.getKey();
            CreatureStack creatureStack = entry.getValue();
            for (ICreature creature : creatureStack) {
                if (creature.getOwnerName() == ownerName) {
                    ownersCreatures.add(new CreatureLocation(creature, point));
                }
            }
        }

        return ownersCreatures;
    }

    /**
     * Returns true if the board is empty, false otherwise
     *
     * @return true if the board is empty (no creatures have been placed on the board), false otherwise
     */
    public boolean isEmpty() {
        return board.isEmpty();
    }
}
