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

    public void placeCreature(ICreature creature, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        if (creaturesAtPoint.isEmpty()) {
            List<ICreature> newCreaturesAtPoint = new ArrayList<>();
            newCreaturesAtPoint.add(creature);
            board.put(new Point(x, y), newCreaturesAtPoint);
        }
    }

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

    public void moveCreature(CreatureName name, int fromX, int fromY, int toX, int toY) {
        Optional<ICreature> specifiedCreature = getCreatureWithName(name, fromX, fromY);

        if (specifiedCreature.isPresent()) {
            removeCreature(name, fromX, fromY);
            placeCreature(specifiedCreature.get(), toX, toY);
        }
    }

    public Optional<ICreature> getTopCreature(int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        if (!creaturesAtPoint.isEmpty()) {
            ICreature topCreature = creaturesAtPoint.get(creaturesAtPoint.size() - 1);
            return Optional.of(topCreature);
        } else {
            return Optional.empty();
        }
    }

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

    public boolean pointIsConnected(int x, int y) {
        for (Point point : board.keySet()) {
            if (calculateDistance(point.x, point.y, x, y) <= 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Source: https://www.redblobgames.com/grids/hexagons/#distances
     */
    public int calculateDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(y1 - y2)
                + Math.abs(y1 + x1 - y2 - x2)
                + Math.abs(x1 - x2)) / 2;
    }
}
