package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;

public class PreMoveNotPinned implements ICondition<PreMoveContext> {

    /**
     * Used to determine prior to pathfinding if the creature being moved is "pinned" - that is, they cannot
     * move in any direction without splitting the colony.
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the creature is not pinned,
     * otherwise "valid" will be set to false with a message describing the error.
     */
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
