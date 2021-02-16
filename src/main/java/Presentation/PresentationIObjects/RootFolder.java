package Presentation.PresentationIObjects;

import Logic.Concurrent.Objects.ConcurrentRootFolder;
import Logic.Interfaces.IFile;
import Logic.Interfaces.IFileAndFolder;
import Presentation.Result;

import java.util.ArrayList;
import java.util.List;

public class RootFolder implements IRootFolder {

    private final String path;
    private long size;
    private final String name;
    private List<IPresentationFileFolder> elements;
    private boolean isFinal;

    public RootFolder(String path, long size, String name) {
        this.path = path;
        this.size = size;
        this.name = name;
        this.elements = new ArrayList<>();
    }

    public RootFolder(ConcurrentRootFolder result) {
        this.path = result.getPath();
        this.size = result.getSize();
        this.name = result.getFullName();
        this.isFinal = result.isFinalSize();
        this.elements = new ArrayList<>();
        for (IFileAndFolder file : result.getFiles().values()) {
            this.elements.add(new PresentationFileFolder(file));
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<IPresentationFileFolder> getSubElements() {
        return null;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public void setAsFinal() {
        this.isFinal = true;
    }
}
