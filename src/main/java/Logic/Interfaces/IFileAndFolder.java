package Logic.Interfaces;

/**
 * The interface for files and folders
 *
 * @author tomsandalon
 */
public interface IFileAndFolder {

    /**
     * Get the name of the file/folder
     *
     * @return The name of the file/folder
     */
    String getName();

    /**
     * Get the size of the file/folder, in bytes
     *
     * @return The size of the file/folder
     */
    long getSize();

    /**
     * Return the parent folder.
     *
     * @return The parent folder. null if the parent has not been scanned.
     */
    IFolder getParent();

    /**
     * Return the absolute path to the file/folder
     *
     * @return The absolute path
     */
    String getPath();

    /**
     * Deletes the file/folder. If folder, delete the files and folder it contains as well.
     *
     * @return True iff successfully deleted the file/folder
     */
    boolean delete();
}
