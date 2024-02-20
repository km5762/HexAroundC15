package hexaround.game.board;

import java.awt.*;

public record PathNode(Point point, int heuristic) implements Comparable<PathNode> {

    @Override
    public int compareTo(PathNode other) {
        return Integer.compare(this.heuristic, other.heuristic);
    }
}
