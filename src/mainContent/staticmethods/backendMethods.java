package mainContent.staticmethods;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainContent.objects.*;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class backendMethods {
    private static TreeMap<String, GameLibrary> userLibraries = new TreeMap<String, GameLibrary>();
    private static Stage toolBar;
    private static Pane toolBarPane;
    private static GameObject gameForPass;
    private static GameLibrary libraryForPass;

    public static void getLibraries(Stage toolBarPassed, Pane toolBarPanePassed){
        toolBar = toolBarPassed;
        toolBarPane = toolBarPanePassed;
        String dir = new File("").getAbsolutePath();
        dir += "\\saveFiles\\userData.xml";
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
                            userLibraries.get(game.getValue().getBelongsTo()).addGame(game.getValue());
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
            TaoLauncherBar toolbarBar = new TaoLauncherBar("T.A.O", toolBarPane, toolBar);
            Button addLibraryButton = new Button();
            addLibraryButton.setLayoutX(userLibraries.size() * 80);
            addLibraryButton.setLayoutY(25);
            addLibraryButton.setId("addlauncherbutton");
            String dir = new File("").getAbsolutePath();
            dir += "\\images\\addlauncher.png";
            addLibraryButton.setGraphic(new ImageView(new Image(new FileInputStream(dir))));
            toolBarPane.getChildren().add(addLibraryButton);
            addLibraryButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    LauncherSelector selector = new LauncherSelector(toolBar);
                }
            });
            int iterator = 0;
            for(Map.Entry<String, GameLibrary> Library: userLibraries.entrySet()){
                Button launcherButton = new Button();
                launcherButton.setLayoutX(iterator * 80);
                launcherButton.setLayoutY(25);
                launcherButton.setId("addlauncherbutton");
                if(Library.getValue().getLibraryName().equals("steam")){
                    String dirTwo = new File("").getAbsolutePath();
                    dirTwo += "\\images\\steamLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
                }else if(Library.getValue().getLibraryName().equals("uplay")){
                    String dirTwo = new File("").getAbsolutePath();
                    dirTwo += "\\images\\uplayLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
                }else if(Library.getValue().getLibraryName().equals("blizzard")){
                    String dirTwo = new File("").getAbsolutePath();
                    dirTwo += "\\images\\blizzardLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
                }else if(Library.getValue().getLibraryName().equals("epic")){
                    String dirTwo = new File("").getAbsolutePath();
                    dirTwo += "\\images\\epicGamesLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
                }else if(Library.getValue().getLibraryName().equals("origin")){
                    String dirTwo = new File("").getAbsolutePath();
                    dirTwo += "\\images\\originLogo.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
                }else if(Library.getValue().getLibraryName().equals("Custom Library")){
                    String dirTwo = new File("").getAbsolutePath();
                    dirTwo += "\\images\\custom.png";
                    launcherButton.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
                }
                launcherButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        viewLibrary(Library.getValue().getLibraryName());
                    }
                });
                ContextMenu toolBarButtonContextMenu = new ContextMenu();
                for(Map.Entry<String, GameObject> game: Library.getValue().getLibrary().entrySet()) {
                    if (game.getValue().getOnToolbar()) {
                        MenuItem gameOnToolBar = new MenuItem();
                        Label menuItemLabel = new Label("Launch " + game.getValue().getName());
                        menuItemLabel.setPrefWidth(120);
                        menuItemLabel.setPrefHeight(10);
                        menuItemLabel.setWrapText(true);
                        gameOnToolBar.setGraphic(menuItemLabel);
                        toolBarButtonContextMenu.getItems().add(gameOnToolBar);
                        gameOnToolBar.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    Runtime runtime = Runtime.getRuntime();
                                    Process gameProcess = runtime.exec(game.getValue().getExecutablePath());
                                } catch (IOException IoExcept) {
                                    IoExcept.printStackTrace();
                                }
                            }
                        });
                    }
                }
                MenuItem removeLibrary = new MenuItem();
                Label menuItemLabel = new Label("Remove Library");
                menuItemLabel.setPrefWidth(120);
                menuItemLabel.setPrefHeight(10);
                menuItemLabel.setWrapText(true);
                removeLibrary.setGraphic(menuItemLabel);
                toolBarButtonContextMenu.getItems().add(removeLibrary);
                removeLibrary.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        userLibraries.remove(Library.getKey());
                        saveLibraries();
                        displayLibraries();
                        returnToBar();
                    }
                });

                launcherButton.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        toolBarButtonContextMenu.show(launcherButton, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
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
            }catch (Exception IoExept){
                System.out.println("Ran into hidden folder/Or corrupt folder, Continuing");;
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
            dir += "\\saveFiles\\userData.xml";
            FileOutputStream fos = new FileOutputStream(new File(dir));
            XMLEncoder encoder = new XMLEncoder(fos);
            for(Map.Entry<String, GameLibrary> Library: userLibraries.entrySet()){
                encoder.remove(Library.getValue().getLibrary());
            }
            for(Map.Entry<String, GameLibrary> Library: userLibraries.entrySet()){
                encoder.writeObject(Library.getValue().getLibrary());
            }
            encoder.close();
            fos.close();
            if(userLibraries.size() < 1){
                File saveData = new File(dir);
                saveData.delete();
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public static void viewLibrary(String libraryName){
        positioningMethods.changeSizeOfWindowAnim(toolBar, Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 ,800);
        toolBarPane.getChildren().remove(toolBarPane.lookup("#titlebar"));
        toolBarPane.getChildren().remove(toolBarPane.lookup("#exitbutton"));
        TaoLauncherBar titleBar = new TaoLauncherBar("T.A.O", toolBarPane, toolBar);

        Button backToToolBar = new Button();
        backToToolBar.setLayoutX(toolBar.getWidth() - 37);
        backToToolBar.setLayoutY(70);
        backToToolBar.setId("addlauncherbutton");
        String dirOne = new File("").getAbsolutePath();
        dirOne += "\\images\\toolbaricontwo.png";
        try{
            backToToolBar.setGraphic(new ImageView(new Image(new FileInputStream(dirOne))));
        }catch (IOException IoExcept){
            IoExcept.printStackTrace();
        }
        backToToolBar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                returnToBar();
            }
        });
        toolBarPane.getChildren().add(backToToolBar);

        Pane LauncherPane = new Pane();
        LauncherPane.setLayoutX(0);
        LauncherPane.setLayoutY(100);
        LauncherPane.setPrefHeight(700);
        LauncherPane.setPrefWidth(toolBarPane.getWidth());
        LauncherPane.setId("LauncherPane");
        toolBarPane.getChildren().add(LauncherPane);

        Label launcherTitle = new Label();
        launcherTitle.setLayoutX(400);
        launcherTitle.setLayoutY(0);
        launcherTitle.setPrefWidth(LauncherPane.getPrefWidth() - 400);
        launcherTitle.setPrefHeight(100);
        launcherTitle.setId("gameTitle");
        launcherTitle.setText(libraryName.substring(0, 1).toUpperCase() + libraryName.substring(1).toLowerCase());
        LauncherPane.getChildren().add(launcherTitle);

        Button addGame = new Button("Add game");
        addGame.setId("steamButtonls");
        addGame.setLayoutX(430);
        addGame.setLayoutY(120);
        addGame.setPrefHeight(50);
        addGame.setPrefWidth(200);
        LauncherPane.getChildren().add(addGame);
        addGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                libraryForPass = userLibraries.get(libraryName);
                TaoDirectoryChooser gameAdder = new TaoDirectoryChooser("addgame", toolBar);
            }
        });
        String dirTwo = new File("").getAbsolutePath();
        dirTwo += "\\images\\addIcon.png";
        try{
            addGame.setGraphic(new ImageView(new Image(new FileInputStream(dirTwo))));
        }catch (IOException IoExcept){
            IoExcept.printStackTrace();
        }

        Button addLibrary = new Button("Add another " + libraryName + " library");
        addLibrary.setId("steamButtonls");
        addLibrary.setLayoutX(650);
        addLibrary.setLayoutY(120);
        addLibrary.setPrefHeight(50);
        addLibrary.setPrefWidth(350);
        LauncherPane.getChildren().add(addLibrary);
        addLibrary.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TaoDirectoryChooser libraryAdder = new TaoDirectoryChooser(libraryName, toolBar);
            }
        });
        String dirThree = new File("").getAbsolutePath();
        dirThree += "\\images\\addIcon.png";
        try{
            addLibrary.setGraphic(new ImageView(new Image(new FileInputStream(dirThree))));
        }catch (IOException IoExcept){
            IoExcept.printStackTrace();
        }

        Button deleteLibrary = new Button("Delete library");
        deleteLibrary.setId("steamButtonls");
        deleteLibrary.setLayoutX(1020);
        deleteLibrary.setLayoutY(120);
        deleteLibrary.setPrefHeight(50);
        deleteLibrary.setPrefWidth(250);
        LauncherPane.getChildren().add(deleteLibrary);
        deleteLibrary.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                userLibraries.remove(libraryName);
                saveLibraries();
                displayLibraries();
                returnToBar();
            }
        });
        String dirFour = new File("").getAbsolutePath();
        dirFour += "\\images\\addIcon.png";
        try{
            deleteLibrary.setGraphic(new ImageView(new Image(new FileInputStream(dirFour))));
        }catch (IOException IoExcept){
            IoExcept.printStackTrace();
        }


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
            if (game.getValue().getOnToolbar()){
                String dir = new File("").getAbsolutePath();
                dir += "\\images\\toolbaricontwo.png";
                try{
                    gameButton.setGraphic(new ImageView(new Image(new FileInputStream(dir))));
                }catch (IOException IoExcept){
                    IoExcept.printStackTrace();
                }
            }

            ContextMenu gameButtonRightClickMenu = new ContextMenu();
            gameButtonRightClickMenu.setPrefWidth(20);
            gameButtonRightClickMenu.setPrefHeight(20);


            if(!game.getValue().getOnToolbar()){
                Label menuItemLabel = new Label("Add to tool bar");
                menuItemLabel.setWrapText(true);
                menuItemLabel.setId("gameButtonContext");
                MenuItem addToToolbar = new MenuItem();
                addToToolbar.setGraphic(menuItemLabel);
                addToToolbar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        game.getValue().setOnToolbar(true);
                        saveLibraries();
                        displayLibraries();
                        viewLibrary(libraryName);
                    }
                });
                gameButtonRightClickMenu.getItems().add(addToToolbar);
            }else{
                Label menuItemLabel = new Label("Remove from toolbar");
                menuItemLabel.setWrapText(true);
                menuItemLabel.setId("gameButtonContext");
                MenuItem removeFromToolBar = new MenuItem();
                removeFromToolBar.setGraphic(menuItemLabel);
                removeFromToolBar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        game.getValue().setOnToolbar(false);
                        saveLibraries();
                        displayLibraries();
                        viewLibrary(libraryName);
                    }
                });
                gameButtonRightClickMenu.getItems().add(removeFromToolBar);
            }

            Label menuItemLabel = new Label("Manually set game executable");
            menuItemLabel.setWrapText(true);
            menuItemLabel.setId("gameButtonContext");
            MenuItem manuallySetExe = new MenuItem();
            manuallySetExe.setGraphic(menuItemLabel);
            manuallySetExe.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    gameForPass = game.getValue();
                    TaoDirectoryChooser chooseExe = new TaoDirectoryChooser("exe", toolBar);
                }
            });
            gameButtonRightClickMenu.getItems().add(manuallySetExe);

            gameButton.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent contextMenuEvent) {
                    gameButtonRightClickMenu.show(gameButton, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                }
            });

            Label menuItemLabelTwo = new Label("Remove from library");
            menuItemLabelTwo.setWrapText(true);
            menuItemLabelTwo.setId("gameButtonContext");
            MenuItem removeGameLibrary = new MenuItem();
            removeGameLibrary.setGraphic(menuItemLabelTwo);
            removeGameLibrary.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    userLibraries.get(libraryName).getLibrary().remove(game.getKey());
                    saveLibraries();
                    displayLibraries();
                    viewLibrary(libraryName);
                }
            });
            gameButtonRightClickMenu.getItems().add(removeGameLibrary);

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
                        playGame.setId("steamButtonls");
                        playGame.setLayoutX(30);
                        playGame.setLayoutY(120);
                        playGame.setText("Play game");
                        playGame.setPrefWidth(200);
                        playGame.setPrefHeight(50);
                        String path = new File("").getAbsolutePath();
                        path += "\\images\\playbutton.png";
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

    public static void returnToBar(){
        toolBar.close();
        mainContent.Main.toolbar(new Stage());
    }

    public static GameObject getGameForPass() {
        return gameForPass;
    }

    public static GameLibrary getLibraryForPass() {
        return libraryForPass;
    }
}

