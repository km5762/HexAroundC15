package hexaround.game.board;

import hexaround.game.creature.ICreature;

public interface IBoard {
    void put(ICreature creature, int x, int y);
    ICreature get(int x, int y);
}
