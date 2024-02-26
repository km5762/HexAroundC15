package hexaround.game.board.pathfinding.movevalidator;

import java.util.List;

public class TestMoveValidator extends MoveValidator {

    public TestMoveValidator(List<IMoveCondition> conditions) {
        super(conditions);
    }

    public List<IMoveCondition> getConditions() {
        return this.conditions;
    }
}
