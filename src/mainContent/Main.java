package mainContent;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Main extends Application {



    @Override
    public void start(Stage toolBar) throws Exception {
        toolbar(toolBar);
    }

    public static void toolbar(Stage toolBar){
        Pane toolbarPane = new Pane();
        Scene toolbarScene = new Scene(toolbarPane, Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 100);
        toolbarScene.getStylesheets().addAll(Main.class.getResource("objects/styling/main.css").toExternalForm());
        toolBar.initStyle(StageStyle.UNDECORATED);
        toolBar.setTitle("T.A.O Toolbar");
        toolBar.setY(mainContent.staticmethods.positioningMethods.setYbasedOnTaskBar());
        toolBar.setX(0);
        toolBar.setScene(toolbarScene);
        String dir = new File("").getAbsolutePath();
        dir += "\\images\\wesLogo.png";
        try {
            toolBar.getIcons().add(new Image(new FileInputStream(dir)));
        }catch(IOException IoExcept){
            IoExcept.printStackTrace();
        }
        toolBar.show();
        mainContent.staticmethods.backendMethods.getLibraries(toolBar, toolbarPane);
    }

    public static void main(String[] args) {
        launch(args);
    }


}