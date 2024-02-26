package hexaround.game.board.pathfinding.movevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.creature.ICreature;

public class MoveConnected implements ICondition<MoveContext> {
    public boolean test(MoveContext context) {
        return moveIsConnected(context.board(), context.creature(), context.fromPoint(), context.toPoint());
    }

    private boolean moveIsConnected(IBoard board, ICreature creature, IPoint fromPoint, IPoint toPoint) {
        IBoard boardSimulation = board.clone();
        boardSimulation.moveCreature(creature, fromPoint, toPoint);

        return boardSimulation.isConnected();
    }
}
