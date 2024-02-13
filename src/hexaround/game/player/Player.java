package hexaround.game.player;

import hexaround.game.creature.CreatureName;

import java.util.Map;

public class Player {
    protected PlayerName name;
    protected Map<CreatureName, Integer> creatures;

    /**
     * Constructs an instance of Player
     * @param name the Player's name
     * @param creatures a Map representing how many Creature's the Player starts with, where
     *                  each CreatureName is mapped to its count.
     */
    public Player(PlayerName name, Map<CreatureName, Integer> creatures) {
        this.name = name;
        this.creatures = creatures;
    }
}
