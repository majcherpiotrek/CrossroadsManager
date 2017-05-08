package simulationmodels;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import util.NWSE;

import java.util.LinkedList;

/**
 * Created by Piotrek on 16.04.2017.
 */
public class CarModel extends Rectangle{

    private SequentialTransition sequentialTransition;
    private Boolean done = false;
    private Boolean stopped = true;
    private NWSE direction;

    public CarModel(double x, double y, double width, double height) {
        super(x, y, width, height);
        sequentialTransition = new SequentialTransition();
        this.direction = null;
    }

    public void addTransition(double moveX, double moveY, double speed){
        TranslateTransition t = new TranslateTransition();
        t.setNode(this);
        t.setByX(moveX);
        t.setByY(moveY);

        if(moveX > 0)
            this.direction = NWSE.E;
        if(moveX < 0)
            this.direction = NWSE.W;
        if(moveY > 0)
            this.direction = NWSE.S;
        if(moveY < 0)
            this.direction = NWSE.N;

        double distance = Math.sqrt(moveX*moveX + moveY*moveY);
        double duration = distance/speed;

        t.setDuration(Duration.seconds(duration));
        t.setCycleCount(1);
        sequentialTransition.getChildren().add(t);
    }

    public void start() {
        this.stopped = false;
        sequentialTransition.play();
    }
    public void stop(){
        this.stopped = true;
        sequentialTransition.pause();
    }

    public Boolean getStopped() {
        return stopped;
    }

    public double getBumperX(){
        return this.getBoundsInParent().getMinX();
    }

    public double getBumperY(){
        return this.getBoundsInParent().getMaxY()+1.0;
    }

    public double getBumberWidth(){
        return this.getWidth();
    }

    public double getBumperHeight() {
        return 10.0;
    }

    public NWSE getDirection() {
        return direction;
    }
}
