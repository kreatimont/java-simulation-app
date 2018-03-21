package model;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import simulation.CornModel;
import ui.MainForm;
import widgets.ChooseRandom;

import java.util.function.BooleanSupplier;

/* Зерноуборочная машина, комбайн */
public class Harvester extends Actor {

    private MainForm gui;

    private String name;

    private QueueForTransactions<Harvester> queue;
    private ChooseRandom random;
    private double finishTime;
    private boolean full;

    public Harvester(MainForm gui, String name, CornModel cornModel) {
        super();
        this.gui = gui;
        this.name = name;

        this.queue = cornModel.getHarvesterQueue();
        this.random = gui.getTimeInterval();
        this.finishTime = gui.getTimeModeling().getDouble();
    }

    @Override
    protected void rule() throws DispatcherFinishException {
        BooleanSupplier full = this::isFull;
        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol(getNameForProtocol() + " working on field");
            holdForTime(random.next());
            getDispatcher().printToProtocol(
                    " " + getNameForProtocol() + "combine take corn");

            queue.add(this);
            waitForCondition(full, "must be full");
        }
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    private boolean isFull() {

        return full;
    }

}
