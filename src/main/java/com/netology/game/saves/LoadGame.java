package com.netology.game.saves;

import com.netology.game.gameprogress.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LoadGame {
    public static GameProgress load(String path, String saveName) {
        String saveFile = saveName + ".dat";
        openZip(path, saveFile);
        GameProgress gameProgress = openProgress(path, saveFile);
        new File(path + "\\" + saveFile).delete();
        return gameProgress;
    }

    private static void openZip(String path, String saveFile) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path + "\\" + SaveGame.ZIP_FILE))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(saveFile)) {
                    try (FileOutputStream fos = new FileOutputStream(path + "\\" + entry.getName())) {
                        for (int c = zis.read(); c != -1; c = zis.read()) {
                            fos.write(c);
                        }
                        fos.flush();
                        zis.closeEntry();
                        fos.close();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static GameProgress openProgress(String path, String saveFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path + "\\" + saveFile); ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
