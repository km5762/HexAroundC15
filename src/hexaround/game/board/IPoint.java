package hexaround.game.board;

import java.util.ArrayList;
import java.util.List;

public interface IPoint {
    List<IPoint> getNeighboringPoints();

    int calculateDistanceTo(IPoint toPoint);

    int getX();

    int getY();

    IPoint clone();
}
