import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.entity.Country.*;
import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

public class MessageSenderImplTest {

    @Test
    void testWithRussiaCase() {
        //arrange
        String moscowIp = "172.123.12.19";
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp(moscowIp)).thenReturn(new Location("Moscow", RUSSIA, "Lenina", 15));

        String expectedMessage = "Добро пожаловать";
        LocalizationService localisationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localisationServiceMock.locale(RUSSIA)).thenReturn("Добро пожаловать");

        Map<String, String> testHeaders = new HashMap<String, String>();
        testHeaders.put(IP_ADDRESS_HEADER, moscowIp);
        //act
        MessageSenderImpl sut = new MessageSenderImpl(geoServiceMock, localisationServiceMock);
        String resultMessage = sut.send(testHeaders);
        //assert
        assertEquals(expectedMessage, resultMessage);
    }
    @Test
    void testWithUSACase() {
        //arrange
        String usaIp = "96.44.183.149";
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp(usaIp)).thenReturn(new Location("New York", USA, "10th Avenue", 32));

        String expectedMessage = "Welcome";
        LocalizationService localisationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localisationServiceMock.locale(USA)).thenReturn("Welcome");

        Map<String, String> testHeaders = new HashMap<String, String>();
        testHeaders.put(IP_ADDRESS_HEADER, usaIp);
        //act
        MessageSenderImpl sut = new MessageSenderImpl(geoServiceMock, localisationServiceMock);
        String resultMessage = sut.send(testHeaders);
        //assert
        assertEquals(expectedMessage, resultMessage);
    }
}
