package Client;

import Client.level.Level_01;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Shared.IupdateCar;
import Shared.carVector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import publisher.IRemotePropertyListener;
import publisher.IRemotePublisherForListener;

public class RacingGame extends Application implements IRemotePropertyListener {
    private Polyline linesUpper;
    private Polyline linesLower;
    private int laps = 2;
    static boolean server;
    static String participantName;
    double width = 800;
    double height = 600;
    private Car car1;
    private Car car2;
    private ArrayList<Line> checkPoints = new ArrayList<>();
    private Map<String, Car> participants = new HashMap<>();
    private int checks = 0;
    private boolean keyPressed = false;
    private KeyCode keyPressedCode = null;
    private Timeline gameLoop;
    private long time = 0;
    private IupdateCar updateCar;
    private Pane container;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UnicastRemoteObject.exportObject(this, 0);


        System.out.println(participantName);

        container = new Pane();
        Scene scene = new Scene(container, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadLevel(Level_01.lowerBounds, Level_01.upperBounds, container.getChildren());
        car1 = new Car();
        car1.setLocationByVector(Level_01.startCar1[0] - 35, height - Level_01.startCar1[1]);
        car1.setDirection(90);
        car1.getGraphics().setFill(Color.MEDIUMPURPLE);

        car2 = new Car();
        car2.setLocationByVector(Level_01.startCar1[0] - 35, height - Level_01.startCar1[1]);
        car2.setDirection(90);
        car2.getGraphics().setFill(Color.MEDIUMPURPLE);

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8087);
            IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup("carRegistry");
            updateCar = (IupdateCar) registry.lookup("carUpdateRegistry");
            publisher.subscribeRemoteListener(RacingGame.this, "car");
        } catch (Exception e) {
            System.out.println("Error setting remote listener" + e);
            e.printStackTrace();
        }


        container.getChildren().addAll(car1.getGraphicsImg(), car2.getGraphicsImg());
        container.setOnKeyPressed(e -> {
            keyPressed = true;
            keyPressedCode = e.getCode();
        });
        container.setOnKeyReleased(e -> keyPressed = false);

        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / 30), e -> {

            /*
             * Handling car controls inside Keyframe as it's smoother as it updates faster
             */
            if (keyPressed) {
                if (car1.speed != 0) {
                    if (keyPressedCode == KeyCode.LEFT) {
                        car1.setDirection(car1.direction += (3));
                    } else if (keyPressedCode == KeyCode.RIGHT) {
                        car1.setDirection(car1.direction -= (3));
                    }
                }
                if (keyPressedCode == KeyCode.UP) {
                    car1.speed += 0.15;
                } else if (keyPressedCode == KeyCode.DOWN) {
                    car1.speed -= 0.2;
                }
            }

            car1.translateByRadius(car1.speed);
            checkForCollisions(car1);

            //TODO Push x, y & direction to server
            try {
               // System.out.println("X: " + car1.locationX.doubleValue() + "Y: " + car1.locationY.doubleValue() + "D: " + car1.direction);
                updateCar.sendCar(new carVector(participantName, car1.locationX.doubleValue(), car1.locationY.doubleValue(), car1.direction));
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
        container.requestFocus();
        primaryStage.setOnCloseRequest(e -> System.exit(1));
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        //TODO Check if carId exists : if not, add new car to list
        carVector carvector = (carVector) propertyChangeEvent.getNewValue();
        String carName = carvector.getName();
        //System.out.println("property changed " + carName + carvector.getName());
        if (!participantName.equals(carName)) {
            if (!participants.containsKey(carName)) {
                System.out.println("Contains key" + carName);
                Car car = new Car();
                car.getGraphics().setFill(Color.MEDIUMPURPLE);
                participants.put(carName, new Car());
                // TODO Add graphics to container children


                car.setLocationByVector(carvector.getLocationX(), carvector.getLocationY());
                car.setDirection(carvector.getDirection());
            } else {
                //Car car = participants.get(carName);
               // System.out.println("Other car coords X:" + car.locationX.getValue());
                car2.setLocationByVector(carvector.getLocationX(), carvector.getLocationY());
                car2.setDirection(carvector.getDirection());
            }
            //TODO Get participant by getId in hashmap, then update its location
        }
    }

    private void loadLevel(double[] level_upper, double[] level_lower, ObservableList<Node> list) {
        linesUpper = new Polyline(level_upper);
        linesLower = new Polyline(level_lower);
        list.add(Level_01.levelGraphics);
        Line l;
    }

    private void checkForCollisions(Car car) {

        Path p3;
        for (int i = 0; i < checkPoints.size(); i++) {
            p3 = (Path) Shape.intersect(car.bounds, checkPoints.get(i));
            if (!p3.getElements().isEmpty()) {

                if (checks == i && checks == 0) {
                    if (laps == 0) {
                        time = System.currentTimeMillis() - time;
                        gameLoop.stop();

                    } else {
                        laps--;

                        checks++;
                        for (int j = 1; j < checkPoints.size(); j++) {
                            checkPoints.get(j).setStroke(Color.GREEN);
                        }
                        break;
                    }

                } else if (checks == i) {
                    checkPoints.get(i).setStroke(Color.RED);

                    checks++;
                    break;
                }
                if (checks == checkPoints.size()) {
                    checks = 0;
                }
            }
        }
        if (CollisionDetectors.PolylineIntersection(car.bounds, linesLower) || CollisionDetectors.PolylineIntersection(car.bounds, linesUpper)) {
            if (!car.isColliding) {
                car.speed *= -0.5;
            }

        } else {
            car.isColliding = false;
        }
    }

}

