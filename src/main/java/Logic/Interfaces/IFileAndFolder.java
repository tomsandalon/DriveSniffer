package Logic.Interfaces;

public interface IFileAndFolder {
    String getShortName();
    String getFullName();
    long getSize();
    IFolder getParent();
    String getPath();
    boolean delete();
}
