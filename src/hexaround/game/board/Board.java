package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board implements IBoard {
    protected Map<Point, List<ICreature>> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public void placeCreature(ICreature creature, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        if (creaturesAtPoint.isEmpty()) {
            List<ICreature> newCreaturesAtPoint = new ArrayList<>();
            newCreaturesAtPoint.add(creature);
            board.put(new Point(x, y), newCreaturesAtPoint);
        } else {
            creaturesAtPoint.add(creature);
        }
    }

    public void removeCreature(CreatureName name, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);
        ICreature specifiedCreature = findCreatureWithName(creaturesAtPoint, name);

        if (specifiedCreature != null) {
            creaturesAtPoint.remove(specifiedCreature);

            if (creaturesAtPoint.isEmpty()) {
                board.remove(new Point(x, y));
            }
        }
    }

    public void moveCreature(CreatureName name, int fromX, int fromY, int toX, int toY) {
        ICreature specifiedCreature = getCreatureWithName(name, fromX, fromY);

        if (specifiedCreature != null) {
            removeCreature(name, fromX, fromY);
            placeCreature(specifiedCreature, toX, toY);
        }
    }

    public ICreature getTopCreature(int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        if (!creaturesAtPoint.isEmpty()) {
            return creaturesAtPoint.get(creaturesAtPoint.size() - 1);
        } else {
            return null;
        }
    }

    public List<ICreature> getAllCreatures(int x, int y) {
        return board.getOrDefault(new Point(x, y), Collections.emptyList());
    }

    public ICreature getCreatureWithName(CreatureName name, int x, int y) {
        List<ICreature> creaturesAtPoint = getAllCreatures(x, y);

        return findCreatureWithName(creaturesAtPoint, name);
    }

    private ICreature findCreatureWithName(List<ICreature> creatures, CreatureName name) {
        for (ICreature creature : creatures) {
            if (creature.getName() == name) {
                return creature;
            }
        }
        return null;
    }
}
