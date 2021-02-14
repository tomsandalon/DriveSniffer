package Logic.Objects;

import Logic.Interfaces.IFileAndFolder;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Folder extends FileFolderSharedMethods implements IFileAndFolder {
    private final String shortName;
    private final Map<String, IFileAndFolder> files = new HashMap<>();
    private final Folder parent;
    private final String path;
    private final String fullName;

    public Folder(String shortName, Folder parent) {
        this.shortName = shortName;
        this.fullName = shortName;
        this.parent = parent;
        String separator = FileSystems.getDefault().getSeparator();
        path = (parent != null) ? parent.getPath().concat(separator).concat(this.fullName) : this.fullName;
    }

    public Folder(String shortName, String path) {
        this.shortName = shortName;
        this.path = path;
        parent = null;
        fullName = shortName;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    public Map<String, IFileAndFolder> getFiles() {
        return files;
    }

    @Override
    public Folder getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String delete() {
        for (IFileAndFolder obj : files.values()) {
            try {
                obj.delete();
            }
            catch (Exception e) {
                return obj.getPath();
            }
        }
        return deleteSelf(parent, path, getShortName());
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public long getSize() {
        long sizeAcc = 0;
        for (IFileAndFolder obj : this.files.values()) {
            sizeAcc += obj.getSize();
        }
        return sizeAcc;
    }

    @Override
    public String getFileType() {
        return ","; //using illegal char in names in order to specify it's a folder
    }

    public void addFile(IFileAndFolder file) {
        files.put(file.getFullName(), file);
    }
}
