package Logic.Interfaces;

import Presentation.Result;

public interface IController {
    Result scan();

    Result navigateTo(String path); //assuming path is a subpath of the one given at construction

    Result update();

    boolean delete(String path);
}
