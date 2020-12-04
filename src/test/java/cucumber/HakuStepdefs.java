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
    
    String otsikko;
    
    String addCommand;
    String exitCommand;
    
    @Before
    public void setUp() throws IOException {
        mockIO = mock(ConsoleIO.class);
        mockDao = mock(LukuvinkkiDao.class);
        mockDao.useTestFile();
        ui = new ConsoleUI(mockIO, mockDao);
        
        addCommand = "u";
        exitCommand = "l";
    }
    
    @Given("on olemassa lukuvinkki nimelta {string}")
    public void lukuvinkkiOnOlemassa(String otsikko) throws IOException {
        
        this.otsikko = otsikko;
        when(mockIO.readInput("(L)opeta (K)aikki (U)usi tai \nkirjoita useampi merkki hakua varten")).thenReturn(addCommand, exitCommand);
        when(mockIO.readInput("Anna lukuvinkin otsikko: ")).thenReturn(otsikko);
        when(mockIO.readInput("Anna lukuvinkin linkki: ")).thenReturn("");
        
        ui.run();
        
    }
    
    @When("kayttaja hakee lukuvinkkia hakusanalla {string}")
    public void lukuvinkkiaHaetaanHakusanalla(String hakusana) throws IOException {
        
        when(mockIO.readInput("(L)opeta (K)aikki (U)usi tai \nkirjoita useampi merkki hakua varten")).thenReturn(hakusana, exitCommand);
        when(mockIO.readInput("<RET>Takaisin (M)uokkaa (P)oista")).thenReturn("ret");

        ui.run();
    
    }
    
    @Then("lukuvinkki {string} naytetaan konsolissa")
    public void lukuvinkkiNaytetaanKonsolissa(String otsikko) throws IOException {

        String expected = otsikko + " URL: NIL";

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockIO, times(4)).printOutput(captor.capture());

        List<String> values = captor.getAllValues();

        boolean expectedwasFound = false;
        for (String val : values) {
            if (val.contains(expected)) {
                expectedwasFound = true;
            }
        }

        assertTrue(expectedwasFound);
        
    }

    @Then("Then molemmat lukuvinkit {string}} ja {string} naytetaan konsolissa")
    public void molemmatLukuvinkitNaytetaanKonsolissa(String otsikko1, String otsikko2) {
        //todo
    }
    
}
