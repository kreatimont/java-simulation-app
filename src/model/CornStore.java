package model;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import simulation.CornModel;
import sun.applet.Main;
import ui.MainForm;
import widgets.ChooseRandom;

import java.util.function.BooleanSupplier;

/*Элеватор, склад зерна -- разгружает машину*/
public class CornStore extends Actor {

    private MainForm gui;
    private String name;

    private QueueForTransactions<Car> queue;
    private ChooseRandom random;
    private double finishTime;

    @Override
    protected void rule() throws DispatcherFinishException {
        BooleanSupplier emptyQueueSupplier = () -> queue.size() > 0;
        while (getDispatcher().getCurrentTime() <= finishTime) {
            waitForCondition(emptyQueueSupplier, "Need at least one car for unloading");
            Car car = this.queue.removeFirst();
            getDispatcher().printToProtocol(
                    "  " + getNameForProtocol() + "unloading car");
            holdForTime(random.next());
            car.setEmpty(true);
            getDispatcher().printToProtocol(
                    "  " + getNameForProtocol() + "unload car success");
        }
    }

    public CornStore(MainForm gui, String name, CornModel cornModel) {
        super();
        this.gui = gui;
        this.name = name;

        this.queue = cornModel.getCornStoreQueue();
        this.random = gui.getTimeUnloadingCar();
        this.finishTime = gui.getTimeModeling().getDouble();

    }


}
