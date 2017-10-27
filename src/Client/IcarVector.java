package Client;

import java.io.Serializable;

public interface IcarVector extends Serializable {
    void setLocationX(double _locationX);
    double getLocationX();

    void setLocationY(double _locationY);
    double getLocationY();

    void setDirection(double _direction);
    double getDirection();
}
