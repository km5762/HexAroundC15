package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;
import hexaround.game.player.PlayerName;
import hexaround.game.rules.ValidationResult;

import java.util.List;
import java.util.Optional;

public interface IBoard {
    void placeCreature(ICreature creature, IPoint point);

    void removeCreature(ICreature creature, IPoint point);

    void moveCreature(ICreature creature, IPoint fromPoint, IPoint toPoint);

    Optional<ICreature> getTopCreature(IPoint point);

    CreatureStack getAllCreatures(IPoint point);

    Optional<ICreature> getCreatureWithNameAndOwner(CreatureName creatureName, PlayerName ownerName, IPoint point);

    boolean pointIsOccupied(IPoint point);

    boolean isSurrounded(IPoint point);

    IBoard clone();

    void removeAllCreatures(IPoint point);

    boolean isConnected();

    ValidationResult existsPath(ICreature creature, IPoint fromPoint, IPoint toPoint);

    ValidationResult existsPath(ICreature creature, IPoint fromPoint);

    Optional<IPoint> findCreature(CreatureName creatureName, PlayerName ownerName);

    List<CreatureLocation> getOwnersCreaturesAndLocations(PlayerName ownerName);

    boolean isEmpty();
}
