package Presentation.PresentationIObjects;

public interface IPresentationFileFolder {

    long getSize();

    String getName();

    String getPath();

    boolean isFile();

    boolean isFolder();

}