package simulationmodels;

/**
 * Class responsible for changing the lights in the TrafficLightsView.
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsModel implements Runnable{

    private TrafficLightsView trafficLightsView;
    private int redLightDuration;
    private int greenLightDuration;

    public TrafficLightsModel(TrafficLightsView trafficLightsView, int redLightDuration, int greenLightDuration) {
        this.trafficLightsView = trafficLightsView;
        this.redLightDuration = redLightDuration;
        this.greenLightDuration = greenLightDuration;
    }

    public TrafficLightsView getTrafficLightsView() {
        return trafficLightsView;
    }

    @Override
    public void run() {
        try{
            trafficLightsView.changeLight(TrafficLightsView.Light.RED);

            while( ! Thread.interrupted() ){
                trafficLightsView.changeLight(TrafficLightsView.Light.GREEN);
                Thread.sleep(greenLightDuration);
                trafficLightsView.changeLight(TrafficLightsView.Light.RED);
                Thread.sleep(redLightDuration);
            }
        }catch (InterruptedException ex){
            System.out.println("Traffic lights modelling stopped.");
        }
    }
}
