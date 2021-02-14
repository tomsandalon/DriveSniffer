package Logic.Interfaces;

public interface IController {
    IFileAndFolder scan();

    boolean delete(String path);
}
