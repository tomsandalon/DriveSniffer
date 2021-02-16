package Presentation.PresentationIObjects;

import java.util.List;

public interface IRootFolder {

    String getPath();

    long getSize();

    long updateSize();

    String getName();

    List<IPresentationFileFolder> getSubElements();

}