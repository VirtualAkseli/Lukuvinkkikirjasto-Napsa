package cucumber;

import dao.LukuvinkkiDao;
import domain.Kirja;
import domain.Lukuvinkki;
import io.ConsoleIO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    ConsoleUI ui;
    String otsikko;
    
    String addCommand;
    String exitCommand;

    @Given("konsoli pyytaa lukuvinkin otsikkoa")
    public void konsoliPyytaaOtsikkoa() throws IOException {
        mockIO = mock(ConsoleIO.class);
        mockLukuvinkkiDao = mock(LukuvinkkiDao.class);
        mockLukuvinkkiDao.useTestFile();
        ui = new ConsoleUI(mockIO, mockLukuvinkkiDao);
        
        addCommand = "u";
        exitCommand = "l";
    }

    
    @When("kelvollinen otsikko {string} syotetaan konsoliin") public void
    kelvollinenOtsikkoSyotetaan(String otsikko) throws IOException {
        this.otsikko = otsikko;
        otsikkoSyotetaan(otsikko); 
    }
     
    @When("tyhja otsikko syotetaan konsoliin") public void tyhjaOtsikkoSyotetaan() throws IOException { 
        String tyhja = "";
        otsikkoSyotetaan(tyhja); 
    }
    
    @Then("konsoli vastaa halutulla viestilla")
    public void konsoliVastaaViestilla() {
        LocalDateTime timeNow = LocalDateTime.now();
        String timeNowString = changeTimeToString(timeNow);
        verify(mockIO).printOutput(eq(timeNowString + " tallennettiin lukuvinkki: " + otsikko + " URL: NIL\n"));
    }
    
     @Then("lukuvinkki ei tallennu tiedostoon") public void
     lukuvinkkiEiTallennuTiedostoon() throws IOException {
        verify(mockLukuvinkkiDao, times(0)).saveToFile(new Kirja(anyString())); 
     }
     

    public void otsikkoSyotetaan(String otsikko) throws IOException {
        when(mockIO.readInput("(L)opeta (K)aikki (U)usi tai \nkirjoita useampi merkki hakua varten")).thenReturn(addCommand, exitCommand);
        when(mockIO.readInput("Anna lukuvinkin otsikko: ")).thenReturn(otsikko);
        when(mockIO.readInput("Anna lukuvinkin linkki: ")).thenReturn("");
        
        ui.run();
      
    }
    
    public String changeTimeToString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"));
    }
     

}
