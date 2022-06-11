import com.netology.game.installer.Installer;

import java.io.IOException;

public class Main {
    public static final String INSTALL_DIRECTORY = "C:\\Games";
    public static void main(String[] args) throws IOException {
        Installer installer = new Installer(INSTALL_DIRECTORY);
        installer.install();
    }
}
