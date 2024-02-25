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
    void removeCreatureNoMatch() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertTrue(creatureStack.removeCreature(CreatureName.BUTTERFLY) instanceof CreatureStack);
        assertEquals(2, creatureStack.getSize());
    }

    @Test
    void removeCreature() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertTrue(creatureStack.removeCreature(CreatureName.CRAB) instanceof CreatureStack);
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
    void getCreatureWithNameNoMatch() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertFalse(creatureStack.getCreatureWithName(CreatureName.GRASSHOPPER).isPresent());
    }

    @Test
    void getCreatureWithName() {
        creatureStack.addCreature(creature1);
        creatureStack.addCreature(creature2);

        assertEquals(creature1, creatureStack.getCreatureWithName(CreatureName.CRAB).get());
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