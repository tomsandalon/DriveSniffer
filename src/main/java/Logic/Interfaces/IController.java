package Logic.Interfaces;

public interface IController {
    IFolder scan();

    boolean delete(String path);
}
