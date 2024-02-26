package hexaround.game.board.pathfinding;

public interface ICondition<T extends Record> {
    boolean test(T context);
}
