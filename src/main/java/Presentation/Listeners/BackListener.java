package Presentation.Listeners;

import Presentation.HomeWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackListener implements ActionListener {

    private final HomeWindow parent;

    public BackListener(HomeWindow parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.parent.onBack();
    }
}
