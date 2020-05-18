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
import java.io.FileInputStream;
import java.io.IOException;

public class LauncherSelector {
    private Pane launcherSelectorPane = new Pane();
    private Stage launcherSelectorStage = new Stage();

    public LauncherSelector(Stage toolBar){

        launcherSelectorStage.initStyle(StageStyle.UNDECORATED);
        launcherSelectorStage.setX(toolBar.getX() + 30);
        launcherSelectorStage.setY(toolBar.getY() + 20);

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
        String dir = new File("").getAbsolutePath();
        dir += "\\images\\steamLogo.png";
        grid.getChildren().add(steam);

        steam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser steamLibChoose = new TaoDirectoryChooser("steam", launcherSelectorStage);
            }
        });

        Button uplay = new Button();
        uplay.setId("steamButtonls");
        uplay.setText("Uplay");
        uplay.setPrefWidth(330);
        uplay.setPrefHeight(50);
        uplay.setLayoutX(0);
        uplay.setLayoutY(70);
        String dirTwo = new File("").getAbsolutePath();
        dirTwo += "\\images\\uplayLogo.png";
        grid.getChildren().add(uplay);

        uplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser uplayLibChoose = new TaoDirectoryChooser("uplay", launcherSelectorStage);
            }
        });

        Button blizzard = new Button();
        blizzard.setId("steamButtonls");
        blizzard.setText("Battle Net");
        blizzard.setPrefWidth(330);
        blizzard.setPrefHeight(50);
        blizzard.setLayoutX(0);
        blizzard.setLayoutY(140);
        String dirBlizz = new File("").getAbsolutePath();
        dirBlizz += "\\images\\blizzardLogo.png";
        grid.getChildren().add(blizzard);

        blizzard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser blizzardLibChoose = new TaoDirectoryChooser("blizzard", launcherSelectorStage);
            }
        });

        Button epicGames = new Button();
        epicGames.setId("steamButtonls");
        epicGames.setText("Epic Games");
        epicGames.setPrefWidth(330);
        epicGames.setPrefHeight(50);
        epicGames.setLayoutX(0);
        epicGames.setLayoutY(210);
        String dirEpic = new File("").getAbsolutePath();
        dirEpic += "\\images\\epicGamesLogo.png";
        grid.getChildren().add(epicGames);

        epicGames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser epicLibChoose = new TaoDirectoryChooser("epic", launcherSelectorStage);
            }
        });

        Button origin = new Button();
        origin.setId("steamButtonls");
        origin.setText("Origin");
        origin.setPrefWidth(330);
        origin.setPrefHeight(50);
        origin.setLayoutX(0);
        origin.setLayoutY(280);
        String dirorigin = new File("").getAbsolutePath();
        dirorigin += "\\images\\originLogo.png";
        grid.getChildren().add(origin);

        origin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser epicLibChoose = new TaoDirectoryChooser("origin", launcherSelectorStage);
            }
        });

        Button custom = new Button();
        custom.setId("steamButtonls");
        custom.setText("Custom Library");
        custom.setPrefWidth(330);
        custom.setPrefHeight(50);
        custom.setLayoutX(0);
        custom.setLayoutY(350);
        String dircustom = new File("").getAbsolutePath();
        dircustom += "\\images\\custom.png";
        grid.getChildren().add(custom);

        custom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser epicLibChoose = new TaoDirectoryChooser("Custom Library", launcherSelectorStage);
            }
        });



        TaoLauncherBar titleBar = new TaoLauncherBar("Launcher Selector", launcherSelectorPane, launcherSelectorStage);
        launcherSelectorStage.setScene(launcherSelectorScene);
        String dirThree = new File("").getAbsolutePath();
        dirThree += "\\images\\wesLogo.png";

        try{
            steam.setGraphic(new ImageView(new Image(new FileInputStream(dir))));
            uplay.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
            blizzard.setGraphic(new ImageView(new Image(new FileInputStream(dirBlizz))));
            epicGames.setGraphic(new ImageView(new Image(new FileInputStream(dirEpic))));
            origin.setGraphic(new ImageView(new Image(new FileInputStream(dirorigin))));
            custom.setGraphic(new ImageView(new Image(new FileInputStream(dircustom))));
            launcherSelectorStage.getIcons().add(new Image(new FileInputStream(dirThree)));
        }catch (IOException IoExcept){
            IoExcept.printStackTrace();
        }

        launcherSelectorStage.setTitle("Launcher Selector");
        launcherSelectorStage.show();


    }
}
