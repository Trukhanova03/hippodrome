import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    void constructor_NullList_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void constructor_NullList_Message() {
        var e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    void constructor_EmptyList_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void constructor_EmptyList_Message() {
        var e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    void getHorses_ReturnsSameObjectsInOrder() {

        List<Horse> horses = new ArrayList<>();
        IntStream.range(0, 30).forEach(i -> horses.add(new Horse("Horse " + i, i)));

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move_CallsMoveForAllHorses() {

        List<Horse> horses = new ArrayList<>();
        IntStream.range(0, 50).forEach(i -> horses.add(mock(Horse.class)));

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner_ReturnsHorseWithMaxDistance() {
        Horse h1 = new Horse("1", 1, 10);
        Horse h2 = new Horse("2", 1, 20); // Победитель
        Horse h3 = new Horse("3", 1, 15);
        Hippodrome hippodrome = new Hippodrome(List.of(h1, h2, h3));

        assertSame(h2, hippodrome.getWinner());
    }

    @Test
    void getHorses_IsUnmodifiableList() {
        List<Horse> horses = new ArrayList<>(List.of(new Horse("Bucephalus", 1)));
        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> returnedList = hippodrome.getHorses();

        // Проверяем, что при попытке добавить лошадь в список напрямую через геттер
        // будет выброшено исключение UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> returnedList.add(new Horse("Spy", 1)));
    }

    @Test
    void getWinner_ReturnsFirstHorseWhenDistancesAreEqual() {

        Horse h1 = new Horse("Equal 1", 1, 100);
        Horse h2 = new Horse("Equal 2", 1, 100);
        Hippodrome hippodrome = new Hippodrome(List.of(h1, h2));

        assertSame(h1, hippodrome.getWinner());
    }

    @Test
    void move_DoesNotChangeDistanceIfSpeedIsZero() {

        Horse staticHorse = new Horse("Static", 0, 50);
        Hippodrome hippodrome = new Hippodrome(List.of(staticHorse));

        hippodrome.move();

        assertEquals(50.0, staticHorse.getDistance());
    }
}