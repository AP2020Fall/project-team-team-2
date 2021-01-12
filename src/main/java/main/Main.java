package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.*;
import view.RiskGameView;
import view.StartGameView;
import view.ViewHandler;
import view.login.WelcomeMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends Application {
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* For Checking game GUI */
        Player newPlayer1 = new Player("new", "mew2");
        Player newPlayer2 = new Player("new2", "mew22");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(newPlayer1);
        players.add(newPlayer2);
        HashMap<String, Object> primitiveSettings = new HashMap<String, Object>();
        primitiveSettings.put("Map Number", 1);
        primitiveSettings.put("Placement", false);
        primitiveSettings.put("Alliance", false);
        primitiveSettings.put("Blizzards", false);
        primitiveSettings.put("Fog of War", false);
        primitiveSettings.put("Duration", 0);
        primitiveSettings.put("PlayersNum", 2);
        primitiveSettings.put("Players", players);
        /* For Checking game GUI */

        window = primaryStage;
        window.setOnCloseRequest(event -> Platform.exit());
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        ViewHandler viewHandler = ViewHandler.getViewHandler();

        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/soundtrack.mp3").toString());
        audioClip.play();

        System.out.println("1- Plato\n2- Risk");
        Scanner input = new Scanner(System.in);

        if (input.nextInt() == 1)
            viewHandler.push(new WelcomeMenu());
        else {
            viewHandler.push(new RiskGameView(primitiveSettings, 20, 1));
        }

        Main.window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void openFiles() {
        try {
            Account.open();
            Event.open();
            Suggestion.open();
            Game.open();
            FriendRequest.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void saveFiles() {
        try {
            Account.save();
            Event.save();
            Suggestion.save();
            Game.save();
            FriendRequest.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}

