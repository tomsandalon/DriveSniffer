package Presentation.Listeners;

import Presentation.HomeWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectFolderListener implements ActionListener {
    private final JFileChooser fileChooser;
    private final HomeWindow parent;

    public SelectFolderListener(JFileChooser fileChooser, HomeWindow parent) {
        this.fileChooser = fileChooser;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int returnVal = this.fileChooser.showSaveDialog(this.parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = this.fileChooser.getSelectedFile();
            parent.onSelectedFolder(selectedFolder);
        }
    }
}
