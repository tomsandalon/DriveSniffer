package Presentation.PresentationIObjects;

public interface IPresentationFileFolder {

    long getSize();

    void setSize(long size);

    String getName();

    String getPath();

    boolean isFile();

    boolean isFolder();

}