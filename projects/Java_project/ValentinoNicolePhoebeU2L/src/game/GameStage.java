package game;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.SceneManager;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameStage {
    private final Stage stage;
    private Scene scene;
    private Pane root;

    // Game elements
    private ImageView player1Sprite;
    private ImageView player2Sprite;
    private Text player1HealthText;
    private Text player2HealthText;

    // Health bars
    private Rectangle player1HealthBar;
    private Rectangle player2HealthBar;

    // Screen dimensions for dynamic sizing
    private double screenWidth;
    private double screenHeight;

    private SceneManager sceneManager;

    public GameStage(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;

        // Get screen dimensions for dynamic resizing
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.screenWidth = screenBounds.getWidth();
        this.screenHeight = screenBounds.getHeight();
        new ArrayList<>();
        new ArrayList<>();
    }

    public void initialize() {
        // Create the root container and set up the scene
        root = new Pane();
        scene = new Scene(root, screenWidth, screenHeight);

        // Apply the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.png"));
        ImageView background = new ImageView(backgroundImage);

        // Set the background image
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);

        // This is so the background will be behind everything else
        root.getChildren().add(background);

        // Initialize sprites (player 1 and player 2)
        player1Sprite = createSprite("/images/char1.png", 50, screenHeight - 300 - 50);
        player2Sprite = createSprite("/images/char2.png", screenWidth - 300 - 50, screenHeight - 300 - 50);
        root.getChildren().addAll(player1Sprite, player2Sprite);

        // Add HUD elements (health display)
        setupHUD();

        // Start the game loop (timer)
        GameTimer gameTimer = new GameTimer(scene, player1Sprite, player2Sprite, player1HealthBar, player2HealthBar, root, sceneManager, stage);
        gameTimer.start();

        // Set and show the stage in fullscreen mode
        stage.setScene(scene);
        stage.setTitle("1v1 Shooting Game");
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();
    }

    // Helper method to create sprites
    private ImageView createSprite(String imagePath, double x, double y) {
        Image spriteImage = new Image(getClass().getResourceAsStream(imagePath));
        ImageView sprite = new ImageView(spriteImage);
        sprite.setX(x);
        sprite.setY(y);
        sprite.setFitWidth(300);
        sprite.setFitHeight(300);
        return sprite;
    }

    private void setupHUD() {
        // Health bar dimensions
        double healthBarWidth = 500;
        double healthBarHeight = 40;

        double borderThickness = 8; // Thickness of the black border

        // Create border rectangles for each player's health bar
        Rectangle player1HealthBorder = new Rectangle(healthBarWidth + 2 * borderThickness, healthBarHeight + 2 * borderThickness);
        player1HealthBorder.setFill(Color.BLACK);
        player1HealthBorder.setX(10);
        player1HealthBorder.setY(10);

        Rectangle player2HealthBorder = new Rectangle(healthBarWidth + 2 * borderThickness, healthBarHeight + 2 * borderThickness);
        player2HealthBorder.setFill(Color.BLACK);
        player2HealthBorder.setX(screenWidth - healthBarWidth - 10 - 2 * borderThickness);
        player2HealthBorder.setY(10);

        // Create inner health bars for each player
        player1HealthBar = new Rectangle(healthBarWidth, healthBarHeight, Color.GREEN);
        player1HealthBar.setX(10 + borderThickness);
        player1HealthBar.setY(10 + borderThickness);

        player2HealthBar = new Rectangle(healthBarWidth, healthBarHeight, Color.GREEN);
        player2HealthBar.setX(1395 + borderThickness);
        player2HealthBar.setY(10 + borderThickness);

        // Health labels
        player1HealthText = new Text(15, 95, "Player 1 Health");
        player1HealthText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family:'Aptos Black';");
        player1HealthText.setStroke(Color.BLACK);
        player1HealthText.setStrokeWidth(2);

        player2HealthText = new Text(1675, 95, "Player 2 Health");
        player2HealthText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family:'Aptos Black';");
        player2HealthText.setStroke(Color.BLACK);
        player2HealthText.setStrokeWidth(2);

        // Add borders, health bars, and labels to the root
        root.getChildren().addAll(player1HealthBorder, player1HealthBar, player1HealthText,
                                  player2HealthBorder, player2HealthBar, player2HealthText);
    }


    public void updateHealthBars(int player1Health, int player2Health) {
        double maxHealth = 100.0;

        // Scale the width of the health bars based on current health
        player1HealthBar.setWidth((player1Health / maxHealth) * 300);
        player2HealthBar.setWidth((player2Health / maxHealth) * 300);

        // Change color based on health percentage
        player1HealthBar.setFill(player1Health > 30 ? Color.GREEN : Color.RED);
        player2HealthBar.setFill(player2Health > 30 ? Color.GREEN : Color.RED);
    }

}
