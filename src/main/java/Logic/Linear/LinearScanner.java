package Logic.Linear;

import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IScanner;
import Logic.Linear.Objects.Folder;
import Logic.Interfaces.IFolder;
import Logic.Interfaces.IFile;
import Logic.enums.Type;
import Logic.Linear.Objects.File;
import java.nio.file.FileSystems;

public class LinearScanner implements IScanner {

    private final String separator = FileSystems.getDefault().getSeparator();

    private final String path;

    public LinearScanner(String path) {
        this.path = path;
    }

    private Type checkType(String path) {
        java.io.File file;
        try {
            file = new java.io.File(path);
            if (!file.exists()) throw new Exception("");
        } catch (Exception e) {
            try{
                file = new java.io.File(this.path.concat(separator).concat(path));
                if (!file.exists()) throw new Exception("");
            }
            catch (Exception ex) {
                file = new java.io.File(this.path.concat(path));
            }
        }
        return file.isDirectory() ? Type.Directory : Type.File;
    }

    private String shortenName(String path) {
        return path.substring(path.lastIndexOf(separator) + 1);
    }

    private IFile scanFile(String path, IFolder parent) {
        try {
            java.io.File file = new java.io.File(path);
            String fullName = shortenName(path);

            int dotIdx = fullName.lastIndexOf('.');
            String name = (dotIdx == -1) ? fullName : fullName.substring(0, dotIdx);
            String extension = (dotIdx == -1) ? "" : fullName.substring(dotIdx + 1);

            long length = file.length();

            return new File(name, extension, length, parent);
        }
        catch (Exception e) {
            return null;
        }
    }

    private void fill(IFolder parent, String[] fileNames) {
        for (String name : fileNames) {
            if (name.equals("")) continue;
            String workName = parent.getPath().concat(separator.concat(name));
            IFileAndFolder scanned = (checkType(workName) == Type.Directory) ? scanFolder(workName, parent) : scanFile(workName, parent);
            if (scanned != null) parent.addFile(scanned);
        }
    }

    private IFolder scanFolder(String path, IFolder parent) {
        try {
            String parentPath = (parent != null) ? parent.getPath().concat(separator) : "";
            java.io.File dir = new java.io.File(path);
            String[] files = dir.list();
            IFolder ret = (parent == null) ? new Folder(shortenName(path), path) : new Folder(shortenName(path), parent);
            if (files == null) return null;
            fill(ret, files);
            return ret;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public IFolder scan(String path) {
        Type type;
        try {
            type = checkType(path);
        }
        catch (Exception e) {
            return null;
        }
        return scanFolder(path, null);
    }

    @Override
    public String getPath() {
        return path;
    }
}
