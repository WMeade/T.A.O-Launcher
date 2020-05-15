package mainContent.staticmethods;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainContent.objects.GameLibrary;
import mainContent.objects.GameObject;
import mainContent.objects.LauncherSelector;
import mainContent.objects.TaoLauncherBar;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class backendMethods {
    static TreeMap<String, GameLibrary> userLibraries = new TreeMap<String, GameLibrary>();
    static Stage toolBar;
    static Pane toolBarPane;

    public static void getLibraries(Stage toolBarPassed, Pane toolBarPanePassed){
        toolBar = toolBarPassed;
        toolBarPane = toolBarPanePassed;
        String dir = new File("").getAbsolutePath();
        dir += "\\src\\mainContent\\saveFiles\\userData.xml";
        File directory = new File(dir);
        if(directory.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(directory);
                XMLDecoder decoder = new XMLDecoder(inputStream);
                HashMap<String, GameObject> games = (HashMap<String, GameObject>) decoder.readObject();
                while (games != null){
                    for(Map.Entry<String, GameObject> game: games.entrySet()){
                        if(!userLibraries.containsKey(game.getValue().getBelongsTo())){
                            userLibraries.put(game.getValue().getBelongsTo(), new GameLibrary(game.getValue().getBelongsTo()));
                        }else{
                            userLibraries.get(game.getValue().getBelongsTo()).addGame(game.getValue());
                        }
                    }
                    try{
                        games = (HashMap<String, GameObject>) decoder.readObject();
                    }catch (Exception Except){
                        break;
                    }
                }
                decoder.close();
                inputStream.close();
            } catch (IOException IoExcept) {
                IoExcept.printStackTrace();
            }
        }
        displayLibraries();
    }

    public static void displayLibraries(){
        try{
            toolBarPane.getChildren().removeIf(Button.class::isInstance);
            TaoLauncherBar toolbarBar = new TaoLauncherBar("T.A.O  Toolbar", toolBarPane, toolBar);
            String filePath = new File("").getAbsolutePath();
            filePath += "\\src\\mainContent\\objects\\images\\addlauncher.png";
            Button addLibraryButton = new Button();
            addLibraryButton.setLayoutX(userLibraries.size() * 80);
            addLibraryButton.setLayoutY(25);
            addLibraryButton.setId("addlauncherbutton");
            addLibraryButton.setGraphic(new ImageView(new Image(new FileInputStream(filePath))));
            toolBarPane.getChildren().add(addLibraryButton);
            addLibraryButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    LauncherSelector selector = new LauncherSelector();
                }
            });
            int iterator = 0;
            for(Map.Entry<String, GameLibrary> Library: userLibraries.entrySet()){
                Button launcherButton = new Button();
                launcherButton.setLayoutX(iterator * 80);
                launcherButton.setLayoutY(25);
                launcherButton.setId("addlauncherbutton");
                if(Library.getValue().getLibraryName().equals("steam")){
                    String path = new File("").getAbsolutePath();
                    path += "\\src\\mainContent\\objects\\images\\steamLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(path))));
                }else if(Library.getValue().getLibraryName().equals("uplay")){
                    String path = new File("").getAbsolutePath();
                    path += "\\src\\mainContent\\objects\\images\\uplayLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(path))));
                }
                launcherButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        viewLibrary(Library.getValue().getLibraryName());
                    }
                });
                toolBarPane.getChildren().add(launcherButton);
                iterator++;
            }
        }catch (IOException IoExcept){
            IoExcept.printStackTrace();
        }
    }

    private static void CreateGamesFromFolder(HashMap<String, String> gamesInFolder, GameLibrary library){
        int gameExeIterator = 0;
        Iterator gamesIterator = gamesInFolder.entrySet().iterator();
        while (gamesIterator.hasNext()) {
            HashMap.Entry gameFolder = (HashMap.Entry) gamesIterator.next();
            File currentGame = new File(gameFolder.getValue().toString());
            ArrayList<File> executablesInGameFolder = new ArrayList<File>();

            try{
                for (Path filepath: Files.walk(Paths.get(currentGame.toString())).filter(Files::isRegularFile).collect(Collectors.toList())) {
                    if(filepath.toString().endsWith(".exe")){
                        executablesInGameFolder.add(filepath.toFile());
                    }
                }
            }catch (IOException IoExept){
                IoExept.printStackTrace();
            }

            File sortedExecutable = null;
            try {
                long maximumSize = executablesInGameFolder.get(0).length();
                if (executablesInGameFolder.get(0) != null) { ;
                    for (File executable : executablesInGameFolder) {
                        if (executable != null) {
                            String[] gameName = executable.getName().split("[_. ]");
                            for (String exe : gameName) {
                                if (FuzzySearch.weightedRatio(currentGame.getName(), exe) >= 90) {
                                    sortedExecutable = executable;
                                    break;
                                }else if (FuzzySearch.weightedRatio(currentGame.getName(), exe) >= 70 && executable.length() > maximumSize){
                                    sortedExecutable = executable;
                                    maximumSize = executable.length();
                                }
                            }
                        }
                    }
                } else {
                    continue;
                }
                if (sortedExecutable != null) {
                    library.addGame(new GameObject(currentGame.getName(), sortedExecutable.getAbsolutePath(), library.getLibraryName(), false));
                }else{
                    for(File file: executablesInGameFolder){
                        if(file.length() >= maximumSize){
                            sortedExecutable = file;
                        }
                    }
                    library.addGame(new GameObject(currentGame.getName(), sortedExecutable.getAbsolutePath(), library.getLibraryName(), false));
                }
            }catch (Exception Except){
                System.out.println("No executable here");;
            }
        }
    }

    public static void createLibrary(String name, String path){
        if(!userLibraries.containsKey(name)){
            userLibraries.put(name, new GameLibrary(name));
        }
        HashMap<String, String> libraryGames = getGamesInFolder(path);
        CreateGamesFromFolder(libraryGames, userLibraries.get(name));
        saveLibraries();
    }

    private static HashMap<String, String> getGamesInFolder(String libPath){
        HashMap<String, String> gamesInFolder = new HashMap<String, String>();
        File library = new File(libPath);
        File[] gameFolders = library.listFiles();
        for(File game: gameFolders){
            gamesInFolder.put(game.getName(), game.getAbsolutePath());
        }
        return gamesInFolder;
    }

    public static void saveLibraries() {
        try {
            String dir = new File("").getAbsolutePath();
            dir += "\\src\\mainContent\\saveFiles\\userData.xml";
            FileOutputStream fos = new FileOutputStream(new File(dir));
            XMLEncoder encoder = new XMLEncoder(fos);
            for(Map.Entry<String, GameLibrary> Library: userLibraries.entrySet()){
                encoder.remove(Library.getValue().getLibrary());
                encoder.writeObject(Library.getValue().getLibrary());
            }
            encoder.close();
            fos.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public static void viewLibrary(String libraryName){
        positioningMethods.changeSizeOfWindowAnim(toolBar, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 ,800);
        toolBarPane.getChildren().remove(toolBarPane.lookup("#titlebar"));
        toolBarPane.getChildren().remove(toolBarPane.lookup("#exitbutton"));
        TaoLauncherBar titleBar = new TaoLauncherBar("T.A.O Launcher", toolBarPane, toolBar);

        Pane LauncherPane = new Pane();
        LauncherPane.setLayoutX(0);
        LauncherPane.setLayoutY(100);
        LauncherPane.setPrefHeight(700);
        LauncherPane.setPrefWidth(toolBarPane.getWidth());
        LauncherPane.setId("LauncherPane");
        toolBarPane.getChildren().add(LauncherPane);

        Pane grid = new Pane();
        ScrollPane gamesScroller = new ScrollPane(grid);
        gamesScroller.setLayoutX(0);
        gamesScroller.setLayoutY(0);
        gamesScroller.setId("lsScrollPane");
        gamesScroller.setPrefWidth(400);
        gamesScroller.setPrefHeight(700);
        gamesScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        gamesScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        LauncherPane.getChildren().add(gamesScroller);

        int buttonYpos = 0;
        for(Map.Entry<String, GameObject> game: userLibraries.get(libraryName).getLibrary().entrySet()){
            Button gameButton = new Button();
            gameButton.setText(game.getValue().getName());
            gameButton.setId("steamButtonls");
            gameButton.setLayoutX(0);
            gameButton.setLayoutY(buttonYpos);
            gameButton.setPrefHeight(50);
            gameButton.setPrefWidth(gamesScroller.getPrefWidth() - 20);
            grid.getChildren().add(gameButton);

            gameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Pane gamePage = new Pane();
                    gamePage.setLayoutX(gamesScroller.getPrefWidth());
                    gamePage.setLayoutY(0);
                    gamePage.setPrefWidth(LauncherPane.getPrefWidth() - 401);
                    gamePage.setId("gamePage");
                    gamePage.setPrefHeight(700);
                    LauncherPane.getChildren().add(gamePage);

                    Label gameTitle = new Label();
                    gameTitle.setLayoutX(0);
                    gameTitle.setLayoutY(0);
                    gameTitle.setPrefWidth(gamePage.getPrefWidth());
                    gameTitle.setPrefHeight(100);
                    gameTitle.setId("gameTitle");
                    gameTitle.setText(game.getValue().getName());
                    gamePage.getChildren().add(gameTitle);

                    try{
                        Button playGame = new Button();
                        playGame.setId("playButton");
                        playGame.setLayoutX(30);
                        playGame.setLayoutY(120);
                        playGame.setText("PLAY");
                        playGame.setPrefWidth(170);
                        playGame.setPrefHeight(50);
                        String path = new File("").getAbsolutePath();
                        path += "\\src\\mainContent\\objects\\images\\playbutton.png";
                        playGame.setGraphic(new ImageView(new Image(new FileInputStream(path))));
                        gamePage.getChildren().add(playGame);

                        playGame.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try{
                                   Runtime runtime = Runtime.getRuntime();
                                   Process gameProcess = runtime.exec(game.getValue().getExecutablePath());
                                }catch (IOException IoExcept){
                                    IoExcept.printStackTrace();
                                }
                            }
                        });
                    }catch (IOException IoExcept){
                        IoExcept.printStackTrace();
                    }
                }
            });
            buttonYpos += 50;
        }

    }
}

