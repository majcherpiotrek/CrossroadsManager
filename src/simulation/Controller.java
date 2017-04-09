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
import simulationmodels.TrafficLightsController;
import simulationmodels.TrafficLightsModel;
import util.SimpleShapePainter;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    private @FXML
    StackPane stackPane;

    private @FXML
    Canvas canvas;

    private @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        crossroadsView.setLightsEAST(new TrafficLightsModel(TrafficLightsModel.Direction.West));
        crossroadsView.setLightsNORTH(new TrafficLightsModel(TrafficLightsModel.Direction.South));
        crossroadsView.setLightsWEST(new TrafficLightsModel(TrafficLightsModel.Direction.East));
        crossroadsView.setLightsSOUTH(new TrafficLightsModel(TrafficLightsModel.Direction.North));

        TrafficLightsController trafficLightsController = new TrafficLightsController(
                crossroadsView,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0);

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

        anchorPane.getChildren().add(crossroadsView.getLightsEAST().getLightsView());
        anchorPane.getChildren().add(crossroadsView.getLightsNORTH().getLightsView());
        anchorPane.getChildren().add(crossroadsView.getLightsWEST().getLightsView());
        anchorPane.getChildren().add(crossroadsView.getLightsSOUTH().getLightsView());


        Thread t = new Thread(trafficLightsController);
        t.start();
    }
}
