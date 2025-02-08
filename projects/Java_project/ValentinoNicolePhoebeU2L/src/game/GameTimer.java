package game;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.SceneManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameTimer extends AnimationTimer {

    private final Scene scene;
    private final ImageView player1Sprite;
    private final ImageView player2Sprite;

    private final Rectangle player1HealthBar;
    private final Rectangle player2HealthBar;

    private final List<Bullet> player1Bullets = new ArrayList<>();
    private final List<Bullet> player2Bullets = new ArrayList<>();

    private final Pane root;
    private final Stage stage;

    // Timer variable
    private long startTime; 							// For tracking elapsed time
    private final long timeLimit = 60 * 1_000_000_000L; // 60s in nanoseconds
    private Text timerText;

    // Power Ups variable
    private long player1LastUsedTime = 0;
    private long player2LastUsedTime = 0;
    private static final long COOLDOWN_TIME = 5000;  // Cooldown time

    // Health variables
    private int player1Health = 1000;
    private int player2Health = 1000;

    // Speed control variables
    private double normalSpeed = 5.0;
    private double dashSpeed = 10.0;
    private double focusSpeed = 2.5;
    private double currentSpeed = normalSpeed;

    // Set to track currently pressed keys
    private final Set<KeyCode> activeKeys = new HashSet<>();

    public GameTimer(Scene scene, ImageView player1Sprite, ImageView player2Sprite,
            Rectangle player1HealthBar, Rectangle player2HealthBar, Pane root,
            SceneManager sceneManager, Stage stage) {
		this.scene = scene;
		this.player1Sprite = player1Sprite;
		this.player2Sprite = player2Sprite;
		this.player1HealthBar = player1HealthBar;
		this.player2HealthBar = player2HealthBar;
		this.root = root;
		this.stage = stage;

		handleKeyPressEvent();
		setupTimer();
    }

    // Handles the user key pressed
    private void handleKeyPressEvent() {
        // Add key to the set when pressed
    	scene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());

            // Activate Dash Mode
            if (e.getCode() == KeyCode.SHIFT) {
                currentSpeed = dashSpeed;
            }
            // Activate Focus Mode
            else if (e.getCode() == KeyCode.CONTROL) {
                currentSpeed = focusSpeed;
            }
        });

        // Deactivate Dash and Focus modes when keys are released
        scene.setOnKeyReleased(e -> {
            activeKeys.remove(e.getCode());

            // Reset to normal speed once released
            if (e.getCode() == KeyCode.SHIFT || e.getCode() == KeyCode.CONTROL) {
                currentSpeed = normalSpeed;
            }
        });
    }

    // Sets up the sprite movements
    private void moveSprite(ImageView sprite, KeyCode up, KeyCode down, KeyCode left, KeyCode right) {
        double moveAmount = currentSpeed;

        // Calculate boundaries based on scene dimensions and sprite size
        double spriteWidth = sprite.getFitWidth();
        double spriteHeight = sprite.getFitHeight();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        // Move up and down within bounds
        if (activeKeys.contains(up) && sprite.getY() - moveAmount >= 0) {
            sprite.setY(sprite.getY() - moveAmount);
        }
        if (activeKeys.contains(down) && sprite.getY() + moveAmount + spriteHeight <= sceneHeight) {
            sprite.setY(sprite.getY() + moveAmount);
        }

        // Move left and right within bounds
        if (activeKeys.contains(left) && sprite.getX() - moveAmount >= 0) {
            sprite.setX(sprite.getX() - moveAmount);
        }
        if (activeKeys.contains(right) && sprite.getX() + moveAmount + spriteWidth <= sceneWidth) {
            sprite.setX(sprite.getX() + moveAmount);
        }

    }

    // Timer text format
    private void setupTimer() {
        timerText = new Text(950, 50, "Time: 60");
        timerText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: white; -fx-font-family:'Aptos Black';");
        timerText.setStroke(Color.BLACK);
        timerText.setStrokeWidth(2);
        root.getChildren().add(timerText);
    }

    // Update the health bar per player health
    public void updateHealthBars() {
    	double maxHealth = 1150.0;

    	double healthBarWidth = scene.getWidth() * 0.3;

    	// Checks the width scale
        double player1HealthBarWidth = (player1Health / maxHealth) * healthBarWidth;
        double player2HealthBarWidth = (player2Health / maxHealth) * healthBarWidth;

        // Checks that the width wont exceed max health width
        player1HealthBar.setWidth(Math.max(0, player1HealthBarWidth));
        player2HealthBar.setWidth(Math.max(0, player2HealthBarWidth));

        // Change color based on health percentage
        player1HealthBar.setFill(player1Health > 30 ? Color.GREEN : Color.RED);
        player2HealthBar.setFill(player2Health > 30 ? Color.GREEN : Color.RED);
    }

    // Shooting mechanics
    public void shootBullet(ImageView player, List<Bullet> bullets, int direction) {
    	// For player 1, use pinkball image; for player 2, use blueball image
        String bulletImagePath = (bullets == player1Bullets) ? "/images/pinkball.png" : "/images/blueball.png";

        // Calculate the offset for the bullet's initial position
        double bulletX;
        double bulletY;

        // Position of the bullet on the player
        if (bullets == player1Bullets) {
            bulletX = player.getX() + player.getFitWidth() / 2;
            bulletY = player.getY() + 40;
        } else {
            bulletX = player.getX() + player.getFitWidth() / 3 ;
            bulletY = player.getY() + 60;
        }

        // Create a new bullet based on the player's position and the bullet image
        Bullet bullet = new Bullet(bulletX, bulletY, 10, bulletImagePath, direction);
        bullets.add(bullet);

        // Add the bullet to the scene
        root.getChildren().add(bullet.getBulletImageView());
    }

    public void moveBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
    }

    // Removes bullet once it has reached the bounce
    public void checkBulletOutOfBounds(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (bullet.getBulletImageView().getY() < 0 || bullet.getBulletImageView().getY() > scene.getHeight()) {
                bullets.remove(bullet);
                root.getChildren().remove(bullet.getBulletImageView());
                break;
            }
        }
    }

    // Bullet collision mechanics
    public void checkBulletCollisions(List<Bullet> bullets, ImageView target, boolean isPlayer2) {
        for (Bullet bullet : bullets) {
            if (bullet.getBulletImageView().getBoundsInParent().intersects(target.getBoundsInParent())) {
                bullets.remove(bullet);
                root.getChildren().remove(bullet.getBulletImageView());  // Remove bullet from the scene
                System.out.println("Bullet hit the target!");

                // Reduce health of the target player
                if (isPlayer2) {
                    player2Health -= 5;  // Reduce player 1's health
                } else {
                    player1Health -= 5;  // Reduce player 2's health
                }
                updateHealthBars();  	// Update health bar after hit
                break;
            }
        }
    }

    // Handles the user key pressed specifically for special attack
    private void handleSpecialAttack() {
        // Player 1 special attack - Fire Ring
        if (activeKeys.contains(KeyCode.E)) {
            summonFireRingAroundPlayer2();
        }

        // Player 2 special attack - Ice Rain
        if (activeKeys.contains(KeyCode.L)) {
            summonIceRainAroundPlayer1();
        }
    }

    // Power up for player 1
    private void summonFireRingAroundPlayer2() {
        // Check if 5 seconds have passed since the last use
        long currentTime = System.currentTimeMillis();
        if (currentTime - player2LastUsedTime < COOLDOWN_TIME) {
            return;
        }

        // Update the last used time to the current time
        player2LastUsedTime = currentTime;

        // Load the fire image
        String fireImagePath = "/images/fire_ring.png";
        javafx.scene.image.Image fireImage = new javafx.scene.image.Image(getClass().getResourceAsStream(fireImagePath));

        // Create an ImageView for the fire ring and position it around Player 2
        ImageView fireRing = new ImageView(fireImage);
        fireRing.setX(player2Sprite.getX() - 20);
        fireRing.setY(player2Sprite.getY() - 20);
        fireRing.setFitWidth(player2Sprite.getFitWidth() + 40);
        fireRing.setFitHeight(player2Sprite.getFitHeight() + 40);
        fireRing.setOpacity(0.5);
        root.getChildren().add(fireRing);

        // Reduce Player 2's health by 10
        player2Health -= 10;
        updateHealthBars();

        // Remove fire ring after a short time
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.5));
        pause.setOnFinished(event -> root.getChildren().remove(fireRing));
        pause.play();
    }

    // Powerup for player 2
    private void summonIceRainAroundPlayer1() {
        // Check if 5 seconds have passed since the last use
        long currentTime = System.currentTimeMillis();
        if (currentTime - player1LastUsedTime < COOLDOWN_TIME) {
            return;
        }

        // Update the last used time to the current time
        player1LastUsedTime = currentTime;

        // Create a simple ice rain and reduce Player 1's health by 10
        for (int i = 0; i < 5; i++) {
            Rectangle raindrop = new Rectangle(player1Sprite.getX() + Math.random() * player1Sprite.getFitWidth(), player1Sprite.getY() - 20, 5, 15);
            raindrop.setFill(Color.CYAN);
            root.getChildren().add(raindrop);

            // Animate the raindrops falling
            javafx.animation.TranslateTransition fallAnimation = new javafx.animation.TranslateTransition(javafx.util.Duration.seconds(1), raindrop);
            fallAnimation.setToY(scene.getHeight());
            fallAnimation.setOnFinished(event -> root.getChildren().remove(raindrop));
            fallAnimation.play();
        }

        // Reduce Player 1's health by 10
        player1Health -= 10;
        updateHealthBars();
    }

    @Override
    public void start() {
        super.start();
        startTime = System.nanoTime(); // Record the start time
    }


    @Override
    public void handle(long now) {
    	// Calculate elapsed time and remaining time
        long elapsedTime = now - startTime;
        long remainingTime = timeLimit - elapsedTime;

        // Update timer display
        long seconds = Math.max(remainingTime / 1_000_000_000L, 0); // Prevent negative time
        timerText.setText("Time: " + seconds);

        // Call the special attack handling method
        handleSpecialAttack();

        // Move players based on keys
        moveSprite(player1Sprite, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D);
        moveSprite(player2Sprite, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);

        // Handle shooting
        if (activeKeys.contains(KeyCode.SPACE)) {
            shootBullet(player1Sprite, player1Bullets, 1);
        }
        if (activeKeys.contains(KeyCode.ENTER)) {
            shootBullet(player2Sprite, player2Bullets, -1);
        }

        // Move bullets
        moveBullets(player1Bullets);
        moveBullets(player2Bullets);

        // Check for bullet collisions
        checkBulletCollisions(player1Bullets, player2Sprite, true);
        checkBulletCollisions(player2Bullets, player1Sprite, false);

        // Stop the game if either player's health reaches 0
        if (player1Health <= 0 || player2Health <= 0) {
            stop();
            System.out.println("Game Over!");

            String winner = player1Health <= 0 ? "Player 2" : "Player 1";
            System.out.println(winner + " wins!");

            // Show winning scene
            SceneManager sceneManager = new SceneManager(stage);
            sceneManager.showWinningScene(winner);
        }

        // Stop the game when time runs out
        if (remainingTime <= 0) {
            stop();
            System.out.println("Time's up!");

            String winner = determineWinnerByHealth();
            SceneManager sceneManager = new SceneManager(stage);
            sceneManager.showWinningScene(winner);
        }
    }

    // Generate a winner depending on the health of the player
    private String determineWinnerByHealth() {
        if (player1Health > player2Health) {
            return "Player 1";
        } else if (player2Health > player1Health) {
            return "Player 2";
        } else {
            return "It's a tie!";
        }
    }

}
