package simulation;

import simulationmodels.TrafficLightsModel;
import simulationmodels.TrafficLightsView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Piotrek on 13.06.2017.
 */
public class TrafficLightsController {

    private ExecutorService executorService;
    private List<TrafficLightsModel> lightsModelList;

    public TrafficLightsController(List<TrafficLightsModel> lights) {
            lightsModelList = lights;
            if (lightsModelList != null) {
                executorService = Executors.newFixedThreadPool(lightsModelList.size());
            } else {
                throw new IllegalArgumentException("The lights list cannot be null");
            }
    }

    public void startLights() {
        if ( lightsModelList != null ) {
            for (TrafficLightsModel light : lightsModelList) {
                executorService.execute(light);
            }
        }
    }

    public void stopLights() {
        Runnable stopTask = () -> executorService.shutdown();
        Thread t = new Thread(stopTask);
        System.out.println("Stopping the traffic lights controller...\n");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Traffic lights controller stopped!\n");
    }
}
