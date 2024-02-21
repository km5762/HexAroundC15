package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.awt.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Board implements IBoard {
    protected Map<IPoint, CreatureStack> board;

    public Board(Map<IPoint, CreatureStack> board) {
        this.board = board;
    }

    /**
     * Places an instance of ICreature at the specified IPoint
     *
     * @param creature an instance of ICreature
     * @param point    the specified IPoint
     */
    public void placeCreature(ICreature creature, IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);

        if (creaturesAtPoint.isEmpty()) {
            creaturesAtPoint.addCreature(creature);
            board.put(point, creaturesAtPoint);
        } else {
            creaturesAtPoint.addCreature(creature);
        }
    }

    /**
     * Removes creature with CreatureName from the specified IPoint
     *
     * @param creatureName the CreatureName of the creature to remove
     * @param point        the specified IPoint
     */
    public void removeCreature(CreatureName creatureName, IPoint point) {
        CreatureStack creaturesAtPoint = getAllCreatures(point);
        creaturesAtPoint.removeCreature(creatureName);

        if (creaturesAtPoint.isEmpty()) {
            board.remove(point);
        }
    }

    /**
     * Moves creature with CreatureName from one IPoint to another
     *
     * @param creatureName the CreatureName of the creature to move
     * @param fromPoint    the IPoint the creature is currently at
     * @param fromPoint    the IPoint to move the creature to
     */
    @Override
    public void moveCreature(CreatureName creatureName, IPoint fromPoint, IPoint toPoint) {
        CreatureStack creaturesAtFromPoint = getAllCreatures(fromPoint);
        Optional<ICreature> creatureWithName = creaturesAtFromPoint.getCreatureWithName(creatureName);

        if (creatureWithName.isPresent()) {
            removeCreature(creatureName, fromPoint);
            placeCreature(creatureWithName.get(), toPoint);
        }
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

    private int getTotalCreatureStacks() {
        return board.size();
    }

    /**
     * Returns true if the specified move disconnects the colony, and false otherwise
     *
     * @param name      the CreatureName of the creature being moved
     * @param fromPoint the IPoint the creature is currently at
     * @param fromPoint the IPoint to move the creature to
     * @return true if the specified move is disconnecting, and false otherwise
     */
    public boolean moveIsDisconnecting(CreatureName name, IPoint fromPoint, IPoint toPoint) {
        Board boardSimulation = createBoardSimulation();
        boardSimulation.moveCreature(name, fromPoint, toPoint);

        if (boardSimulation.getTotalCreatureStacks() <= 1) {
            return false;
        }

        return !boardSimulation.isConnected();
    }

    /**
     * Returns true if the board is disconnected
     *
     * @return true if the board is disconnected, false otherwise
     */
    private boolean isConnected() {
        Set<IPoint> occupiedPoints = board.keySet();
        Set<IPoint> visitedPoints = new HashSet<>();
        Queue<IPoint> pointQueue = new LinkedList<>();
        IPoint firstPoint = board.keySet().iterator().next();
        pointQueue.add(firstPoint);

        while (!pointQueue.isEmpty()) {
            IPoint currentPoint = pointQueue.poll();

            for (IPoint neighboringPoint : currentPoint.getNeighboringPoints()) {
                if (!visitedPoints.contains(neighboringPoint) && !getAllCreatures(neighboringPoint).isEmpty()) {
                    visitedPoints.add(neighboringPoint);
                    pointQueue.add(neighboringPoint);
                }
            }
        }

        return visitedPoints.containsAll(occupiedPoints);
    }

    private Board createBoardSimulation() {
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
            CreatureStack neighboringCreatures = getAllCreatures(neighboringPoint);
            if (!neighboringCreatures.isEmpty()) {
                occupiedNeighboringPoints.add(neighboringPoint);
            }
        }

        return occupiedNeighboringPoints;
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

        Board boardSimulation = createBoardSimulation();
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

    /**
     * Finds all possible valid path lengths from one IPoint to another
     *
     * Source: https://www.geeksforgeeks.org/print-paths-given-source-destination-using-bfs/
     *
     * @param fromPoint the starting IPoint
     * @param toPoint   the target IPoint
     * @return a List containing Integers, each of which represent a valid path length.
     */
    public List<Integer> findPathLengths(IPoint fromPoint, IPoint toPoint) {
        Queue<List<IPoint>> pathQueue = new LinkedList<>();
        List<Integer> pathLengths = new ArrayList<>();

        List<IPoint> startPath = new ArrayList<>();
        startPath.add(fromPoint);
        pathQueue.add(startPath);

        while (!pathQueue.isEmpty()) {
            List<IPoint> currentPath = pathQueue.poll();
            IPoint lastPoint = currentPath.get(currentPath.size() - 1);

            if (lastPoint.equals(toPoint)) {
                pathLengths.add(currentPath.size() - 1);
                System.out.println(currentPath);
            }

            for (IPoint neighboringPoint : lastPoint.getNeighboringPoints()) {
                if (!currentPath.contains(neighboringPoint)
                        && !getOccupiedNeighboringPoints(neighboringPoint).isEmpty()
                        && getAllCreatures(neighboringPoint).isEmpty()) {
                    List<IPoint> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighboringPoint);
                    pathQueue.add(newPath);
                }
            }
        }

        return pathLengths;
    }
}
