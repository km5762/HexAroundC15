package hexaround.game.board.pathfinding;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;

import java.util.List;

public class Validator<T extends Record> {
    protected List<ICondition<T>> conditions;

    public Validator (List<ICondition<T>> conditions) {
        this.conditions = conditions;
    }
    public boolean validate(T context) {
        for (ICondition<T> condition : conditions) {
            if (!condition.test(context)) {
                return false;
            }
        }
        return true;
    }
}
