package hexaround.game.board.geometry;

import java.util.ArrayList;
import java.util.List;

public record HexPoint(int x, int y) implements IPoint {
    /**
     * Get all HexPoints neighboring this HexPoint
     *
     * @return a List of all neighboring HexPoints
     */
    public List<IPoint> getNeighboringPoints() {
        List<IPoint> neighboringHexPoints = new ArrayList<>();

        for (Vector unitVector : UnitVectors.VECTORS) {
            IPoint neighboringHexPoint = new HexPoint(x + unitVector.dX(), y + unitVector.dY());
            neighboringHexPoints.add(neighboringHexPoint);
        }

        return neighboringHexPoints;
    }

    /**
     * Calculates the distance to another HexPoint
     * Adapted from: https://www.redblobgames.com/grids/hexagons/#distances
     *
     * @param toPoint another HexPoint
     * @return the distance between the two HexPoints
     */
    public int calculateDistanceTo(IPoint toPoint) {
        return (Math.abs(y - toPoint.getY())
                + Math.abs(y + x - toPoint.getY() - toPoint.getX())
                + Math.abs(x - toPoint.getX())) / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Creates a deep copy of this instance of HexPoint
     *
     * @return a deep copy of this instance of HexPoint
     */
    @Override
    public IPoint clone() {
        return new HexPoint(x, y);
    }

    /**
     * Returns true if the other IPoint has the same x and y value as this one
     *
     * @param other another instance of IPoint
     * @return true if the other IPoint has the same x and y values, false otherwise
     */
    @Override
    public boolean equals(IPoint other) {
        return x == other.getX() && y == other.getY();
    }

    /**
     * Returns true if the other IPoint is inline to this one
     *
     * @param toPoint another instance of IPoint
     * @return true if the toPoint is inline to this point (on the same x file, y file, or diagonal), false otherwise
     */
    @Override
    public boolean inlineTo(IPoint toPoint) {
        return x == toPoint.getX() || y == toPoint.getY() || -y - x == -toPoint.getX() - toPoint.getY();
    }
}
