package simulation;

import javafx.scene.layout.Pane;
import simulationmodels.CrossroadsModel;
import simulationmodels.TrafficLightsModel;
import simulationmodels.TrafficLightsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for managing the traffic on a particular crossroads
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficManager implements Runnable{

    private CrossroadsModel crossroadsModel;
    private Pane pane;
    List<TrafficLightsModel> lights;

    public TrafficManager(CrossroadsModel crossroadsModel, Pane pane) {
        this.lights = new ArrayList<>();
        this.pane = pane;
        this.crossroadsModel = crossroadsModel;
        TrafficLightsModel lightRoadEast = new TrafficLightsModel(TrafficLightsModel.Direction.West);
        TrafficLightsView lightsViewEast = new TrafficLightsView(50,20,70,20);
        lightsViewEast.setStrokeWidth(5);
        lightRoadEast.setLightsView(lightsViewEast);

        TrafficLightsModel lightRoadWest = new TrafficLightsModel(TrafficLightsModel.Direction.East);
        TrafficLightsView lightsViewWest = new TrafficLightsView(50,40,70,40);
        lightsViewWest.setStrokeWidth(5);
        lightRoadWest.setLightsView(lightsViewWest);

        TrafficLightsModel lightRoadNorth = new TrafficLightsModel(TrafficLightsModel.Direction.South);
        TrafficLightsView lightsViewNorth = new TrafficLightsView(50,60,70,60);
        lightsViewNorth.setStrokeWidth(5);
        lightRoadNorth.setLightsView(lightsViewNorth);

        TrafficLightsModel lightRoadSouth = new TrafficLightsModel(TrafficLightsModel.Direction.North);
        TrafficLightsView lightsViewSouth = new TrafficLightsView(50,80,70,80);
        lightsViewSouth.setStrokeWidth(5);
        lightRoadSouth.setLightsView(lightsViewSouth);

        crossroadsModel.getRoadEAST().setLights(lightRoadEast,null);
        crossroadsModel.getRoadWEST().setLights(null, lightRoadWest);
        crossroadsModel.getRoadNORTH().setLights(null, lightRoadNorth);
        crossroadsModel.getRoadSOUTH().setLights(lightRoadSouth, null);

        pane.getChildren().add(lightsViewEast);
        pane.getChildren().add(lightsViewWest);
        pane.getChildren().add(lightsViewNorth);
        pane.getChildren().add(lightsViewSouth);

        this.lights.add(lightRoadEast);
        this.lights.add(lightRoadWest);
        this.lights.add(lightRoadNorth);
        this.lights.add(lightRoadSouth);
    }

    @Override
    public void run() {
        ArrayList<Thread> threads = new ArrayList<>();
        for( TrafficLightsModel model : this.lights)
            threads.add(new Thread(model));

        try {
            threads.get(0).start();
            threads.get(1).start();
            Thread.sleep(5000);
            threads.get(2).start();
            threads.get(3).start();

        }catch (InterruptedException ex){
            System.err.println("Traffic manager stopped working");
            for (Thread t : threads)
                t.interrupt();
        }
    }
}
