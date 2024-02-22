package hexaround.game.player;

import hexaround.game.creature.CreatureName;

import java.util.Map;

public class TestPlayer extends Player {
    public TestPlayer(PlayerName name, Map<CreatureName, Integer> creatureCounts) {
        super(name, creatureCounts);
    }

    public Map<CreatureName, Integer> getCreatureCounts() {
        return creatureCounts;
    }
}
