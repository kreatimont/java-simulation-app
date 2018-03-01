import rnd.Negexp;
import widgets.ChooseRandom;

import javax.swing.*;

public class MainForm {

    private JPanel modelConfigurationPanel;
    private ChooseRandom timeModeling;
    private JSpinner carAmount;
    private JSpinner combineAmount;
    private ChooseRandom timeAssembly;
    private ChooseRandom timeLoadingCar;
    private ChooseRandom timeUnloadingCar;
    private JButton runBtn;

    //время сборки зерна
    //время разгрузки
    //врея загрузки
    private MainForm() {
        carAmount.setValue(5);
        combineAmount.setValue(2);
        timeModeling.setRandom(new Negexp(1));
    }
//
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new MainForm().modelConfigurationPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
