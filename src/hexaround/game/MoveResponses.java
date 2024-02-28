package hexaround.game;

public class MoveResponses {
    static MoveResponse NO_PATH = new MoveResponse(MoveResult.MOVE_ERROR, "There exists no legal path to that point");
    static MoveResponse ILLEGAL_PLACEMENT = new MoveResponse(MoveResult.MOVE_ERROR, "This placement is not legal");
    static MoveResponse LEGAL_MOVE = new MoveResponse(MoveResult.OK, "Legal move");
    static MoveResponse CREATURE_DOES_NOT_EXIST = new MoveResponse(MoveResult.MOVE_ERROR, "A creature does not exist at that point with that name");
    static MoveResponse CREATURE_NOT_DEFINED = new MoveResponse(MoveResult.MOVE_ERROR, "A creature with that name has not been defined in the configuration");
    static MoveResponse MUST_PLACE_CREATURE_IN_OPENING = new MoveResponse(MoveResult.MOVE_ERROR, "Only placements may be made in the opening");
    static MoveResponse PLACEMENT_NOT_CONNECTED = new MoveResponse(MoveResult.MOVE_ERROR, "That placement is not connected");
    static MoveResponse PLACEMENT_NOT_EMPTY = new MoveResponse(MoveResult.MOVE_ERROR, "That placement is occupied");
    static MoveResponse PLACEMENT_NEXT_TO_ENEMY = new MoveResponse(MoveResult.MOVE_ERROR, "That placement is next to an enemy creature");
    static MoveResponse PLACEMENT_NOT_NEXT_TO_ALLY = new MoveResponse(MoveResult.MOVE_ERROR, "That placement is not next to an ally creature");
    static MoveResponse PLACEMENT_MISSING_CREATURE = new MoveResponse(MoveResult.MOVE_ERROR, "That player does not have that creature");
    static MoveResponse MUST_PLACE_BUTTERFLY = new MoveResponse(MoveResult.MOVE_ERROR, "That player has a butterfly they must place");

}
