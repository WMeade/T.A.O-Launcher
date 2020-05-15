package mainContent.objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class TaoDirectoryChooser {

    private TextField DirectoryDisplay = new TextField();
    public TaoDirectoryChooser(String launcher){
       Stage directoryChooser = new Stage();
       directoryChooser.initStyle(StageStyle.UNDECORATED);
       Pane directoryChooserPane = new Pane();

       directoryChooser.setX(200);
       directoryChooser.setY(mainContent.staticmethods.positioningMethods.setYbasedOnTaskBar() + 50);

       Scene directoryChooserScene = new Scene(directoryChooserPane, 350, 100);

       TaoLauncherBar currentLauncherBar = new TaoLauncherBar("Directory Chooser", directoryChooserPane, directoryChooser);

       this.DirectoryDisplay.setPrefWidth(300);
       this.DirectoryDisplay.setLayoutX(10);
       this.DirectoryDisplay.setLayoutY(25);
       this.DirectoryDisplay.setId("usernameentry");
       directoryChooserPane.getChildren().add(this.DirectoryDisplay);

       Button directoryChooserButton = new Button();
       directoryChooserButton.setLayoutX(310);
       directoryChooserButton.setLayoutY(23);
       directoryChooserButton.setPrefWidth(0);
       directoryChooserButton.setPrefHeight(0);
       directoryChooserButton.setId("dirchoosebut");
       directoryChooserButton.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/openDirectoryChooser.png"))));
       directoryChooserPane.getChildren().add(directoryChooserButton);

       directoryChooserButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
             DirectoryChooser fileSelector = new DirectoryChooser();
             File selectedDir = fileSelector.showDialog(directoryChooser);
             if(selectedDir == null){
                DirectoryDisplay.setText("No path selected!");
             }else{
                DirectoryDisplay.setText(selectedDir.getAbsolutePath());
             }
          }
       });

       Button submitButton = new Button();
       submitButton.setLayoutX(230);
       submitButton.setLayoutY(60);
       submitButton.setText("Submit");
       submitButton.setId("loginButton");
       submitButton.setPrefWidth(100);
       submitButton.setPrefHeight(20);
       directoryChooserPane.getChildren().add(submitButton);

       submitButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
             directoryChooser.close();
             mainContent.staticmethods.backendMethods.createLibrary(launcher, DirectoryDisplay.getText());
             mainContent.staticmethods.backendMethods.saveLibraries();
             mainContent.staticmethods.backendMethods.displayLibraries();
          }
       });

       directoryChooser.getIcons().add(new Image(this.getClass().getResource("images/wesLogo.png").toExternalForm()));
       directoryChooser.setTitle("Directory Chooser");
       directoryChooserScene.getStylesheets().addAll(this.getClass().getResource("styling/main.css").toExternalForm());
       directoryChooser.setScene(directoryChooserScene);
       directoryChooser.show();


   }

   public String getDir(){
        return this.DirectoryDisplay.getText();
   }
}
