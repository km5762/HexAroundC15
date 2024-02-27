package hexaround.game.board.pathfinding.premovevalidator;

import hexaround.game.board.CreatureStack;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.board.pathfinding.ICondition;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;

import java.util.ArrayList;
import java.util.List;

public class PreMoveTrapped implements ICondition<PreMoveContext> {

    @Override
    public boolean test(PreMoveContext context) {
        IPoint fromPoint = context.fromPoint();
        IBoard board = context.board();
        ICreature creature = context.creature();

        for (ICreature aboveCreature : getAboveCreatures(board, creature, fromPoint)) {
            if (aboveCreature.hasProperty(CreatureProperty.TRAPPING)) {
                return true;
            }
        }

        return false;
    }

    private List<ICreature> getAboveCreatures(IBoard board, ICreature creature, IPoint point) {
        CreatureStack creatureStack = board.getAllCreatures(point);
        List<ICreature> aboveCreatures = new ArrayList<>();
        boolean above = false;

        for (ICreature stackCreature : creatureStack) {
            if (above) {
                aboveCreatures.add(stackCreature);
            }

            if (stackCreature.equals(creature)) {
                above = true;
            }
        }

        return aboveCreatures;
    }
}
