package Presentation.Components;

import java.awt.*;
import java.text.AttributedString;

import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.Rotation;

import javax.swing.*;


public class PieChart {
    private DefaultPieDataset result = new DefaultPieDataset();
    private JComponent parent;
    public PieChart(JComponent parent) {
        this.parent = parent;
        final PieDataset dataset = createSampleDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        this.parent.add(chartPanel);
//        setContentPane(chartPanel);
        chartPanel.addChartMouseListener(new ClickSectionListener(this));
    }

    public void updateData(Comparable key, long value){
        result.setValue(key,value);
    }

    private PieDataset createSampleDataset() {
        //TODO Logic.Sample
        result.setValue("A", 20);
        result.setValue("B", 20);
        result.setValue("C", 20);
        result.setValue("D", 20);
        result.setValue("E", 20);
        return result;
    }

    private JFreeChart createChart(final PieDataset dataset) {
        final JFreeChart chart = ChartFactory.createPieChart("3D Pie Chart", dataset, false, true, false);
        final PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(0);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setLabelGenerator(new CustomLabelGenerator());
        return chart;
    }

    static class CustomLabelGenerator implements PieSectionLabelGenerator {


        @Override
        public AttributedString generateAttributedSectionLabel(PieDataset pd, Comparable cmprbl) {
            return null;
        }

        @Override
        public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
            String result = null;
            if (dataset != null) {
                result = key.toString();
                System.out.println(result);
            }
            return result;
        }

    }
}
class ClickSectionListener implements ChartMouseListener{
    private PieChart parent;

    public ClickSectionListener(PieChart parent) {
        this.parent = parent;
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        ChartEntity entity = event.getEntity();
        if(entity instanceof PieSectionEntity){
            PieSectionEntity section = (PieSectionEntity) entity;
            //TODO Create Pie chart for selected folder
            // Logic.sample (entity)
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {}
}