package Logic.Interfaces;

import Presentation.Result;

/**
 * This is the Controller interface, which is a fascade (from Fascade Design Pattern) for the logic layer
 * @author tomsandalon
 */
public interface IController {
    /**
     * Perform a scan of the path held by the controller
     * @return A result containing the scanned path, or an error
     */
    Result scan();

    /**
     * Set and return the folder
     * @param path The path to the folder
     * @return a result containing the requested folder, or an error if the path provided is invalid or is a file
     */
    Result navigateTo(String path); //assuming path is a subpath of the one given at construction

    /**
     * Get an updated folder
     * @return A result containing an updated view of the current folder, or an error
     */
    Result update();

    /**
     * Returns whether the current scan is done
     * @return True iff the current scan is done
     */
    boolean isFinal();

    /**
     * Delete the file/folder in the path
     * @param path The path of the file/folder to delete
     * @return A result which indicates whether the delete was successful, or an error.
     */
    Result delete(String path);

    /**
     * Return the current directory of the scanner
     * @return The current directory
     */
    Result getCurrentDir();
}
