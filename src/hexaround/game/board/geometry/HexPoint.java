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
    
    @Override
    public boolean equals(IPoint other) {
        return x == other.getX() && y == other.getY();
    }

    @Override
    public boolean inlineTo(IPoint toPoint) {
        return x == toPoint.getX() || y == toPoint.getY() || -y - x == -toPoint.getX() - toPoint.getY();
    }
}
