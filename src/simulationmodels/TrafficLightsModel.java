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
 * North, East, South or West.
 * Created by Piotrek on 09.04.2017.
 */
public class TrafficLightsModel {

    public enum Direction{
        North,East,South,West
    }

    enum Light { RED, GREEN }

    private Direction direction;
    private Light light = Light.RED;
    private Shape lightsView = null;

    public TrafficLightsModel(Direction direction) {
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
