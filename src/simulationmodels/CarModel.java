package simulationmodels;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.LinkedList;

/**
 * Created by Piotrek on 16.04.2017.
 */
public class CarModel extends Rectangle{

    private SequentialTransition sequentialTransition;
    private Boolean done = false;

    public CarModel(double x, double y, double width, double height) {
        super(x, y, width, height);
        sequentialTransition = new SequentialTransition();
    }

    public void addTransition(double moveX, double moveY, double speed){
        TranslateTransition t = new TranslateTransition();
        t.setNode(this);
        t.setByX(moveX);
        t.setByY(moveY);

        double distance = Math.sqrt(moveX*moveX + moveY*moveY);
        double duration = distance/speed;

        t.setDuration(Duration.seconds(duration));
        t.setCycleCount(1);
        sequentialTransition.getChildren().add(t);
    }

    public void start() {
        sequentialTransition.play();
    }
    public void stop(){
        sequentialTransition.pause();
    }
}
