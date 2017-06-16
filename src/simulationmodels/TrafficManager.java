package simulationmodels;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Piotrek on 19.04.2017.
 */
public class TrafficManager {

    private CopyOnWriteArrayList<CarModel> allCars;
    private List<RoadModel> allRoads;
    private Pane parentPane;
    private ScheduledExecutorService executorService;
    private final long PERIOD = 40;

    public TrafficManager(CopyOnWriteArrayList<CarModel> allCars, List<RoadModel> allRoads, Pane parentPane) {
        this.executorService = Executors.newScheduledThreadPool(2);
        this.allCars = allCars;
        this.allRoads = allRoads;
        this.parentPane = parentPane;
    }

    public void startManager() {
        Runnable managerTask = () -> {
            boolean bumped = false;
            boolean stoppedAtLights = false;

            for (CarModel car : this.allCars) {
                //CHECK IF THE CAR SHOULD STOP IF IS MOVING
                if (!car.getStopped()) {
                    for (RoadModel road : this.allRoads) {
                        if (road.getTrafficLightsModelEndA()!= null && road.getTrafficLightsModelEndA().getLight() == TrafficLightsView.Light.RED) {
                            if (road.getTrafficLightsModelEndA().getTrafficLightsView().getBoundsInParent().intersects(car.getBumperX(),car.getBumperY(),car.getBumberWidth(),car.getBumperHeight())) {
                                car.stop();
                                car.setStopType(CarModel.StopType.stoppedOnLights);
                            }
                        }

                        if (road.getTrafficLightsModelEndB() != null && road.getTrafficLightsModelEndB().getLight() == TrafficLightsView.Light.RED) {
                            if(road.getTrafficLightsModelEndB().getTrafficLightsView().getBoundsInParent().intersects(car.getBumperX(),car.getBumperY(),car.getBumberWidth(),car.getBumperHeight())) {
                                car.stop();
                                car.setStopType(CarModel.StopType.stoppedOnLights);
                            }
                        }
                    }
                    for (CarModel secondCar : this.allCars) {
                        if (secondCar == car)
                            continue;
                        if (secondCar.getBoundsInParent().intersects(car.getBumperX(), car.getBumperY(), car.getBumberWidth(), car.getBumperHeight())) {
                            car.stop();
                            car.setStopType(CarModel.StopType.crashed);
                        }
                    }
                } else {
                    //CHECK IF THE CAR SHOULD START IF STOPPED ON LIGHTS
                    if (car.getStopType().equals(CarModel.StopType.stoppedOnLights)){
                        for (RoadModel road : this.allRoads) {
                            if (road.getTrafficLightsModelEndA() != null && road.getTrafficLightsModelEndA().getLight() == TrafficLightsView.Light.GREEN) {
                                if (road.getTrafficLightsModelEndA().getTrafficLightsView().getBoundsInParent().intersects(car.getBumperX(), car.getBumperY(), car.getBumberWidth(), car.getBumperHeight())) {
                                    car.start();
                                }
                            }

                            if (road.getTrafficLightsModelEndB() != null && road.getTrafficLightsModelEndB().getLight() == TrafficLightsView.Light.GREEN) {
                                if (road.getTrafficLightsModelEndB().getTrafficLightsView().getBoundsInParent().intersects(car.getBumperX(), car.getBumperY(), car.getBumberWidth(), car.getBumperHeight())) {
                                    car.start();
                                }
                            }
                        }
                    }
                    //CHECK IF THE CAR SHOULD START IF STOPPED ON CRASH
                    boolean canStartAgain = true;
                    if (car.getStopType().equals(CarModel.StopType.crashed)) {
                        for (CarModel secondCar : this.allCars) {
                            if (secondCar == car)
                                continue;
                            if (secondCar.getBoundsInParent().intersects(car.getBumperX(), car.getBumperY(), car.getBumberWidth(), car.getBumperHeight())) {
                                canStartAgain = false;
                                break;
                            }
                        }
                        if (canStartAgain) {
                            car.start();
                        }
                    }
                }
            }
        };
        Runnable carGarbageCollectorTask = () -> {
            synchronized (allCars) {
                for (Iterator<CarModel> it = allCars.iterator(); it.hasNext();) {
                    CarModel car = it.next();
                    if (car.getDone()) {
                        allCars.remove(car);
                        Platform.runLater(() -> {
                            int index = parentPane.getChildren().indexOf(car);
                            parentPane.getChildren().remove(index);
                        });
                    }
                }
            }
        };
        executorService.scheduleAtFixedRate(managerTask, 0, PERIOD, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(carGarbageCollectorTask, 250, 250, TimeUnit.MILLISECONDS);
    }

    public void stopManager() {
        Runnable stopTask = () -> executorService.shutdown();
        Thread t = new Thread(stopTask);
        System.out.println("Stopping the traffic manager...\n");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Traffic manager stopped\n");
    }

}
