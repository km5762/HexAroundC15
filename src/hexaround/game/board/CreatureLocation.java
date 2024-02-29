package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

/**
 * Constructs an instance of CreatureLocation
 * @param creature the ICreature at point
 * @param point the IPoint the creature is at
 */
public record CreatureLocation(ICreature creature, IPoint point) {
}
