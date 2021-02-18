package Presentation;

import Logic.Interfaces.IController;
import Presentation.PresentationIObjects.IPresentationFileFolder;
import Presentation.PresentationIObjects.IRootFolder;
import Presentation.Utils.Path;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

class ChartReporter implements Runnable {
    private static final long UPDATE_INTERVAL = 500;
    private final PieChart chart;
    private final IController controller;
    private Path currentPath;
    private Frame currentWindow;

    public ChartReporter(PieChart chart, IRootFolder rootFolder, IController controller, String rootPath, Frame currentWindow) {
        this.currentWindow = currentWindow;
        this.chart = chart;
        this.controller = controller;
        this.currentPath = new Path(rootPath);
        this.chart.makeChart(rootFolder);
        this.chart.initChart();
    }

    public static ChartReporter createChartReporter(PieChart chart, IRootFolder result, IController dirController, String selectedPath, Frame currentWindow) {
        ChartReporter temp =  new ChartReporter(chart, result, dirController, selectedPath, currentWindow);
        chart.setReporter(temp);
        return temp;
    }

    public void onSelectedSection(String name){
        Result resultDir = this.controller.navigateTo(this.currentPath.navigate(name));
        if(resultDir.isSuccess()){
            System.out.println("Success");
            this.chart.makeChart(resultDir.getResult());
        } else {
            JOptionPane.showMessageDialog(this.currentWindow, resultDir.getErrorMsg(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }



    public void onBack() {
        Result resultDir = this.controller.navigateTo(this.currentPath.goBack());
        if(resultDir.isSuccess()){
            this.chart.makeChart(resultDir.getResult());
        } else {
            JOptionPane.showMessageDialog(this.currentWindow, resultDir.getErrorMsg(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }



    @Override
    public void run() {
        try {
            do {
                Thread.sleep(UPDATE_INTERVAL);
                Result result = this.controller.update();
                if (!result.isSuccess()) {
                    JOptionPane.showMessageDialog(this.currentWindow, result.getErrorMsg(), "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                IRootFolder rootFolder = result.getResult();
                this.chart.updateChart(rootFolder);
            } while (!this.controller.isFinal());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDelete(String name) {
        Result result = this.controller.delete( this.currentPath.getCurrent() + File.separator + name);
        if(result.isSuccess()){

            try {
                do {
                    Thread.sleep(UPDATE_INTERVAL);
                } while (!this.controller.isFinal());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result update = this.controller.update();
            this.chart.makeChart(update.getResult());
        } else {
            JOptionPane.showMessageDialog(this.currentWindow, result.getErrorMsg(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
