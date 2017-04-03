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
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import simulationmodels.CrossroadsModel;
import simulationmodels.RoadModel;
import util.SimpleShapePainter;


import java.awt.*;
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

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.CHOCOLATE);
        context.fillRect(0,0,800,600);

        //RoadModel roadModelV = new RoadModel(600.0,20.0,2, RoadModel.Orientation.VERTICAL, new Point2D(380.0,0.0));
        //RoadModel roadModelH = new RoadModel(800.0,20.0,2, RoadModel.Orientation.HORIZONTAL, new Point2D(0.0,280.0));
        //SimpleShapePainter.drawShape(roadModelV,context);
        //SimpleShapePainter.drawShape(roadModelH,context);

        CrossroadsModel crossroadsModel = new CrossroadsModel(new Point2D(380.0,280.0),
                2,20.0,100,100,100,100);
        SimpleShapePainter.drawShape(crossroadsModel,context);


        for(int i=0; i < 100; i++)
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

        new Thread(() -> {
            for(int i=0; i<15;i++)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            for(int i=0; i< transList.size(); i++)
                if(i%2 == 0)
                    transList.get(i).pause();

        }).start();




    }
}
