package ui;

import simulation.CornModel;
import process.Dispatcher;
import process.IModelFactory;
import rnd.Negexp;
import rnd.Norm;
import rnd.Triangular;
import widgets.ChooseData;
import widgets.ChooseRandom;
import widgets.Diagram;

import javax.swing.*;
import java.awt.*;

//время сборки зерна
//время разгрузки
//врея загрузки

public class MainForm {

    private JPanel modelConfigurationPanel;
    private JSpinner carAmount;
    private JSpinner combineAmount;
    private ChooseRandom timeModeling;
    private ChooseRandom timeAssembly;
    private ChooseRandom timeLoadingCar;
    private ChooseRandom timeUnloadingCar;
    private JButton runBtn;
    private JTabbedPane tabbedPane;
    private JPanel diagramTabPanel;
    private JTextArea resultTextArea;
    private Diagram diagram3;
    private Diagram diagram2;
    private Diagram diagramHarvesterTime;
    private ChooseData carAmountChooseData;

    private MainForm() {
        this.setupUI();
        this.setUIActions();
        this.setupFields();
    }

    private void setupFields() {
        carAmount.setValue(5);
        combineAmount.setValue(2);

        timeModeling.setRandom(new Negexp(1));
        timeLoadingCar.setRandom(new Triangular(2, 3, 5));
        timeModeling.setRandom(new Norm(5, 1));
        timeUnloadingCar.setRandom(new Negexp(4));
        timeAssembly.setRandom(new Negexp(5));

    }

    private void setupUI() {
        timeLoadingCar.setTitle("Время загрузки машины");
        timeModeling.setTitle("Время моделирования");
        timeUnloadingCar.setTitle("Время разгрузки машины");
        timeAssembly.setTitle("Время сборки");

        carAmountChooseData.setTitle("Количество машин");
    }

    private void setUIActions() {
        runBtn.addActionListener(e -> {
            resultTextArea.setText("button 'RUN' clicked");
            runBtn.setEnabled(false);
            this.startTest();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Simulation RGR");
        frame.setContentPane(new MainForm().modelConfigurationPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setVisible(true);
    }

    private void startTest() {
        this.runBtn.setEnabled(false);
        diagramHarvesterTime.clear();
        diagram2.clear();
        diagram3.clear();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.addDispatcherFinishListener(() -> MainForm.this.runBtn.setEnabled(true));
        IModelFactory factory = (d) -> new CornModel(dispatcher, this);
        CornModel cornModel = (CornModel) factory.createModel(dispatcher);
        cornModel.initForTest();
        dispatcher.start();
    }

    public Diagram getDiagramHarvesterTime() {
        return diagramHarvesterTime;
    }

    public int getCarAmount() {
        return this.carAmountChooseData.getInt();
    }
}
