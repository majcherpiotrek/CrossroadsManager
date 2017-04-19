package simulation;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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
                2,40.0,260,360,260,360);
        SimpleShapePainter.drawShape(crossroadsView,context);
        /**
         * Add traffic lights to the crossroads
         */
        RoadModel roadN = new RoadModel(crossroadsView.getRoadNORTH());
        roadN.addLightsEndA(3000,3000);
        roadN.addLightsEndB(3000,3000);

        RoadModel roadE = new RoadModel(crossroadsView.getRoadEAST());
        roadE.addLightsEndA(3000,3000);
        roadE.addLightsEndB(3000,3000);

        RoadModel roadS = new RoadModel(crossroadsView.getRoadSOUTH());
        roadS.addLightsEndA(3000,3000);
        roadS.addLightsEndB(3000,3000);

        RoadModel roadW = new RoadModel(crossroadsView.getRoadWEST());
        roadW.addLightsEndA(3000,3000);
        roadW.addLightsEndB(3000,3000);

        canvasPane.getChildren().add(roadN.getTrafficLightsModelEndA().getTrafficLightsView().getLightsView());
        canvasPane.getChildren().add(roadN.getTrafficLightsModelEndB().getTrafficLightsView().getLightsView());

        canvasPane.getChildren().add(roadE.getTrafficLightsModelEndA().getTrafficLightsView().getLightsView());
        canvasPane.getChildren().add(roadE.getTrafficLightsModelEndB().getTrafficLightsView().getLightsView());

        canvasPane.getChildren().add(roadS.getTrafficLightsModelEndA().getTrafficLightsView().getLightsView());
        canvasPane.getChildren().add(roadS.getTrafficLightsModelEndB().getTrafficLightsView().getLightsView());

        canvasPane.getChildren().add(roadW.getTrafficLightsModelEndA().getTrafficLightsView().getLightsView());
        canvasPane.getChildren().add(roadW.getTrafficLightsModelEndB().getTrafficLightsView().getLightsView());

        TrafficManager manager = new TrafficManager(roadN,roadE,roadS,roadW,anchorPane);
        new Thread(manager).start();
//        new Thread(() ->{
//            for(int i=0; i < 4; i++) {
//                CarModel carModel = new CarModel(0 + 20 * i, 100, 10, 10);
//                carModel.addTransition(100,0,20);
//                carModel.addTransition(0,100,20);
//                carModel.addTransition(-100,0,20);
//                carModel.addTransition(0,-100,20);
//                anchorPane.getChildren().add(carModel);
//                new Thread(()->{
//
//                    try {
//                        carModel.start();
//                        Thread.sleep(2000);
//                        carModel.stop();
//                        System.out.println("STOP");
//                        Thread.sleep(10000);
//                        System.out.println("Staruje znowu!");
//                        carModel.start();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }).start();
//            }

////            ArrayList<TranslateTransition> transList = new ArrayList<>();
////            for(Node node : anchorPane.getChildren()){
////                transList.add(new TranslateTransition());
////                transList.get(transList.size()-1).setDuration(Duration.seconds(2));
////                transList.get(transList.size()-1).setNode(node);
////                transList.get(transList.size()-1).setFromX(500);
////                transList.get(transList.size()-1).setFromY(300);
////                transList.get(transList.size()-1).setToY(100);
////                transList.get(transList.size()-1).setToY(100);
////                transList.get(transList.size()-1).setToX(50);
////                transList.get(transList.size()-1).setToY(100);
////                transList.get(transList.size()-1).setAutoReverse(true);
////                transList.get(transList.size()-1).setCycleCount(10);
////                transList.get(transList.size()-1).play();
////                try {
////                    Thread.sleep(5000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                transList.get(transList.size()-1).pause();
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                //transList.get(transList.size()-1).play();
//
//            }
//        }).start();

        new Thread(roadN.getTrafficLightsModelEndA()).start();
        new Thread(roadN.getTrafficLightsModelEndB()).start();
    }
}
