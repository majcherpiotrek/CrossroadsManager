package simulationmodels;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Created by Piotrek on 19.04.2017.
 */
public class TrafficManager implements Runnable {

    private RoadModel roadModelN;
    private RoadModel roadModelE;
    private RoadModel roadModelS;
    private RoadModel roadModelW;
    private Pane parentPane;

    public TrafficManager(RoadModel roadModelN, RoadModel roadModelE, RoadModel roadModelS, RoadModel roadModelW, Pane parentPane) {
        this.roadModelN = roadModelN;
        this.roadModelE = roadModelE;
        this.roadModelS = roadModelS;
        this.roadModelW = roadModelW;
        this.parentPane = parentPane;
    }

    @Override
    public void run() {
        ArrayList<CarModel> carModelArrayList = new ArrayList<>();
        for(int i=0; i<5; i++){
            Point2D roadCorner = roadModelN.getRoadView().getLeftUpperCorner();
            double laneWidth = roadModelN.getRoadView().getLaneWidth();
            double roadLength = roadModelN.getRoadView().getRoadLength();

            CarModel carModel = new CarModel(roadCorner.getX()+laneWidth/3, roadCorner.getY(), laneWidth/3, laneWidth/3);
            if(i%2==0){
                carModel.addTransition(0,2*roadLength+2*laneWidth,30);
            }else{
                carModel.addTransition(0,roadLength+0.5*laneWidth,30);
                carModel.addTransition(-(roadLength+laneWidth/3),0,30);
            }
            carModelArrayList.add(carModel);
            this.parentPane.getChildren().add(carModel);
        }

            try {
                for(CarModel carModel : carModelArrayList){
                    carModel.start();
                    Thread.sleep(2000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
