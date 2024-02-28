package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

public record CreatureLocation(ICreature creature, IPoint point) {
}
