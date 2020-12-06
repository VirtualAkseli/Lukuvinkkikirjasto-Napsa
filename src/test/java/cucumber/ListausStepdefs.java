package cucumber;

import dao.LukuvinkkiDao;
import io.ConsoleIO;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.ArgumentCaptor;
import ui.ConsoleUI;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ListausStepdefs {

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

    @Given("ei ole olemassa yhtaan lukuvinkkia")
    public void eiOleLukuvinkkeja() {

    }

    @When("kayttaja listaa lukuvinkit listauskomennolla")
    public void lukuvinkitListataan() throws IOException {

        when(mockIO.readInput(vals.mainMenu)).thenReturn(vals.kaikkiVal, vals.lopetusVal);

        ui.run();

    }

    @Then("lukuvinkki {string} naytetaan listassa")
    public void lukuvinkkiNaytetaanListassa(String otsikko) throws IOException {

        String expected = otsikko;

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockIO, times(1)).printOutput(captor.capture());

        List<String> values = captor.getAllValues();

        //tässä values kuuluisi sisältää printOutputin argumentit jotka sitten tarkistetaan, mutta ei sisällä mitään

        boolean expectedwasFound = false;
        for (String val : values) {
            if (val.contains(expected)) {
                expectedwasFound = true;
            }
        }

        assertTrue(expectedwasFound);

    }
    
}
