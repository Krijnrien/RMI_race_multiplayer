package Shared;

public class carVector implements IcarVector {
    private String name;
    private double locationX;
    private double locationY;
    private double direction = 0;

    public carVector(String _name, double _locationX, double _locationY, double _direction) {
        setName(_name);
        setLocationX(_locationX);
        setLocationY(_locationY);
        setDirection(_direction);
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getName() {
        return this.name;
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
