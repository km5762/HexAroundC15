package hexaround.game.board.geometry;

public class UnitVectors {
    private static final Vector[] VECTORS = new Vector[]{
            new Vector(0, 1),
            new Vector(1, 0),
            new Vector(1, -1),
            new Vector(0, -1),
            new Vector(-1, 0),
            new Vector(-1, 1),
    };

    public Vector[] getVectors() {
        return VECTORS;
    }
}
