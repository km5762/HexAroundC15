package hexaround.game;

import hexaround.game.board.CreatureLocation;
import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.IBoard;
import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.player.Player;
import hexaround.game.player.PlayerName;
import hexaround.game.rules.ValidationResult;
import hexaround.game.rules.Validator;
import hexaround.game.rules.placement.PlacementContext;
import hexaround.game.rules.placement.PostOpeningPlacementValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HexAroundGameManager implements IHexAroundGameManager {
    protected Map<PlayerName, Player> players;
    protected IBoard board;
    protected CreatureFactory creatureFactory;
    protected PlayerName nameOfPlayerWithTurn;
    protected Validator<PlacementContext> placementValidator;
    protected int turnNumber;

    /**
     * This is the default constructor, and the only constructor
     * that you can use. The builder creates an instance using
     * this connector. You should add getters and setters as
     * necessary for any instance variables that you create and
     * will be filled in by the builder.
     */
    public HexAroundGameManager() {
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

    public void setPlacementValidator(Validator<PlacementContext> placementValidator) {
        this.placementValidator = placementValidator;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * Places a creature with CreatureName at the point specified by x and y
     *
     * @param creature the CreatureName of the creature to be palced
     * @param x        the x coordinate to place the creature at
     * @param y        the y coordinate to place the creature at
     * @return a MoveResponse describing the outcome of the placement
     */
    @Override
    public MoveResponse placeCreature(CreatureName creature, int x, int y) {
        Player playerWithTurn = players.get(nameOfPlayerWithTurn);
        IPoint point = new HexPoint(x, y);

        if (!inOpening()) {
            placementValidator = new PostOpeningPlacementValidator();
        }

        if (creature != CreatureName.BUTTERFLY && mustPlaceButterfly()) {
            return MoveResponses.MUST_PLACE_BUTTERFLY;
        }

        PlacementContext placementContext = new PlacementContext(board, playerWithTurn, creature, point);
        ValidationResult placementValidation = placementValidator.validate(placementContext);
        if (!placementValidation.valid()) {
            return new MoveResponse(MoveResult.MOVE_ERROR, placementValidation.message());
        }

        Optional<ICreature> creatureInstance = creatureFactory.makeCreature(creature, nameOfPlayerWithTurn);
        if (creatureInstance.isEmpty()) {
            return MoveResponses.CREATURE_NOT_DEFINED;
        }

        playerWithTurn.decrementCreature(creature);
        board.placeCreature(creatureInstance.get(), point);

        turnNumber++;
        switchTurn();
        return MoveResponses.LEGAL_MOVE;
    }

    /**
     * Moves a creature with CreatureName at the point specified by x and y
     *
     * @param creature the CreatureName of the creature to be moved
     * @param fromX    the x coordinate to move the creature from
     * @param fromY    the y coordinate to move the creature from
     * @param toX      the x coordinate to move the creature to
     * @param toY      the y coordinate to move the creature to
     * @return a MoveResponse describing the outcome of the move
     */
    @Override
    public MoveResponse moveCreature(CreatureName creature, int fromX, int fromY, int toX, int toY) {
        IPoint fromPoint = new HexPoint(fromX, fromY);
        IPoint toPoint = new HexPoint(toX, toY);
        Optional<ICreature> specifiedCreature = board.getCreatureWithNameAndOwner(creature, nameOfPlayerWithTurn, fromPoint);

        if (inOpening()) {
            return MoveResponses.MUST_PLACE_CREATURE_IN_OPENING;
        }

        if (mustPlaceButterfly()) {
            return MoveResponses.MUST_PLACE_BUTTERFLY;
        }

        if (specifiedCreature.isEmpty()) {
            return MoveResponses.CREATURE_DOES_NOT_EXIST;
        }

        if (!board.existsPath(specifiedCreature.get(), fromPoint, toPoint)) {
            return MoveResponses.NO_PATH;
        }

        performCreatureMovement(specifiedCreature.get(), fromPoint, toPoint);

        turnNumber++;
        switchTurn();
        return MoveResponses.LEGAL_MOVE;
    }

    private void switchTurn() {
        nameOfPlayerWithTurn = getNameOfPlayerOffTurn();
    }

    private PlayerName getNameOfPlayerOffTurn() {
        return (nameOfPlayerWithTurn == PlayerName.RED) ? PlayerName.BLUE : PlayerName.RED;
    }

    private void returnCreature(ICreature creature) {
        PlayerName ownerName = creature.getOwnerName();
        Player owner = players.get(ownerName);
        owner.incrementCreature(creature.getName());
    }

    private void performCreatureMovement(ICreature creature, IPoint fromPoint, IPoint toPoint) {

        if (creature.hasProperty(CreatureProperty.KAMIKAZE)) {
            Optional<ICreature> removedCreature = board.getTopCreature(toPoint);

            if (removedCreature.isPresent()) {
                board.removeCreature(creature, fromPoint);
                board.removeCreature(removedCreature.get(), toPoint);
                returnCreature(removedCreature.get());
            } else {
                board.moveCreature(creature, fromPoint, toPoint);
            }
        } else {
            board.removeCreature(creature, fromPoint);
            if (creature.hasProperty(CreatureProperty.SWAPPING)) {
                Optional<ICreature> swappedCreature = board.getTopCreature(toPoint);
                board.placeCreature(creature, toPoint);
                if (swappedCreature.isPresent()) {
                    board.moveCreature(swappedCreature.get(), toPoint, fromPoint);
                }
            } else {
                board.placeCreature(creature, toPoint);
            }
        }
    }

    private MoveResult evaluateGameState() {
        boolean blueButterflySurrounded = playersButterflySurrounded(PlayerName.BLUE);
        boolean redButterflySurrounded = playersButterflySurrounded(PlayerName.RED);

        boolean bluePlayerStuck = playerHasMoves(PlayerName.BLUE) || playerHasPlacements(PlayerName.BLUE);
        boolean redPlayerStuck = playerHasMoves(PlayerName.RED) || playerHasPlacements(PlayerName.RED);

        boolean blueWon = redButterflySurrounded && !blueButterflySurrounded || redPlayerStuck && !bluePlayerStuck;
        boolean redWon = blueButterflySurrounded && !redButterflySurrounded || bluePlayerStuck && !redPlayerStuck;
        boolean draw = blueButterflySurrounded && redButterflySurrounded || bluePlayerStuck && redPlayerStuck;

        MoveResult gameState = null;
        if (blueWon) {
            gameState = MoveResult.BLUE_WON;
        } else if (redWon) {
            gameState = MoveResult.RED_WON;
        } else if (draw) {
            gameState = MoveResult.DRAW;
        }

        return gameState;
    }

    private boolean playersButterflySurrounded(PlayerName playerName) {
        Optional<IPoint> butterflyLocation = board.findCreature(CreatureName.BUTTERFLY, playerName);
        boolean butterflySurrounded = false;

        if (butterflyLocation.isPresent()) {
            butterflySurrounded = board.isSurrounded(butterflyLocation.get());
        }

        return butterflySurrounded;
    }

    private boolean playerHasMoves(PlayerName playerName) {
        List<CreatureLocation> creatureLocations = board.getOwnersCreaturesAndLocations(playerName);

        for (CreatureLocation creatureLocation : creatureLocations) {
            ICreature creature = creatureLocation.creature();
            IPoint point = creatureLocation.point();

            if (board.existsPath(creature, point)) {
                return true;
            }
        }
        return false;
    }

    private boolean playerHasPlacements(PlayerName playerName) {
        Player player = players.get(playerName);

        if (player.outOfCreatures()) {
            return false;
        }

        List<CreatureLocation> creatureLocations = board.getOwnersCreaturesAndLocations(playerName);

        for (CreatureLocation creatureLocation : creatureLocations) {
            IPoint point = creatureLocation.point();

            for (IPoint neighboringPoint : point.getNeighboringPoints()) {
                PlacementContext context = new PlacementContext(board, player, null, neighboringPoint);
                if (placementValidator.validate(context).valid()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inOpening() {
        return turnNumber < 2;
    }

    private boolean mustPlaceButterfly() {
        Player playerWithTurn = players.get(nameOfPlayerWithTurn);
        boolean hasButterfly = playerWithTurn.getCreatureCount(CreatureName.BUTTERFLY) > 0;
        boolean pastFourthTurn = turnNumber > 7;

        return hasButterfly && pastFourthTurn;
    }
}
