package mainContent.objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TaoLauncherBar {
    private double xOffset = 0;
    private double yOffset = 0;
    private String text;
    private Stage currentStage;
    private Pane affectedPane;
    private Label currentTitle = new Label();
    private Button minimizeButton;
    private Button exitButton;

    public TaoLauncherBar(String text, Pane affectedPane, Stage currentStage){
        this.text = text;
        this.affectedPane = affectedPane;
        this.currentTitle.setText(text);
        this.currentTitle.setId("titlebar");
        this.currentTitle.setLayoutX(0);
        this.currentTitle.setLayoutY(0);
        this.currentTitle.setPrefWidth(this.affectedPane.getWidth());
        this.currentTitle.setPrefHeight(15);
        this.affectedPane.getChildren().add(this.currentTitle);
        this.exitButton = new Button();
        this.minimizeButton = new Button();

        this.exitButton.setLayoutY(-2);
        this.exitButton.setLayoutX(this.affectedPane.getWidth() - 30);
        this.affectedPane.getChildren().add(this.exitButton);
        this.exitButton.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/exitLogo.png"))));
        this.exitButton.setId("exitbutton");

        this.minimizeButton.setLayoutY(-2);
        this.minimizeButton.setLayoutX(this.affectedPane.getWidth() - 60);
        this.affectedPane.getChildren().add(this.minimizeButton);
        this.minimizeButton.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/minimize.png"))));
        this.minimizeButton.setId("exitbutton");

        this.exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentStage.close();
            }
        });

        this.minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentStage.setIconified(true);
            }
        });

        this.currentTitle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        this.currentTitle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentStage.setX(event.getScreenX() - xOffset);
                currentStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
