package interfaces;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface for objects drawable on the javafx.scene.canvas.GraphicsContext
 * Created by Piotrek on 28.03.2017.
 */
public interface Drawable {

    public void draw(GraphicsContext gc);
}
