package simulationmodels;

import interfaces.StraightLinesShapeInterface;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * A class modelling a simple, two roads', right angle crossroads.
 * The roads are entering the crossroads from East, West, North and South.
 * Possibility to add traffic lights to each road entering the crossroads.
 * Created by Piotrek on 03.04.2017.
 */
public class CrossroadsView implements StraightLinesShapeInterface{

    private RoadModel roadNORTH;
    private RoadModel roadEAST;
    private RoadModel roadSOUTH;
    private RoadModel roadWEST;

    private TrafficLightsModel lightsNORTH = null;
    private TrafficLightsModel lightsEAST = null;
    private TrafficLightsModel lightsSOUTH = null;
    private TrafficLightsModel lightsWEST = null;

    private Point2D leftUpperCorner;

    /**
     * The constructor of the crossroads
     * @param upperLeftCorner left upper corner of the crossroads - the point where the road from the North and from the West meet
     * @param lanesNum number of lanes on the roads
     * @param laneWidth the width of each lane
     * @param len_roadN the length of the road coming from the North
     * @param len_roadE the length of the road coming from the East
     * @param len_roadS the length of the road coming from the South
     * @param len_roadW the length of the road coming from the West
     */
    public CrossroadsView(Point2D upperLeftCorner,
                          Integer lanesNum,
                          double laneWidth,
                          double len_roadN,
                          double len_roadE,
                          double len_roadS,
                          double len_roadW) {
        this.leftUpperCorner = upperLeftCorner;
        /**
         * Create the road from the NORTH
         */
        roadNORTH = new RoadModel(len_roadN,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.VERTICAL,
                new Point2D(this.leftUpperCorner.getX(), this.leftUpperCorner.getY() - len_roadN)
        );

        /**
         * Create the road from the EAST
         */
        roadEAST = new RoadModel(len_roadE,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.HORIZONTAL,
                new Point2D(this.leftUpperCorner.getX() + lanesNum*laneWidth, this.leftUpperCorner.getY())
        );

        /**
         * Create the road from the SOUTH
         */
        roadSOUTH = new RoadModel(len_roadS,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.VERTICAL,
                new Point2D(this.leftUpperCorner.getX(), this.leftUpperCorner.getY() + lanesNum*laneWidth)
        );

        /**
         * Create the road from the WEST
         */
        roadWEST = new RoadModel(len_roadW,
                laneWidth,
                lanesNum,
                RoadModel.Orientation.HORIZONTAL,
                new Point2D(this.leftUpperCorner.getX() - len_roadW, this.leftUpperCorner.getY())
        );
    }

    public RoadModel getRoadNORTH() {
        return roadNORTH;
    }

    public RoadModel getRoadEAST() {
        return roadEAST;
    }

    public RoadModel getRoadSOUTH() {
        return roadSOUTH;
    }

    public RoadModel getRoadWEST() {
        return roadWEST;
    }

    public Point2D getLeftUpperCorner() {
        return leftUpperCorner;
    }

    public TrafficLightsModel getLightsNORTH() {
        return lightsNORTH;
    }

    public void setLightsNORTH(TrafficLightsModel lightsNORTH) {
        this.lightsNORTH = lightsNORTH;
        Circle circle = new Circle(
                this.leftUpperCorner.getX()+0.5*this.roadNORTH.getLaneWidth(),
                this.leftUpperCorner.getY(),
                this.roadNORTH.getLaneWidth()/2

        );
        this.lightsNORTH.setLightsView(circle);
    }

    public TrafficLightsModel getLightsEAST() {
        return lightsEAST;
    }

    public void setLightsEAST(TrafficLightsModel lightsEAST) {
        this.lightsEAST = lightsEAST;
        Circle circle = new Circle(
                this.leftUpperCorner.getX(),
                this.leftUpperCorner.getY()+1.5*this.roadEAST.getLaneWidth(),
                this.roadEAST.getLaneWidth()/2

        );
        this.lightsEAST.setLightsView(circle);
    }

    public TrafficLightsModel getLightsSOUTH() {
        return lightsSOUTH;
    }

    public void setLightsSOUTH(TrafficLightsModel lightsSOUTH) {
        this.lightsSOUTH = lightsSOUTH;
        Circle circle = new Circle(
                this.leftUpperCorner.getX()+1.5*this.roadSOUTH.getLaneWidth(),
                this.leftUpperCorner.getY()+this.roadEAST.getLaneWidth()*this.roadEAST.getTotalLanesNum(),
                this.roadEAST.getLaneWidth()/2
        );
        this.lightsSOUTH.setLightsView(circle);
    }

    public TrafficLightsModel getLightsWEST() {
        return lightsWEST;
    }

    public void setLightsWEST(TrafficLightsModel lightsWEST) {
        this.lightsWEST = lightsWEST;
        Circle circle = new Circle(
                this.leftUpperCorner.getX()+this.roadNORTH.getTotalLanesNum()*this.roadNORTH.getLaneWidth(),
                this.leftUpperCorner.getY()+0.5*this.roadWEST.getLaneWidth(),
                this.roadEAST.getLaneWidth()/2

        );
        this.lightsWEST.setLightsView(circle);
    }



    @Override
    public List<StraightLine> getLines() {
        List<StraightLine> list = new ArrayList<>();
        list.addAll(roadNORTH.getLines());
        list.addAll(roadEAST.getLines());
        list.addAll(roadSOUTH.getLines());
        list.addAll(roadWEST.getLines());

        return list;
    }
}
