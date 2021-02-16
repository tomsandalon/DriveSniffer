package Presentation.PresentationIObjects;

import java.util.List;

public interface IRootFolder {

    String getPath();

    long getSize();


    String getName();

    List<IPresentationFileFolder> getSubElements();

    boolean isFinal();
}