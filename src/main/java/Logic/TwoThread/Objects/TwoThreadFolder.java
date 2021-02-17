package Logic.TwoThread.Objects;

import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TwoThreadFolder implements IFolder {

    private TwoThreadFolder parent;
    private String path;
    private AtomicLong size;
    private Map<String, IFileAndFolder> files;
    private String shortName;

    public TwoThreadFolder(String path, TwoThreadFolder parent, String shortName) {
        this.path = path;
        this.size = new AtomicLong(0);
        this.files = new HashMap<>();
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
    public boolean delete() {
        Set<IFileAndFolder> filesClone = new HashSet<>(files.values());
        for (IFileAndFolder file : filesClone){
            if (!file.delete()) return false;
        }
        File file = new File(path);
        if (!file.delete()) return false;
        parent.getFiles().remove(path);
        return true;
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
        files.put(file.getPath(), file);
    }
}
