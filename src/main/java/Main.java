import Logic.Controller;
import Logic.Interfaces.IController;
import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IScanner;
import Logic.ScanStrategy.LinearScanner;

public class Main {

    public static void main (String args[]) {
        System.out.println("Hello world");
        String path = "/Users/tomsandalon/Documents";
        IScanner scanner = new LinearScanner(path);
        IController controller = new Controller(scanner);
        IFileAndFolder result = controller.scan();
        System.out.println("Size is " + result.getSize());
        boolean deleteSuccess = controller.delete("/Users/tomsandalon/Documents/Numeric Analysis");
        System.out.println(deleteSuccess);
    }
}
