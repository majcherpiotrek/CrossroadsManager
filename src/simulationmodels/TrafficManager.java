package simulationmodels;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Piotrek on 19.04.2017.
 */
public class TrafficManager implements Runnable {


//    private class CarControllerThread implements Runnable {
//
//        private CarModel car;
//        private List<CarModel> otherCars;
//        private List<TrafficLightsView> trafficLights;
//
//        public CarControllerThread(CarModel car, List<CarModel> otherCars, List<TrafficLightsView> trafficLights) {
//            this.car = car;
//            this.otherCars = otherCars;
//            this.trafficLights = trafficLights;
//        }
//
//        @Override
//        public void run() {
//            System.out.println("Car controller thread running");
//            boolean bumped = false;
//            boolean stoppedAtLights = false;
//            while (!Thread.interrupted()) {
//                synchronized (this.car){
//                    for( CarModel carInFront : this.otherCars) {
//                        if(carInFront == this.car)
//                            continue;
//                        //checking collision
//                        if(carInFront.getBoundsInParent().intersects(
//                                this.car.getBumperX(),
//                                this.car.getBumperY(),
//                                this.car.getBumberWidth(),
//                                this.car.getBumperHeight()) &&
//                                !this.car.getStopped()) {
//                            this.car.stop();
//                            bumped = true;
//                        }
//                        //checking if collision passed
//                        if(bumped &&
//                                !carInFront.getBoundsInParent().intersects(
//                                        this.car.getBumperX(),
//                                        this.car.getBumperY(),
//                                        this.car.getBumberWidth(),
//                                        this.car.getBumperHeight()) &&
//                                this.car.getStopped()) {
//                            this.car.start();
//                            bumped = false;
//
//                        }
//                    }
//                    //checking traffic lights
//                    for(TrafficLightsView trafficLightsView : this.trafficLights) {
//                        System.out.print("sialalalal");
//                        if(this.car.getBoundsInParent().intersects(trafficLightsView.getBoundsInParent()) &&
//                                trafficLightsView.getLight() == TrafficLightsView.Light.RED &&
//                                !this.car.getStopped()) {
//                            this.car.stop();
//                            stoppedAtLights = true;
//                        }
//
//                        if(this.car.getBoundsInParent().intersects(trafficLightsView.getBoundsInParent()) &&
//                                trafficLightsView.getLight() == TrafficLightsView.Light.GREEN &&
//                                this.car.getStopped() && stoppedAtLights) {
//                            this.car.start();
//                            stoppedAtLights = false;
//                        }
//                    }
//                }
//                try {
//                    Thread.sleep(30);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private CopyOnWriteArrayList<CarModel> allCars;
    private List<RoadModel> allRoads;
    private Pane parentPane;

    public TrafficManager(CopyOnWriteArrayList<CarModel> allCars, List<RoadModel> allRoads, Pane parentPane) {
        this.allCars = allCars;
        this.allRoads = allRoads;
        this.parentPane = parentPane;
    }

    @Override
    public void run() {
        System.out.println("Thread manager launched!");
        System.out.println("Car controller thread running");
        boolean bumped = false;
        boolean stoppedAtLights = false;

        while (!Thread.interrupted()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (CarModel car : this.allCars) {
                if (car.getDone()) {
                    //Platform.runLater(() -> this.parentPane.getChildren().remove(car));
                    this.allCars.remove(car);
                    System.out.println("Deleting car");
                    continue;
                }
                //CHECK IF THE CAR SHOULD STOP IF IS MOVING
                if (!car.getStopped()) {
                    for (RoadModel road : this.allRoads) {
                        if (road.getTrafficLightsModelEndA().getLight() == TrafficLightsView.Light.RED) {
                            if (road.getTrafficLightsModelEndA().getTrafficLightsView().getBoundsInParent().intersects(car.getBumperX(),car.getBumperY(),car.getBumberWidth(),car.getBumperHeight())) {
                                car.stop();
                                car.setStopType(CarModel.StopType.stoppedOnLights);
                            }
                        }

                        if (road.getTrafficLightsModelEndB().getLight() == TrafficLightsView.Light.RED) {
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
                            if (road.getTrafficLightsModelEndA().getLight() == TrafficLightsView.Light.GREEN) {
                                if (road.getTrafficLightsModelEndA().getTrafficLightsView().getBoundsInParent().intersects(car.getBumperX(), car.getBumperY(), car.getBumberWidth(), car.getBumperHeight())) {
                                    car.start();
                                }
                            }

                            if (road.getTrafficLightsModelEndB().getLight() == TrafficLightsView.Light.GREEN) {
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
        }

    }
}
