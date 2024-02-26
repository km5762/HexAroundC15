package hexaround.game.creature;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.MovementRules;
import hexaround.game.player.PlayerName;

public interface ICreature {
    CreatureName getName();

    int getMaxDistance();

    boolean hasProperty(CreatureProperty property);

    PlayerName getOwnerName();

    MovementRules getMovementRules();
}
