package util;

import interfaces.StraightLinesShapeInterface;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulationmodels.StraightLine;

import java.util.List;

/**
 * Class for painting objects implementing the StraightLineShapeInterface
 * on the GraphicsContext.
 * Created by Piotrek on 01.04.2017.
 */
public class SimpleShapePainter {
    public static void drawShape(StraightLinesShapeInterface shape, GraphicsContext graphicsContext){
        List<StraightLine> lines = shape.getLines();
        graphicsContext.setStroke(Color.BLACK);
        for(StraightLine sl : lines)
            graphicsContext.strokeLine(sl.getPointA().getX(), sl.getPointA().getY() ,
                                        sl.getPointB().getX(), sl.getPointB().getY());

    }
}
