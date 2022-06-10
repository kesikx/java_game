package com.netology.game.saves;

import com.netology.game.gameprogress.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SaveGame {
    public static String ZIP_FILE = "saves.zip";
    public static void save(GameProgress gameProgress, String path, String saveName) {
        String saveFile = path + "\\" + saveName + ".dat";
        try (FileOutputStream fos = new FileOutputStream(saveFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path + "\\" + ZIP_FILE))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(path + "\\" + file)) {
                    ZipEntry entry = new ZipEntry(file);
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                }
                 catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        files.forEach(file -> new File(path + "\\" + file).delete());
    }
}
