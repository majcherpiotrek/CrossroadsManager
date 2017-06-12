package simulationmodels;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.layout.Pane;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A factory for the CarModel class
 * Created by Piotrek on 08.05.2017.
 */
public class CarModelGenerator implements Runnable{

    private class Generator implements Runnable {
        private Point2D entryPoint;
        private Integer timeBetweenCarsMilis;
        private List<Point3D> carRoute;
        private Pane parent;
        private double carsWidth;
        private double carsHeight;
        private List<CarModel> generatedCarsList;
        private List<RoadModel> roadsList;

        public void setTimeBetweenCarsMilis(Integer timeBetweenCarsMilis) {
            this.timeBetweenCarsMilis = timeBetweenCarsMilis;
        }

        public Generator(List<RoadModel> roadsList, List<CarModel> generatedCarsList, Point2D entryPoint, Integer timeBetweenCarsMilis, List<Point3D> carRoute, Pane parent, double carsWidth, double carsHeight) {
            this.generatedCarsList = generatedCarsList;
            this.entryPoint = entryPoint;
            this.timeBetweenCarsMilis = timeBetweenCarsMilis;
            this.carRoute = carRoute;
            this.parent = parent;
            this.carsWidth = carsWidth;
            this.carsHeight = carsHeight;
            this.roadsList = roadsList;
        }

        @Override
        public void run() {
            while (true) {
                CarModel carModel = new CarModel(entryPoint.getX(), entryPoint.getY(), this.carsWidth, this.carsHeight);
                for(Point3D transition : this.carRoute) {
                    carModel.addTransition(transition.getX(), transition.getY(), transition.getZ());
                }

                synchronized (this.generatedCarsList) {
                    this.generatedCarsList.add(carModel);
                    Platform.runLater(() -> parent.getChildren().add(carModel));
                    carModel.start();
                }
                try {
                    Thread.sleep(this.timeBetweenCarsMilis);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    List<CarModel> generatedCarsList;
    private Pane parent;
    private List<Generator> carGenerators;
    private List<RoadModel> roadsList;

    public CarModelGenerator(Pane parent, List<CarModel> generatedCarsList, List<RoadModel> roadsList) {
        this.generatedCarsList = generatedCarsList;
        this.parent = parent;
        this.carGenerators = new LinkedList<>();
        this.roadsList = roadsList;
    }

    public void addRoadTraffic(Point2D entryPoint, Integer timeBetweenCarsMilis, List<Point3D> carRoute, double carsWidth, double carsHeight) {
        this.carGenerators.add(new Generator(this.roadsList, this.generatedCarsList, entryPoint, timeBetweenCarsMilis, carRoute, this.parent, carsWidth, carsHeight));
    }


    @Override
    public void run() {
        System.out.println("Starting the car model factory...");
        if(this.carGenerators.isEmpty())
            return;
        ExecutorService executor = Executors.newFixedThreadPool(this.carGenerators.size());
        System.out.println("Preparing to launch "+this.carGenerators.size()+" car generator threads...");
        for( Runnable r : this.carGenerators) {
            executor.execute(r);
        }
        System.out.println("Generator threads working!");
        while (!Thread.interrupted()) {
        }
        executor.shutdownNow();
        while (!executor.isTerminated()){
        }
        System.out.println("The car model factory finished working!");
    }

    public void setTimeBetweenCars(int generatorId, int value) {
        this.carGenerators.get(generatorId).setTimeBetweenCarsMilis(value);
    }
}
