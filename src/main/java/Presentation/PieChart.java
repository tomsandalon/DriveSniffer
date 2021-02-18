package Presentation;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.AttributedString;
import java.util.List;


public class PieChart {
    private final int width;
    private final int height;
    private final DefaultPieDataset result; // PieChartDataSet
    private final JComponent parent;
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

    public void initChart() {
        if (isAdded) return;
        final PieDataset dataset = result;
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(width, height));
        this.parent.add(chartPanel);
        ClickSectionListener listener = new ClickSectionListener(this);
        chartPanel.addChartMouseListener(listener);
        chartPanel.addMouseListener(listener);
        chartPanel.setPopupMenu(null); // disable
        isAdded = true;
    }

    public void makeChart(IRootFolder rootFolder) {
        result.clear();
        updateChart(rootFolder);
    }

    public void updateChart(IRootFolder rootFolder) {
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


    public void onSelectedSection(String name) {
        this.reporter.onSelectedSection(name);
    }

    public void onDelete(String name) {
        int input = JOptionPane.showOptionDialog(this.parent, "Delete?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (input == 0) { // yes
            this.reporter.onDelete(name);
        }
        // otherwise user pressed X button or selected No, thus do nothing
    }
}

class ClickSectionListener implements ChartMouseListener, MouseListener {
    private final PieChart chart;
    private boolean isMouseRightClick;

    public ClickSectionListener(PieChart parent) {
        this.chart = parent;
        this.isMouseRightClick = false;
    }

    //mouseRelease always invoked before chartMouseClicked
    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        ChartEntity entity = event.getEntity();
        if (entity instanceof PieSectionEntity) {
            PieSectionEntity section = (PieSectionEntity) entity;
            if (isMouseRightClick) {
                this.chart.onDelete((String) section.getSectionKey());
                isMouseRightClick = false;
            } else {
                this.chart.onSelectedSection((String) section.getSectionKey());
            }
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    //mouseRelease always invoked before chartMouseClicked
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            this.isMouseRightClick = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
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
