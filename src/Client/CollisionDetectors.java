package Client;

import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

class CollisionDetectors {

    static boolean PolylineIntersection(Polyline shape, Polyline world) {
        Path result = (Path) Shape.intersect(shape, world);
        return !result.getElements().isEmpty();
    }
}
