package Logic.Interfaces;

import java.util.Map;

/**
 * Interface for folders
 *
 * @author tomsandalon
 */
public interface IFolder extends IFileAndFolder {

    /**
     * Return the elements map of the folder.
     * Path is Key.
     * Subfolder/file is Value.
     *
     * @return The Key-Value mapping of the paths to the subfolder
     */
    Map<String, IFileAndFolder> getFiles();

    /**
     * Add a file or folder to this folder's map
     *
     * @param file The file to add
     */
    void addFile(IFileAndFolder file);

    /**
     * Set that the folder size is already known, and doesn't need to be recalculated
     *
     * @param state The state of the size, true iff the size is final
     */
    void setSizeIsFinal(boolean state);
}
