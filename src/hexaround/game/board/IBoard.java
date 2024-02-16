package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.util.List;
import java.util.Optional;

public interface IBoard {
    void placeCreature(ICreature creature, int x, int y);

    void removeCreature(CreatureName name, int x, int y);

    void moveCreature(CreatureName name, int fromX, int fromY, int toX, int toY);

    List<ICreature> getAllCreatures(int x, int y);

    Optional<ICreature> getCreatureWithName(CreatureName name, int x, int y);

    Optional<ICreature> getTopCreature(int x, int y);

    boolean pointIsConnected(int x, int y);

    int calculateDistance(int x1, int y1, int x2, int y2);

}
