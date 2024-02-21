package oslomet.testing;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetController {

    @InjectMocks
    private Sikkerhet sikkherhetsController;

    @Mock
    private BankRepository repository;

    @Mock
    private MockHttpSession session;

    @Before
    public void initSession() {
    }

    @Test
    public void testSjekkLoggInn_OK() {
        // Arrange
        String personnummer = "12345678901";
        String passord = "validPassword";
        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");

        // Act
        String resultat = sikkherhetsController.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void testSjekkLoggInn_FeilPersonnr() {
        // Arrange
        String personnummer = "123";
        String passord = "validPassword";

        // Act
        String resultat = sikkherhetsController.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void testSjekkLoggInn_FeilPassord() {
        // Arrange
        String personnummer = "12345678901";
        String passord = "pass";

        // Act
        String resultat = sikkherhetsController.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void testSjekkLoggInn_FeilPåloggingsinformasjon() {
        // Arrange
        String personnummer = "12345678901";
        String passord = "wrongpassword";
        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("INVALID");

        // Act
        String resultat = sikkherhetsController.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }


    @Test
    public void testLoggUt_SessionNotNull() {
        // Arrange
        String innloggetPersonnummer = "12345678901";
        session.setAttribute("Innlogget", innloggetPersonnummer);

        // Act
        sikkherhetsController.loggUt();

        // Assert, Sjekker at "Innlogget" blir satt til null
        verify(session).setAttribute("Innlogget", null);
    }

    @Test
    public void testLoggUt_SessionNull() {
        // Arrange

        // Act
        sikkherhetsController.loggUt();

        // Assert, Sjekker at ingenting skjer, fordi "Innlogget" allerede er null
        verify(session).setAttribute("Innlogget", null);
    }

    @Test
    public void testLoggInnAdmin_OK() {
        // Arrange
        String bruker = "Admin";
        String passord = "Admin";

        // Act
        String resultat = sikkherhetsController.loggInnAdmin(bruker, passord);

        // Assert
        assertEquals("Logget inn", resultat);
        verify(session).setAttribute("Innlogget", "Admin");
    }

    @Test
    public void testLoggInnAdmin_Feil() {
        // Arrange
        String bruker = "FeilBruker";
        String passord = "FeilPassord";

        // Act
        String resultat = sikkherhetsController.loggInnAdmin(bruker, passord);

        // Assert
        assertEquals("Ikke logget inn", resultat);
        verify(session).setAttribute("Innlogget", null);
    }

    @Test
    public void testLoggetInn_SessionNotNull() {
        // Arrange
        String forventetInnloggetVerdi = "Admin";
        when(session.getAttribute("Innlogget")).thenReturn(forventetInnloggetVerdi);

        // Act
        String resultat = sikkherhetsController.loggetInn();

        // Assert
        assertEquals(forventetInnloggetVerdi, resultat);
    }

    @Test
    public void testLoggetInn_SessionNull() {
        // Arrange
        when(session.getAttribute("Innlogget")).thenReturn(null);

        // Act
        String resultat = sikkherhetsController.loggetInn();

        // Assert
        assertEquals(null, resultat);
    }

}




