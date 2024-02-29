package hexaround.game.rules.placement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;

/**
 * A Record containing information needed to evaluate a given placement
 *
 * @param board the IBoard on which the placement is occurring
 * @param player the Player who is making the placement
 * @param creatureName the CreatureName of the creature the player is placing
 * @param point the IPoint at which the placement is being made
 */
public record PlacementContext(IBoard board, Player player, CreatureName creatureName, IPoint point) {
}
