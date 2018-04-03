package ui;

import simulation.CornModel;
import process.Dispatcher;
import process.IModelFactory;
import rnd.Negexp;
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
    private ChooseData carAmount;
    private ChooseData timeModeling;
    // час авто в дороге
    private ChooseRandom timeInterval;
    //время загрузки/разгрузки
    private ChooseRandom timeLoadingCar;
    private ChooseRandom timeUnloadingCar;
    private JButton runBtn;
    private JTabbedPane tabbedPane;
    private JPanel diagramTabPanel;
    private JTextArea resultTextArea;
    //диаграмма очереди машин на разгрузку
    private Diagram diagramCarUnloadingQueue;
    //диагрмма очереди машин на загрузку
    private Diagram diagramCarLoadingQueue;
    //диаграмма очереди комбайнов
    private Diagram diagramHarvesterQueue;
    private ChooseData harvesterAmount;
    private JSplitPane splitPanel;

    private MainForm() {
        this.setupUI();
        this.setUIActions();
        this.setupFields();
    }

    private void setupFields() {
        carAmount.setDouble(5.0);
        harvesterAmount.setDouble(5.0);
        timeModeling.setDouble(5.0);

        timeLoadingCar.setRandom(new Triangular(2, 3, 5));
        timeUnloadingCar.setRandom(new Negexp(4));
        timeInterval.setRandom(new Negexp(5));

    }

    private void setupUI() {
        timeLoadingCar.setTitle("Время загрузки машины");
        timeModeling.setTitle("Время моделирования");
        timeUnloadingCar.setTitle("Время сбора зерна");
        timeInterval.setTitle("Интервал машины в дороге");

        carAmount.setTitle("Количество машин");
        harvesterAmount.setTitle("Количество комбайнов");

        diagramHarvesterQueue.setTitleText("Время очереди комбайна");
        diagramCarLoadingQueue.setTitleText("Время очереди машин на загрузку");
        diagramCarUnloadingQueue.setTitleText("Время очереди машин на разгрузку");
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
        diagramHarvesterQueue.clear();
        diagramCarLoadingQueue.clear();
        diagramCarUnloadingQueue.clear();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.addDispatcherFinishListener(() -> MainForm.this.runBtn.setEnabled(true));
        IModelFactory factory = (d) -> new CornModel(dispatcher, this);
        CornModel cornModel = (CornModel) factory.createModel(dispatcher);
        cornModel.initForTest();
        dispatcher.start();
    }

    public Diagram getDiagramCarUnloadingQueue() {
        return diagramCarUnloadingQueue;
    }

    public Diagram getDiagramHarvesterQueue() {
        return diagramHarvesterQueue;
    }

    public Diagram getDiagramCarLoadingQueue() {
        return diagramCarLoadingQueue;
    }

    public int getCarAmount() {
        return this.carAmount.getInt();
    }

    public int getHarvesterAmount() {
        return this.harvesterAmount.getInt();
    }

    public ChooseData getTimeModeling() {
        return timeModeling;
    }

    public ChooseRandom getTimeInterval() {
        return timeInterval;
    }

    public ChooseRandom getTimeLoadingCar() {
        return timeLoadingCar;
    }

    public ChooseRandom getTimeUnloadingCar() {
        return timeUnloadingCar;
    }

}
