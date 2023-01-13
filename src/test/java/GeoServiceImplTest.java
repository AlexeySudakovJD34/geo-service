import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.*;


public class GeoServiceImplTest {

    GeoServiceImpl sut;

    @BeforeEach
    public void startedEach() {
        sut = new GeoServiceImpl();
    }
    @AfterEach
    public void finishedEach() {
        sut = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.0.32.11", "172.44.183.149", "172.0.0.1"})
    public void testStartsWithMoscowIp(String ip){
        //arrange
        String city = "Moscow";
        Country country = RUSSIA;
        //act
        Location result = sut.byIp(ip);
        //assert
        assertEquals(city, result.getCity());
        assertEquals(country, result.getCountry());
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.0.32.11", "96.44.183.149", "96.0.0.1"})
    public void testStartsWithNYIp(String ip){
        //arrange
        String city = "New York";
        Country country = USA;
        //act
        Location result = sut.byIp(ip);
        //assert
        assertEquals(city, result.getCity());
        assertEquals(country, result.getCountry());
    }

    @ParameterizedTest (name = "{index} - {0} - is a wrong ip")
    @ValueSource(strings = {"125.0.32.11", "72.44.183.149", "69.0.0.1", "wrong ip", ""})
    public void testByWrongIp(String ip){
        //arrange

        //act
        Location result = sut.byIp(ip);
        //assert
        assertNull(result);
    }

    @Test
    public void testByLOCALHOSTIp() {
        //arrange
        String ip = "127.0.0.1";
        int building = 0;
        //act
        Location result = sut.byIp(ip);
        //assert
        assertNull(result.getCity());
        assertNull(result.getCountry());
        assertNull(result.getStreet());
        assertEquals(building, result.getBuilding());
    }

    @Test
    public void testByMOSCOWIp() {
        //arrange
        String ip = "172.0.32.11";
        String city = "Moscow";
        Country country = RUSSIA;
        String street = "Lenina";
        int building = 15;
        //act
        Location result = sut.byIp(ip);
        //assert
        assertEquals(city, result.getCity());
        assertEquals(country, result.getCountry());
        assertEquals(street, result.getStreet());
        assertEquals(building, result.getBuilding());
    }

    @Test
    public void testByNYIp() {
        //arrange
        String ip = "96.44.183.149";
        String city = "New York";
        Country country = USA;
        String street = "10th Avenue";
        int building = 32;
        //act
        Location result = sut.byIp(ip);
        //assert
        assertEquals(city, result.getCity());
        assertEquals(country, result.getCountry());
        assertEquals(street, result.getStreet());
        assertEquals(building, result.getBuilding());
    }

    @Test
    public void testForException() {
        //arrange
        var expected = RuntimeException.class;
        double latitude = Math.random();
        double longitude = Math.random();
        //act
        //assert
        assertThrows(expected, () -> sut.byCoordinates(latitude, longitude));
    }
}
