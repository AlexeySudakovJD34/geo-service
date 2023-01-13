import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.*;

public class LocalizationServiceImplTest {

    LocalizationServiceImpl sut;

    @BeforeEach
    public void startedEach() {
        sut = new LocalizationServiceImpl();
    }
    @AfterEach
    public void finishedEach() {
        sut = null;
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void testWithAnyCountryCase(Country country) {
        //arrange

        //act
        String result = sut.locale(country);
        //assert
        assertTrue(result.length() > 0);
    }

    @ParameterizedTest
    @MethodSource
    void testWithNonRussiaCase(Country country) {
        //arrange
        String nonRussianMessage = "Welcome";
        //act
        String result = sut.locale(country);
        //assert
        assertEquals(nonRussianMessage, result);
    }
    static Stream<Country> testWithNonRussiaCase() {
        return Stream.of(USA, GERMANY, BRAZIL);
    }

    @Test
    void testWithRussiaCase() {
        //arrange
        Country country = RUSSIA;
        String russianMessage = "Добро пожаловать";
        //act
        String result = sut.locale(country);
        //assert
        assertEquals(russianMessage, result);
    }
}
