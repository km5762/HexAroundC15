package hexaround.game.board;

import hexaround.game.board.geometry.IPoint;
import hexaround.game.creature.Creature;
import hexaround.game.creature.CreatureName;
import hexaround.game.creature.CreatureProperty;
import hexaround.game.creature.ICreature;
import hexaround.game.player.PlayerName;

import java.util.Collections;

public class BoardTestingUtils {
    public static void placeCreatures(ICreature creature, IPoint[] points, IBoard board) {
        for (IPoint atPoint : points) {
            board.placeCreature(creature, atPoint);
        }
    }
}
