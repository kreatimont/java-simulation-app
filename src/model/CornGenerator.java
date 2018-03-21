package model;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import simulation.CornModel;
import ui.MainForm;
import widgets.ChooseRandom;

public class CornGenerator extends Actor {

    private MainForm gui;
    private CornModel model;

    private String name;
    private QueueForTransactions<Corn> queue;
    private ChooseRandom random;
    private double finishTime;

    public CornGenerator(MainForm gui, CornModel model, String name) {
        super();
        this.gui = gui;
        this.model = model;
        this.name = name;
        this.queue = model.getHarvesterQueue();
        this.random = gui.getTimeInterval();
        this.finishTime = gui.getTimeModeling().getDouble();
    }

    @Override
    protected void rule() throws DispatcherFinishException {
        while (getDispatcher().getCurrentTime() <= finishTime) {
            holdForTime(random.next());
            getDispatcher().printToProtocol(
                    "  " + getNameForProtocol() + "CornGenerator");
            Corn corn = new Corn(dispatcher.getCurrentTime());
            queue.add(corn);
        }
    }
}
