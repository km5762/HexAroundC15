package hexaround.game.board;

import hexaround.game.creature.*;
import hexaround.game.player.PlayerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureStackTest {
    CreatureStack creatureStack;
    ICreature creature1;
    ICreature creature2;

    @BeforeEach
    void setUp() {
        creatureStack = new CreatureStack();
        creature1 = new Creature(CreatureName.CRAB, PlayerName.RED, 5, null, Collections.singleton(CreatureProperty.WALKING));
        creature2 = new Creature(CreatureName.SPIDER, PlayerName.RED, 2, null, Collections.singleton(CreatureProperty.WALKING));
    }

    @Test
    void addCreature() {
        assertTrue(creatureStack.addCreature(creature1) instanceof CreatureStack);
        assertEquals(1, creatureStack.getSize());
    }

    @Test
    void removeCreature() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertTrue(creatureStack.removeCreature(creature2) instanceof CreatureStack);
        assertEquals(1, creatureStack.getSize());
    }

    @Test
    void getTopCreatureOfEmptyStack() {
        assertFalse(creatureStack.getTopCreature().isPresent());
    }

    @Test
    void getTopCreature() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertEquals(creature2, creatureStack.getTopCreature().get());
    }

    @Test
    void getCreatureWithNameAndOwnerWrongCreatureName() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertFalse(creatureStack.getCreatureWithNameAndOwner(CreatureName.GRASSHOPPER, PlayerName.RED).isPresent());
    }

    @Test
    void getCreatureWithNameAndOwnerWrongPlayerName() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertFalse(creatureStack.getCreatureWithNameAndOwner(CreatureName.CRAB, PlayerName.BLUE).isPresent());
    }

    @Test
    void getCreatureWithNameAndOwner() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertEquals(creature1, creatureStack.getCreatureWithNameAndOwner(CreatureName.CRAB, PlayerName.RED).get());
    }

    @Test
    void isEqualSameInstance() {
        assertTrue(creatureStack.equals(creatureStack));
    }

    @Test
    void isNotEqualNotInstanceOf() {
        Object dummyObject = new Object();
        assertFalse(creatureStack.equals(dummyObject));
    }

    @Test
    void isEqualDifferentInstance() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        CreatureStack otherStack = new CreatureStack();
        otherStack.addCreature(creature1);
        otherStack.addCreature(creature2);

        assertTrue(creatureStack.equals(otherStack));
    }

    @Test
    void cloneCreatureStack() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        CreatureStack copiedCreatureStack = creatureStack.clone();

        assertNotSame(creatureStack, copiedCreatureStack);
        assertEquals(creatureStack, copiedCreatureStack);
    }
}