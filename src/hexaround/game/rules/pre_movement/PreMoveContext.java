package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

/**
 * A Record containing information needed to evaluate a move prior to any pathfinding
 *
 * @param board     the IBoard on which the movement is occurring
 * @param creature  the ICreature being moved
 * @param fromPoint the IPoint the creature is being moved from
 * @param toPoint   the IPoint the creature is being moved to. May be null if any path is being found,
 *                  rather than a path to a specific point.
 */
public record PreMoveContext(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
}
