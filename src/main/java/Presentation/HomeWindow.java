package Presentation;

import Logic.Concurrent.ConcurrentController;
import Logic.Interfaces.IController;
import Presentation.Listeners.AnalyzeListener;
import Presentation.Listeners.SelectFolderListener;
import Presentation.PresentationIObjects.IPresentationFileFolder;
import Presentation.PresentationIObjects.IRootFolder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class HomeWindow extends JFrame {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGH = 800;
    private final JFileChooser fileChooser;
    private File selectedFolder;
    private JComponent description; //TODO find bettere solution to hide description
    private JComponent analyzeBtn;
    private IController dirController;

    public HomeWindow() {
        setTitle("Drive Sniffer");
        setSize(WINDOW_WIDTH, WINDOW_HEIGH);

        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File(".")); // start at application current directory
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public void configureWindow(){
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        Container container = this.getContentPane();

        configureLOGO(container);

        JButton button = new JButton("Select Folder/Drive");
        JPanel panelBtn = new JPanel();
        button.addActionListener(new SelectFolderListener(this.fileChooser, this));
        panelBtn.add(button);
        container.add(panelBtn);

        configureDescription(container);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationToCenter(this);
    }

    private void configureDescription(Container container) {
        JLabel labelDescription = new JLabel("Description");
        JPanel panelL = new JPanel();
        panelL.add(labelDescription);
        container.add(panelL);
        this.description = labelDescription;
    }

    private void configureLOGO(Container container) {
        JLabel labelLOGO = new JLabel("LOGO");
        JPanel panelLOGO = new JPanel();
        panelLOGO.add(labelLOGO);
        container.add(panelLOGO);
    }

    public void onSelectedFolder(File dir){
        this.selectedFolder = dir;
        JButton button = new JButton("Analyze");
        this.analyzeBtn = button;
        JPanel panelBtn = new JPanel();
        button.addActionListener(new AnalyzeListener(this));
        panelBtn.add(button);
        this.getContentPane().add(panelBtn, 2);
        //update window
        setVisible(true);
    }

    public void onStartAnalyzing(){
        this.dirController = new ConcurrentController(this.selectedFolder.getAbsolutePath());
        //Logic.start
        Result result = this.dirController.scan();

        if(!result.isSuccess()){
            //TODO Show Message
            return;
        }
        JPanel panelChart = new JPanel();
        PieChart chart = new PieChart(panelChart);
        this.getContentPane().add(panelChart);
        this.description.setVisible(false);
//        this.analyzeBtn.setVisible(false);
        setVisible(true);
        Runnable chartReport = new ChartReporter(chart, result.getResult(), this.dirController, this.selectedFolder.getAbsolutePath());
        Thread chartReporter = new Thread(chartReport);
        chartReporter.start();
    }

    private void setLocationToCenter(Frame frame) {
        GraphicsConfiguration config = frame.getGraphicsConfiguration();
        // get screen boundaries
        Rectangle bounds = config.getBounds();
        // insets are like windows bottom toolbar or Mac Menu Bar
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
        // center by x
        int x = (bounds.x + bounds.width - insets.right - frame.getWidth()) / 2;
        // center by y
        int y = (bounds.height + insets.top - frame.getHeight())/2;
        frame.setLocation(x, y);
    }
}
