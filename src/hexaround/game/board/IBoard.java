package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import hexaround.game.player.PlayerName;

import java.util.List;
import java.util.Optional;

public interface IBoard {
    void placeCreature(ICreature creature, IPoint point);

    void removeCreature(ICreature creature, IPoint point);

    void moveCreature(ICreature creature, IPoint fromPoint, IPoint toPoint);

    Optional<ICreature> getTopCreature(IPoint point);

    CreatureStack getAllCreatures(IPoint point);

    Optional<ICreature> getCreatureWithNameAndOwner(CreatureName creatureName, PlayerName ownerName, IPoint point);

    List<IPoint> getOccupiedNeighboringPoints(IPoint point);

    boolean placementIsDisconnecting(ICreature creature, IPoint point);

    boolean creatureCanSlide(IPoint fromPoint, IPoint toPoint);

    boolean pointIsOccupied(IPoint point);

    IBoard clone();

    boolean isConnected();

    boolean existsPath(ICreature creature, IPoint fromPoint, IPoint toPoint);

    boolean existsPath(ICreature creature, IPoint fromPoint);
}
