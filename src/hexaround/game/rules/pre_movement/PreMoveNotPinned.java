package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveNotPinned implements ICondition<PreMoveContext> {
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint fromPoint = context.fromPoint();
        IBoard boardSimulation = board.clone();
        ValidationResult result = new ValidationResult(true, null);

        if (boardSimulation.getAllCreatures(fromPoint).getSize() < 2) {
            boardSimulation.removeAllCreatures(fromPoint);

            if (!boardSimulation.isConnected()) {
                result = new ValidationResult(false, "This creature cannot move without disconnecting the colony");
            }
        }

        return result;
    }
}
