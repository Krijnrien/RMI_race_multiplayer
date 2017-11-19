package Client;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

class Car {

	private Rectangle graphics;
	DoubleProperty locationX, locationY;
	private double direction = 0;
	private double speed = 0;
	private boolean isColliding;
	private ImageView graphicsImg;

	Rectangle getGraphics() {
		return graphics;
	}

	Polyline bounds = new Polyline(
			34.0, 13.0, 34.0, 6.0,
			34.0, 7.0, 30.0, 3.0,
			30.0, 3.0, 6.0, 3.0,
			6.0, 3.0, 2.0, 7.0,
			2.0, 8.0, 2.0, 14.0,
			2.0, 14.0, 7.0, 16.0,
			7.0, 16.0, 32.0, 15.0,
			32.0, 15.0, 34.0, 14.0
	);

	Car() {
		graphicsImg = new ImageView(new Image(RacingGame.class.getResourceAsStream("car1.png")));
		locationX = new SimpleDoubleProperty(0);
		locationY = new SimpleDoubleProperty(0);
		double w = 35;
		double h = 18;
		graphics = new Rectangle(w, h);
		graphics.setStroke(Color.BLACK);
		graphics.setRotationAxis(Rotate.Z_AXIS);
		graphics.xProperty().bind(locationX.add(w / 2));
		graphics.yProperty().bind(locationY.multiply(-1).add(600 - w / 2));
		graphicsImg.setRotationAxis(Rotate.Z_AXIS);
		graphicsImg.xProperty().bind(graphics.xProperty());
		graphicsImg.yProperty().bind(graphics.yProperty());
		graphicsImg.rotateProperty().bind(graphics.rotateProperty());
		bounds.translateXProperty().bind(graphics.xProperty());
		bounds.translateYProperty().bind(graphics.yProperty());
		bounds.rotateProperty().bind(graphics.rotateProperty());
		graphics.setFill(Color.MEDIUMPURPLE);
		graphics.setWidth(w);
		graphics.setHeight(h);

	}

	private void translateByVector(Vector v) {
		locationX.set(locationX.get() + v.getX());
		locationY.set(locationY.get() + v.getY());

	}

	void setLocationByVector(double x, double y) {
		locationX.set(x);
		locationY.set(y);
	}

	void setDirection(double angle) {
		graphics.setRotate(180 - angle);
		direction = angle;
	}

	double getDirection() {
		return this.direction;
	}

	void translateByRadius(double d) {
		Vector v = new Vector(d, direction / 180.0 * Math.PI, Vector.getPolar());
		translateByVector(v);
	}

	ImageView getGraphicsImg() {
		return graphicsImg;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isColliding() {
		return isColliding;
	}

	public void setColliding(boolean colliding) {
		isColliding = colliding;
	}
}

