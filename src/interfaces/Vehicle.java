package interfaces;

import javafx.geometry.Point2D;

/**
 * Interface for movable objects.
 * Created by Piotrek on 28.03.2017.
 */
public interface Vehicle {

    void move(double targetX, double targetY);
    Point2D getPosition();
    void setPosition(Point2D position);
}
