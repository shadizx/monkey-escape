package com.monkeyescape.painter;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PainterTest {
    private Painter testPainter;
    private final Panel testPanel = new Panel(true);
    private final KeyHandler testKeyHandler = new KeyHandler();
    private final Game testGame = new Game(testKeyHandler);
    @BeforeEach
    void setup(){
        testPainter = new Painter(testGame, testPanel);
    }

    @Test
    @DisplayName("Running getImageExitMenuTest")
    void getImageExitMenuTest(){
        BufferedImage image = testPainter.getImage("exit");
        assertNotNull(image);
    }
    @Test
    @DisplayName("Running getImageGameOverMenuTest")
    void getImageGameOverMenuTest(){
        BufferedImage image = testPainter.getImage("gameover");
        assertNotNull(image);
    }
    @Test
    @DisplayName("Running getImageInstructionsMenuTest")
    void getImageInstructionsMenuTest(){
        BufferedImage image = testPainter.getImage("instructions");
        assertNotNull(image);
    }
    @Test
    @DisplayName("Running getImagePauseMenuTest")
    void getImagePauseMenuTest(){
        BufferedImage image = testPainter.getImage("pause");
        assertNotNull(image);
    }
    @Test
    @DisplayName("Running getImageStartMenuTest")
    void getImageStartMenuTest(){
        BufferedImage image = testPainter.getImage("start");
        assertNotNull(image);
    }

    @Test
    @DisplayName("Running getImageInvalidMenuTest")
    void getImageInvalidMenuTest(){
        BufferedImage image = testPainter.getImage("notrealmenu");
        assertNull(image);
    }
}
