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
public class TrafficLightsModel implements Runnable {

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


    @Override
    public void run() {

        FillTransition lightChanges = new FillTransition(Duration.millis(3000),this.lightsView,Color.RED,Color.GREEN);
        lightChanges.setCycleCount(10);
        lightChanges.play();
        try{
            while (! Thread.interrupted()){
                this.light = Light.RED;
                this.lightsView.setFill(Color.RED);
                System.out.println(this.light);
                Thread.sleep(5000);
                this.light = Light.GREEN;
                this.lightsView.setFill(Color.GREEN);
                System.out.println(this.light);
                Thread.sleep(5000);
            }
        }catch (InterruptedException ex){
            System.err.println("Lights stopped working");
        }
    }

}
