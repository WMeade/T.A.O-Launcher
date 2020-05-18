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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TaoDirectoryChooser {

    private TextField DirectoryDisplay = new TextField();
    private TextField GameTitleDisplay = new TextField("Name of game");
    public TaoDirectoryChooser(String launcher, Stage toolBar){
       Stage directoryChooser = new Stage();
       directoryChooser.initStyle(StageStyle.UNDECORATED);
       Pane directoryChooserPane = new Pane();

       directoryChooser.setX(toolBar.getX() + 50);
       directoryChooser.setY(toolBar.getY() + 70);

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
       String dir = new File("").getAbsolutePath();
       dir += "\\images\\openDirectoryChooser.png";
       directoryChooserPane.getChildren().add(directoryChooserButton);

       if(launcher.equals("exe")||launcher.equals("addgame")){
          directoryChooserButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                FileChooser fileSelector = new FileChooser();
                File selectedDir = fileSelector.showOpenDialog(directoryChooser);
                if(selectedDir == null){
                   DirectoryDisplay.setText("No path selected!");
                }else{
                   DirectoryDisplay.setText(selectedDir.getAbsolutePath());
                }
             }
          });
       }else{
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
       }

       Button submitButton = new Button();
       submitButton.setLayoutX(230);
       submitButton.setLayoutY(60);
       submitButton.setText("Submit");
       submitButton.setId("loginButton");
       submitButton.setPrefWidth(100);
       submitButton.setPrefHeight(20);
       directoryChooserPane.getChildren().add(submitButton);

       if(launcher.equals("addgame")){
          directoryChooser.setHeight(130);
          this.GameTitleDisplay.setPrefWidth(300);
          this.GameTitleDisplay.setLayoutX(10);
          this.GameTitleDisplay.setLayoutY(25);
          this.GameTitleDisplay.setId("usernameentry");
          this.DirectoryDisplay.setLayoutY(60);
          directoryChooserButton.setLayoutY(58);
          submitButton.setLayoutY(95);
          directoryChooserPane.getChildren().add(this.GameTitleDisplay);
       }


       if(launcher.equals("exe")){
          submitButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                directoryChooser.close();
                mainContent.staticmethods.backendMethods.getGameForPass().setExecutablePath(DirectoryDisplay.getText());
                mainContent.staticmethods.backendMethods.saveLibraries();
                mainContent.staticmethods.backendMethods.displayLibraries();
             }
          });
       }else if(launcher.equals("addgame")){
          submitButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                directoryChooser.close();
                GameLibrary lib = mainContent.staticmethods.backendMethods.getLibraryForPass();
                lib.getLibrary().put(GameTitleDisplay.getText(), new GameObject(GameTitleDisplay.getText(), DirectoryDisplay.getText(), lib.getLibraryName(), false));
                mainContent.staticmethods.backendMethods.saveLibraries();
                mainContent.staticmethods.backendMethods.displayLibraries();
                mainContent.staticmethods.backendMethods.viewLibrary(lib.getLibraryName());
             }
          });
       } else{
          submitButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                directoryChooser.close();
                mainContent.staticmethods.backendMethods.createLibrary(launcher, DirectoryDisplay.getText());
                mainContent.staticmethods.backendMethods.saveLibraries();
                mainContent.staticmethods.backendMethods.displayLibraries();
                mainContent.staticmethods.backendMethods.viewLibrary(launcher);
             }
          });
       }

       String dirTwo = new File("").getAbsolutePath();
       dirTwo += "\\images\\wesLogo.png";
       directoryChooser.setTitle("Directory Chooser");
       directoryChooserScene.getStylesheets().addAll(this.getClass().getResource("styling/main.css").toExternalForm());
       directoryChooser.setScene(directoryChooserScene);

       try{
          directoryChooserButton.setGraphic(new ImageView(new Image(new FileInputStream(dir))));
          directoryChooser.getIcons().add(new Image(new FileInputStream(dirTwo)));
       }catch (IOException IoExcept){
          IoExcept.printStackTrace();
       }

       directoryChooser.show();
    }

   public String getDir(){
        return this.DirectoryDisplay.getText();
   }
}
