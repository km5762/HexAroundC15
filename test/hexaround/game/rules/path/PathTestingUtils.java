package hexaround.game.rules.path;

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
