package simulation;

import model.Car;
import model.Corn;
import model.CornGenerator;
import model.Harvester;
import process.Dispatcher;
import process.MultiActor;

import process.QueueForTransactions;
import stat.DiscretHisto;
import ui.MainForm;

public class CornModel {

    private MainForm gui;

    private Dispatcher dispatcher;
    private CornGenerator cornGenerator;

    private Corn corn;
    private Car car;
    private Harvester harvester;

    private MultiActor cars;
    private MultiActor harvesters;

    private QueueForTransactions<Corn> harvesterQueue;
    private QueueForTransactions<Corn> carQueue;

    private DiscretHisto harvesterDiscreteHisto;
    private DiscretHisto carDiscreteHisto;

    public CornModel(Dispatcher dispatcher, MainForm gui) {
        this.dispatcher = dispatcher;
        this.gui = gui;

        this.harvesterDiscreteHisto = new DiscretHisto();
        this.harvesterQueue = new QueueForTransactions<>("HarvesterQueue", this.dispatcher, this.harvesterDiscreteHisto);

        this.carDiscreteHisto = new DiscretHisto();
        this.carQueue = new QueueForTransactions<>("CarQueue", this.dispatcher, this.carDiscreteHisto);

        this.car = new Car("Car", this.gui, this);
        this.corn = new Corn(123.0);
        this.harvester = new Harvester(this.gui, "Harvester", this);

        this.cars = new MultiActor();
        this.cars.setNameForProtocol("MultiActor Car");
        this.cars.setOriginal(this.car);
        this.cars.setNumberOfClones(gui.getCarAmount());

        this.harvesters = new MultiActor();
        this.harvesters.setNameForProtocol("MultiActor Harvester");
        this.harvesters.setOriginal(this.harvester);
        this.harvesters.setNumberOfClones(gui.getHarvesterAmount());

        this.cornGenerator = new CornGenerator(this.gui, this, "CornGenerator");

        this.componentToStartList();
    }

    private void componentToStartList() {
        dispatcher.addStartingActor(this.harvester);
        dispatcher.addStartingActor(this.cars);
        dispatcher.addStartingActor(this.harvesters);
        dispatcher.addStartingActor(this.cornGenerator);
    }

    public void initForTest() {
        this.harvesterQueue.setPainter(gui.getDiagramHarvesterTime().getPainter());
        this.carQueue.setPainter(gui.getDiagramCarTime().getPainter());
        dispatcher.setProtocolFileName("Console");
    }

    public QueueForTransactions<Corn> getHarvesterQueue() {
        return harvesterQueue;
    }
}
