package Logic.Interfaces;

import Logic.Interfaces.IFileAndFolder;

public interface IScanner {
    IFolder scan(String path);

    String getPath();
}
