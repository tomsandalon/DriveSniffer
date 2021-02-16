package Presentation.PresentationIObjects;

import Logic.Interfaces.IFile;
import Logic.Interfaces.IFileAndFolder;

public class PresentationFileFolder implements IPresentationFileFolder {

    boolean isFile;
    String name;
    String path;
    long size;

    public PresentationFileFolder(boolean isFile, String name, String path, long size) {
        this.isFile = isFile;
        this.name = name;
        this.path = path;
        this.size = size;
    }

    public PresentationFileFolder(IFileAndFolder file) {
        this.isFile = (file instanceof IFile);
        this.name = file.getFullName();
        this.path = file.getPath();
        this.size = file.getSize();
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
    public String getPath() {
        return path;
    }

    @Override
    public boolean isFile() {
        return isFile;
    }

    @Override
    public boolean isFolder() {
        return !isFile;
    }

}
