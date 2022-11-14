package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SoundTest {
    private Sound sound;

    @BeforeEach
    void setup() {
        sound = new Sound();
    }

    @Test
    @DisplayName("Running setFile for invalid index")
    void setFileInvalidIndex() {
        sound.setFile(-1);
        assertNotEquals(null, sound.clip);
    }

    @Test
    @DisplayName("Running setFile for valid index")
    void setFileValidIndex() {
        sound.setFile(1);
        assertNotEquals(null, sound.clip);
    }
}