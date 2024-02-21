package hexaround.game;

import hexaround.game.board.HexPoint;
import hexaround.game.board.IBoard;
import hexaround.game.board.IPoint;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;

import java.util.Collection;
import java.util.Optional;

public class HexAroundFirstSubmission implements IHexAroundGameManager {
    protected Collection<Player> players;
    protected IBoard board;
    protected CreatureFactory creatureFactory;

    /**
     * This is the default constructor, and the only constructor
     * that you can use. The builder creates an instance using
     * this connector. You should add getters and setters as
     * necessary for any instance variables that you create and
     * will be filled in by the builder.
     */
    public HexAroundFirstSubmission() {
        // Nothing to do.
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    public void setBoard(IBoard board) {
        this.board = board;
    }

    public void setCreatureFactory(CreatureFactory creatureFactory) {
        this.creatureFactory = creatureFactory;
    }

    /**
     * Places a creature with CreatureName at the point specified by x and y
     *
     * @param creature the CreatureName of the creature to be palced
     * @param x the x coordinate to place the creature at
     * @param y the y coordinate to place the creature at
     * @return a MoveResponse describing the outcome of the placement
     */
    @Override
    public MoveResponse placeCreature(CreatureName creature, int x, int y) {
        IPoint point = new HexPoint(x, y);
        ICreature creatureInstance = creatureFactory.makeCreature(creature);
        board.placeCreature(creatureInstance, point);

        return new MoveResponse(MoveResult.OK, "Legal move");
    }

    /**
     * Moves a creature with CreatureName at the point specified by x and y
     *
     * @param creature the CreatureName of the creature to be moved
     * @param fromX the x coordinate to move the creature from
     * @param fromY the y coordinate to move the creature from
     * @param toX the x coordinate to move the creature to
     * @param toY the y coordinate to move the creature to
     * @return a MoveResponse describing the outcome of the move
     */
    @Override
    public MoveResponse moveCreature(CreatureName creature, int fromX, int fromY, int toX, int toY) {
        IPoint fromPoint = new HexPoint(fromX, fromY);
        IPoint toPoint = new HexPoint(toX, toY);

        if (board.moveIsDisconnecting(creature, fromPoint, toPoint)) {
            return new MoveResponse(MoveResult.MOVE_ERROR, "Colony is not connected, try again");
        }

        board.moveCreature(creature, fromPoint, toPoint);
        return new MoveResponse(MoveResult.OK, "Legal move");
    }
}
