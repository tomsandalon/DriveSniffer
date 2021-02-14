import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main (String args[]) {
        Frame f=new JFrame();//creating instance of JFrame

        JTextField searchField = new JTextField("Search here Fo");
        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);//x axis, y axis, width, height

        f.add(b);//adding button in JFrame

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }
}
