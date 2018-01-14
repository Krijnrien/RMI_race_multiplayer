package Client;

import javafx.scene.input.KeyCode;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class RacingGameTest {

    /**
     * Testing if keycode typed is same keycode. Cannot actually be properly tested since its user hardware interaction which cannot be imitated through Junit4 testing software directly.
     */
    @Test
    private void keyPressedTest() {
        RacingGame racingGame = new RacingGame();
        racingGame.keyPressed();

        assertEquals(KeyCode.E, racingGame.keyPressedCode);
    }

    /**
     * Check if car has collided with race track wall.
     */
    @Test
    private void carCollissionWallTrue(){

    }
}