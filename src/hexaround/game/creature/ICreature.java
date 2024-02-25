package hexaround.game.creature;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.player.PlayerName;

public interface ICreature {
    CreatureName getName();

    int getMaxDistance();

    boolean hasProperty(CreatureProperty property);

    PlayerName getOwnerName();

    boolean canMove(IBoard board, IPoint fromPoint);
}
