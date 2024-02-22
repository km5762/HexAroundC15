package hexaround.game.creature;

import hexaround.game.player.PlayerName;

public interface ICreature {
    CreatureName getName();

    int getMaxDistance();

    boolean hasProperty(CreatureProperty property);

    PlayerName getOwnerName();
}
