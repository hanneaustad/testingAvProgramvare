package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void testHentTransaksjoner() {

        String kontoNr = "123456";
        String fraDato = "2024-01-01";
        String tilDato = "2024-01-31";
        Konto mockKonto = new Konto();

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.hentTransaksjoner(kontoNr, fraDato, tilDato)).thenReturn(mockKonto);

        Konto resultat = repository.hentTransaksjoner(kontoNr, fraDato, tilDato);

        assertEquals(mockKonto, resultat);
    }


    @Test
    public void testHentSaldi() {

        String personnummer = "123456789";
        List<Konto> mockSaldi = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.hentSaldi(personnummer)).thenReturn(mockSaldi);

        List<Konto> resultat = bankController.hentSaldi();

        assertEquals(mockSaldi, resultat);
    }

    @Test
    public void testRegistrerBetaling() {
        Transaksjon mockBetaling = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn("123456789");

        when(repository.registrerBetaling(mockBetaling)).thenReturn("Betaling registrert");

        String resultat = bankController.registrerBetaling(mockBetaling);

        assertEquals("Betaling registrert", resultat);
    }

    @Test
    public void testHentBetalinger() {
        when(sjekk.loggetInn()).thenReturn("123456789");

        List<Transaksjon> mockBetalinger = new ArrayList<>();

        when(repository.hentBetalinger("123456789")).thenReturn(mockBetalinger);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(mockBetalinger, resultat);
    }

    @Test
    public void testUtforBetaling() {
        when(sjekk.loggetInn()).thenReturn("123456789");

        int transaksjonsID = 123;

        List<Transaksjon> oppdatertTranskasjonsliste = new ArrayList<>();

        when(repository.utforBetaling(transaksjonsID)).thenReturn("OK");
        when(repository.hentBetalinger("123456789")).thenReturn(oppdatertTranskasjonsliste);

        List<Transaksjon> resultat = bankController.utforBetaling(transaksjonsID);

        assertEquals(oppdatertTranskasjonsliste, resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void testEndreKundeInfo() {
        when(sjekk.loggetInn()).thenReturn("123456789");

        Kunde kunde = new Kunde();
        kunde.setFornavn("Kari Nordmann");

        when(repository.endreKundeInfo(kunde)).thenReturn("Kundeinfo endret");

        String resultat = bankController.endre(kunde);

        assertEquals("Kundeinfo endret", resultat);
    }
}

