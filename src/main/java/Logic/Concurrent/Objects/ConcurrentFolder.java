package Logic.Concurrent.Objects;

import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentFolder implements IFolder {

    private ConcurrentFolder parent;
    private String path;
    private AtomicLong size;
    private Map<String, IFileAndFolder> files;
    private String shortName;

    public ConcurrentFolder(String path, ConcurrentFolder parent, String shortName) {
        this.path = path;
        this.size = new AtomicLong(0);
        this.files = new ConcurrentHashMap<>();
        this.parent = parent;
        this.shortName = shortName;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public Map<String, IFileAndFolder> getFiles() {
        return files;
    }

    @Override
    public IFolder getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String delete() { //TODO
        return null;
    }

    @Override
    public String getFullName() {
        return shortName;
    }

    @Override
    public long getSize() {
        return size.get();
    }

    public void addToSize(long size) {
        this.size.addAndGet(size);
        if (parent != null) this.parent.addToSize(size);
    }

    @Override
    public void addFile(IFileAndFolder file) {
        files.put(file.getFullName(), file);
    }
}
