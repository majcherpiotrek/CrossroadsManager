package simulationelements;

import interfaces.Drawable;
import interfaces.Vehicle;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class representing a car on a JavaFX Canvas
 * Created by Piotrek on 28.03.2017.
 */
public class Car implements Vehicle, Drawable {
    @Override
    public void move(double targetX, double targetY) {

    }

    @Override
    public Point2D getPosition() {
        return null;
    }

    @Override
    public void setPosition(Point2D position) {

    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
