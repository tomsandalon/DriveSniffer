package Logic.Objects;

public class FileFolderSharedMethods {

    protected String deleteSelf(Folder parent, String path, String shortName) {
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
