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

    public void onSelectedSection(String name){
        Result resultDir = this.controller.navigateTo(this.currentPath.navigate(name));
        if(resultDir.isSuccess()){
            this.chart.makeChart(resultDir.getResult());
        } else {
            //TODO present message scan is not complited yet
        }
    }

    @Override
    public void run() {
        IRootFolder rootFolder;
        try {
            do {
                Thread.sleep(UPDATE_INTERVAL);
                Result result = this.controller.update();
                if (!result.isSuccess()) {
                    //TODO Present Error message
                    System.out.print("I am dead");
                    return;
                }
                rootFolder = result.getResult();
                this.chart.updateChart(rootFolder);
            } while (!rootFolder.isFinal());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
