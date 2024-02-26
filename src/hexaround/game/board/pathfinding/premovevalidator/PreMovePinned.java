package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.board.pathfinding.movevalidator.MoveConnected;
import hexaround.game.creature.ICreature;

public class PreMovePinned implements ICondition<PreMoveContext> {
    public boolean test(PreMoveContext context) {
        IBoard board = context.board();
        IPoint fromPoint = context.fromPoint();

        IBoard boardSimulation = board.clone();

        if (boardSimulation.getAllCreatures(fromPoint).getSize() > 1) {
            return false;
        }

        boardSimulation.removeAllCreatures(fromPoint);

        return !boardSimulation.isConnected();
    }
}
