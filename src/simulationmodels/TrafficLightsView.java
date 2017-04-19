package simulationmodels;

import com.sun.javafx.scene.traversal.Direction;
import interfaces.StraightLinesShapeInterface;
import javafx.animation.FillTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import util.SimpleShapePainter;

import java.util.List;

/**
 * Class representing traffic lights. Lights are defined by
 * the direction of the road on which they are situated:
 * North, East, South or West. If the road is coming TO the crossroads
 * FROM the east, then the lights have the direction WEST,
 * because the road is going to the west.
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsView {

    public enum Direction{
        North,East,South,West
    }

    enum Light { RED, GREEN }

    //direction of the road
    private Direction direction;
    //will be used for checking which light is currently on (so we don't have to check the shape's color)
    private Light light = Light.RED;
    //shape of the light to be displayed on the screen
    private Shape lightsView = null;

    public TrafficLightsView(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Light getLight() {
        return light;
    }

    public Node getLightsView() {
        return lightsView;
    }

    public void setLightsView(Shape lightsView) {
        this.lightsView = lightsView;
        if (this.light == Light.GREEN)
            this.lightsView.setFill(Color.GREEN);
        else
            this.lightsView.setFill(Color.RED);
    }

    public void changeLight(Light light){
        this.light = light;
        if(this.lightsView != null)
            this.lightsView.setFill((this.light == Light.GREEN) ? Color.GREEN : Color.RED);
    }

}
