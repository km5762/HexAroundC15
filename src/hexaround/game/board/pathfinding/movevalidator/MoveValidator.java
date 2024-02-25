package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class MoveValidator implements IMoveValidator {
    protected List<IMoveCondition> conditions;

    public MoveValidator(List<IMoveCondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean validate(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        for (IMoveCondition condition : conditions) {
            if (!condition.test(board, creature, fromPoint, toPoint)) {
                return false;
            }
        }
        return true;
    }
}
