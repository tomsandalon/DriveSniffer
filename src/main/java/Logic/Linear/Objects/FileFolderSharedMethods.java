package Logic.Linear.Objects;

import Logic.Interfaces.IFolder;

public class FileFolderSharedMethods {

    protected String deleteSelf(IFolder parent, String path, String shortName) {
        try{
            java.io.File toDelete = new java.io.File(path);
            if (parent != null) parent.getFiles().remove(shortName);
            if (toDelete.delete()) return "";
            return path;
        }
        catch (Exception e) {
            return path;
        }
    }
}
