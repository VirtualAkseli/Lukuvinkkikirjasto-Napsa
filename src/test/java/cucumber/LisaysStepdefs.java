
package cucumber;

import dao.LukuvinkkiDao;
import domain.Kirja;
import io.ConsoleIO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ui.ConsoleUI;

public class LisaysStepdefs {

    ConsoleIO mockIO;
    LukuvinkkiDao mockLukuvinkkiDao;
    Kirja vinkki;
    ConsoleUI ui;
    UiValues vals;

    @Given("konsoli pyytaa lukuvinkin otsikkoa")
    public void konsoliPyytaaOtsikkoa() {
        mockIO = mock(ConsoleIO.class);
        mockLukuvinkkiDao = mock(LukuvinkkiDao.class);
        vinkki = new Kirja("Learn Java");
        ui = new ConsoleUI(mockIO, mockLukuvinkkiDao);
        vals = new UiValues();
    }

    @When("kelvollinen otsikko {string} syotetaan konsoliin")
    public void
    kelvollinenOtsikkoSyotetaan(String otsikko) throws IOException {
        otsikkoSyotetaan(otsikko);
    }

    @When("tyhja otsikko syotetaan konsoliin")
    public void tyhjaOtsikkoSyotetaan() throws IOException {
        String tyhja = "";
        otsikkoSyotetaan(tyhja);
    }

    @Then("konsoli vastaa halutulla viestilla")
    public void konsoliVastaaViestilla() {

        // Myöhempää käyttöä varten vaihtoehtoisia tapoja...
        // String oikea = vinkki.changeTimeToString(vinkki.getAddDateTime()) + " tallennettiin lukuvinkki: Testiotsikko URL: NIL, tägit: [tagi1, tagi2]\n";
        // verify(mockIO, times(1)).printOutput(oikea);
        // verify(mockIO, atLeastOnce()).printOutput(oikea);
        verify(mockIO).printOutput(eq(vinkki.changeTimeToString(vinkki.getAddDateTime()) + " tallennettiin lukuvinkki: Learn Java URL: NIL, tägit: [tagi1, tagi2]\n"));

    }

    @Then("lukuvinkki ei tallennu tiedostoon")
    public void
    lukuvinkkiEiTallennuTiedostoon() throws IOException {
        verify(mockLukuvinkkiDao, times(0)).saveToFile(new Kirja(anyString()));
    }

    public void otsikkoSyotetaan(String otsikko) throws IOException {
        when(mockIO.readInput(vals.mainMenu)).thenReturn("u", "l");
        when(mockIO.readInput(vals.otsikonPyynto)).thenReturn(otsikko);
        when(mockIO.readInput(vals.linkinPyynto)).thenReturn("");
        when(mockIO.readInput(vals.tagienPyynto)).thenReturn("tagi1;tagi2");

        ui.run();

    }

}
