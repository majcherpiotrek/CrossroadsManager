package simulationmodels;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class representing traffic lights. Lights are defined by
 * the direction of the road on which they are situated:
 * North, East, South or West. If the road is coming TO the crossroads
 * FROM the east, then the lights have the direction WEST,
 * because the road is going to the west.
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsView extends Rectangle{

    enum Light { RED, GREEN }
    //will be used for checking which light is currently on (so we don't have to check the shape's color)
    private Light light;
    //shape of the light to be displayed on the screen
    private Rectangle lightsView = null;

    public TrafficLightsView(double x, double y, double width, double height) {
        super(x,y,width,height);
        this.setFill(Color.RED);
        this.light = Light.RED;
    }

    public Light getLight() {
        return light;
    }

    public void changeLight(Light light){
        this.light = light;
        this.setFill((this.light == Light.GREEN) ? Color.GREEN : Color.RED);
    }

}
