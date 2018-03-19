import rnd.Negexp;
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
    private Diagram resultDiagram;

    private MainForm() {
        carAmount.setValue(5);
        combineAmount.setValue(2);
        timeModeling.setRandom(new Negexp(1));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Simulation RGR");
        frame.setContentPane(new MainForm().modelConfigurationPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setVisible(true);
    }


}
