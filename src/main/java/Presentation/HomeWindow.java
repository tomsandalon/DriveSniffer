package Presentation;

import Presentation.Components.PieChart;
import Presentation.Listeners.AnalyzeListener;
import Presentation.Listeners.SelectFolderListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HomeWindow extends JFrame {
    private JFileChooser fileChooser;
    private File selectedFolder;
    private PieChart chart;
    private JComponent description; //TODO find bettere solution to hide description
    private JComponent analyzeBtn;
    public HomeWindow() {
        setTitle("Drive Sniffer");
        setSize(1000, 800);

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


        //TODO Logic.start??
        // Note Pie chart.createSampleDataSet comes at first time to sample the Logic
        JPanel panelChart = new JPanel();
        this.chart = new PieChart(panelChart);
//        panelChart.add(chart);
        this.getContentPane().add(panelChart);
        this.description.setVisible(false);
//        this.analyzeBtn.setVisible(false);
        setVisible(true);
//        Thread chartReporter = new Thread(new ChartReporter(this.chart));
//        chartReporter.start();
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

class ChartReporter implements Runnable{
    private PieChart chart;

    public ChartReporter(PieChart chart) {
        this.chart = chart;
    }

    @Override
    public void run() {
        //TODO while
        //  Logic.sample
        //  chart.update(data)
    }
}