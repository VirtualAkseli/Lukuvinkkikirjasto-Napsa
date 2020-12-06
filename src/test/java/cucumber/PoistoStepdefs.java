package cucumber;

import dao.LukuvinkkiDao;
import domain.Lukuvinkki;
import io.ConsoleIO;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.ArgumentCaptor;
import ui.ConsoleUI;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PoistoStepdefs {
    
    ConsoleIO mockIO;
    LukuvinkkiDao mockDao;
    ConsoleUI ui;
    UiValues vals;
    String chooseFirst;
    
    @Before
    public void setUp() throws IOException {
        mockIO = mock(ConsoleIO.class);
        mockDao = mock(LukuvinkkiDao.class);
        mockDao.useTestFile();
        ui = new ConsoleUI(mockIO, mockDao);
        vals = new UiValues();

        //choose first element is list vinkit
        chooseFirst = "1";
    }

    @When("kayttaja poistaa lukuvinkin {string} poistokomennolla")
    public void kayttajaPoistaaLukuvinkin(String otsikko) throws IOException {

        when(mockIO.readInput(vals.mainMenu)).thenReturn(chooseFirst, vals.lopetusVal);
        when(mockIO.readInput(vals.naytaLukuvinkkiMenu)).thenReturn(vals.poistoVal);

        ui.run();

    }

    @Then("lukuvinkki {string} poistetaan")
    public void lukuvinkkiPoistetaan(String otsikko) {

        String expected = "Poistettiin: " + otsikko;

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
    
}
