package interfaces;

import simulationmodels.StraightLine;

import java.util.List;

/**
 * Interface for simple shapes represented by straight lines.
 * Created by Piotrek on 01.04.2017.
 */
public interface StraightLinesShapeInterface {
    /**
     * A method used to define the lines constructing the shape
     * @return The array of lines representing a simple shape
     */
    List<StraightLine> getLines();
}
