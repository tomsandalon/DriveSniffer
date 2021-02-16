package Presentation.PresentationIObjects;

import java.util.List;

public interface IRootFolder {

    String getPath();

    long getSize();

    void setSize(long size);

    String getName();

    List<IPresentationFileFolder> getSubElements();

    void setAsFinal();
}