package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKontoController {


    @InjectMocks
    private AdminKontoController kontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void testHentAlleKonti() {

        List<Konto> kontoList = Arrays.asList(new Konto(), new Konto());

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.hentAlleKonti()).thenReturn(kontoList);

        List<Konto> resultat = kontoController.hentAlleKonti();

        assertEquals(kontoList, resultat);
    }

    @Test
    public void testRegistrerKonto() {
        when(sjekk.loggetInn()).thenReturn("Logget inn");
        Konto konto = new Konto();
        konto.setPersonnummer("12345678");
        konto.setKontonummer("123");
        konto.setSaldo(1000.0);
        konto.setType("Sparekonto");
        konto.setValuta("NOK");
        konto.setTransaksjoner(new ArrayList<>());

        when(repository.registrerKonto(konto)).thenReturn("Konto registrert!");

        String resultat = kontoController.registrerKonto(konto);

        assertEquals("Konto registrert!", resultat);
    }


    @Test
    public void testEndreKonto() {
        Konto eksisterendeKonto = new Konto();
        eksisterendeKonto.setKontonummer("123");
        eksisterendeKonto.setSaldo(1000.0);
        eksisterendeKonto.setType("Sparekonto");
        eksisterendeKonto.setValuta("NOK");
        eksisterendeKonto.setTransaksjoner(new ArrayList<>());

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(kontoController.endreKonto(eksisterendeKonto)).thenReturn("Konto endret");

        eksisterendeKonto.setType("Brukskonto");
        eksisterendeKonto.setSaldo(1500.0);

        Konto forventetTilstand = new Konto();
        forventetTilstand.setKontonummer("123");
        forventetTilstand.setSaldo(1500.0);
        forventetTilstand.setType("Brukskonto");
        forventetTilstand.setValuta("NOK");
        forventetTilstand.setTransaksjoner(new ArrayList<>());

        String resultat = repository.endreKonto(eksisterendeKonto);

        assertEquals("Konto endret", resultat);

    }

    @Test
    public void testSlettKonto() {
        String personnummer = "123456789";

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.slettKunde(personnummer)).thenReturn("Kunde slettet");

        String resultat = repository.slettKunde(personnummer);

        assertEquals("Kunde slettet", resultat);
    }








}



