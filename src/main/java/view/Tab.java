package view;

import javafx.scene.Parent;

import java.io.IOException;

public interface Tab {
    Parent show() throws IOException;
}
