package hexaround.game;

import hexaround.game.board.CreatureStack;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;
import hexaround.game.player.PlayerName;

import java.util.Map;
import java.util.Optional;

public class HexAroundFirstSubmission implements IHexAroundGameManager {
    protected Map<PlayerName, Player> players;
    protected IBoard board;
    protected CreatureFactory creatureFactory;
    protected PlayerName nameOfPlayerWithTurn;

    /**
     * This is the default constructor, and the only constructor
     * that you can use. The builder creates an instance using
     * this connector. You should add getters and setters as
     * necessary for any instance variables that you create and
     * will be filled in by the builder.
     */
    public HexAroundFirstSubmission() {
    }

    public void setPlayers(Map<PlayerName, Player> players) {
        this.players = players;
    }

    public void setBoard(IBoard board) {
        this.board = board;
    }

    public void setCreatureFactory(CreatureFactory creatureFactory) {
        this.creatureFactory = creatureFactory;
    }

    public void setNameOfPlayerWithTurn(PlayerName nameOfPlayerWithTurn) {
        this.nameOfPlayerWithTurn = nameOfPlayerWithTurn;
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
        Player playerWithTurn = players.get(nameOfPlayerWithTurn);
        IPoint point = new HexPoint(x, y);
        Optional<ICreature> creatureInstance = creatureFactory.makeCreature(creature, nameOfPlayerWithTurn);

        if (creatureInstance.isEmpty()) {
            return MoveResponses.CREATURE_NOT_DEFINED;
        }

        playerWithTurn.decrementCreature(creature);

        board.placeCreature(creatureInstance.get(), point);
        switchTurn();
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
        Optional<ICreature> specifiedCreature = board.getCreatureWithNameAndOwner(creature, nameOfPlayerWithTurn, fromPoint);

        if (specifiedCreature.isEmpty()) {
            return MoveResponses.CREATURE_DOES_NOT_EXIST;
        }

        if (specifiedCreature.get().hasProperty(CreatureProperty.KAMIKAZE)) {
            CreatureStack affectedCreatures = board.getAllCreatures(toPoint);
            returnCreatures(affectedCreatures, getNameOfPlayerOffTurn());
        }

        board.moveCreature(specifiedCreature.get(), fromPoint, toPoint);

        switchTurn();
        return MoveResponses.LEGAL_MOVE;
    }

    private void switchTurn() {
        nameOfPlayerWithTurn = getNameOfPlayerOffTurn();
    }

    private PlayerName getNameOfPlayerOffTurn() {
        return (nameOfPlayerWithTurn == PlayerName.RED) ? PlayerName.BLUE : PlayerName.RED;
    }

    private void returnCreatures(CreatureStack creatures, PlayerName ownerName) {
        for (ICreature creature : creatures) {
            if (creature.getOwnerName() == ownerName) {
                Player owner = players.get(ownerName);
                owner.incrementCreature(creature.getName());
            }
        }
    }
}
