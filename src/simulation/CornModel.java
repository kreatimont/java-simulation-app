package simulation;

import model.*;
import process.Dispatcher;
import process.MultiActor;

import process.QueueForTransactions;
import stat.DiscretHisto;
import ui.MainForm;

public class CornModel {

    private MainForm gui;

    private Dispatcher dispatcher;

    private Car car;
    private Harvester harvester;
    private CornStore cornStore;

    private MultiActor cars;
    private MultiActor harvesters;

    private QueueForTransactions<Harvester> harvesterQueue;
    private QueueForTransactions<Car> carQueue;
    private QueueForTransactions<Car> cornStoreQueue;

    private DiscretHisto harvesterDiscreteHisto;
    private DiscretHisto carDiscreteHisto;
    private DiscretHisto cornStoreHisto;

    public CornModel(Dispatcher dispatcher, MainForm gui) {
        this.dispatcher = dispatcher;
        this.gui = gui;

        this.harvesterDiscreteHisto = new DiscretHisto();
        this.harvesterQueue = new QueueForTransactions<>("HarvesterQueue", this.dispatcher, this.harvesterDiscreteHisto);

        this.carDiscreteHisto = new DiscretHisto();
        this.carQueue = new QueueForTransactions<>("CarQueue", this.dispatcher, this.carDiscreteHisto);

        this.cornStoreHisto = new DiscretHisto();
        this.cornStoreQueue = new QueueForTransactions<>("CornStoreQueue", this.dispatcher, this.cornStoreHisto);

        this.car = new Car(this.gui, "Car", this);
        this.harvester = new Harvester(this.gui, "Harvester", this);
        this.cornStore = new CornStore(this.gui, "CornStore", this);

        this.cars = new MultiActor();
        this.cars.setNameForProtocol("MultiActor Car");
        this.cars.setOriginal(this.car);
        this.cars.setNumberOfClones(gui.getCarAmount());

        this.harvesters = new MultiActor();
        this.harvesters.setNameForProtocol("MultiActor Harvester");
        this.harvesters.setOriginal(this.harvester);
        this.harvesters.setNumberOfClones(gui.getHarvesterAmount());

        this.componentToStartList();
    }

    private void componentToStartList() {
        dispatcher.addStartingActor(this.harvesters);
        dispatcher.addStartingActor(this.cars);
        dispatcher.addStartingActor(this.cornStore);
    }

    public void initForTest() {
        this.harvesterQueue.setPainter(gui.getDiagramHarvesterQueue().getPainter());
        this.carQueue.setPainter(gui.getDiagramCarLoadingQueue().getPainter());
        this.cornStoreQueue.setPainter(gui.getDiagramCarUnloadingQueue().getPainter());
        dispatcher.setProtocolFileName("Console");
    }

    public QueueForTransactions<Harvester> getHarvesterQueue() {
        return harvesterQueue;
    }

    public QueueForTransactions<Car> getCarQueue() {
        return carQueue;
    }

    public QueueForTransactions<Car> getCornStoreQueue() {
        return cornStoreQueue;
    }
}
