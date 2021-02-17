package Logic.Interfaces;

import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;

public interface IFile extends IFileAndFolder {
    @Override
    String getShortName();

    @Override
    String getFullName();

    @Override
    long getSize();

    @Override
    IFolder getParent();

    @Override
    String getPath();

    boolean delete();
}
