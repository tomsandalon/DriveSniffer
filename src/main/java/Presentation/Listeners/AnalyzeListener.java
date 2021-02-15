package Presentation.Listeners;

import Presentation.HomeWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeListener implements ActionListener {

    private HomeWindow parent;

    public AnalyzeListener(HomeWindow parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        parent.onStartAnalyzing();
    }
}
