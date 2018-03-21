package model;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import simulation.CornModel;
import sun.applet.Main;
import ui.MainForm;
import widgets.ChooseRandom;

/*Элеватор, склад зерна -- разгружает машину*/
public class CornStore extends Actor {

    private MainForm gui;
    private String name;

    private QueueForTransactions<Car> queue;
    private ChooseRandom random;
    private double finishTime;

    @Override
    protected void rule() throws DispatcherFinishException {
        while (getDispatcher().getCurrentTime() <= finishTime) {
            holdForTime(random.next());
            getDispatcher().printToProtocol(
                    "  " + getNameForProtocol() + "CornStore");
//            Corn corn = new Corn(dispatcher.getCurrentTime());

//            queue.add();
        }
    }

    public CornStore(MainForm gui, String name, CornModel cornModel) {
        super();
        this.gui = gui;
        this.name = name;

        this.queue = cornModel.getCornStoreQueue();
        this.random = gui.getTimeInterval();
        this.finishTime = gui.getTimeModeling().getDouble();

    }


}
