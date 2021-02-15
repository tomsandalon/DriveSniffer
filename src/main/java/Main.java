import Logic.Concurrent.ConcurrentController;
import Logic.Concurrent.Objects.ConcurrentRootFolder;
import Logic.Interfaces.IController;

import static java.lang.Thread.sleep;

public class Main {

    public static void main (String[] args) throws InterruptedException {
        System.out.println("Hello world");
        String path = "/Users/tomsandalon/Documents";
        long size = -1;
        for (int i = 0; i < 20; i++) {
            IController controller = new ConcurrentController(path);
            ConcurrentRootFolder result = (ConcurrentRootFolder) controller.scan();
            while(!result.isFinalSize()) {
                sleep(100);
            }
            if (size == -1) {
                size = result.getSize();
                continue;
            }
            if (size != result.getSize()) {
                System.out.println("Not deterministic");
                return;
            }
        }
        System.out.println("Is deterministic");
    }
}
