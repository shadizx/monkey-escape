package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        window.addPanel(new Panel(true));
        assertEquals(count, Window.window.getComponentCount());
        assertTrue(Window.window.isVisible());
    }
}
