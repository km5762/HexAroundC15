package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board implements IBoard {
    protected Map<Point, List<ICreature>> board;

    public Board(Map<Point, List<ICreature>> board) {
        this.board = board;
    }

    /**
     * Places an instance of ICreature at the specified point
     * @param creature an instance of ICreature
     * @param x
     * @param y
     */
    public void placeCreature(ICreature creature, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        if (creaturesAtPoint.isEmpty()) {
            List<ICreature> newCreaturesAtPoint = new ArrayList<>();
            newCreaturesAtPoint.add(creature);
            board.put(new Point(x, y), newCreaturesAtPoint);
        }
    }

    /**
     * Removes creature with CreatureName from the specified point
     * @param name the CreatureName of the creature to remove
     * @param x
     * @param y
     */
    public void removeCreature(CreatureName name, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);
        Optional<ICreature> specifiedCreature = findCreatureWithName(creaturesAtPoint, name);

        if (specifiedCreature.isPresent()) {
            creaturesAtPoint.remove(specifiedCreature.get());

            if (creaturesAtPoint.isEmpty()) {
                board.remove(new Point(x, y));
            }
        }
    }

    /**
     * Moves creature with CreatureName from one point to another
     * @param name the CreatureName of the creature to move
     * @param fromX the initial x coordinate of the creature
     * @param fromY the initial y coordinate of the creature
     * @param toX the x coordinate to move the creature to
     * @param toY the y coordinate to move the creature to
     */
    public void moveCreature(CreatureName name, int fromX, int fromY, int toX, int toY) {
        Optional<ICreature> specifiedCreature = getCreatureWithName(name, fromX, fromY);

        if (specifiedCreature.isPresent()) {
            removeCreature(name, fromX, fromY);
            placeCreature(specifiedCreature.get(), toX, toY);
        }
    }

    /**
     * Returns the creature on top of the stack of creatures at the specified point
     * @param x
     * @param y
     * @return the top ICreature at point (x, y)
     */
    public Optional<ICreature> getTopCreature(int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        if (!creaturesAtPoint.isEmpty()) {
            ICreature topCreature = creaturesAtPoint.get(creaturesAtPoint.size() - 1);
            return Optional.of(topCreature);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns all creatures at the specified point
     * @param x
     * @param y
     * @return all creatures at point (x, y)
     */
    public List<ICreature> getAllCreatures(int x, int y) {
        return board.getOrDefault(new Point(x, y), Collections.emptyList());
    }

    public Optional<ICreature> getCreatureWithName(CreatureName name, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        return findCreatureWithName(creaturesAtPoint, name);
    }

    private Optional<ICreature> findCreatureWithName(List<ICreature> creatures, CreatureName name) {
        for (ICreature creature : creatures) {
            if (creature.getName() == name) {
                return Optional.of(creature);
            }
        }
        return Optional.empty();
    }

    /**
     * Source: https://www.redblobgames.com/grids/hexagons/#distances
     * Calculates the distance between two points on the board
     * @param x1 the x coordinate of the first point
     * @param y1 the y coordinate of the first point
     * @param x2 the x coordinate of the second point
     * @param y2 the y coordinate of the second point
     * @return the distance between the two points
     */
    public int calculateDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(y1 - y2)
                + Math.abs(y1 + x1 - y2 - x2)
                + Math.abs(x1 - x2)) / 2;
    }

    /**
     * Returns true if the specified move disconnects the colony, and false otherwise
     * @param name the CreatureName of the creature being moved
     * @param fromX the x coordinate the creature is being moved from
     * @param fromY the y coordinate the creauter is being moved from
     * @param toX the x coordinate the creature is being moved to
     * @param toY the y coordinate the creature is being moved to
     * @return true if the specified move is disconnecting, and false otherwise
     */
    public boolean moveIsDisconnecting(CreatureName name, int fromX, int fromY, int toX, int toY) {
        Board boardSimulation = createBoardSimulation();

        boardSimulation.moveCreature(name, fromX, fromY, toX, toY);

        return boardSimulation.isDisconnected();
    }

    private Board createBoardSimulation() {
        Map<Point, List<ICreature>> copiedBoard = new HashMap<>();

        for (Map.Entry<Point, List<ICreature>> entry : board.entrySet()) {
            Point copiedPoint = new Point(entry.getKey());
            List<ICreature> copiedCreatures = new ArrayList<>(entry.getValue());

            copiedBoard.put(copiedPoint, copiedCreatures);
        }

        return new Board(copiedBoard);
    }

    private boolean isDisconnected() {
        for (Point point : board.keySet()) {
            for (Point otherPoint : board.keySet()) {
                if (calculateDistance(point.x, point.y, otherPoint.x, otherPoint.y) > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean placementIsDisconnecting(ICreature creature, int x, int y) {
        Board boardSimulation = createBoardSimulation();

        boardSimulation.placeCreature(creature, x, y);

        return boardSimulation.isDisconnected();
    }
}
