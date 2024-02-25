package hexaround.game.board.pathfinding.pathvalidator;

import hexaround.game.board.geometry.HexPoint;
import hexaround.game.board.geometry.IPoint;

import java.util.ArrayList;
import java.util.List;

public class PathTestingUtils {
    public static List<IPoint> constructPath(IPoint[] points) {
        List<IPoint> path = new ArrayList<>();
        for (IPoint point : points) {
            path.add(point);
        }
        return path;
    }
}
