package Logic.Objects;
import Logic.Interfaces.IFileAndFolder;

import java.nio.file.FileSystems;


public class File extends FileFolderSharedMethods implements IFileAndFolder {
    private final String shortName;
    private final String fileType;
    private final long size;
    private final Folder parent;
    private final String path;
    private final String fullName;

    public File(String shortName, String fileType, long size, Folder parent) {
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
    public String getFileType() {
        return fileType;
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
    public Folder getParent() {
        return parent;
    }

    @Override
    public String getPath() {
        return null;
    }

    public String fullName() {
        return fullName;
    }

    public String delete() {
        return deleteSelf(parent, path, getShortName());
    }
}
