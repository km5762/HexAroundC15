package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreatureStack {
    protected List<ICreature> creatureStack;

    public CreatureStack() {
        this.creatureStack = new ArrayList<>();
    }

    public CreatureStack addCreature(ICreature creature) {
        creatureStack.add(creature);
        return this;
    }

    public CreatureStack removeCreature(CreatureName creatureName) {
        creatureStack.removeIf(creature -> creature.getName() == creatureName);
        return this;
    }

    public Optional<ICreature> getTopCreature() {
        if (!creatureStack.isEmpty()) {
            ICreature topCreature = creatureStack.get(creatureStack.size() - 1);
            return Optional.of(topCreature);
        } else {
            return Optional.empty();
        }
    }

    public Optional<ICreature> getCreatureWithName(CreatureName creatureName) {
        for (ICreature creature : creatureStack) {
            if (creature.getName() == creatureName) {
                return Optional.of(creature);
            }
        }
        return Optional.empty();
    }

    public boolean isEmpty() {
        return creatureStack.isEmpty();
    }

    public int getSize() {
        return creatureStack.size();
    }

    @Override
    public CreatureStack clone() {
        CreatureStack copiedCreatureStack = new CreatureStack();

        for (ICreature creature : creatureStack) {
            /// ICreature does not need to be cloned - not mutated
            copiedCreatureStack.addCreature(creature);
        }

        return copiedCreatureStack;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CreatureStack)) {
            return false;
        }

        CreatureStack otherCreatureStack = (CreatureStack) other;

        return creatureStack.equals(otherCreatureStack.creatureStack);
    }
}
