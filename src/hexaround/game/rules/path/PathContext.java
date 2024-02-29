package hexaround.game.rules.path;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

/**
 * A Record containing context relating to the evaluation of a path as a whole
 *
 * @param path     a List of IPoints containing the tiles the creature has travelled upon thus far
 * @param board    the IBoard the movement is occurring on
 * @param creature the ICreature being moved
 */
public record PathContext(List<IPoint> path, IBoard board, ICreature creature) {
}
