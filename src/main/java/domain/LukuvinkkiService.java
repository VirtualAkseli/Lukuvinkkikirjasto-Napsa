
package domain;

import dao.LukuvinkkiDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linjokin
 */
public class LukuvinkkiService {
    
    private List<Lukuvinkki> vinkit;
    private final LukuvinkkiDao dao;
    
    
    public LukuvinkkiService(LukuvinkkiDao dao) {
        this.dao = dao;
        this.vinkit = new ArrayList<>();
    }
    
        public List<Lukuvinkki> lueVinkit() throws IOException, FileNotFoundException {
        try {
            this.vinkit = dao.readFromFile();
        } catch (Exception e) {
            this.vinkit = new ArrayList<>();
        }
        return this.vinkit;
    }

    public void saveListToFile(List<Lukuvinkki> vinkit) {
        dao.saveListToFile(vinkit);
    }

    public void deleteFromFile(Lukuvinkki v) {
        dao.deleteFromFile(v);
    }

    public List<Lukuvinkki> getVinkit() {
        return vinkit;
    }

    public void setVinkit(List<Lukuvinkki> vinkit) {
        this.vinkit = vinkit;
    }
    
    
}
