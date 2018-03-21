package model;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import simulation.CornModel;
import ui.MainForm;
import widgets.ChooseRandom;

import java.util.function.BooleanSupplier;

/* Сборочная машина(собирает зерно, приготовленное комбайном) */
public class Car extends Actor {

    private MainForm gui;
    private String name;

    private QueueForTransactions<Car> queueField;
    private QueueForTransactions<Car> queueStore;
    private ChooseRandom random;
    private double finishTime;
    private boolean full;
    private boolean empty;

    @Override
    protected void rule() throws DispatcherFinishException {
        BooleanSupplier full = this::isFull;
        BooleanSupplier empty = () -> !isFull();
        while (getDispatcher().getCurrentTime() <= finishTime) {
            getDispatcher().printToProtocol(getNameForProtocol() + " go to field");
            holdForTime(random.next());
            getDispatcher().printToProtocol(
                    " " + getNameForProtocol() + "Car come on the field");

            queueField.add(this);
            waitForCondition(full, "must be full");
            getDispatcher().printToProtocol(getNameForProtocol() + " go to corn store");
            holdForTime(random.next());
            queueStore.add(this);
            waitForCondition(empty, "must be empty");
        }
    }



    public void setFull(boolean full) {
        this.full = full;
    }

    private boolean isFull() {

        return full;
    }

    public Car(MainForm gui, String name, CornModel cornModel) {
        this.gui = gui;
        this.name = name;

        this.queueField = cornModel.getCarQueue();
        this.queueStore = cornModel.getCornStoreQueue();
        this.random = gui.getTimeInterval();
        this.finishTime = gui.getTimeModeling().getDouble();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
