
package domain;

import domain.Lukuvinkki;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LukuvinkkiTest {

    private LukuvinkkiImpl lukuvinkki;

    @Before
    public void setUp() {
        lukuvinkki = new LukuvinkkiImpl();
    }

    @Test
    public void testSetAndGetLabel() {
        lukuvinkki.setLabel("testLabel2");
        assertEquals(lukuvinkki.getLabel(), "testLabel2");
    }

    @Test
    public void testSetAndGetType() {
        lukuvinkki.setType("testType");
        assertEquals(lukuvinkki.getType(), "testType");
    }

    @Test
    public void testToString() {
        lukuvinkki.setLabel("testLabel3");
        assertEquals(lukuvinkki.toString(), "testLabel3 URL: NIL, tägit: []");
    }

    @Test
    public void testAddTag() {
        lukuvinkki.addTagi("test1");
        lukuvinkki.addTagi("test2");
        assertEquals(lukuvinkki.getTagit().size(), 2);
        assertEquals(lukuvinkki.getTagit().get(1), "test2");
    }

    @Test
    public void newLukuvinkkiHasSameAddAndModifiedTime() {
        lukuvinkki.setLabel("testLabel3");
        assertEquals(lukuvinkki.getAddTime(), lukuvinkki.getModifiedTime());
    }

    @Test
    public void validLinkCanBeAddedToLukuvinkki() {
        URL link = null;
        boolean excpt = false;
        try {
            // Huom! Koska validointi on javan URL luokkaan sisään rakennettuna,
            // ei linkin oikeellisuutta ole tarpeen erikseen varmistaa
            // tässä sovelluksessa testeillä
            link = new URL("https://github.com/VirtualAkseli/Lukuvinkkikirjasto-Napsa");
        } catch (Exception e) {
            excpt = true;
        }
        lukuvinkki.setLinkki(link);
        assertEquals(lukuvinkki.getLinkki(), link);
        assertFalse(excpt);

    }

    public class LukuvinkkiImpl extends Lukuvinkki {

        public LukuvinkkiImpl() {
            super("testLabel");
        }
    }

}