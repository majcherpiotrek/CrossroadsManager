package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    private @FXML Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.AQUA);
        gc.setStroke(Color.RED);

        gc.strokeLine(0,0,50,50);
        gc.fillOval(50,50,20,20);
    }
}
