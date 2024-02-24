package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public interface IPathValidator {
    boolean validate(List<IPoint> path, ICreature creature, IPoint targetPoint);
}
