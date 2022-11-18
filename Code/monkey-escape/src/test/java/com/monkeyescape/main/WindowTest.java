package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WindowTest {
    private Window window;

    @BeforeEach
    void setup() {
        window = new Window();
    }

    @Test
    @DisplayName("Add panel to window")
    void addPanel() {
        int count = Window.window.getComponentCount();
        window.addPanel(new Panel(new Game(false, false)));
        assertEquals(count, Window.window.getComponentCount());
    }
}