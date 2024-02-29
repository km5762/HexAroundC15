package hexaround.game.player;

import hexaround.game.creature.CreatureName;

import java.util.Map;

public class Player {
    protected PlayerName name;
    protected Map<CreatureName, Integer> creatureCounts;

    /**
     * Constructs an instance of Player
     *
     * @param name           the Player's name
     * @param creatureCounts a Map representing how many Creature's the Player starts with, where
     *                       each CreatureName is mapped to its count.
     */
    public Player(PlayerName name, Map<CreatureName, Integer> creatureCounts) {
        this.name = name;
        this.creatureCounts = creatureCounts;
    }

    /**
     * Gets the number of creatures with CreatureName this Player has
     *
     * @param creatureName the specified CreatureName
     * @return the number of that creature this Player has
     */
    public int getCreatureCount(CreatureName creatureName) {
        return creatureCounts.getOrDefault(creatureName, 0);
    }

    /**
     * Returns true if this Player is out of creatures
     *
     * @return true if this Player is out of creatures (all of their creature's counts are 0), false otherwise
     */
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
