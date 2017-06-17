package simulationmodels;

/**
 * Class responsible for changing the lights in the TrafficLightsView.
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsModel implements Runnable{

    private TrafficLightsView trafficLightsView;
    private int redLightDuration;
    private int greenLightDuration;
    private int offset;
    private int ttl;

    public TrafficLightsModel(TrafficLightsView trafficLightsView) {
        this.trafficLightsView = trafficLightsView;
        this.redLightDuration = 0;
        this.greenLightDuration = 0;
        this.offset = 0;
    }

    public TrafficLightsView getTrafficLightsView() {
        return trafficLightsView;
    }

    public int getRedLightDuration() {
        return redLightDuration;
    }

    public void setRedLightDuration(int redLightDuration) {
        this.redLightDuration = redLightDuration;
    }

    public int getGreenLightDuration() {
        return greenLightDuration;
    }

    public void setGreenLightDuration(int greenLightDuration) {
        this.greenLightDuration = greenLightDuration;
    }

    public TrafficLightsView.Light getLight(){
        return this.trafficLightsView.getLight();
    }

    public void setOffset(int offsetTime) {
        this.offset = offsetTime;
    }

    @Override
    public void run() {
        try{
                trafficLightsView.changeLight(TrafficLightsView.Light.RED);
                Thread.sleep(this.offset);
                while (!Thread.interrupted()) {
                    trafficLightsView.changeLight(TrafficLightsView.Light.GREEN);
                    Thread.sleep(greenLightDuration);
                    trafficLightsView.changeLight(TrafficLightsView.Light.RED);
                    Thread.sleep(redLightDuration);
                }
        }catch (InterruptedException ex){
            trafficLightsView.changeLight(TrafficLightsView.Light.RED);
            System.out.println("Traffic lights modelling stopped.");
        }
    }
}
