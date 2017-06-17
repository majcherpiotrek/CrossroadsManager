package simulation;

import simulationmodels.TrafficLightsModel;
import simulationmodels.TrafficLightsView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public void stopLights() throws InterruptedException {
        System.out.println("Stopping the lights...\n");
        if (!executorService.isTerminated()) {
            executorService.shutdownNow();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
        System.out.println("Lights stopped\n");
    }
}
