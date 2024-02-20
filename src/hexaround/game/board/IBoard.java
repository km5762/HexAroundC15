package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.util.Optional;

public interface IBoard {
    void placeCreature(ICreature creature, IPoint point);

    void removeCreature(CreatureName creatureName, IPoint point);

    void moveCreature(CreatureName creatureName, IPoint fromPoint, IPoint toPoint);

    Optional<ICreature> getTopCreature(IPoint point);

    CreatureStack getAllCreatures(IPoint point);

    boolean moveIsDisconnecting(CreatureName name, IPoint fromPoint, IPoint toPoint);

    boolean placementIsDisconnecting(ICreature creature, IPoint point);

    boolean creatureCanSlide(IPoint fromPoint, IPoint toPoint);
}
