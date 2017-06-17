package simulationmodels;

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

    public void addLightsEndA(){
        if(roadView.getOrientation() == RoadView.Orientation.HORIZONTAL){
            this.trafficLightsModelEndA = new TrafficLightsModel(
                    new TrafficLightsView(
                            roadView.getLeftUpperCorner().getX(),
                            roadView.getLeftUpperCorner().getY(),
                            roadView.getLaneWidth()/3,roadView.getLaneWidth()));
        }else{
            this.trafficLightsModelEndA = new TrafficLightsModel(
                    new TrafficLightsView(
                            roadView.getLeftUpperCorner().getX()+roadView.getLaneWidth(),
                            roadView.getLeftUpperCorner().getY(),
                            roadView.getLaneWidth(),
                            roadView.getLaneWidth()/3));
        }
    }

    public void addLightsEndB(){
        if(roadView.getOrientation() == RoadView.Orientation.HORIZONTAL){
            this.trafficLightsModelEndB = new TrafficLightsModel(
                    new TrafficLightsView(
                            roadView.getLeftUpperCorner().getX()+roadView.getRoadLength()-roadView.getLaneWidth()/3,
                            roadView.getLeftUpperCorner().getY()+roadView.getLaneWidth(),
                            roadView.getLaneWidth()/3,roadView.getLaneWidth()
                            ));
        } else {
            this.trafficLightsModelEndB = new TrafficLightsModel(
                    new TrafficLightsView(
                            roadView.getLeftUpperCorner().getX(),
                            roadView.getLeftUpperCorner().getY()+roadView.getRoadLength()-roadView.getLaneWidth()/3,
                            roadView.getLaneWidth(), roadView.getLaneWidth()/3
                    ));
        }
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

    public boolean isConnected(RoadModel road) {

        if(this == road)
            return true;

        double roadAx = this.roadView.getLeftUpperCorner().getX();
        double roadAy = this.roadView.getLeftUpperCorner().getY();

        double roadBx = road.getRoadView().getLeftUpperCorner().getX();
        double roadBy = road.getRoadView().getLeftUpperCorner().getY();

        double length = this.roadView.getRoadLength();
        double laneWidth = this.roadView.getLaneWidth();
        int lanesNum = this.roadView.getTotalLanesNum();

        //Check if horizontal roads are connected
        if(this.roadView.getOrientation() == road.getRoadView().getOrientation() && this.roadView.getOrientation() == RoadView.Orientation.HORIZONTAL) {
            //Check the connection conditions
            if(/*roads on the same level*/Math.abs(roadAy - roadBy) < 1.0 &&
                    /*check if there is just a crossroads in between the roads*/Math.abs(Math.abs(roadAx - roadBx) - (lanesNum*laneWidth + length)) < 1.0)
                return true;
            return false;
        } else {
            //check if vertical roads are connected
            if(this.roadView.getOrientation() == road.getRoadView().getOrientation()) {
                if(/*roads in the same column*/Math.abs(roadAx - roadBx) < 1.0 &&
                    /*check if there is just a crossroads in between the roads*/Math.abs(Math.abs(roadAy - roadBy) - (lanesNum*laneWidth + length)) < 1.0)
                    return true;
                return false;
            } else {
                //check if roads create a curve

                /*Road A horizontal and turning right down into Road B or Road B horizontal and turning right down into Road A*/
                if( Math.abs((roadAx+length)-roadBx) < 1.0 && Math.abs((roadAy+lanesNum*laneWidth) - roadBy) < 1.0 ||
                        Math.abs((roadBx+length)-roadAx) < 1.0 && Math.abs((roadBy+lanesNum*laneWidth) - roadAy) < 1.0 )
                    return true;
                /*Road A horizontal and turning left up into Road B or Road B horizontal and turning left up into Road A*/
                if( Math.abs((roadAx+length)-roadBx) < 1.0 && Math.abs(roadAy - (roadBy+length)) < 1.0 ||
                        Math.abs((roadBx+length)-roadAx) < 1.0 && Math.abs(roadBy - (roadAy+length)) < 1.0 )
                    return true;
                /*Road A horizontal and turning right and up into Road B or Road B horizontal and turning right and up into Road A*/
                if( Math.abs((roadAx + lanesNum*laneWidth)-roadBx) < 1.0 && Math.abs((roadAy+length) - roadBy) < 1.0 ||
                        Math.abs((roadBx + lanesNum*laneWidth)-roadAx) < 1.0 && Math.abs((roadBy+length) - roadAy) < 1.0)
                    return true;
                /*Road A horizontal and turning left and down into Road B or Road B horizontal and turning left and down into Road A*/
                if( Math.abs((roadAx + lanesNum*laneWidth)-roadBx) < 1.0 && Math.abs(roadAy -(roadBy+lanesNum*laneWidth)) < 1.0 ||
                        Math.abs((roadBx + lanesNum*laneWidth)-roadAx) < 1.0 && Math.abs(roadBy -(roadAy+lanesNum*laneWidth)) < 1.0 )
                    return true;
                return false;
            }
        }

    }
}
