package ua.kpi.comsys.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionHistoryManagerTest {

    private ActionHistoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new ActionHistoryManager(3);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("maxCapacity <= 0 throws IllegalArgumentException")
        void zeroCapacity() {
            assertThrows(IllegalArgumentException.class, () -> new ActionHistoryManager(0));
        }

        @Test
        @DisplayName("negative maxCapacity throws IllegalArgumentException")
        void negativeCapacity() {
            assertThrows(IllegalArgumentException.class, () -> new ActionHistoryManager(-5));
        }

        @Test
        @DisplayName("valid maxCapacity creates empty manager")
        void validCapacity() {
            var m = new ActionHistoryManager(10);
            assertEquals(0, m.getHistorySize());
        }
    }

    @Nested
    @DisplayName("performAction")
    class PerformActionTests {

        @Test
        @DisplayName("null action throws IllegalArgumentException")
        void nullAction() {
            assertThrows(IllegalArgumentException.class, () -> manager.performAction(null));
        }

        @Test
        @DisplayName("blank action throws IllegalArgumentException")
        void blankAction() {
            assertThrows(IllegalArgumentException.class, () -> manager.performAction("   "));
        }

        @Test
        @DisplayName("empty action throws IllegalArgumentException")
        void emptyAction() {
            assertThrows(IllegalArgumentException.class, () -> manager.performAction(""));
        }

        @Test
        @DisplayName("adds action to history")
        void addsAction() {
            manager.performAction("open file");
            assertEquals(1, manager.getHistorySize());
            assertEquals("open file", manager.peekRecentAction());
        }

        @Test
        @DisplayName("newest action is first")
        void newestFirst() {
            manager.performAction("action1");
            manager.performAction("action2");
            assertEquals("action2", manager.peekRecentAction());
        }

        @Test
        @DisplayName("evicts oldest when capacity exceeded")
        void evictsOldest() {
            manager.performAction("a1");
            manager.performAction("a2");
            manager.performAction("a3");
            manager.performAction("a4");

            assertEquals(3, manager.getHistorySize());
            assertEquals(List.of("a4", "a3", "a2"), manager.getHistory());
        }
    }

    @Nested
    @DisplayName("undo")
    class UndoTests {

        @Test
        @DisplayName("returns null on empty history")
        void undoEmpty() {
            assertNull(manager.undo());
        }

        @Test
        @DisplayName("removes and returns most recent action")
        void undoRemoves() {
            manager.performAction("a1");
            manager.performAction("a2");

            assertEquals("a2", manager.undo());
            assertEquals(1, manager.getHistorySize());
            assertEquals("a1", manager.peekRecentAction());
        }

        @Test
        @DisplayName("multiple undos drain history")
        void multipleUndos() {
            manager.performAction("a1");
            manager.performAction("a2");

            manager.undo();
            manager.undo();

            assertEquals(0, manager.getHistorySize());
            assertNull(manager.undo());
        }
    }

    @Nested
    @DisplayName("peekRecentAction")
    class PeekTests {

        @Test
        @DisplayName("returns null on empty history")
        void peekEmpty() {
            assertNull(manager.peekRecentAction());
        }

        @Test
        @DisplayName("does not remove action")
        void peekDoesNotRemove() {
            manager.performAction("a1");
            manager.peekRecentAction();
            assertEquals(1, manager.getHistorySize());
        }
    }

    @Nested
    @DisplayName("getHistory")
    class GetHistoryTests {

        @Test
        @DisplayName("empty history returns empty list")
        void emptyHistory() {
            assertTrue(manager.getHistory().isEmpty());
        }

        @Test
        @DisplayName("returns actions newest to oldest")
        void orderNewestToOldest() {
            manager.performAction("a1");
            manager.performAction("a2");
            manager.performAction("a3");
            assertEquals(List.of("a3", "a2", "a1"), manager.getHistory());
        }

        @Test
        @DisplayName("modifying returned list does not affect internal state")
        void defensiveCopy() {
            manager.performAction("a1");
            List<String> history = manager.getHistory();
            try { history.add("hack"); } catch (UnsupportedOperationException ignored) {}
            assertEquals(1, manager.getHistorySize());
        }
    }

    @Nested
    @DisplayName("getHistorySize")
    class SizeTests {

        @Test
        @DisplayName("initially zero")
        void initiallyZero() {
            assertEquals(0, manager.getHistorySize());
        }

        @Test
        @DisplayName("increments after performAction")
        void incrementsAfterAction() {
            manager.performAction("a1");
            assertEquals(1, manager.getHistorySize());
        }

        @Test
        @DisplayName("decrements after undo")
        void decrementsAfterUndo() {
            manager.performAction("a1");
            manager.undo();
            assertEquals(0, manager.getHistorySize());
        }
    }

    @Nested
    @DisplayName("clear")
    class ClearTests {

        @Test
        @DisplayName("clears all actions")
        void clearsAll() {
            manager.performAction("a1");
            manager.performAction("a2");
            manager.clear();
            assertEquals(0, manager.getHistorySize());
            assertNull(manager.peekRecentAction());
        }

        @Test
        @DisplayName("clear on empty history is safe")
        void clearEmpty() {
            assertDoesNotThrow(() -> manager.clear());
        }
    }

    @Nested
    @DisplayName("Integration scenarios")
    class IntegrationTests {

        @Test
        @DisplayName("perform, undo, perform cycle respects capacity")
        void cycleWithCapacity() {
            var m = new ActionHistoryManager(2);
            m.performAction("a1");
            m.performAction("a2");
            m.undo();
            m.performAction("a3");
            m.performAction("a4");

            assertEquals(2, m.getHistorySize());
            assertEquals(List.of("a4", "a3"), m.getHistory());
        }

        @Test
        @DisplayName("capacity of 1 keeps only latest")
        void capacityOne() {
            var m = new ActionHistoryManager(1);
            m.performAction("a1");
            m.performAction("a2");
            m.performAction("a3");

            assertEquals(1, m.getHistorySize());
            assertEquals("a3", m.peekRecentAction());
        }
    }
}
