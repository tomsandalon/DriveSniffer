import Presentation.HomeWindow;

import javax.swing.*;
import java.awt.*;
import java.text.AttributedString;

public class Main {

    public static void main (String args[]) {
        HomeWindow startPage = new HomeWindow();
        startPage.configureWindow();
        startPage.onStartAnalyzing();
    }
}
