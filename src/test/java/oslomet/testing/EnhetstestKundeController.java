package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKundeController {

    @InjectMocks
    private AdminKundeController kundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void testHentAlle() {
        // Arrange
        List <Kunde> kundeList = new ArrayList<>();
        Kunde kunde1 = new Kunde("123456789", "Per", "Hansen",
                "Gågata 3", "1515", "Oslo", "98765432", "passord123");
        Kunde kunde2 = new Kunde("987654321", "Lise", "Jensen",
                "Storgata 13", "2423", "Bergen", "48275925", "passord456");
        kundeList.add(kunde1);
        kundeList.add(kunde2);
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.hentAlleKunder()).thenReturn(kundeList);

        // Act
        List<Kunde> resultat = kundeController.hentAlle(null);

        // Assert
        assertEquals(kundeList, resultat);
    }
    @Test
    public void testHentAlle_IkkeInnlogget() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789", "Per", "Hansen",
                "Gågata 3", "1515", "Oslo", "98765432", "passord123");
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Kunde> resultat = kundeController.hentAlle(null);

        // Assert
        assertNull(resultat);
    }

    @Test
    public void testLagreKunde() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789","Per","Hansen",
                "Gågata 3","1515","Oslo","98765432","passord123");

        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.registrerKunde(kunde1)).thenReturn("OK");
        // Act
        String resultat = kundeController.lagreKunde(kunde1);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void testLagreKunde_IkkeInnlogget() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789","Per","Hansen",
                "Gågata 3","1515","Oslo","98765432","passord123");

        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = kundeController.lagreKunde(kunde1);

        // Assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void testEndreInfo() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789", "Per", "Hansen",
                "Gågata 3", "1515", "Oslo", "98765432", "passord123");
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.endreKundeInfo(kunde1)).thenReturn("OK");

        // Act
        String resultat = kundeController.endre(kunde1);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void testEndreInfo_IkkeInnlogget() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789", "Per", "Hansen",
                "Gågata 3", "1515", "Oslo", "98765432", "passord123");
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = kundeController.endre(kunde1);

        // Assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void testSlett() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789", "Per", "Hansen",
                "Gågata 3", "1515", "Oslo", "98765432", "passord123");
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.slettKunde("123456789")).thenReturn("OK");

        // Act
        String resultat = kundeController.slett("123456789");

        // Assert
        assertEquals("OK", resultat);
        verify(repository).slettKunde("123456789");
    }

    @Test
    public void testSlett_IkkeInnlogget() {
        // Arrange
        Kunde kunde1 = new Kunde("123456789", "Per", "Hansen",
                "Gågata 3", "1515", "Oslo", "98765432", "passord123");
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = kundeController.slett("123456789");

        // Assert
        assertEquals("Ikke logget inn", resultat);
    }

}

