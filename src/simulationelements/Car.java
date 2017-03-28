package simulationelements;

import javafx.scene.paint.Color;
import interfaces.Drawable;
import interfaces.Vehicle;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

/**
 * Class representing a car on a JavaFX Canvas
 * Created by Piotrek on 28.03.2017.
 */
public class Car implements Vehicle, Drawable {

    Point2D position;

    public Car(Point2D startPos){
        position = startPos;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Point2D pos) {
        this.position = pos;
    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.setFill(Color.RED);
        gc.fillOval(position.getX(),position.getY(),20,20);
    }
}
