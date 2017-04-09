package simulationmodels;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Olaf on 2017-04-09.
 */
class RoadModelTest {

    private RoadModel testHorizontalRoad = new RoadModel(30.0, 20.0, 2, RoadModel.Orientation.HORIZONTAL, new Point2D(0, 0));
    private RoadModel testVerticalRoad = new RoadModel(30.0, 20.0, 2, RoadModel.Orientation.VERTICAL, new Point2D(0, 0));

    @Test
    void checkPointOnHorizontalRoad(){
        assertTrue(testHorizontalRoad.pointOnRoad(new Point2D(30,0)));
    }

    @Test
    void checkIfPointIsNotOnHorizontalRoad(){
        assertFalse(testHorizontalRoad.pointOnRoad(new Point2D(30, 50)));
    }
    @Test
    void checkPointOnVerticalRoad(){
        assertTrue(testVerticalRoad.pointOnRoad(new Point2D(40,0)));
    }

    @Test
    void checkIfPointIsNotOnVerticalRoad(){
        assertFalse(testVerticalRoad.pointOnRoad(new Point2D(30, 50)));
    }
}