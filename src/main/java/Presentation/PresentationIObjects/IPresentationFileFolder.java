package Presentation.PresentationIObjects;

/**
 * The presentation interface for files and folders
 *
 * @author tomsandalon
 */
public interface IPresentationFileFolder {

    /**
     * Return the size of the file/folder
     *
     * @return The size
     */
    long getSize();

    /**
     * Return the name of the file/folder
     *
     * @return The name
     */
    String getName();

    /**
     * Return the absolute path of the file/folder
     *
     * @return Absolute path
     */
    String getPath();

    /**
     * Indicates whether it is a file
     *
     * @return True iff this is a file
     */
    boolean isFile();

    /**
     * Indicates Whether it is a folder
     *
     * @return True iff this is a folder
     */
    boolean isFolder();

}