package mainContent;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;


public class Main extends Application {



    @Override
    public void start(Stage toolBar) throws Exception {
        toolbar(toolBar);
    }

    public void toolbar(Stage toolBar){
        Pane toolbarPane = new Pane();
        Scene toolbarScene = new Scene(toolbarPane, Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 100);
        toolbarScene.getStylesheets().addAll(this.getClass().getResource("objects/styling/main.css").toExternalForm());
        toolBar.initStyle(StageStyle.UNDECORATED);
        toolBar.setTitle("T.A.O Toolbar");
        toolBar.setY(mainContent.staticmethods.positioningMethods.setYbasedOnTaskBar());
        toolBar.setX(0);
        toolBar.setScene(toolbarScene);
        toolBar.getIcons().add(new Image(this.getClass().getResource("objects/images/wesLogo.png").toExternalForm()));
        toolBar.show();
        mainContent.staticmethods.backendMethods.getLibraries(toolBar, toolbarPane);
    }

    public static void main(String[] args) {
        launch(args);
    }


}