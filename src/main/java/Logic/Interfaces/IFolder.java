package Logic.Interfaces;

import java.util.Map;

public interface IFolder extends IFileAndFolder {
    @Override
    String getShortName();

    Map<String, IFileAndFolder> getFiles();

    @Override
    IFolder getParent();

    @Override
    String getPath();

    @Override
    String delete();

    @Override
    String getFullName();

    @Override
    long getSize();

    void addFile(IFileAndFolder file);
}
