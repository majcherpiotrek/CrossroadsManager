package simulationmodels;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * Class responsible for changing the lights on the crossroads.
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsController implements Runnable{

    private CrossroadsView crossroadsView;

    /**
     * Fields for duration of the lights on particular roads.
     * This will be the end point for the API
     */
    //NOT USED YET
    private int redLightDurationNorth;
    private int greenLightDurationNorth;

    private int redLightDurationEast;
    private int greenLightDurationEast;

    private int redLightDurationSouth;
    private int greenLightDurationSouth;

    private int redLightDurationWest;
    private int greenLightDurationWest;

    public TrafficLightsController(CrossroadsView crossroadsView,
                                   int redLightDurationNorth, int greenLightDurationNorth,
                                   int redLightDurationEast, int greenLightDurationEast,
                                   int redLightDurationSouth, int greenLightDurationSouth,
                                   int redLightDurationWest, int greenLightDurationWest) {
        this.crossroadsView = crossroadsView;
        this.redLightDurationNorth = redLightDurationNorth;
        this.greenLightDurationNorth = greenLightDurationNorth;
        this.redLightDurationEast = redLightDurationEast;
        this.greenLightDurationEast = greenLightDurationEast;
        this.redLightDurationSouth = redLightDurationSouth;
        this.greenLightDurationSouth = greenLightDurationSouth;
        this.redLightDurationWest = redLightDurationWest;
        this.greenLightDurationWest = greenLightDurationWest;
    }

    @Override
    public void run() {
        try{
            crossroadsView.getLightsNORTH().changeLight(TrafficLightsModel.Light.RED);
            crossroadsView.getLightsEAST().changeLight(TrafficLightsModel.Light.RED);
            crossroadsView.getLightsSOUTH().changeLight(TrafficLightsModel.Light.RED);
            crossroadsView.getLightsWEST().changeLight(TrafficLightsModel.Light.RED);

            while( ! Thread.interrupted() ){
                crossroadsView.getLightsNORTH().changeLight(TrafficLightsModel.Light.GREEN);
                crossroadsView.getLightsSOUTH().changeLight(TrafficLightsModel.Light.GREEN);
                Thread.sleep(3000);
                crossroadsView.getLightsNORTH().changeLight(TrafficLightsModel.Light.RED);
                crossroadsView.getLightsSOUTH().changeLight(TrafficLightsModel.Light.RED);
                Thread.sleep(500);
                crossroadsView.getLightsEAST().changeLight(TrafficLightsModel.Light.GREEN);
                crossroadsView.getLightsWEST().changeLight(TrafficLightsModel.Light.GREEN);
                Thread.sleep(3000);
                crossroadsView.getLightsEAST().changeLight(TrafficLightsModel.Light.RED);
                crossroadsView.getLightsWEST().changeLight(TrafficLightsModel.Light.RED);
                Thread.sleep(500);
            }
        }catch (InterruptedException ex){
            System.out.println("Traffic lights modelling stopped.");
        }
        catch (NullPointerException ex){
            System.out.println("You need 4 traffic lights on the crossroads!");
        }
    }
}
