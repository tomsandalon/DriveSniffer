package Presentation;

import Logic.Interfaces.IController;
import Presentation.PresentationIObjects.IPresentationFileFolder;
import Presentation.PresentationIObjects.IRootFolder;
import Presentation.Utils.Path;

import java.io.File;
import java.util.List;

class ChartReporter implements Runnable {
    private static final long UPDATE_INTERVAL = 500;
    private final PieChart chart;
    private final IController controller;
    private Path currentPath;

    public ChartReporter(PieChart chart, IRootFolder rootFolder, IController controller, String rootPath) {
        this.chart = chart;
        this.controller = controller;
        this.currentPath = new Path(rootPath);
        this.chart.updateChart(rootFolder);
        this.chart.initChart();
    }

    public static Runnable createChartReporter(PieChart chart, IRootFolder result, IController dirController, String selectedPath) {
        ChartReporter temp =  new ChartReporter(chart, result, dirController, selectedPath);
        chart.setReporter(temp);
        return temp;
    }

    public void onSelectedSection(String name){
        Result resultDir = this.controller.navigateTo(this.currentPath.navigate(name));
        if(resultDir.isSuccess()){
            System.out.println("Success");
            this.chart.makeChart(resultDir.getResult());
        } else {
            //TODO present message scan is not complited yet
        }
    }

    @Override
    public void run() {
        try {
            do {
                Thread.sleep(UPDATE_INTERVAL);
                Result result = this.controller.update();
                if (!result.isSuccess()) {
                    //TODO Present Error message
                    System.out.print("I am dead");
                    return;
                }
                IRootFolder rootFolder = result.getResult();
                this.chart.updateChart(rootFolder);
            } while (!this.controller.isFinal());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
