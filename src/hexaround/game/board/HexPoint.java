package hexaround.game.board;

import java.util.ArrayList;
import java.util.List;

public record HexPoint(int x, int y) implements IPoint {
    protected static final UnitVectors unitVectors = new UnitVectors();

    public List<IPoint> getNeighboringPoints() {
        List<IPoint> neighboringHexPoints = new ArrayList<>();

        for (Vector unitVector : unitVectors.getVectors()) {
            IPoint neighboringHexPoint = new HexPoint(x + unitVector.dX(), y + unitVector.dY());
            neighboringHexPoints.add(neighboringHexPoint);
        }

        return neighboringHexPoints;
    }
    
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

    @Override
    public IPoint clone() {
        return new HexPoint(x, y);
    }
}
