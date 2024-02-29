package hexaround.game.rules.movement;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.rules.ICondition;
import hexaround.game.creature.ICreature;
import hexaround.game.rules.ValidationResult;

public class MoveConnected implements ICondition<MoveContext> {
    public ValidationResult test(MoveContext context) {
        IBoard board = context.board();
        ICreature creature = context.creature();
        IPoint fromPoint = context.fromPoint();
        IPoint toPoint = context.toPoint();
        ValidationResult result = new ValidationResult(true, null);

        if (!moveIsConnected(board, creature, fromPoint, toPoint)) {
            result = new ValidationResult(false, "That destination is disconnected");
        }

        return result;
    }

    private boolean moveIsConnected(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        IBoard boardSimulation = board.clone();
        boardSimulation.moveCreature(creature, fromPoint, toPoint);

        return boardSimulation.isConnected();
    }
}
