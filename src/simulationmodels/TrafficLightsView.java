package simulationmodels;

import javafx.scene.shape.Line;

/**
 * Class for viewing the lights on the crossroads
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsView extends Line{

    public TrafficLightsView(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
}
