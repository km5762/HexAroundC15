package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.util.List;

public interface IBoard {
    void placeCreature(ICreature creature, int x, int y);

    void removeCreature(CreatureName name, int x, int y);

    void moveCreature(CreatureName name, int fromX, int fromY, int toX, int toY);

    List<ICreature> getAllCreatures(int x, int y);

    ICreature getCreatureWithName(CreatureName name, int x, int y);

    ICreature getTopCreature(int x, int y);

}
