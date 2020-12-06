
import dao.LukuvinkkiDao;
import domain.LukuvinkkiService;
import io.ConsoleIO;
import ui.ConsoleUI;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConsoleIO console = new ConsoleIO();
        LukuvinkkiDao dao = new LukuvinkkiDao();
        LukuvinkkiService service = new LukuvinkkiService(dao);

        ConsoleUI ui = new ConsoleUI(console, service);
        ui.run();
    }

}
