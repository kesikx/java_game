package com.netology.game.installer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.netology.game.filesystem.DirStructure;
import com.netology.game.filesystem.FsObjectType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Installer implements Installable {
    private final String gameDirectory;

    @Override
    public void install() {
        String logDirName = "temp";
        String logDir = gameDirectory + "\\" + logDirName;
        String logFileName = "log.txt";
        String logFile = logDir + "\\" + logFileName;
        String fileStructurePath = "src/main/resources/file_structure.json";
        Type DirStructureListType = new TypeToken<ArrayList<DirStructure>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        try {
            new File(gameDirectory).mkdir();
            new File(logDir).mkdir();
            new File(logFile).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (Reader gameFileStructure = Files.newBufferedReader(Paths.get(fileStructurePath))) {
            List<DirStructure> dirStructureList = gson.fromJson(gameFileStructure, DirStructureListType);
            createFiles(dirStructureList, gameDirectory, logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Installer(String gameDirectory) {
        this.gameDirectory = gameDirectory;
    }

    private void createFiles(List<DirStructure> dirStructureList, String parentDir, String logFile) {
        if (dirStructureList.size() == 0) {
            return;
        }
        for (DirStructure directory : dirStructureList) {
            String currentDir = parentDir + "\\" + directory.getName();
            if (new File(currentDir).mkdir()) {
                logObjectCreation(logFile, FsObjectType.DIRECTORY, currentDir, OperationResult.SUCCESS);
            }
            for (String file : directory.getFiles()) {
                createFile(currentDir, file, logFile);
            }
            createFiles(directory.getDirectories(), currentDir, logFile);
        }
    }

    private void createFile(String directory, String fileName, String logFile) {
        try {
            if (new File(directory + "\\" + fileName).createNewFile()) {
                logObjectCreation(logFile, FsObjectType.FILE, fileName, OperationResult.SUCCESS);
            }
        } catch (IOException e) {
            logObjectCreation(logFile, FsObjectType.FILE, fileName, OperationResult.FAILURE);
        }
    }

    private void logObjectCreation(String logfile, FsObjectType fsObjectType, String fsObjName, OperationResult result) {
        String string = String.format("%s '%s' creation %s \n", fsObjectType, fsObjName, result);
        try (FileWriter file = new FileWriter(logfile, true)) {
            file.write(string);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
