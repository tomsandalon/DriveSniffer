import Presentation.HomeWindow;

public class Main {


    public static void main(String[] args) throws InterruptedException {
//        HomeWindow startPage = new HomeWindow();
//        startPage.configureWindow();

        String desc = "press me";
        StringBuilder text = new StringBuilder(desc);
        System.out.println(text.toString());
        String left = "From left";
        text = text.insert(0, left);
        System.out.println(text.toString());
//        JFrame frame = new JFrame();
//        frame.setSize(1000,700);
//        JButton button = new JButton("Hey");
//        frame.add(button);
//
//        int input = JOptionPane.showOptionDialog(frame, "Delete?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
//        System.out.println(input);
//        frame.setVisible(true);
//        IController controller = new TwoThreadController("/Users/tomsandalon/Documents");
//        Result res = controller.scan();
//        while(!controller.isFinal()) {
//            Thread.sleep(100);
//        }
//        controller.delete("/Users/tomsandalon/Documents/Numeric Analysis");
    }
}