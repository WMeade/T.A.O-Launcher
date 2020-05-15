package mainContent.objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainContent.objects.TaoLauncherBar;
import mainContent.staticmethods.positioningMethods;

import java.io.File;

public class LauncherSelector {
    private Pane launcherSelectorPane = new Pane();
    private Stage launcherSelectorStage = new Stage();

    public LauncherSelector(){

        launcherSelectorStage.initStyle(StageStyle.UNDECORATED);
        launcherSelectorStage.setX(100);
        launcherSelectorStage.setY(positioningMethods.setYbasedOnTaskBar() + 20);

        Scene launcherSelectorScene = new Scene(launcherSelectorPane, 350, 300);
        launcherSelectorScene.getStylesheets().addAll(this.getClass().getResource("styling/main.css").toExternalForm());

        Pane grid = new Pane();
        ScrollPane launcherSelectorScrollPane = new ScrollPane(grid);
        launcherSelectorScrollPane.setLayoutX(1);
        launcherSelectorScrollPane.setLayoutY(35);
        launcherSelectorScrollPane.setId("lsScrollPane");
        launcherSelectorScrollPane.setPrefWidth(348);
        launcherSelectorScrollPane.setPrefHeight(250);
        launcherSelectorScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        launcherSelectorScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        launcherSelectorPane.getChildren().add(launcherSelectorScrollPane);

        Button steam = new Button();
        steam.setId("steamButtonls");
        steam.setText("Steam");
        steam.setPrefWidth(330);
        steam.setPrefHeight(50);
        steam.setLayoutX(0);
        steam.setLayoutY(0);
        steam.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/steamLogo.png"))));
        grid.getChildren().add(steam);

        steam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser steamLibChoose = new TaoDirectoryChooser("steam");
            }
        });

        Button uplay = new Button();
        uplay.setId("steamButtonls");
        uplay.setText("Uplay");
        uplay.setPrefWidth(330);
        uplay.setPrefHeight(50);
        uplay.setLayoutX(0);
        uplay.setLayoutY(70);
        uplay.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/uplayLogo.png"))));
        grid.getChildren().add(uplay);

        uplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser uplayLibChoose = new TaoDirectoryChooser("uplay");
            }
        });

        TaoLauncherBar titleBar = new TaoLauncherBar("Launcher Selector", launcherSelectorPane, launcherSelectorStage);
        launcherSelectorStage.setScene(launcherSelectorScene);
        launcherSelectorStage.getIcons().add(new Image(this.getClass().getResource("images/wesLogo.png").toExternalForm()));
        launcherSelectorStage.setTitle("Launcher Selector");
        launcherSelectorStage.show();


    }
}
