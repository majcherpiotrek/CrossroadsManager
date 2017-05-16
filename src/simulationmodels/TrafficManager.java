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

    private CopyOnWriteArrayList<CarModel> allCars;
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

    private List<CarModel> allCars;
    private List<RoadModel> allRoads;

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

        new Thread(()->{

            while(true){

                try {
                    //Check collisions more or less every 30 milis
                    Thread.sleep(30);
                    for (CarModel car : this.allCars) {

                        for (CarModel secondCar : this.allCars) {
                            if(car == secondCar)
                                continue;

                            if(car.getBoundsInParent().intersects(secondCar.getBumperX(),secondCar.getBumperY(),secondCar.getBumberWidth(),secondCar.getBumperHeight())
                                    && !secondCar.getStopped()) {
                                secondCar.stop();
                            }

                        }
                    }
                    //TODO Check collisions between the cars
                    int j = 1;
                    for( CarModel car : this.allCars){
                        if (car.getDone()){
                            this.allCars.remove(car);
                            Platform.runLater(() -> this.parentPane.getChildren().add(car));
                            continue;
                        }
                        //Check collisions with the traffic lights on all the roads
                        for(RoadModel road : this.allRoads){
                            //Check if the car is crossing any of the traffic lights
                            //check road end A
                            if(car.getBoundsInParent().intersects(road.getTrafficLightsModelEndA().getTrafficLightsView().getBoundsInParent())){
                                //If the light is red and the car is running -> stop the car
                                if(road.getTrafficLightsModelEndA().getLight() == TrafficLightsView.Light.RED && !car.getStopped())
                                    car.stop();
                                //If the light is green and the car doesn't go -> start the car
                                if(road.getTrafficLightsModelEndA().getLight() == TrafficLightsView.Light.GREEN && car.getStopped())
                                    car.start();
                            } else {
                                // check road end B
                                if(car.getBoundsInParent().intersects(road.getTrafficLightsModelEndB().getTrafficLightsView().getBoundsInParent())){
                                    //If the light is red and the car is running -> stop the car
                                    if(road.getTrafficLightsModelEndB().getLight() == TrafficLightsView.Light.RED && !car.getStopped())
                                        car.stop();
                                    //If the light is green and the car doesn't go -> start the car
                                    if(road.getTrafficLightsModelEndB().getLight() == TrafficLightsView.Light.GREEN && car.getStopped())
                                        car.start();
                                }
                            }
                        }
                    }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

    }
}
