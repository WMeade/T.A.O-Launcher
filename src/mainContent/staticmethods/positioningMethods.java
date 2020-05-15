package mainContent.staticmethods;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.nio.file.Path;

public class positioningMethods {
    public static double setYbasedOnTaskBar() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);
        for (int i = 0; i <= 50; i++) {
            if (screenInsets.equals(new Insets(i, 0, 0, 0))) {
                return 40;
            } else if (screenInsets.equals(new Insets(0, 0, i, 0))) {
                return 0;
            }
        }
        return 40;
    }

    public static void changeSizeOfWindowAnim(Stage launcherStage, double x, double y){
        launcherStage.setHeight(y);
        launcherStage.setWidth(x);

    }
}

