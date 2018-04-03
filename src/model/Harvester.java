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

    private QueueForTransactions<Harvester> queueHarvesters;
    private QueueForTransactions<Car> queueCarForLoading;
    private ChooseRandom random;
    private double finishTime;
    private boolean full;

    public Harvester(MainForm gui, String name, CornModel cornModel) {
        super();
        this.gui = gui;
        this.name = name;

        this.queueHarvesters = cornModel.getHarvesterQueue();
        this.queueCarForLoading = cornModel.getCarQueue();
        this.random = gui.getTimeInterval();
        this.finishTime = gui.getTimeModeling().getDouble();
    }

    @Override
    protected void rule() throws DispatcherFinishException {
        BooleanSupplier full = this::isFull;
        BooleanSupplier queueCarEmptySupplier =  () -> this.queueCarForLoading.size() > 0;
        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol(getNameForProtocol() + " working on field");
            holdForTime(random.next());
            getDispatcher().printToProtocol(
                    " " + getNameForProtocol() + "harvester take corn");
            this.queueHarvesters.add(this);
            holdForTime(random.next());
            waitForCondition(full, "harvester must be full");
            this.setFull(true);
            getDispatcher().printToProtocol(getNameForProtocol() + " harvester is full");
            if (this.queueCarForLoading.size() > 0) {
                Car car = this.queueCarForLoading.removeFirst();
                getDispatcher().printToProtocol(getNameForProtocol() + " harvester start load car");
                holdForTime(random.next());
                car.setFull(true);
                getDispatcher().printToProtocol(getNameForProtocol() + " harvester load car success");
                this.setFull(false);
            }
            
        }
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    private boolean isFull() {
        return full;
    }

}
