package simulationmodels;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.layout.Pane;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A factory for the CarModel class
 * Created by Piotrek on 08.05.2017.
 */
public class CarModelGenerator{

    private class Generator implements Runnable {
        private Point2D entryPoint;
        private Integer timeBetweenCarsMilis;
        private List<Point3D> carRoute;
        private Pane parent;
        private double carsWidth;
        private double carsHeight;
        private final List<CarModel> generatedCarsList;

        void setTimeBetweenCarsMilis(Integer timeBetweenCarsMilis) {
            this.timeBetweenCarsMilis = timeBetweenCarsMilis;
        }

        Integer getTimeBetweenCarsMilis() {
            return timeBetweenCarsMilis;
        }

        Generator(List<CarModel> generatedCarsList, Point2D entryPoint, Integer timeBetweenCarsMilis, List<Point3D> carRoute, Pane parent, double carsWidth, double carsHeight) {
            this.generatedCarsList = generatedCarsList;
            this.entryPoint = entryPoint;
            this.timeBetweenCarsMilis = timeBetweenCarsMilis;
            this.carRoute = carRoute;
            this.parent = parent;
            this.carsWidth = carsWidth;
            this.carsHeight = carsHeight;
        }

        @Override
        public void run() {
                CarModel carModel = new CarModel(entryPoint.getX(), entryPoint.getY(), this.carsWidth, this.carsHeight);
                for(Point3D transition : this.carRoute) {
                    carModel.addTransition(transition.getX(), transition.getY(), transition.getZ());
                }

                synchronized (this.generatedCarsList) {
                    this.generatedCarsList.add(carModel);
                    Platform.runLater(() -> parent.getChildren().add(carModel));
                    carModel.start();
                }
        }
    }

    private List<CarModel> generatedCarsList;
    private Pane parent;
    private List<Generator> carGenerators;
    private ScheduledExecutorService executorService;

    public CarModelGenerator(Pane parent, List<CarModel> generatedCarsList) {
        this.generatedCarsList = generatedCarsList;
        this.parent = parent;
        this.carGenerators = new LinkedList<>();
    }

    public void addRoadTraffic(Point2D entryPoint, Integer timeBetweenCarsMilis, List<Point3D> carRoute, double carsWidth, double carsHeight) {
        this.carGenerators.add(new Generator(this.generatedCarsList, entryPoint, timeBetweenCarsMilis, carRoute, this.parent, carsWidth, carsHeight));
    }

    public void startGenerator() {
        if (!carGenerators.isEmpty()) {
            executorService = Executors.newScheduledThreadPool(carGenerators.size());
            for (Generator g : carGenerators) {
                executorService.scheduleAtFixedRate(g, 0, g.getTimeBetweenCarsMilis(), TimeUnit.MILLISECONDS);
            }
        }
    }

    public void stopGenerator() {
        Runnable stopTask = () -> executorService.shutdown();
        Thread t = new Thread(stopTask);
        System.out.println("Stopping the car model generator...\n");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Car model generator stopped!\n");
    }

    public void setTimeBetweenCars(int generatorId, int value) {
        this.carGenerators.get(generatorId).setTimeBetweenCarsMilis(value);
    }
}
