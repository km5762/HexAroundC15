package hexaround.game.board;

import hexaround.game.creature.CreatureName;
import hexaround.game.creature.ICreature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CreatureStack implements Iterable<ICreature> {
    protected List<ICreature> creatureStack;

    /**
     * Constructs an instance of CreatureStack
     */
    public CreatureStack() {
        this.creatureStack = new ArrayList<>();
    }

    /**
     * Add an ICreature to the CreatureStack
     *
     * @param creature an instance of ICreature to add
     * @return this CreatureStack instance
     */
    public CreatureStack addCreature(ICreature creature) {
        creatureStack.add(creature);
        return this;
    }

    /**
     * Remove an ICreature with CreatureName from the CreatureStack
     * Source: https://stackoverflow.com/questions/72735225/remove-first-element-satisfying-condition-in-java-list
     *
     * @param creatureName the CreatureName of the creature to remove
     * @return this CreatureStack instance
     */
    public CreatureStack removeCreature(CreatureName creatureName) {
        Iterator<ICreature> iterator = creatureStack.iterator();

        while(iterator.hasNext()) {
            ICreature creature = iterator.next();

            if (creature.getName() == creatureName) {
                iterator.remove();
                break;
            }
        }
        return this;
    }

    /**
     * Get the ICreature on the top of the CreatureStack
     *
     * @return the topmost ICreature, or an empty Optional if the CreatureStack is empty
     */
    public Optional<ICreature> getTopCreature() {
        if (!creatureStack.isEmpty()) {
            ICreature topCreature = creatureStack.get(creatureStack.size() - 1);
            return Optional.of(topCreature);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get an instance of ICreature with CreatureName from the CreatureStack
     *
     * @param creatureName the CreatureName of the specified creature
     * @return the ICreature with CreatureName, or an empty Optional if there is no match or the CreatureStack is empty
     */
    public Optional<ICreature> getCreatureWithName(CreatureName creatureName) {
        for (ICreature creature : creatureStack) {
            if (creature.getName() == creatureName) {
                return Optional.of(creature);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns true if the CreatureStack is empty
     *
     * @return true if the CreatureStack is empty, false otherwise
     */
    public boolean isEmpty() {
        return creatureStack.isEmpty();
    }

    /**
     * Return the size of the CreatureStack
     *
     * @return the size of the CreatureStack
     */
    public int getSize() {
        return creatureStack.size();
    }

    /**
     * Creates a deep copy of this CreatureStack instance
     *
     * @return a deep copy of this CreatureStack instance
     */
    @Override
    public CreatureStack clone() {
        CreatureStack copiedCreatureStack = new CreatureStack();

        for (ICreature creature : creatureStack) {
            /// ICreature does not need to be cloned - not mutated
            copiedCreatureStack.addCreature(creature);
        }

        return copiedCreatureStack;
    }

    /**
     * Returns true if the given Object is a CreatureStack instance which contains the same ICreatures
     * Source: https://www.geeksforgeeks.org/overriding-equals-method-in-java/
     *
     * @param other any Object
     * @return true if the given Object is an instance of CreatureStack containing the same ICreatures in the same order
     */
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

    /**
     * Returns an iterator to the CreatureStack
     *
     * @return an iterator over the ICreatures in the stack
     */
    @Override
    public Iterator<ICreature> iterator() {
        return creatureStack.iterator();
    }
}
