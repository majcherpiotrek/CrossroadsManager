package simulation;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import simulationmodels.*;
import util.CanvasPane;
import util.SimpleShapePainter;


import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;


public class Controller implements Initializable{

    private @FXML
    StackPane stackPane;

    private @FXML
    GridPane gridPane;

    private AnchorPane anchorPane;
    private TrafficManager manager;
    private CarModelGenerator carModelGenerator;
    private TrafficLightsController lightsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Create the Canvas and add it to the window
         */
        CanvasPane canvasPane = new CanvasPane(800,900);
        Canvas canvas = canvasPane.getCanvas();
        anchorPane = new AnchorPane();
        stackPane.getChildren().addAll(canvasPane,anchorPane);

        Text text1 = new Text("Road 1 car freq");
        Text text2 = new Text("Road 2 car freq");
        Text text3 = new Text("Road 3 car freq");
        Text text4 = new Text("Road 4 car freq");

        Slider slider1 = new Slider(100, 5000, 150);
        Slider slider2 = new Slider(100, 5000, 150);
        Slider slider3 = new Slider(100, 5000, 150);
        Slider slider4 = new Slider(100, 5000, 150);

        TextField textfield1 = new TextField();
        TextField textfield2 = new TextField();
        TextField textfield3 = new TextField();
        TextField textfield4 = new TextField();
        textfield1.setMaxWidth(50);
        textfield2.setMaxWidth(50);
        textfield3.setMaxWidth(50);
        textfield4.setMaxWidth(50);

        Button button1 = new Button("Play");
        Button button2 = new Button("Stop");

        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(text1, 0, 0);
        gridPane.add(slider1, 1, 0);
        gridPane.add(textfield1, 2, 0);

        gridPane.add(text2, 0, 1);
        gridPane.add(slider2, 1, 1);
        gridPane.add(textfield2, 2, 1);

        gridPane.add(text3, 0, 2);
        gridPane.add(slider3, 1, 2);
        gridPane.add(textfield3, 2, 2);


        gridPane.add(text4, 0, 3);
        gridPane.add(slider4, 1, 3);
        gridPane.add(textfield4, 2, 3);

        gridPane.add(button1, 0, 6);
        gridPane.add(button2, 1, 6);

        /**
         * Prepare background
         */
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.CHOCOLATE);
        context.fillRect(0,0,800,600);

        /**
         * Draw the crossroads
         */
        CrossroadsView crossroadsView = new CrossroadsView(new Point2D(380.0/2,280.0/2),
                2,40.0/2,260/2,360/2,250/2,350/2);
        SimpleShapePainter.drawShape(crossroadsView,context);

        CrossroadsView crossroadsView2 = new CrossroadsView(new Point2D(585,280.0/2),
                2,40.0/2,260/2,360/2,250/2,350/2);
        SimpleShapePainter.drawShape(crossroadsView2,context);
        /**
         * Add traffic lights to the crossroads
         */
        RoadModel roadN = new RoadModel(crossroadsView.getRoadNORTH());
        roadN.addLightsEndB(5000,3000);

        RoadModel roadE = new RoadModel(crossroadsView.getRoadEAST());
        roadE.addLightsEndA(5000,3000);
        roadE.getTrafficLightsModelEndA().setOffset(3000);

        RoadModel roadS = new RoadModel(crossroadsView.getRoadSOUTH());
        roadS.addLightsEndA(5000,3000);

        RoadModel roadW = new RoadModel(crossroadsView.getRoadWEST());
        roadW.addLightsEndB(5000,3000);
        roadW.getTrafficLightsModelEndB().setOffset(3000);

        RoadModel roadN2 = new RoadModel(crossroadsView2.getRoadNORTH());
        roadN2.addLightsEndB(5000,3000);

        RoadModel roadE2 = new RoadModel(crossroadsView2.getRoadEAST());
        roadE2.addLightsEndA(5000,3000);
        roadE2.getTrafficLightsModelEndA().setOffset(3000);

        RoadModel roadS2 = new RoadModel(crossroadsView2.getRoadSOUTH());
        roadS2.addLightsEndA(5000,3000);

        RoadModel roadW2 = new RoadModel(crossroadsView2.getRoadWEST());
        roadW2.addLightsEndB(5000,3000);
        roadW2.getTrafficLightsModelEndB().setOffset(3000);

        canvasPane.getChildren().add(roadN.getTrafficLightsModelEndB().getTrafficLightsView());

        canvasPane.getChildren().add(roadE.getTrafficLightsModelEndA().getTrafficLightsView());

        canvasPane.getChildren().add(roadS.getTrafficLightsModelEndA().getTrafficLightsView());

        canvasPane.getChildren().add(roadW.getTrafficLightsModelEndB().getTrafficLightsView());

        canvasPane.getChildren().add(roadN2.getTrafficLightsModelEndB().getTrafficLightsView());

        canvasPane.getChildren().add(roadE2.getTrafficLightsModelEndA().getTrafficLightsView());

        canvasPane.getChildren().add(roadS2.getTrafficLightsModelEndA().getTrafficLightsView());

        canvasPane.getChildren().add(roadW2.getTrafficLightsModelEndB().getTrafficLightsView());

        ArrayList<RoadModel> roadModels = new ArrayList<>();
        roadModels.add(roadE);
        roadModels.add(roadN);
        roadModels.add(roadS);
        roadModels.add(roadW);

        roadModels.add(roadE2);
        roadModels.add(roadN2);
        roadModels.add(roadS2);
        roadModels.add(roadW2);

        /**
         * Create cars
         */
        CopyOnWriteArrayList<CarModel> carModels = new CopyOnWriteArrayList<>();

        carModelGenerator = new CarModelGenerator(anchorPane,carModels);

        List<Point3D> roadNRoutes = new LinkedList<>();
        roadNRoutes.add(new Point3D(0,3*roadN.getRoadView().getRoadLength(),40));
        carModelGenerator.addRoadTraffic(new Point2D(
                roadN.getRoadView().getLeftUpperCorner().getX() + roadN.getRoadView().getLaneWidth()/3,
                roadN.getRoadView().getLeftUpperCorner().getY()),
                1000,roadNRoutes,roadN.getRoadView().getLaneWidth()/3, roadN.getRoadView().getLaneWidth()/3);
        List<Point3D> roadWRoutes = new LinkedList<>();
        roadWRoutes.add(new Point3D(5*roadW.getRoadView().getRoadLength(),0,40));
        carModelGenerator.addRoadTraffic(new Point2D(
                        roadW.getRoadView().getLeftUpperCorner().getX(),
                        roadW.getRoadView().getLeftUpperCorner().getY() + roadW.getRoadView().getLaneWidth()+roadW.getRoadView().getLaneWidth()/3),
                1000,roadWRoutes,roadW.getRoadView().getLaneWidth()/3, roadW.getRoadView().getLaneWidth()/3);
        List<Point3D> roadSRoutes = new LinkedList<>();
        roadSRoutes.add(new Point3D(0,-3*roadS.getRoadView().getRoadLength(),40));
        carModelGenerator.addRoadTraffic(new Point2D(
                        roadS.getRoadView().getLeftUpperCorner().getX()+roadS.getRoadView().getLaneWidth()+roadS.getRoadView().getLaneWidth()/3,
                        roadS.getRoadView().getLeftUpperCorner().getY()+roadS.getRoadView().getRoadLength()),
                1000,roadSRoutes,roadS.getRoadView().getLaneWidth()/3, roadS.getRoadView().getLaneWidth()/3);

        List<Point3D> roadERoutes2 = new LinkedList<>();
        roadERoutes2.add(new Point3D(-5*roadE2.getRoadView().getRoadLength(),0,40));
        carModelGenerator.addRoadTraffic(new Point2D(
                        roadE2.getRoadView().getLeftUpperCorner().getX()+roadE2.getRoadView().getRoadLength(),
                        roadE2.getRoadView().getLeftUpperCorner().getY()+roadE2.getRoadView().getLaneWidth()/3),
                5000, roadERoutes2, roadE2.getRoadView().getLaneWidth()/3, roadE2.getRoadView().getLaneWidth()/3);
        List<Point3D> roadNRoutes2 = new LinkedList<>();
        roadNRoutes2.add(new Point3D(0,3*roadN2.getRoadView().getRoadLength(),40));
        carModelGenerator.addRoadTraffic(new Point2D(
                        roadN2.getRoadView().getLeftUpperCorner().getX() + roadN2.getRoadView().getLaneWidth()/3,
                        roadN2.getRoadView().getLeftUpperCorner().getY()),
                5000,roadNRoutes2,roadN2.getRoadView().getLaneWidth()/3, roadN2.getRoadView().getLaneWidth()/3);
        List<Point3D> roadSRoutes2 = new LinkedList<>();
        roadSRoutes2.add(new Point3D(0,-3*roadS2.getRoadView().getRoadLength(),40));
        carModelGenerator.addRoadTraffic(new Point2D(
                        roadS2.getRoadView().getLeftUpperCorner().getX()+roadS2.getRoadView().getLaneWidth()+roadS2.getRoadView().getLaneWidth()/3,
                        roadS2.getRoadView().getLeftUpperCorner().getY()+roadS2.getRoadView().getRoadLength()),
                5000,roadSRoutes2,roadS2.getRoadView().getLaneWidth()/3, roadS2.getRoadView().getLaneWidth()/3);

        List<TrafficLightsModel> lightsModels = new ArrayList<>();
        lightsModels.add(roadN.getTrafficLightsModelEndB());
        lightsModels.add(roadS.getTrafficLightsModelEndA());
        lightsModels.add(roadE.getTrafficLightsModelEndA());
        lightsModels.add(roadW.getTrafficLightsModelEndB());
        lightsModels.add(roadN2.getTrafficLightsModelEndB());
        lightsModels.add(roadS2.getTrafficLightsModelEndA());
        lightsModels.add(roadE2.getTrafficLightsModelEndA());
        lightsModels.add(roadW2.getTrafficLightsModelEndB());

        button1.setOnAction(event -> runSimulation(roadModels, carModels, lightsModels));
        button2.setOnAction(event -> this.stopSimulation());

        slider1.valueProperty().addListener((observable, oldValue, newValue) -> {
            carModelGenerator.setTimeBetweenCars(0, newValue.intValue());
            textfield1.setText(String.valueOf(slider1.getValue()));
        });
        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {
            carModelGenerator.setTimeBetweenCars(1, newValue.intValue());
            textfield2.setText(String.valueOf(slider2.getValue()));
        });
        slider3.valueProperty().addListener((observable, oldValue, newValue) -> {
            carModelGenerator.setTimeBetweenCars(2, newValue.intValue());
            textfield3.setText(String.valueOf(slider3.getValue()));
        });
        slider4.valueProperty().addListener((observable, oldValue, newValue) -> {
            carModelGenerator.setTimeBetweenCars(3, newValue.intValue());
            textfield4.setText(String.valueOf(slider4.getValue()));
        });
    }

    private void stopSimulation() {
        if (manager != null) {
            manager.stopManager();
        }

        if (carModelGenerator != null) {
            carModelGenerator.stopGenerator();
        }

        if (lightsController != null) {
            lightsController.stopLights();
        }

        Platform.runLater(() -> anchorPane.getChildren().clear());
    }

    private void runSimulation(ArrayList<RoadModel> roadModels, CopyOnWriteArrayList<CarModel> carModels, List<TrafficLightsModel> lightsModels) {
        lightsController = new TrafficLightsController(lightsModels);
        lightsController.startLights();
        carModelGenerator.startGenerator();
        manager = new TrafficManager(carModels, roadModels, anchorPane);
        manager.startManager();
    }
}
