import com.netology.game.gameprogress.GameProgress;
import com.netology.game.installer.Installable;
import com.netology.game.installer.Installer;
import com.netology.game.saves.LoadGame;
import com.netology.game.saves.SaveGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String INSTALL_DIRECTORY = "C:\\Games";
    public static final String SAVES_DIRECTORY = INSTALL_DIRECTORY + "\\savegames";
    public static final String GAME_SAVE = "save1";

    public static void main(String[] args) throws IOException {
        Installable installer = new Installer(INSTALL_DIRECTORY);
        installer.install();

        List<String> saves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GameProgress gameProgress = new GameProgress(5, 10, 20, 30.0);
            String saveName = "save" + i;
            SaveGame.save(gameProgress, SAVES_DIRECTORY, saveName);
            saves.add(saveName + ".dat");
        }
        SaveGame.zipFiles(SAVES_DIRECTORY, saves);
        GameProgress gameProgress = LoadGame.load(SAVES_DIRECTORY, GAME_SAVE);
        System.out.println(gameProgress);
    }
}
