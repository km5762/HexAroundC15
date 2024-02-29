package hexaround.game.board.geometry;

/**
 * A utility class containing the directional unit vectors on a hex grid
 */
public class UnitVectors {
    public static final Vector[] VECTORS = new Vector[]{
            new Vector(0, 1),
            new Vector(1, 0),
            new Vector(1, -1),
            new Vector(0, -1),
            new Vector(-1, 0),
            new Vector(-1, 1),
    };
}
