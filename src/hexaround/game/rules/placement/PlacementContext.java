package hexaround.game.rules.placement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;

public record PlacementContext(IBoard board, Player player, CreatureName creatureName, IPoint point) {
}
