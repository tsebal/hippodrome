import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {
    @Test
    void hippodromeConstructorShouldThrowExceptionIfParamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void hippodromeConstructorShouldConsistMessageOfThrownExceptionIfParamIsNull() {
        String exceptedMessage = "Horses cannot be null.";

        try {
            new Hippodrome(null);
            fail();
        } catch (IllegalArgumentException actualException) {
            assertEquals(exceptedMessage, actualException.getMessage());
        }
    }

    @Test
    void hippodromeConstructorShouldThrowExceptionAndMessageIfParamIsEmpty() {
        String exceptedMessage = "Horses cannot be empty.";

        IllegalArgumentException actualException =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));

        assertEquals(exceptedMessage, actualException.getMessage());
    }

    @Test
    void methodGetNameShouldReturnTheSameObjectsInList() {
        List<Horse> exceptedHorsesList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            exceptedHorsesList.add(new Horse("Horse " + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(exceptedHorsesList);

        assertEquals(exceptedHorsesList, hippodrome.getHorses());
    }

    @Test
    void methodMoveShouldInvokeEachHorseMoveMethod() {
        List<Horse> exceptedHorsesList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            exceptedHorsesList.add(mock(Horse.class));
        }

        new Hippodrome(exceptedHorsesList).move();

        for (Horse horse : exceptedHorsesList) {
            verify(horse).move();
        }
    }

    @Test
    void methodGetWinnerShouldReturnHorseWithMaxDistanceValue() {
        Horse expectedHorse = new Horse("Horse1", 1, 4);
        Horse horse2 = new Horse("Horse2", 1, 3);
        Horse horse3 = new Horse("Horse3", 1, 2.5);
        Horse horse4 = new Horse("Horse4", 1, 2);
        Hippodrome hippodrome = new Hippodrome(List.of(expectedHorse, horse2, horse3, horse4));

        assertSame(expectedHorse, hippodrome.getWinner());
    }
}