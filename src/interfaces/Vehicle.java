package interfaces;

import javafx.geometry.Point2D;

/**
 * Interface for movable objects.
 * Created by Piotrek on 28.03.2017.
 */
public interface Vehicle {

    Point2D getPosition();
    void setPosition(Point2D pos);
}
