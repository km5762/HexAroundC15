package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.pathfinding.ICondition;

public class MoveInline implements ICondition<MoveContext> {

    @Override
    public boolean test(MoveContext context) {
        return context.fromPoint().inlineTo(context.toPoint());
    }
}
