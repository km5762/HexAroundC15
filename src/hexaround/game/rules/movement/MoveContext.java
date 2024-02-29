package hexaround.game.rules.movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

/**
 * A Record containing context pertaining to a movement taken while travelling to a destination
 *
 * @param board       the IBoard the movement is occurring on
 * @param creature    the ICreature being moved
 * @param originPoint the IPoint the path started from
 * @param fromPoint   the IPoint the creature is moving from
 * @param toPoint     the candidate IPoint the creature is evaluating a move to
 */
public record MoveContext(IBoard board, ICreature creature, IPoint originPoint, IPoint fromPoint, IPoint toPoint) {
}
