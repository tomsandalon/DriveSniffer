import Logic.Interfaces.IController;
import Logic.TwoThread.TwoThreadController;
import Presentation.HomeWindow;
import Presentation.Result;

public class Main {



    public static void main (String args[]) throws InterruptedException {
//        HomeWindow startPage = new HomeWindow();
//        startPage.configureWindow();
//        startPage.onStartAnalyzing();
        IController controller = new TwoThreadController("/Users/tomsandalon/Documents");
        Result res = controller.scan();
        while(!controller.isFinal()) {
            Thread.sleep(100);
        }
        controller.delete("/Users/tomsandalon/Documents/Numeric Analysis");
    }
}
