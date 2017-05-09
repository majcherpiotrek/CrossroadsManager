package simulationmodels;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private List<CarModel> allCars;
    private List<RoadModel> allRoads;

    public TrafficManager(List<CarModel> allCars, List<RoadModel> allRoads) {
        this.allCars = allCars;
        this.allRoads = allRoads;
    }

    @Override
    public void run() {
//        System.out.println("Thread manager launched!");
//        System.out.println("Car controller thread running");
//        boolean bumped = false;
//        boolean stoppedAtLights = false;
//
//            synchronized (this.allCars){
//                for(CarModel car : this.allCars)
//                    for( CarModel carInFront : this.allCars) {
//                    if(carInFront == car)
//                        continue;
//                    //checking collision
//                    if(carInFront.getBoundsInParent().intersects(
//                            car.getBumperX(),
//                            car.getBumperY(),
//                            car.getBumberWidth(),
//                            car.getBumperHeight()) &&
//                            !car.getStopped()) {
//                        car.stop();
//                        bumped = true;
//                    }
//                    //checking if collision passed
//                    if(bumped &&
//                            !carInFront.getBoundsInParent().intersects(
//                                    car.getBumperX(),
//                                    car.getBumperY(),
//                                    car.getBumberWidth(),
//                                    car.getBumperHeight()) &&
//                            car.getStopped()) {
//                        car.start();
//                        bumped = false;
//
//                    }
//                }
//                //checking traffic lights
//                for(TrafficLightsView trafficLightsView : this.trafficLights) {
//                    System.out.print("sialalalal");
//                    if(car.getBoundsInParent().intersects(trafficLightsView.getBoundsInParent()) &&
//                            trafficLightsView.getLight() == TrafficLightsView.Light.RED &&
//                            !car.getStopped()) {
//                        car.stop();
//                        stoppedAtLights = true;
//                    }
//
//                    if(car.getBoundsInParent().intersects(trafficLightsView.getBoundsInParent()) &&
//                            trafficLightsView.getLight() == TrafficLightsView.Light.GREEN &&
//                            car.getStopped() && stoppedAtLights) {
//                        car.start();
//                        stoppedAtLights = false;
//                    }
//                }
//            }
//            try {
//                Thread.sleep(30);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
    }
}
