package simulationmodels;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import static java.lang.System.out;

/**
 * Created by Olaf on 2017-04-11.
 */
public class CarController {

    private CarModel car;

    public CarController(CarModel car) {
        this.car = car;
    }

    public void rideFrorward(int pixels) {
        TranslateTransition trs = new TranslateTransition();
        trs.setNode(this.car.getCarView());
        trs.setDuration(new Duration(pixels*50));

        trs.setToX(-pixels);

        trs.play();
    }
}
