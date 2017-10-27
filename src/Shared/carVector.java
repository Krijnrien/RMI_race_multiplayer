package Shared;

public class carVector implements IcarVector {
    private double locationX;
    private double locationY;
    private double direction = 0;

    public carVector(double _locationX, double _locationY, double _direction) {
        setLocationX(_locationX);
        setLocationY(_locationY);
        setDirection(_direction);
    }

    public void setLocationX(double _locationX) {
        this.locationX = _locationX;
    }

    public double getLocationX() {
        return this.locationX;
    }

    public void setLocationY(double _locationY) {
        this.locationY = _locationY;
    }

    public double getLocationY() {
        return this.locationY;
    }

    public void setDirection(double _direction) {
        this.direction = _direction;
    }

    public double getDirection() {
        return this.direction;
    }
}
