import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

class HorseTest {

    // Проверка null в имени
    @Test
    void constructor_NullName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    // Проверка сообщения при null
    @Test
    void constructor_NullName_Message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    // Проверка пустых строк (параметризованный)
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "   "})
    void constructor_BlankName_ThrowsException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
    }

    // Проверка сообщения при пустой строке
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void constructor_BlankName_Message(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    // Проверка отрицательной скорости
    @Test
    void constructor_NegativeSpeed_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Lucky", -1.0, 1));
    }

    // Сообщение при отрицательной скорости
    @Test
    void constructor_NegativeSpeed_Message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Lucky", -1.0, 1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    // Проверка отрицательной дистанции
    @Test
    void constructor_NegativeDistance_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Lucky", 1, -1.0));
    }

    // Сообщение при отрицательной дистанции
    @Test
    void constructor_NegativeDistance_Message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Lucky", 1, -1.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName_ReturnsNameFromConstructor() {
        String name = "Spirit";
        Horse horse = new Horse(name, 1, 1);
        assertEquals(name, horse.getName());
    }

    @Test
    void getSpeed_ReturnsSpeedFromConstructor() {
        double speed = 2.5;
        Horse horse = new Horse("Spirit", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistance_ReturnsDistanceFromConstructor() {
        double distance = 10.0;
        Horse horse = new Horse("Spirit", 1, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    void getDistance_ReturnsZeroForTwoParamConstructor() {
        Horse horse = new Horse("Spirit", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void move_CallsGetRandomDoubleWithCorrectParams() {
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {
            new Horse("Spirit", 1, 1).move();

            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @Test
    void move_CalculatesDistanceCorrectlyWithHighSpeed() {
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            double bigSpeed = 1.0e10;
            Horse horse = new Horse("Giant", bigSpeed, 0);
            horse.move();

            assertEquals(bigSpeed * 0.5, horse.getDistance());
        }
    }

    @Test
    void move_WithMinimumRandomValue() {
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {

            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.2);
            Horse horse = new Horse("Slowy", 10, 100);
            horse.move();


            assertEquals(102.0, horse.getDistance());
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.8})
    void move_CalculatesDistanceByFormula(double randomValue) {
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {

            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            double speed = 2.0;
            double initialDistance = 10.0;
            Horse horse = new Horse("Spirit", speed, initialDistance);

            horse.move();

            double expectedDistance = initialDistance + speed * randomValue;

            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
