package simulationmodels;

import javafx.scene.shape.Rectangle;

/**
 * Class for modelling a road with possible traffic lights on both ends.
 * Created by Piotrek on 19.04.2017.
 */
public class RoadModel {

    private RoadView roadView;
    private TrafficLightsModel trafficLightsModelEndA = null; //NORTH in VERTICAL, WEST in HORIZONTAL
    private TrafficLightsModel trafficLightsModelEndB = null ; //SOUTH in VERTICAL, EAST in HOTIZONTAL

    public RoadModel(RoadView roadView) {
        this.roadView = roadView;
        }

    public void addLightsEndA(int redLightDuration, int greenLightDuration){
        this.trafficLightsModelEndA = new TrafficLightsModel(
                new TrafficLightsView((roadView.getOrientation() == RoadView.Orientation.HORIZONTAL) ? TrafficLightsView.Direction.West : TrafficLightsView.Direction.North),
                redLightDuration, greenLightDuration);

        Rectangle lightsView;
        if(roadView.getOrientation() == RoadView.Orientation.HORIZONTAL)
            lightsView = new Rectangle(roadView.getLeftUpperCorner().getX(),
                    roadView.getLeftUpperCorner().getY(),
                    roadView.getLaneWidth()/3,roadView.getLaneWidth());
        else
            lightsView = new Rectangle(roadView.getLeftUpperCorner().getX()+roadView.getLaneWidth(),
                    roadView.getLeftUpperCorner().getY(),
                    roadView.getLaneWidth(),
                    roadView.getLaneWidth()/3);

        this.trafficLightsModelEndA.getTrafficLightsView().setLightsView(lightsView);
    }

    public void addLightsEndB(int redLightDuration, int greenLightDuration){
        this.trafficLightsModelEndB = new TrafficLightsModel(
                new TrafficLightsView((roadView.getOrientation() == RoadView.Orientation.HORIZONTAL) ? TrafficLightsView.Direction.East : TrafficLightsView.Direction.South),
                redLightDuration, greenLightDuration);

        Rectangle lightsView;
        if(roadView.getOrientation() == RoadView.Orientation.HORIZONTAL)
            lightsView = new Rectangle(roadView.getLeftUpperCorner().getX()+roadView.getRoadLength()-roadView.getLaneWidth()/3,
                    roadView.getLeftUpperCorner().getY()+roadView.getLaneWidth(),
                    roadView.getLaneWidth()/3,roadView.getLaneWidth());
        else
            lightsView = new Rectangle(roadView.getLeftUpperCorner().getX(),
                    roadView.getLeftUpperCorner().getY()+roadView.getRoadLength()-roadView.getLaneWidth()/3,
                    roadView.getLaneWidth(), roadView.getLaneWidth()/3);

        this.trafficLightsModelEndB.getTrafficLightsView().setLightsView(lightsView);

    }
    public RoadView getRoadView() {
        return roadView;
    }

    public TrafficLightsModel getTrafficLightsModelEndA() {
        return trafficLightsModelEndA;
    }

    public TrafficLightsModel getTrafficLightsModelEndB() {
        return trafficLightsModelEndB;
    }
}
