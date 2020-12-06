package cucumber;

import dao.LukuvinkkiDao;
import domain.Lukuvinkki;
import io.ConsoleIO;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import ui.ConsoleUI;

public class HakuStepdefs {
    
    ConsoleIO mockIO;
    LukuvinkkiDao mockDao;
    ConsoleUI ui;
    UiValues vals;
    
    @Before
    public void setUp() throws IOException {
        mockIO = mock(ConsoleIO.class);
        mockDao = mock(LukuvinkkiDao.class);
        mockDao.useTestFile();
        ui = new ConsoleUI(mockIO, mockDao);
        vals = new UiValues();
    }
    
    @Given("on olemassa lukuvinkki nimelta {string}")
    public void lukuvinkkiOnOlemassa(String otsikko) throws IOException {

        when(mockIO.readInput(vals.mainMenu)).thenReturn(vals.uusiVal, vals.lopetusVal);
        when(mockIO.readInput(vals.otsikonPyynto)).thenReturn(otsikko);
        when(mockIO.readInput(vals.linkinPyynto)).thenReturn("");
        when(mockIO.readInput(vals.tagienPyynto)).thenReturn("tagi1;tagi2");
        
        ui.run();
        
    }

    @Given("on olemassa lukuvinkit nimelta {string} ja {string}")
    public void lukuvinkitOvatOlemassa(String otsikko1, String otsikko2) throws IOException {

        when(mockIO.readInput(vals.mainMenu)).thenReturn(vals.uusiVal, vals.uusiVal, vals.lopetusVal);
        when(mockIO.readInput(vals.otsikonPyynto)).thenReturn(otsikko1, otsikko2);
        when(mockIO.readInput(vals.linkinPyynto)).thenReturn("");
        when(mockIO.readInput(vals.tagienPyynto)).thenReturn("tagi1;tagi2");

        ui.run();

    }

    @Given("ei ole olemassa lukuvinkkia nimelta {string}")
    public void lukuvinkkiaEiOleOlemassa(String otsikko) throws IOException {

    }
    
    @When("kayttaja hakee lukuvinkkia hakusanalla {string}")
    public void lukuvinkkiaHaetaanHakusanalla(String hakusana) throws IOException {
        
        when(mockIO.readInput(vals.mainMenu)).thenReturn(hakusana, vals.lopetusVal);
        when(mockIO.readInput(vals.naytaLukuvinkkiMenu)).thenReturn(vals.returnVal);

        ui.run();
    
    }
    
    @Then("lukuvinkki {string} naytetaan konsolissa")
    public void lukuvinkkiNaytetaanKonsolissa(String otsikko) throws IOException {

        String expected = otsikko + " URL: NIL";

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockIO, times(3)).printOutput(captor.capture());

        List<String> values = captor.getAllValues();

        boolean expectedwasFound = false;
        for (String val : values) {
            if (val.contains(expected)) {
                expectedwasFound = true;
            }
        }

        assertTrue(expectedwasFound);
        
    }

    @Then("molemmat lukuvinkit {string} ja {string} naytetaan konsolissa")
    public void molemmatLukuvinkitNaytetaanKonsolissa(String otsikko1, String otsikko2) {

        String expected1 = otsikko1 + " URL: NIL";
        String expected2 = otsikko2 + " URL: NIL";

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockIO, times(5)).printOutput(captor.capture());

        List<String> values = captor.getAllValues();

        boolean expected1wasFound = false;
        boolean expected2wasFound = false;
        for (String val : values) {
            if (val.contains(expected1)) {
                expected1wasFound = true;
            }
            if (val.contains(expected2)) {
                expected2wasFound = true;
            }
        }

        assertTrue(expected1wasFound && expected2wasFound);

    }

    @Then("lukuvinkkia {string} ei loydy")
    public void yhtaanLukuvinkkiaEiLoydy(String otsikko) {

        String expected = "Ei löytynyt: " + otsikko;

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockIO, times(1)).printOutput(captor.capture());

        List<String> values = captor.getAllValues();

        boolean expectedwasFound = false;
        for (String val : values) {
            if (val.contains(expected)) {
                expectedwasFound = true;
            }
        }

        assertFalse(expectedwasFound);

    }
    
}
