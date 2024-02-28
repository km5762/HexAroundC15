package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public record PathContext(List<IPoint> path, IBoard board, ICreature creature) {
}
