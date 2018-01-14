package Client;

import Client.level.Level_01;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import Shared.IupdateCar;
import Shared.Stats.IScoreHandler;
import Shared.carVector;
import StatsServer.ScoreObject;
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
import PositionServer.publisher.IRemotePropertyListener;
import PositionServer.publisher.IRemotePublisherForListener;

public class RacingGame extends Application implements IRemotePropertyListener {
    private Polyline linesUpper;
    private Polyline linesLower;
    static String participantName;
    private Car car1;
    private Car car2;
    private Map<String, Car> participants = new HashMap<>();
    public boolean keyPressed = false;
    public KeyCode keyPressedCode = null;
    private IupdateCar updateCar;
    private Registry registry;
    private IScoreHandler scoreHandler;
    private Line finishLine = new Line();
    public Pane container;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates two cars for controlling and sends RMI car object for positionServer identification.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        UnicastRemoteObject.exportObject(this, 0);

        System.out.println(participantName);

        container = new Pane();
        double width = 800;
        double height = 600;
        Scene scene = new Scene(container, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadLevel(Level_01.getLowerBounds(), Level_01.getUpperBounds(), container.getChildren());
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
        keyPressed();
        gameLoop();

        primaryStage.setOnCloseRequest(e -> System.exit(1));
    }

    /**
     * Sets the keycode pressed on the gameloop in class variable
     */
    public void keyPressed() {
        container.setOnKeyPressed(e -> {
            keyPressed = true;
            keyPressedCode = e.getCode();
        });
        container.setOnKeyReleased(e -> keyPressed = false);
    }


    /**
     * Loop of the game. No delta time is implementated thus runs as fast as possible giving others possible advantage.
     * Each iteration key press is detected and saved, current implementation only allows one keypress at a time.
     */
    public void gameLoop() {
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / 30), e -> {
            /*
             * Handling car controls inside Keyframe as it's smoother as it updates faster
             */
            if (keyPressed) {

                double tempDirection = car1.getDirection();
                double tempSpeed = car1.getSpeed();

                if (car1.getSpeed() != 0) {
                    if (keyPressedCode == KeyCode.LEFT) {
                        tempDirection += 3;
                    } else if (keyPressedCode == KeyCode.RIGHT) {
                        tempDirection -= 3;
                    }
                    car1.setDirection(tempDirection);
                }
                if (keyPressedCode == KeyCode.UP) {
                    tempSpeed += 0.15;
                } else if (keyPressedCode == KeyCode.DOWN) {
                    tempSpeed -= 0.2;
                }
                car1.setSpeed(tempSpeed);
            }

            car1.translateByRadius(car1.getSpeed());
            checkForCollisions(car1);

            //TODO Push x, y & direction to server
            try {
                updateCar.sendCar(new carVector(participantName, car1.locationX.doubleValue(), car1.locationY.doubleValue(), car1.getDirection()));
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
        container.requestFocus();
    }

    /**
     * RMI property change event.
     * Sets the car location received through RMI and recognized by the hashmap car IDs.
     * @param propertyChangeEvent
     * @throws RemoteException
     */
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

    /**
     * Called when a car crosses the finish.
     * Sends RMI object to stats server with the finish time and username.
     */
    private void finished() {
        try {
            this.registry = LocateRegistry.getRegistry("localhost", 8086);
            scoreHandler = (IScoreHandler) registry.lookup("finishRegistry");
            scoreHandler.addScore(new ScoreObject("username", System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loads level boundaries and texture.
     * @param level_upper upper double points for wall.
     * @param level_lower lower double points for wall.
     * @param list Javafx observable list to add graphics to.
     */
    private void loadLevel(double[] level_upper, double[] level_lower, ObservableList<Node> list) {
        linesUpper = new Polyline(level_upper);
        linesLower = new Polyline(level_lower);
        list.add(Level_01.levelGraphics);

        finishLine = new Line(95.0, 340.0, 6.0, 341.0);
        finishLine.setStroke(Color.RED);
        finishLine.setStrokeWidth(5);
        list.add(finishLine);
    }

    /**
     * Called every game loop iteration. Check for car and wall collission by using shape intersection method.
     * @param car
     */
    private void checkForCollisions(Car car) {
        if (!((Path) Shape.intersect(car.bounds, finishLine)).getElements().isEmpty()) {
            finished();
        }

        if (CollisionDetectors.PolylineIntersection(car.bounds, linesLower) || CollisionDetectors.PolylineIntersection(car.bounds, linesUpper)) {
            if (!car.isColliding()) {
                car1.setSpeed(car1.getSpeed() * 0.5);
            }
        } else {
            car.setColliding(false);
        }
    }
}

