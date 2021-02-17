package Presentation;

import java.text.AttributedString;
import java.util.List;

import Presentation.PresentationIObjects.IPresentationFileFolder;
import Presentation.PresentationIObjects.IRootFolder;
import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;


public class PieChart {
    private int width;
    private int height;
    private DefaultPieDataset result; // PieChartDataSet
    private JComponent parent;
    private ChartReporter reporter;
    private boolean isAdded;
    public PieChart(JComponent parent, int width, int height) {
        this.width = width;
        this.height = height;
        this.parent = parent;
        this.isAdded = false;
        this.result = new DefaultPieDataset();
    }

    public void setReporter(ChartReporter reporter) {
        this.reporter = reporter;
    }

    public void initChart(){
        if (isAdded) return;
        final PieDataset dataset = result;
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(width, height));
        this.parent.add(chartPanel);
        chartPanel.addChartMouseListener(new ClickSectionListener(this));
        isAdded = true;
    }

    public void makeChart(IRootFolder rootFolder){
        result.clear();
        updateChart(rootFolder);
    }

    public void updateChart(IRootFolder rootFolder){
        List<IPresentationFileFolder> subElements = rootFolder.getSubElements();
        for (IPresentationFileFolder element : subElements) {
            result.setValue(element.getName(), element.getSize());
        }
    }

    private JFreeChart createChart(final PieDataset dataset) {
        final JFreeChart chart = ChartFactory.createPieChart("3D Pie Chart", dataset, false, true, false);
        final PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(0);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setLabelGenerator(new CustomLabelGenerator());
        return chart;
    }

    public void onSelectedSection(String name){
        this.reporter.onSelectedSection(name);
    }
}
class ClickSectionListener implements ChartMouseListener{
    private PieChart chart;

    public ClickSectionListener(PieChart parent) {
        this.chart = parent;
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        ChartEntity entity = event.getEntity();
        if(entity instanceof PieSectionEntity){
            PieSectionEntity section = (PieSectionEntity) entity;
            this.chart.onSelectedSection((String) section.getSectionKey());
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {}
}

class CustomLabelGenerator implements PieSectionLabelGenerator {


    @Override
    public AttributedString generateAttributedSectionLabel(PieDataset pd, Comparable cmprbl) {
        return null;
    }

    @Override
    public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
        String result = null;
        if (dataset != null) {
            result = key.toString() + "," + dataset.getValue(key) + " Bytes";
        }
        return result;
    }

}