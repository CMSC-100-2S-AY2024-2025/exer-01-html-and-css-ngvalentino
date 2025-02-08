package ui;

import game.GameStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // Displays the main menu
    public void showMenuScene() {
        // Create the root container (Pane) to hold the background and the menu content
        Pane layout = new Pane();

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/menu.jpg"));
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1920);
        background.setFitHeight(1080);

        // Add the background image to the Pane
        layout.getChildren().add(background);

        // Create the title text
        Text title = new Text("1v1 Shooting Game");
        title.setFill(javafx.scene.paint.Color.WHITE);
        title.setStyle("-fx-font-size: 48; -fx-font-weight: bold; -fx-font-family: 'Colonna MT';");
        title.setTranslateY(205);
        title.setTranslateX(800);

        Image borderImage = new Image(getClass().getResourceAsStream("/images/title.jpg"));
        ImageView border = new ImageView(borderImage);
        border.setTranslateY(120);
        border.setTranslateX(700);
        border.setFitHeight(150);
        layout.getChildren().add(border);

        // Create the buttons
        Button startGameButton = createStyledButton("Start Game");
        Button aboutButton = createStyledButton("About");
        Button creditsButton = createStyledButton("Credits");
        Button exitButton = createStyledButton("Exit");

        // Set button actions
        startGameButton.setOnAction(e -> showGameSceneWithCountdown());
        aboutButton.setOnAction(e -> showAboutScene());
        creditsButton.setOnAction(e -> showCreditsScene());
        exitButton.setOnAction(e -> stage.close());

        // Create a VBox to hold the buttons and set their position
        VBox buttonLayout = new VBox(50);  // 50px space between buttons
        buttonLayout.setStyle("-fx-alignment: center;");
        buttonLayout.setTranslateY(350);
        buttonLayout.setTranslateX(800);

        buttonLayout.getChildren().addAll(startGameButton, aboutButton, creditsButton, exitButton);

        // Add the VBox (buttons and title) to the Pane (above the background)
        layout.getChildren().addAll(title, buttonLayout);

        // Create the scene and set it to the stage
        Scene menuScene = new Scene(layout, 1920, 1080);
        stage.setScene(menuScene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    // Create a button with a custom style
    private Button createStyledButton(String text) {
        Button button = new Button();

        button.setStyle("-fx-background-color: black; -fx-border-color: black;");

        // Set a background image for the button using ImageView
        Image buttonImage = new Image(getClass().getResourceAsStream("/images/button.jpg"));
        ImageView buttonBackground = new ImageView(buttonImage);
        buttonBackground.setFitWidth(350);  // Adjust size as needed
        buttonBackground.setFitHeight(100); // Adjust size as needed

        // Create a StackPane to layer the image and text inside the button
        StackPane buttonContent = new StackPane();

        // Add the image to the StackPane
        buttonContent.getChildren().add(buttonBackground);

        // Create the text and center it inside the button
        Text buttonText = new Text(text);
        buttonText.setFill(javafx.scene.paint.Color.WHITE);
        buttonText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-family: 'Colonna MT';");

        // Add the text on top of the image in the StackPane
        buttonContent.getChildren().add(buttonText);

        // Set the StackPane as the button's content
        button.setGraphic(buttonContent);

        return button;
    }

    // Displays the game proper
    public void showGameScene() {
        GameStage gameStage = new GameStage(stage, this);
        gameStage.initialize();
    }

    // Displays the About screen
    public void showAboutScene() {
        // Create the root container (Pane) to hold the background and the menu content
        Pane layout = new Pane();

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/menu.jpg"));
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1920);
        background.setFitHeight(1080);

        // Add the background image to the Pane
        layout.getChildren().add(background);

        Text titleText = new Text("GAME INSTRUCTIONS");
        titleText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family: 'Baskerville Old Face';");
        titleText.setTranslateX(1300);
        titleText.setTranslateY(100);

        // Create the initial text (Game Instructions)
        Text aboutText = new Text(
            "                                      Welcome to the game! Here’s how you can play:\n\n" +
            "                    Player 1:\n" +
            "Movement:\n" +
            "     Up: Use the W key to move up.\n" +
            "     Down: Use the S key to move down.\n" +
            "     Left: Use the A key to move left.\n" +
            "     Right: Use the D key to move right.\n" +
            "     Special Actions:\n" +
            "     Dash Mode: Hold Shift to dash and move faster.\n" +
            "     Focus Mode: Hold Ctrl to focus and move slower.\n" +
            "     Special Attack (Fire Ring): Press E to activate a fire ring around Player 2 (Cool down: 5 seconds).\n\n" +
            "                    Player 2:\n" +
            "Movement:\n" +
            "     Up: Use the Up Arrow key to move up.\n" +
            "     Down: Use the Down Arrow key to move down.\n" +
            "     Left: Use the Left Arrow key to move left.\n" +
            "     Right: Use the Right Arrow key to move right.\n" +
            "     Special Actions:\n" +
            "     Dash Mode: Hold Shift to dash and move faster.\n" +
            "     Focus Mode: Hold Ctrl to focus and move slower.\n" +
            "     Special Attack (Ice Rain): Press L to activate ice rain around Player 1 (Cool down: 5 seconds).\n\n"
        );
        aboutText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family: 'Baskerville Old Face';");
        aboutText.setTranslateY(300);
        aboutText.setTranslateX(500);

        // Create the instruction image
        Image instruction = new Image(getClass().getResourceAsStream("/images/instruction.png"));
        ImageView instruct = new ImageView(instruction);
        instruct.setTranslateY(30);
        instruct.setTranslateX(220);
        instruct.setFitHeight(1100);
        instruct.setFitWidth(1500);

        // Create a StackPane to layer the text and the instruction image together
        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(instruct);
        layout.getChildren().add(titleText);
        stackPane.getChildren().add(aboutText);
        StackPane.setAlignment(aboutText, javafx.geometry.Pos.TOP_LEFT);
        layout.getChildren().add(stackPane);

        // Create the back button
        Button backButton = createStyledButton("Back to Menu");
        backButton.setOnAction(e -> showMenuScene());

        // Position the back button at the top left of the screen
        backButton.setTranslateX(50);
        backButton.setTranslateY(50);

        // Add the back button to the layout
        layout.getChildren().add(backButton);

        // Create the "More Information" button
        Button moreInfoButton = createStyledButton("More Information");
        moreInfoButton.setTranslateX(1500);
        moreInfoButton.setTranslateY(900);

        // Add action for "More Information" button
        moreInfoButton.setOnAction(e -> showMoreInformationScene());

        layout.getChildren().add(moreInfoButton);

        // Create the scene with the layout and set it on the stage
        Scene aboutScene = new Scene(layout, 1920, 1080);
        stage.setScene(aboutScene);
        stage.setTitle("About");
    }

    // Displays the More Information screen
    public void showMoreInformationScene() {
        // Create the root container (Pane) for the new scene
        Pane layout = new Pane();

        // Load the background image for the new scene
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/menu.jpg"));
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1920);
        background.setFitHeight(1080);

        // Add the background image to the Pane
        layout.getChildren().add(background);

        // Create the text for more information (Gameplay Features)
        Text moreInfoText = new Text(
            "\t\t\t\t\t\t\t\tGameplay Features:\n\n" +
            "Shooting:\n" +
            "     Both players can shoot bullets by pressing Spacebar and Enter\n" +
            "     Player 1 shoots a pink bullet, while Player 2 shoots a blue bullet.\n\n" +
            "Health Bars:\n" +
            "     Each player starts with 1000 health points.\n" +
            "     When a player is hit by a bullet, they lose 5 health points.\n" +
            "     Special attacks reduce health by 10 points.\n" +
            "     Health bars will show up at the top of the screen and change color \n" +
            "     based on remaining health (Green: Healthy, Red: Critical).\n\n" +
            "Time Limit:\n" +
            "     The game is set to a 60-second timer.\n" +
            "     Keep an eye on the timer at the top of the screen to see how much time remains.\n\n" +
            "Tips:\n" +
            "     Use the Dash Mode for quick escapes and to avoid bullets.\n" +
            "     Focus Mode can help you move more precisely to aim at the opponent.\n" +
            "     Plan your special attacks wisely, as they have a cooldown time of 5 seconds.\n\n" +
            "\t\t\t\t\t\t\t\t   Good Luck!\n" +
            "\t\t\t\tKeep playing to defeat your opponent and claim victory!"
        );
        moreInfoText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family: 'Baskerville Old Face';");
        moreInfoText.setTranslateY(300);
        moreInfoText.setTranslateX(500);

        // Create the instruction image
        Image instruction = new Image(getClass().getResourceAsStream("/images/instruction.png"));
        ImageView instruct = new ImageView(instruction);
        instruct.setTranslateY(30);
        instruct.setTranslateX(220);
        instruct.setFitHeight(1100);
        instruct.setFitWidth(1500);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(instruct);
        stackPane.getChildren().add(moreInfoText);
        StackPane.setAlignment(moreInfoText, javafx.geometry.Pos.TOP_LEFT);
        layout.getChildren().add(stackPane);

        // Create the back button
        Button backButton = createStyledButton("Back to Menu");
        backButton.setOnAction(e -> showMenuScene());

        // Position the back button at the top left of the screen
        backButton.setTranslateX(50);
        backButton.setTranslateY(50);

        // Add the back button to the layout
        layout.getChildren().add(backButton);

        // Create the scene for more information and set it on the stage
        Scene moreInfoScene = new Scene(layout, 1920, 1080);
        stage.setScene(moreInfoScene);
        stage.setTitle("More Information");
    }



    // Displays the Credits screen
    public void showCreditsScene() {
    	// Create the root container (Pane) to hold the background and the menu content
        Pane layout = new Pane();

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/menu.jpg"));
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1920);
        background.setFitHeight(1080);

        // Add the background image to the Pane
        layout.getChildren().add(background);

        Text creditsText = new Text("\t\t\tDeveloped by:\n\n" +
        		"   Nicole Phoebe G. Valentino\n" +
        		"         BS Computer Science\n\n" +
                "Currently hanging by the thread :)\n");
        creditsText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family: 'Baskerville Old Face';");
        creditsText.setTranslateY(500);
        creditsText.setTranslateX(800);

        Image credits = new Image(getClass().getResourceAsStream("/images/instruction.png"));
        ImageView cred = new ImageView(credits);
        cred.setTranslateY(120);
        cred.setTranslateX(400);
        cred.setFitHeight(900);
        cred.setFitWidth(1200);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(cred);
        stackPane.getChildren().add(creditsText);
        layout.getChildren().add(stackPane);

        // Create the back button
        Button backButton = createStyledButton("Back to Menu");
        backButton.setOnAction(e -> showMenuScene());

        // Add the back button to the layout
        layout.getChildren().addAll(creditsText, backButton);

        // Create the scene for credits and set it on the stage
        Scene creditsScene = new Scene(layout, 1920, 1080);
        stage.setScene(creditsScene);
        stage.setTitle("Credits");
    }

    // Displays the Winning Scene
    public void showWinningScene(String winner) {
        Pane layout = new Pane();

        // Load the same background image used in the game scene
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1920);  // Adjust to screen width
        background.setFitHeight(1080); // Adjust to screen height
        background.setOpacity(0.5);    // Reduce opacity (50%)

        // Add the background image to the Pane
        layout.getChildren().add(background);

        // Display "Game Over" message
        Text gameOverText = new Text("GAME OVER!!");
        gameOverText.setStyle("-fx-font-size: 256; -fx-font-weight: bold; -fx-fill: Red; -fx-font-family: 'Chiller';");
        gameOverText.setTranslateY(500);
        gameOverText.setTranslateX(400);

        // Create the text showing the winner
        Text winningText = new Text(winner + " Wins!");
        winningText.setStyle("-fx-font-size: 100px; -fx-font-weight: bold; -fx-fill: Red; -fx-font-family: 'Chiller';");
        winningText.setTranslateY(600);
        winningText.setTranslateX(750);

        Button backButton = createStyledButton("Back to Menu");
        backButton.setOnAction(e -> showMenuScene());

        layout.getChildren().addAll(gameOverText, winningText, backButton);

        // Set the scene for the winning screen
        Scene winningScene = new Scene(layout, 1920, 1080);
        stage.setScene(winningScene);
        stage.setTitle("Winner");
    }

    // Display countdown after clicking the button 'start game'
    public void showGameSceneWithCountdown() {
        Pane layout = new Pane();

        // Load the game background
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1920);
        background.setFitHeight(1080);
        layout.getChildren().add(background);

        // Create a Text element for the countdown
        Text countdownText = new Text();
        countdownText.setStyle("-fx-font-size: 128px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family: 'Aptos Black';");
        countdownText.setStroke(Color.BLACK);
        countdownText.setStrokeWidth(3);
        countdownText.setText("     3");
        countdownText.setX(800);
        countdownText.setY(540);
        layout.getChildren().add(countdownText);

        Scene countdownScene = new Scene(layout, 1920, 1080);
        stage.setScene(countdownScene);

        // Create a timeline for the countdown
        Timeline countdownTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> countdownText.setText("     2")),
            new KeyFrame(Duration.seconds(2), e -> countdownText.setText("     1")),
            new KeyFrame(Duration.seconds(3), e -> countdownText.setText("READY?!")),
            new KeyFrame(Duration.seconds(4), e -> countdownText.setText("FIGHT!!")),
            new KeyFrame(Duration.seconds(5), e -> {
                // Start the actual game scene after the countdown
                showGameScene();
            })
        );
        countdownTimeline.setCycleCount(1);
        countdownTimeline.play();
    }


}
