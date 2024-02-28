package hexaround.game.creature;

import hexaround.game.rules.MovementRules;
import hexaround.game.player.PlayerName;

public interface ICreature {
    CreatureName getName();

    int getMaxDistance();

    boolean hasProperty(CreatureProperty property);

    PlayerName getOwnerName();

    MovementRules getMovementRules();
}
