package Client;

import Client.level.Level_01;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionDetectorsTest {

    @Test
    public void polylineIntersectionCollidesTrue() {
        Car car = new Car();
        car.setLocationByVector(Level_01.startCar1[0], 600 - Level_01.startCar1[1]);

        Polyline linesUpper = new Polyline(Level_01.getUpperBounds());
        Polyline linesLower = new Polyline(Level_01.getLowerBounds());

        if (CollisionDetectors.PolylineIntersection(car.bounds, linesLower) || CollisionDetectors.PolylineIntersection(car.bounds, linesUpper)) {
            assertTrue("Car intersects with wall", true);
        } else {
            assertFalse("Car doesnt intersect with wall", false);
        }
    }

    @Test
    public void polylineIntersectionCollidesFalse() {
        Car car = new Car();
        car.setLocationByVector(Level_01.startCar1[0] - 35, 600 - Level_01.startCar1[1]);

        Polyline linesUpper = new Polyline(Level_01.getUpperBounds());
        Polyline linesLower = new Polyline(Level_01.getLowerBounds());

        if (CollisionDetectors.PolylineIntersection(car.bounds, linesLower) || CollisionDetectors.PolylineIntersection(car.bounds, linesUpper)) {
            assertFalse("Car doesnt intersect with wall", false);
        } else {
            assertTrue("Car intersects with wall", true);
        }
    }

    @Test
    public void polylineIntersectionAlmostCollides() {
        Car car = new Car();
        car.setLocationByVector(Level_01.startCar1[0] - 10, 600 - Level_01.startCar1[1]);

        Polyline linesUpper = new Polyline(Level_01.getUpperBounds());
        Polyline linesLower = new Polyline(Level_01.getLowerBounds());

        if (CollisionDetectors.PolylineIntersection(car.bounds, linesLower) || CollisionDetectors.PolylineIntersection(car.bounds, linesUpper)) {
            assertFalse("Car doesnt intersect with wall", false);
        } else {
            assertTrue("Car intersects with wall", true);
        }
    }
}