package Logic.Linear;

import Logic.Interfaces.IController;
import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IScanner;
import Logic.Interfaces.IFolder;

import java.nio.file.FileSystems;

public class Controller implements IController {

    private final String initialPath;
    private final String separator = FileSystems.getDefault().getSeparator();
    private final IFolder data;
    private final IScanner scanner;

    public Controller(IScanner scanner) {
        this.scanner = scanner;
        this.initialPath = scanner.getPath();
        this.data = scan();
    }

    @Override
    public IFolder scan() {
        if (data != null) return data;
        return scanner.scan(initialPath);
    }

    private IFileAndFolder getFromFolderAndFile(String path) {
        if (!this.initialPath.equals(path.substring(0, initialPath.length()))) return null;
        String relativePath = path.substring(initialPath.length());
        String[] relativePathArr = relativePath.split(separator);
        IFileAndFolder cur = data;

        for (String name : relativePathArr) {
            try {
                if (name.equals("")) continue;
                if  (((IFolder)cur).getFiles().containsKey(name)) cur = (((IFolder)cur).getFiles().get(name));
                else throw new Exception ("Illegal path");
            }
            catch (Exception e) {
                return null;
            }
        }
        return cur;
    }

    @Override
    public boolean delete(String path) {
        IFileAndFolder toDelete = getFromFolderAndFile(path);
        if (toDelete == null) return false;
        String resultOfDel = toDelete.delete();
        return resultOfDel.equals("");
    }
}
