package simulationview;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sun.awt.windows.ThemeReader;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
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

        for(int i=0; i < 10; i++)
            anchorPane.getChildren().add(new Rectangle(20+20*i,100,10,10));

        ArrayList<TranslateTransition> transList = new ArrayList<>();
        for(Node node : anchorPane.getChildren()){
            transList.add(new TranslateTransition());
            transList.get(transList.size()-1).setDuration(Duration.seconds(10));
            transList.get(transList.size()-1).setNode(node);
            transList.get(transList.size()-1).setToY(200);
            transList.get(transList.size()-1).play();
        }




    }
}
