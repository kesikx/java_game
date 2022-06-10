package com.netology.game.filesystem;

import java.util.List;

public class DirStructure {
    private String name;
    private List<String> files;
    private List<DirStructure> directories;

    public String getName() {
        return name;
    }

    public List<String> getFiles() {
        return files;
    }

    public List<DirStructure> getDirectories() {
        return directories;
    }
}
