package hexaround.game.board;

import hexaround.game.creature.ICreature;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Board implements IBoard {
    protected Map<Point, ICreature> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public void put(ICreature creature, int x, int y) {
        board.put(new Point(x, y), creature);
    }

    public ICreature get(int x, int y) {
        return board.getOrDefault(new Point(x, y), null);
    }
}
