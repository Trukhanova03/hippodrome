import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Test
    @Disabled("Тест отключен, чтобы не занимать время при запуске всех тестов")
    @Timeout(value = 22)
    void main_RunsWithinTimeLimit() throws Exception {

        Main.main(new String[]{});
    }
}