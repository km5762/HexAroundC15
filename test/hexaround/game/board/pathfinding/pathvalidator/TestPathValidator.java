package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.pathfinding.movevalidator.IMoveCondition;

import java.util.List;

public class TestPathValidator extends PathValidator {

    public TestPathValidator(List<IPathCondition> conditions) {
        super(conditions);
    }

    public List<IPathCondition> getConditions() {
        return this.conditions;
    }
}
