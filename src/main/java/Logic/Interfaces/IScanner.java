package Logic.Interfaces;

import Logic.Interfaces.IFileAndFolder;

public interface IScanner {
    IFileAndFolder scan(String path);

    String getPath();
}
