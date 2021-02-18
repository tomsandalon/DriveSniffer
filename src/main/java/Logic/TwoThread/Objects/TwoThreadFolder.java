package Logic.TwoThread.Objects;

import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TwoThreadFolder implements IFolder {

    private final TwoThreadFolder parent;
    private final String path;
    private final Map<String, IFileAndFolder> files;
    private final String name;
    private long size;
    private boolean sizeIsFinal;

    public TwoThreadFolder(String path, TwoThreadFolder parent, String shortName) {
        this.path = path;
        this.files = new ConcurrentHashMap<>();
        this.parent = parent;
        this.name = shortName;
        size = -1;
        sizeIsFinal = false;
    }

    @Override
    public String getName() {
        return name;
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
        for (IFileAndFolder file : filesClone) {
            if (!file.delete()) return false;
        }
        File file = new File(path);
        if (!file.delete()) return false;
        if (parent != null) {
            parent.getFiles().remove(path);
            parent.setSizeIsFinal(false);
        }
        return true;
    }

    @Override
    public void addFile(IFileAndFolder file) {
        files.put(file.getPath(), file);
    }

    private void calculateSize() {
        this.size = files.values().stream().mapToLong(IFileAndFolder::getSize).sum();
    }

    @Override
    public long getSize() {
        if (sizeIsFinal) return this.size;
        else calculateSize();
        return this.size;
    }

    @Override
    public void setSizeIsFinal(boolean sizeIsFinal) {
        boolean prevState = this.sizeIsFinal;
        this.sizeIsFinal = sizeIsFinal;
        calculateSize();
        if (!sizeIsFinal || prevState) return;
        for (IFileAndFolder subFolder : files.values())
            if (subFolder instanceof IFolder) ((IFolder) subFolder).setSizeIsFinal(true);
    }
}
