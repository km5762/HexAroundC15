package hexaround.game.board.geometry;

import java.util.List;

public interface IPoint {
    List<IPoint> getNeighboringPoints();

    int calculateDistanceTo(IPoint toPoint);

    int getX();

    int getY();

    IPoint clone();

    boolean equals(IPoint other);
}
