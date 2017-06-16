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

    public TrafficLightsModel(TrafficLightsView trafficLightsView, int redLightDuration, int greenLightDuration) {
        this.trafficLightsView = trafficLightsView;
        this.redLightDuration = redLightDuration;
        this.greenLightDuration = greenLightDuration;
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
            synchronized (this) {
                trafficLightsView.changeLight(TrafficLightsView.Light.RED);
                Thread.sleep(this.offset);
                while (!Thread.interrupted()) {
                    trafficLightsView.changeLight(TrafficLightsView.Light.GREEN);
                    Thread.sleep(greenLightDuration);
                    trafficLightsView.changeLight(TrafficLightsView.Light.RED);
                    Thread.sleep(redLightDuration);
                }
            }
        }catch (InterruptedException ex){
            System.out.println("Traffic lights modelling stopped.");
        }
    }
}
