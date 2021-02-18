package Presentation;

import Logic.Interfaces.IController;
import Logic.TwoThread.TwoThreadController;
import Presentation.Listeners.AnalyzeListener;
import Presentation.Listeners.BackListener;
import Presentation.Listeners.InfoListener;
import Presentation.Listeners.SelectFolderListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeWindow extends JFrame {
//    public static String selectedPath = "D:\\OLD\\MARIK";
    private final static String mouseLeftClickIcon = new java.io.File("res").getAbsolutePath() + File.separator + "mouse_left_click_icon.png";
    private final static String mouseRightClickIcon = new java.io.File("res").getAbsolutePath() + File.separator + "mouse_right_click_icon.png";
    private final static String infoMessage = "DriveSniffer analyzes a folder you select, presents its contents and allows you understand which files and folders take most of your precious storage.\n" +
        "Usage:\n" +
        "- Select a folder to analyze.\n" +
        "- Press analyze to start the scan.\n" +
        "- Left click on the relevant slice to inspect that folder\n" +
        "- Right click on the relevant slice to delete that file or folder\n" +
        "- Back button to go back in path.\n";
    private final static String infoIcon = new java.io.File("res").getAbsolutePath() + File.separator + "information_icon.png";
    private final JFileChooser fileChooser;
    private File selectedFolder;
    private JComponent analyzeBtn;
    private PieChart chart;
    private JPanel panelChart;
    private ChartReporter chartReport;

    public HomeWindow() {
        setTitle("Drive Sniffer");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(DimMax.width / 2, (int) (DimMax.height * 0.75));
        this.fileChooser = new JFileChooser();
//        fileChooser.setCurrentDirectory(new java.io.File(selectedPath)); // start at application current directory
        fileChooser.setCurrentDirectory(new java.io.File(".")); // start at application current directory

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public void configureWindow() {
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
//        configureLOGO(container);
        configureToolBar(container);

        Dimension windowSizes = this.getSize();
        configureChart(container, windowSizes.width, windowSizes.height);
        configureAnalyzeButton(container, 2);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationToCenter(this);
    }

    private void configureToolBar(Container container) {
        JPanel toolBarPanel = new JPanel();
        container.add(toolBarPanel);
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.X_AXIS));
        configureBackButton(toolBarPanel);
        configureSelectButton(toolBarPanel);
        configureImageButton(toolBarPanel, HomeWindow.infoIcon, new InfoListener(this), "info");
        configureImageButton(toolBarPanel, HomeWindow.mouseLeftClickIcon, "Navigate to folder", "Left Mouse button for", null);
        configureImageButton(toolBarPanel, HomeWindow.mouseRightClickIcon, "Delete File or Folder", "Right Mouse button for", null);
    }

    private void configureImageButton(Container container, String imagePath, ActionListener listener, String title) {
        try {
            AbstractButton button =  configureImageButton(imagePath, listener);
            container.add(button);
        } catch (IOException e) {
            configureButton(container, title, null);
        }
    }

    private void configureBackButton(Container container) {
        configureButton(container, "Back", new BackListener(this));
    }

    private JButton configureButton(Container container, String title, ActionListener listener){
        JButton button = new JButton(title);
        if(listener != null) button.addActionListener(listener);
        container.add(button);
        return button;
    }

    private void configureSelectButton(Container container) {
        configureButton(container, "Select Folder/Drive", new SelectFolderListener(this.fileChooser, this));
    }

    private void configureAnalyzeButton(Container container, int position) {
        JButton button = new JButton("Analyze");
        this.analyzeBtn = button;
        JPanel panelBtn = new JPanel();
        button.addActionListener(new AnalyzeListener(this));
        panelBtn.add(button);
        container.add(panelBtn, position);
        button.setVisible(false);
    }

    private void configureChart(Container container, int width, int height) {
        this.panelChart = new JPanel();
        this.chart = new PieChart(panelChart, width, height);
        container.add(panelChart);
        this.panelChart.setVisible(false);
    }

    private AbstractButton configureImageButton(String imagePath, ActionListener listener) throws IOException{
        BufferedImage buttonIcon = ImageIO.read(new File(imagePath));
        JButton button = new JButton(new ImageIcon(buttonIcon));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        if(listener != null) button.addActionListener(listener);
        return button;
    }

    private void configureImageButton(Container container, String imagePath, String description, String leftOptionalDescription, ActionListener listener) {
        StringBuilder text = new StringBuilder(description);
        try {
            AbstractButton button =  configureImageButton(imagePath, listener);
            container.add(button);
        } catch (IOException e) {
            text = text.insert(0, ' ');
            text = text.insert(0, leftOptionalDescription);
        }
        JLabel descriptionLabel = new JLabel(text.toString());
        container.add(descriptionLabel);
    }

    private void configureLOGO(Container container) {
        JLabel labelLOGO = new JLabel("LOGO");
        JPanel panelLOGO = new JPanel();
        panelLOGO.add(labelLOGO);
        container.add(panelLOGO);
    }

    public void onSelectedFolder(File dir) {
        this.selectedFolder = dir;
        this.analyzeBtn.setVisible(true);
        //update window
        setVisible(true);
    }

    public void onBack() {
        if (chartReport == null) return;
        chartReport.onBack();
    }


    public void onStartAnalyzing() {
        String selectedPath = this.selectedFolder.getAbsolutePath();
        IController dirController = new TwoThreadController(selectedPath);
        //Logic.start
        Result result = dirController.scan();
        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(this, result.getErrorMsg(), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.panelChart.setVisible(true);
        this.analyzeBtn.setVisible(false);
        this.chartReport = ChartReporter.createChartReporter(this.chart, result.getResult(), dirController, selectedPath, this);
        Thread chartReporter = new Thread(chartReport);
        chartReporter.start();
        setVisible(true);
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
        int y = (bounds.height + insets.top - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    public void onClickInfo() {
        JOptionPane.showMessageDialog(this, HomeWindow.infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
