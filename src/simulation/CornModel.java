package simulation;

import model.*;
import process.Dispatcher;
import process.MultiActor;

import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import stat.IHisto;
import ui.MainForm;
import widgets.stat.IStatisticsable;

import java.util.HashMap;
import java.util.Map;

public class CornModel implements IStatisticsable {

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

    private Histo harvesterWaitingHisto;
    private Histo carWaitingOnFieldHisto;
    private Histo carWaitingOnElevatorHisto;

    public Histo getHarvesterWaitingHisto() {
        return harvesterWaitingHisto;
    }

    public void setHarvesterWaitingHisto(Histo harvesterWaitingHisto) {
        this.harvesterWaitingHisto = harvesterWaitingHisto;
    }

    public Histo getCarWaitingOnFieldHisto() {
        return carWaitingOnFieldHisto;
    }

    public void setCarWaitingOnFieldHisto(Histo carWaitingOnFieldHisto) {
        this.carWaitingOnFieldHisto = carWaitingOnFieldHisto;
    }

    public Histo getCarWaitingOnElevatorHisto() {
        return carWaitingOnElevatorHisto;
    }

    public void setCarWaitingOnElevatorHisto(Histo carWaitingOnElevatorHisto) {
        this.carWaitingOnElevatorHisto = carWaitingOnElevatorHisto;
    }

    public CornModel(Dispatcher dispatcher, MainForm gui) {
        this.dispatcher = dispatcher;
        this.gui = gui;

        this.harvesterWaitingHisto = new Histo();
        this.carWaitingOnFieldHisto = new Histo();
        this.carWaitingOnElevatorHisto = new Histo();


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

    @Override
    public Map<String, IHisto> getStatistics() {
        HashMap<String, IHisto> map = new HashMap<>();
        map.put("очередь комбайнов", this.harvesterWaitingHisto);
        map.put("очередь авто на загрузку", this.carWaitingOnFieldHisto);
        map.put("очередь авто на разгрузку", this.carWaitingOnElevatorHisto);
        map.put("harvesterDiscreteHisto", this.harvesterDiscreteHisto);
        map.put("carDiscreteHisto", this.carDiscreteHisto);
        map.put("cornStoreHisto", this.cornStoreHisto);
        return map;
    }

    @Override
    public void initForStatistics() {

    }
}
