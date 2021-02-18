package Presentation.Listeners;

import Presentation.HomeWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoListener implements ActionListener {
    private final HomeWindow parent;

    public InfoListener(HomeWindow parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.onClickInfo();
    }
}
