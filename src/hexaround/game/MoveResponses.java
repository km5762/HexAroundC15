package hexaround.game;

public class MoveResponses {
    static MoveResponse DISCONNECTING_MOVE = new MoveResponse(MoveResult.MOVE_ERROR, "Colony is not connected, try again");
    static MoveResponse LEGAL_MOVE = new MoveResponse(MoveResult.OK, "Legal move");
    static MoveResponse CREATURE_DOES_NOT_EXIST = new MoveResponse(MoveResult.MOVE_ERROR, "A creature does not exist at that point with that name");
    static MoveResponse CREATURE_NOT_DEFINED = new MoveResponse(MoveResult.MOVE_ERROR, "A creature with that name has not been defined in the configuration");
}
