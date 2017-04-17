package simulationmodels;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Olaf on 2017-04-10.
 */
public class CarModel {

    private final Group carGroup = new Group();

    public enum Facing {
        NORTH, EAST, SOUTH, WEST
    }

    private Point2D leftUpperCorner;
    private double width;
    private double length;
    private Facing facing;
    private Color bodyColor;



    public CarModel(Point2D leftUpperCorner, double width, double length, Facing facing, Color bodyColor) {
        this(leftUpperCorner, width, length, facing);
        this.bodyColor = bodyColor;
    }
    public CarModel(Point2D leftUpperCorner, double width, double length, Facing facing) {
        this(leftUpperCorner, width, length);
        this.facing = facing;
    }
    public CarModel(Point2D leftUpperCorner, double width, double length) {
        this.leftUpperCorner = leftUpperCorner;
        this.width = width;
        this.length = length;
        this.facing = Facing.NORTH;
        this.bodyColor = Color.SILVER;
    }

    public Group getCarView(){
        Rectangle car = new Rectangle(this.leftUpperCorner.getX(), this.leftUpperCorner.getY(), this.width, this.length);
        Rectangle frontLightL = new Rectangle(this.leftUpperCorner.getX(), this.leftUpperCorner.getY(), this.width*0.3, this.length*0.1);
        Rectangle frontLightR = new Rectangle(this.leftUpperCorner.getX()+0.7*this.width, this.leftUpperCorner.getY(), this.width*0.3, this.length*0.1);
        Rectangle rearLightL = new Rectangle(this.leftUpperCorner.getX(), this.leftUpperCorner.getY()+0.9*this.length, this.width*0.3, this.length*0.1);
        Rectangle rearLightR = new Rectangle(this.leftUpperCorner.getX()+0.7*this.width, this.leftUpperCorner.getY()+0.9*this.length, this.width*0.3, this.length*0.1);

        rearLightR.setFill(Color.RED);
        rearLightL.setFill(Color.RED);
        frontLightR.setFill(Color.GOLD);
        frontLightL.setFill(Color.GOLD);
        car.setFill(this.bodyColor);

        carGroup.getChildren().add(car);
        carGroup.getChildren().add(frontLightL);
        carGroup.getChildren().add(frontLightR);
        carGroup.getChildren().add(rearLightR);
        carGroup.getChildren().add(rearLightL);

        this.rotateToFacingDirection();

        return carGroup;

    }

    private void rotateToFacingDirection() {
        if (this.facing == Facing.EAST){
            this.carGroup.setRotate(90.0);
        }
        else if (this.facing == Facing.SOUTH){
            this.carGroup.setRotate(180.0);
        }
        else if (this.facing == Facing.WEST){
            this.carGroup.setRotate(-90.0);
        }
    }

    public void setFacing(Facing facing) {
        this.facing = facing;
    }
}
