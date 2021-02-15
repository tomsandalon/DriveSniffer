package Logic.Linear.Objects;
import Logic.Interfaces.IFile;
import Logic.Interfaces.IFolder;

import java.nio.file.FileSystems;


public class File extends FileFolderSharedMethods implements IFile {
    private final String shortName;
    private final String fileType;
    private final long size;
    private final IFolder parent;
    private final String path;
    private final String fullName;

    public File(String shortName, String fileType, long size, IFolder parent) {
        this.shortName = shortName;
        this.fileType = fileType;
        this.size = size;
        this.parent = parent;
        this.fullName = (fileType.length() > 0) ? shortName.concat(".").concat(fileType) : shortName;
        String separator = FileSystems.getDefault().getSeparator();
        path = parent.getPath().concat(separator).concat(fullName);
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public String getFullName() {
        return fullName;
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
    public String getPath() {
        return null;
    }

    public String fullName() {
        return fullName;
    }

    @Override
    public String delete() {
        return deleteSelf(parent, path, getShortName());
    }
}
