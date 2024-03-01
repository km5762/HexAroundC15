package hexaround.game.rules.pre_movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.movement.MoveConnected;
import hexaround.game.rules.movement.MoveContext;

public class PreMoveDestinationConnected implements ICondition<PreMoveContext> {
    ICondition<MoveContext> moveConnected = new MoveConnected();

    /**
     * Used to determine prior to pathfinding if the destination is connected
     *
     * @param context a PreMoveContext describing the context of the situation
     * @return a ValidationResult with "valid" set to true if the destination is connected,
     * otherwise "valid" will be set to false with a message describing the error.
     */
    @Override
    public ValidationResult test(PreMoveContext context) {
        IBoard board = context.board();
        ICreature creature = context.creature();
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();
        MoveContext moveContext = new MoveContext(board, creature, fromPoint, fromPoint, toPoint);
        ValidationResult result = new ValidationResult(true, null);

        if (toPoint != null) {
            result = moveConnected.test(moveContext);
        }

        return result;
    }
}
