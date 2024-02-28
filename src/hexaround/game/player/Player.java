package hexaround.game.player;

import hexaround.game.creature.CreatureName;

import java.util.Map;

public class Player {
    protected PlayerName name;
    protected Map<CreatureName, Integer> creatureCounts;

    /**
     * Constructs an instance of Player
     * @param name the Player's name
     * @param creatureCounts a Map representing how many Creature's the Player starts with, where
     *                  each CreatureName is mapped to its count.
     */
    public Player(PlayerName name, Map<CreatureName, Integer> creatureCounts) {
        this.name = name;
        this.creatureCounts = creatureCounts;
    }

    public int getCreatureCount(CreatureName creatureName) {
        return creatureCounts.getOrDefault(creatureName, 0);
    }

    public boolean outOfCreatures() {
        for (int count : creatureCounts.values()) {
            if (count > 0) {
                return false;
            }
        }
        return true;
    }

    public PlayerName getName() {
        return name;
    }

    /**
     * Decrement the count for CreatureName
     *
     * @param creatureName the name of the creature to decrement
     */
    public void decrementCreature(CreatureName creatureName) {
        if (creatureCounts.containsKey(creatureName)) {
            int creatureCount = creatureCounts.get(creatureName);
            creatureCounts.put(creatureName, creatureCount - 1);
        }
    }

    /**
     * Increment the count for CreatureName
     *
     * @param creatureName the name of the creature to increment
     */
    public void incrementCreature(CreatureName creatureName) {
        if (creatureCounts.containsKey(creatureName)) {
            int creatureCount = creatureCounts.get(creatureName);
            creatureCounts.put(creatureName, creatureCount + 1);
        }
    }
}
