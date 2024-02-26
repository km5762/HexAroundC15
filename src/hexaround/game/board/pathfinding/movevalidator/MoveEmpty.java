package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.pathfinding.ICondition;

public class MoveEmpty implements ICondition<MoveContext> {
    public boolean test(MoveContext context) {
        return !context.board().pointIsOccupied(context.toPoint());
    }
}
