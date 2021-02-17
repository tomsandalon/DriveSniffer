package Presentation.PresentationIObjects;

import java.util.List;

/**
 * This class interface represents the folder shown on screen
 *
 * @author tomsandalon
 */
public interface IRootFolder {

    /**
     * This function returns the absolute path of the folder
     *
     * @return Absolute path
     */
    String getPath();

    /**
     * Returns the size of the folder, in bytes
     *
     * @return Size in bytes
     */
    long getSize();

    /**
     * Return the name of the folder, without its path
     *
     * @return The name of the folder
     */
    String getName();

    /**
     * Return the folder's contents
     *
     * @return The folder's contents
     */
    List<IPresentationFileFolder> getSubElements();
}