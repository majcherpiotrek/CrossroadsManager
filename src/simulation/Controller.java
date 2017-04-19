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
import simulationmodels.CrossroadsView;
import simulationmodels.RoadModel;
import simulationmodels.TrafficLightsModel;
import simulationmodels.TrafficLightsView;
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
                2,20.0,100,100,100,100);
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


        for(int i=0; i < 4; i++)
            anchorPane.getChildren().add(new Rectangle(0+20*i,100,10,10));

        ArrayList<TranslateTransition> transList = new ArrayList<>();
        for(Node node : anchorPane.getChildren()){
            transList.add(new TranslateTransition());
            transList.get(transList.size()-1).setDuration(Duration.seconds(10));
            transList.get(transList.size()-1).setNode(node);
            transList.get(transList.size()-1).setToY(200);
            transList.get(transList.size()-1).setAutoReverse(true);
            transList.get(transList.size()-1).setCycleCount(10);
            transList.get(transList.size()-1).play();
        }
        new Thread(roadN.getTrafficLightsModelEndA()).start();
        new Thread(roadN.getTrafficLightsModelEndB()).start();
    }
}
