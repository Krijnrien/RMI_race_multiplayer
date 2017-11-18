/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DialogDesignController implements Initializable {

    @FXML
    Button continuebtn;
    @FXML
    TextField participantName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        continuebtn.setOnAction(e -> {
            RacingGame.participantName = participantName.getText();
            RacingGame game = new RacingGame();
            try {
                game.start(OptionDialog.window);
                OptionDialog.window.centerOnScreen();
            } catch (Exception ignored) {
            }
        });
    }

}
