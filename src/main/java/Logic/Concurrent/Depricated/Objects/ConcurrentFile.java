package Logic.Concurrent.Depricated.Objects;

import Logic.Interfaces.IFile;
import Logic.Interfaces.IFolder;

import java.io.File;

public class ConcurrentFile implements IFile {

    private final ConcurrentFolder parent;
    private final String path;
    private final long size;
    private final String name;

    public ConcurrentFile(ConcurrentFolder parent, String path, String name, long size) {
        this.parent = parent;
        this.path = path;
        this.size = size;
        this.name = name;
    }

    @Override
    public String getShortName() {
        return name;
    }

    @Override
    public String getFullName() {
        return path;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public IFolder getParent() {
        return parent;
    }

    @Override
    public String getPath() { //same as full name
        return path;
    }

    @Override
    public boolean delete() {
        File file = new File(path);
        if (!file.delete()) return false;
        parent.getFiles().remove(path);
        return true;
    }

}
