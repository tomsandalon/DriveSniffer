package Logic.Interfaces;

import Logic.Objects.Folder;

public interface IFileAndFolder {
    String getShortName();
    String getFileType();
    String getFullName();
    long getSize();
    Folder getParent();
    String getPath();
    String delete();
}
