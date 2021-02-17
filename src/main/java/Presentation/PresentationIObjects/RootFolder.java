package Presentation.PresentationIObjects;

import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;

import java.util.ArrayList;
import java.util.List;

public class RootFolder implements IRootFolder {

    private final String path;
    private final long size;
    private final String name;
    private final List<IPresentationFileFolder> elements;

    public RootFolder(IFolder result) {
        this.path = result.getPath();
        this.size = result.getSize();
        this.name = result.getName();
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
    public String getName() {
        return name;
    }

    @Override
    public List<IPresentationFileFolder> getSubElements() {
        return this.elements;
    }
}
