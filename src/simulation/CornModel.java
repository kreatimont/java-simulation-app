package simulation;

import model.Car;
import model.Corn;
import model.Harvester;
import process.Dispatcher;
import process.MultiActor;

import process.QueueForTransactions;
import stat.DiscretHisto;
import ui.MainForm;

public class CornModel {

    private MainForm gui;

    private Dispatcher dispatcher;

    private Corn corn;
    private Car car;
    private Harvester harvester;

    private MultiActor cars;
    private MultiActor harvesters;

    private QueueForTransactions<Corn> harvesterQueue;

    private DiscretHisto harvesterDiscreteHisto;

    public CornModel(Dispatcher dispatcher, MainForm gui) {
        this.dispatcher = dispatcher;
        this.gui = gui;
        this.componentToStartList();

        this.harvesterDiscreteHisto = new DiscretHisto();
        this.harvesterQueue = new QueueForTransactions<>("HarvesterQueue", this.dispatcher, this.harvesterDiscreteHisto);

        this.car = new Car("Car");
        this.corn = new Corn(123.0);
        this.harvester = new Harvester(this.gui, "Harvester", this);

        this.cars = new MultiActor();
        this.cars.setNameForProtocol("MultiActor Car");
        this.cars.setOriginal(this.car);
        this.cars.setNumberOfClones(gui.getCarAmount());
    }

    private void componentToStartList() {
        // TODO Auto-generated method stub
        dispatcher.addStartingActor(this.harvester);
        dispatcher.addStartingActor(this.cars);
        dispatcher.addStartingActor(this.harvesters);
    }

    public void initForTest() {
        this.harvesterQueue.setPainter(gui.getDiagramHarvesterTime().getPainter());
        dispatcher.setProtocolFileName("Console");
    }
}
