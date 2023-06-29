import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {
    @Test
    void horseConstructorShouldThrowExceptionIfFirstParamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 20));
    }

    @Test
    void horseConstructorShouldConsistMessageOfThrownExceptionIfFirstParamIsNull() {
        String exceptedMessage = "Name cannot be null.";

        try {
            new Horse(null, 10, 20);
            fail();
        } catch (IllegalArgumentException actualException) {
            assertEquals(exceptedMessage, actualException.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t\t", "   ", "\n\n"})
    void horseConstructorShouldThrowExceptionAndMessageIfFirstParamIsBlank(String name) {
        String exceptedMessage = "Name cannot be blank.";

        IllegalArgumentException actualException =
                assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10, 20));

        assertEquals(exceptedMessage, actualException.getMessage());
    }

    @Test
    void horseConstructorShouldThrowExceptionIfSecondParamIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Voronoy", -1, 20));
    }

    @Test
    void horseConstructorShouldConsistMessageOfThrownExceptionIfSecondParamIsNegative() {
        String exceptedMessage = "Speed cannot be negative.";

        try {
            new Horse("Voronoy", -1, 20);
            fail();
        } catch (IllegalArgumentException actualException) {
            assertEquals(exceptedMessage, actualException.getMessage());
        }
    }

    @Test
    void horseConstructorShouldThrowExceptionIfThirdParamIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Voronoy", 10, -1));
    }

    @Test
    void horseConstructorShouldConsistMessageOfThrownExceptionIfThirdParamIsNegative() {
        String exceptedMessage = "Distance cannot be negative.";

        try {
            new Horse("Voronoy", 10, -1);
            fail();
        } catch (IllegalArgumentException actualException) {
            assertEquals(exceptedMessage, actualException.getMessage());
        }
    }

    @Test
    void methodGetNameShouldReturnFirstParamOfHorseConstructor() {
        String exceptedHorseName = "Voronoy";

        Horse horse = new Horse(exceptedHorseName, 10, 20);

        try {
            Field horseName = Horse.class.getDeclaredField("name");
            horseName.setAccessible(true);
            String actualHorseName = (String) horseName.get(horse);
            assertEquals(exceptedHorseName, actualHorseName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void methodGetSpeedShouldReturnSecondParamOfHorseConstructor() {
        double exceptedHorseSpeed = 10;

        Horse horse = new Horse("Voronoy", exceptedHorseSpeed, 20);

        try {
            Field horseSpeed = Horse.class.getDeclaredField("speed");
            horseSpeed.setAccessible(true);
            double actualHorseSpeed = (Double) horseSpeed.get(horse);
            assertEquals(exceptedHorseSpeed, actualHorseSpeed);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void methodGetDistanceShouldReturnThirdParamOfHorseConstructor() {
        double exceptedHorseDistance = 20;

        Horse horse = new Horse("Voronoy", 10, exceptedHorseDistance);

        try {
            Field horseDistance = Horse.class.getDeclaredField("distance");
            horseDistance.setAccessible(true);
            double actualHorseDistance = (Double) horseDistance.get(horse);
            assertEquals(exceptedHorseDistance, actualHorseDistance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void methodGetDistanceShouldReturnZeroDistanceIfObjCreatedWithTwoParamConstructor() {
        double exceptedHorseDistance = 0;

        Horse horse = new Horse("Voronoy", 10);

        try {
            Field horseDistance = Horse.class.getDeclaredField("distance");
            horseDistance.setAccessible(true);
            double actualHorseDistance = (Double) horseDistance.get(horse);
            assertEquals(exceptedHorseDistance, actualHorseDistance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void methodMoveShouldInvokeGetRandomDoubleMethodWithTwoParams() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Voronoy", 10, 20).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.55, 0.6})
    void methodMoveShouldReturnDistanceFormulaValue(double randomDouble) {
        double horseSpeed = 10;
        double horseDistance = 20;
        double exceptedHorseDistance = horseDistance + horseSpeed * randomDouble;

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            Horse horse = new Horse("Voronoy", horseSpeed, horseDistance);
            horse.move();

            try {
                Field horseDistanceField = Horse.class.getDeclaredField("distance");
                horseDistanceField.setAccessible(true);
                double actualHorseDistance = (Double) horseDistanceField.get(horse);
                assertEquals(exceptedHorseDistance, actualHorseDistance);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
