package hexaround.game.board;

public class UnitVectors {
    protected static final Vector[] vectors = new Vector[]{
            new Vector(0, 1),
            new Vector(1, 0),
            new Vector(1, -1),
            new Vector(0, -1),
            new Vector(-1, 0),
            new Vector(-1, 1),
    };

    public Vector[] getVectors() {
        return vectors;
    }
}
