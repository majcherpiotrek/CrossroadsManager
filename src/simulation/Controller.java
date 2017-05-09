package simulation;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import simulationmodels.*;
import util.CanvasPane;
import util.SimpleShapePainter;


import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    private @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Create the Canvas and add it to the window
         */
        CanvasPane canvasPane = new CanvasPane(800,600);
        Canvas canvas = canvasPane.getCanvas();
        AnchorPane anchorPane = new AnchorPane();
        stackPane.getChildren().addAll(canvasPane,anchorPane);

        /**
         * Prepare background
         */
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.CHOCOLATE);
        context.fillRect(0,0,800,600);

        /**
         * Draw the crossroads
         */
        CrossroadsView crossroadsView = new CrossroadsView(new Point2D(380.0,280.0),
                2,40.0,260,360,250,350);
        SimpleShapePainter.drawShape(crossroadsView,context);
        /**
         * Add traffic lights to the crossroads
         */
        RoadModel roadN = new RoadModel(crossroadsView.getRoadNORTH());
        roadN.addLightsEndA(5000,3000);
        roadN.addLightsEndB(5000,3000);

        RoadModel roadE = new RoadModel(crossroadsView.getRoadEAST());
        roadE.addLightsEndA(5000,3000);
        roadE.addLightsEndB(5000,3000);

        RoadModel roadS = new RoadModel(crossroadsView.getRoadSOUTH());
        roadS.addLightsEndA(5000,3000);
        roadS.addLightsEndB(5000,3000);

        RoadModel roadW = new RoadModel(crossroadsView.getRoadWEST());
        roadW.addLightsEndA(5000,3000);
        roadW.addLightsEndB(5000,3000);

        canvasPane.getChildren().add(roadN.getTrafficLightsModelEndA().getTrafficLightsView());
        canvasPane.getChildren().add(roadN.getTrafficLightsModelEndB().getTrafficLightsView());

        canvasPane.getChildren().add(roadE.getTrafficLightsModelEndA().getTrafficLightsView());
        canvasPane.getChildren().add(roadE.getTrafficLightsModelEndB().getTrafficLightsView());

        canvasPane.getChildren().add(roadS.getTrafficLightsModelEndA().getTrafficLightsView());
        canvasPane.getChildren().add(roadS.getTrafficLightsModelEndB().getTrafficLightsView());

        canvasPane.getChildren().add(roadW.getTrafficLightsModelEndA().getTrafficLightsView());
        canvasPane.getChildren().add(roadW.getTrafficLightsModelEndB().getTrafficLightsView());

        try {
            new Thread(roadN.getTrafficLightsModelEndA()).start();
            Thread.sleep(1000);
            new Thread(roadN.getTrafficLightsModelEndB()).start();
            Thread.sleep(1000);
            new Thread(roadS.getTrafficLightsModelEndA()).start();
            Thread.sleep(1000);
            new Thread(roadS.getTrafficLightsModelEndB()).start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<RoadModel> roadModels = new ArrayList<>();
        roadModels.add(roadE);
        roadModels.add(roadN);
        roadModels.add(roadS);
        roadModels.add(roadW);
        ArrayList<CarModel> carModels = new ArrayList<>();

        CarModelGenerator carModelGenerator = new CarModelGenerator(anchorPane,carModels,roadModels);
        List<Point3D> roadERoutes = new LinkedList<>();
        roadERoutes.add(new Point3D(-3*roadE.getRoadView().getRoadLength(),0,40));
        carModelGenerator.addRoadTraffic(new Point2D(
                roadE.getRoadView().getLeftUpperCorner().getX()+roadE.getRoadView().getRoadLength(),
                roadE.getRoadView().getLeftUpperCorner().getY()+roadE.getRoadView().getLaneWidth()/3),
                4000, roadERoutes, 10, 10);
        List<Point3D> roadNRoutes = new LinkedList<>();
        roadNRoutes.add(new Point3D(0,3*roadN.getRoadView().getRoadLength(),40));
        carModelGenerator.addRoadTraffic(new Point2D(
                roadN.getRoadView().getLeftUpperCorner().getX()+roadN.getRoadView().getLaneWidth()/3,
                roadN.getRoadView().getLeftUpperCorner().getY()),
                4000,roadNRoutes,10,10);
        new Thread(carModelGenerator).start();
//        CarModel car = new CarModel(
//                roadN.getRoadView().getLeftUpperCorner().getX()+roadN.getRoadView().getLaneWidth()/3,
//                roadN.getRoadView().getLeftUpperCorner().getY(),
//                roadN.getRoadView().getLaneWidth()/3,
//                roadN.getRoadView().getLaneWidth()/3
//        );
//
//        car.addTransition(0, roadN.getRoadView().getRoadLength()*2,40);
//        car.addTransition(0, -roadN.getRoadView().getRoadLength()*2,40);
//        car.addTransition(0, roadN.getRoadView().getRoadLength()*2,40);
//        car.addTransition(0, -roadN.getRoadView().getRoadLength()*2,40);
//        car.addTransition(0, roadN.getRoadView().getRoadLength()*2,40);
//
//        carModels.add(car);
//        anchorPane.getChildren().add(car);
//        car.start();
//
//        new Thread(() -> {
//            while (true){
//                try {
//                    CarModel car2 = new CarModel(
//                            roadN.getRoadView().getLeftUpperCorner().getX()+roadN.getRoadView().getLaneWidth()/3,
//                            roadN.getRoadView().getLeftUpperCorner().getY(),
//                            roadN.getRoadView().getLaneWidth()/3,
//                            roadN.getRoadView().getLaneWidth()/3
//                    );
//
//                    car2.addTransition(0, roadN.getRoadView().getRoadLength()*2,40);
//                    car2.addTransition(0, -roadN.getRoadView().getRoadLength()*2,40);
//                    car2.addTransition(0, roadN.getRoadView().getRoadLength()*2,40);
//                    car2.addTransition(0, -roadN.getRoadView().getRoadLength()*2,40);
//                    car2.addTransition(0, roadN.getRoadView().getRoadLength()*2,40);
//
//                    carModels.add(car2);
//                    Platform.runLater(() -> anchorPane.getChildren().add(car2));
//                    car2.start();
//                    Thread.sleep(3000);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        TrafficManager manager = new TrafficManager(carModels, roadModels);
        new Thread(manager).start();

        }
}
